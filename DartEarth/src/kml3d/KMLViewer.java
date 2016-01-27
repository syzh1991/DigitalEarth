/*
Copyright (C) 2001, 2010 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
 */

package kml3d;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.KMLAbstractFeature;
import gov.nasa.worldwind.ogc.kml.KMLRoot;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.util.layertree.KMLLayerTreeNode;
import gov.nasa.worldwind.util.layertree.KMLNetworkLinkTreeNode;
import gov.nasa.worldwind.util.layertree.LayerTree;
import gov.nasa.worldwindx.examples.kml.KMLApplicationController;
import gov.nasa.worldwindx.examples.util.BalloonController;
import gov.nasa.worldwindx.examples.util.HotSpotController;

//import gov.nasa.worldwind.examples.ApplicationTemplate;
//import gov.nasa.worldwind.examples.kml.KMLApplicationController;
//import gov.nasa.worldwind.examples.util.BalloonController;
//import gov.nasa.worldwind.examples.util.HotSpotController;
//import gov.nasa.worldwind.examples.util.layertree.KMLLayerTreeNode;
//import gov.nasa.worldwind.examples.util.layertree.KMLNetworkLinkTreeNode;
//import gov.nasa.worldwind.examples.util.layertree.LayerTree;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import kml3d.event.ViewMoveEvent;
import kml3d.event.ViewMoveListener;
import kml3d.objects.Bonzai3DModel;
import kml3d.ogc.kml.model.CustomKMLRoot;

/**
 * KML Viewer. Currently under initial development.
 * 
 * @author tag
 * @version $Id: KMLViewer.java 14917 2011-03-09 17:40:49Z dcollins $
 */
public class KMLViewer extends ApplicationTemplate {
	public static class AppFrame extends ApplicationTemplate.AppFrame {
		protected LayerTree layerTree;
		protected RenderableLayer hiddenLayer;

		protected List<RenderableLayer> layerlist;

		protected HotSpotController hotSpotController;
		protected KMLApplicationController kmlAppController;
		protected BalloonController balloonController;

		protected ViewMoveListener viewMovelistener = null;
		protected Long preTime = null;
		
	    public void setViewMoveListener(ViewMoveListener listener)
	    {
	        this.viewMovelistener = listener;
	    }

	    public void removePositionListener()
	    {
	    	this.viewMovelistener = null;
	    }

	    protected void callPositionListeners(final ViewMoveEvent event)
	    {
	        EventQueue.invokeLater(new Runnable()
	        {
	            public void run()
	            {
	            	viewMovelistener.moved(event);
	            }
	        });
	    }

		public AppFrame() {
			super(true, false, false); // Don't include the layer panel; we're
										// using the on-screen layer tree.

			Bonzai3DModel.init(getWwd());
			// Add the on-screen layer tree, refreshing model with the
			// WorldWindow's current layer list. We
			// intentionally refresh the tree's model before adding the layer
			// that contains the tree itself. This
			// prevents the tree's layer from being displayed in the tree
			// itself.
			this.layerTree = new LayerTree();
			this.layerTree.getModel().refresh(
					this.getWwd().getModel().getLayers());

			// to comment
			layerlist = new ArrayList<RenderableLayer>();

			// Set up a layer to display the on-screen layer tree in the
			// WorldWindow. This layer is not displayed in
			// the layer tree's model. Doing so would enable the user to hide
			// the layer tree display with no way of
			// bringing it back.
			this.hiddenLayer = new RenderableLayer();

			// this.hiddenLayer.setMinActiveAltitude(100d);
			// this.hiddenLayer.setMaxActiveAltitude(200d);

			this.hiddenLayer.addRenderable(this.layerTree);
			this.getWwd().getModel().getLayers().add(this.hiddenLayer);

			// Add a controller to handle input events on the layer selector and
			// on browser balloons.
			this.hotSpotController = new HotSpotController(this.getWwd());

			// Add a controller to handle common KML application events.
			this.kmlAppController = new KMLApplicationController(this.getWwd());

			// Add a controller to display balloons when placemarks are clicked.
			// We override the method addDocumentLayer
			// so that loading a KML document by clicking a KML balloon link
			// displays an entry in the on-screen layer
			// tree.
			this.balloonController = new BalloonController(this.getWwd()) {
				@Override
				protected void addDocumentLayer(KMLRoot document) {
					addKMLLayer(document);
				}
			};

			// Give the KML app controller a reference to the BalloonController
			// so that the app controller can open
			// KML feature balloons when feature's are selected in the on-screen
			// layer tree.
			this.kmlAppController.setBalloonController(balloonController);

			// Size the World Window to take up the space typically used by the
			// layer panel.
			Dimension size = new Dimension(1000, 600);
			this.setPreferredSize(size);
			this.pack();
			WWUtil.alignComponent(null, this, AVKey.CENTER);

			makeMenu(this);
		}

		/**
		 * Adds the specified <code>kmlRoot</code> to this app frame's
		 * <code>WorldWindow</code> as a new <code>Layer</code>, and adds a new
		 * <code>KMLLayerTreeNode</code> for the <code>kmlRoot</code> to this
		 * app frame's on-screen layer tree.
		 * <p/>
		 * This expects the <code>kmlRoot</code>'s
		 * <code>AVKey.DISPLAY_NAME</code> field to contain a display name
		 * suitable for use as a layer name.
		 * 
		 * @param kmlRoot
		 *            the KMLRoot to add a new layer for.
		 */
		protected void addKMLLayer(KMLRoot kmlRoot) {
			// Create a KMLController to adapt the KMLRoot to the World Wind
			// renderable interface.
			KMLController kmlController = new KMLController(kmlRoot);

			// Adds a new layer containing the KMLRoot to the end of the
			// WorldWindow's layer list. This
			// retrieve's the layer name from the KMLRoot's DISPLAY_NAME field.
			RenderableLayer layer = new RenderableLayer();
			layerlist.add(layer);

			// layer.setMinActiveAltitude(2000d);
			layer.setMaxActiveAltitude(4000d);

			layer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
			layer.addRenderable(kmlController);
			this.getWwd().getModel().getLayers().add(layer);

			// Adds a new layer tree node for the KMLRoot to the on-screen layer
			// tree, and makes the new node visible
			// in the tree. This also expands any tree paths that represent open
			// KML containers or open KML network
			// links.
			KMLLayerTreeNode layerNode = new KMLLayerTreeNode(layer, kmlRoot);
			this.layerTree.getModel().addLayer(layerNode);
			this.layerTree.makeVisible(layerNode.getPath());
			layerNode.expandOpenContainers(this.layerTree);

			// Listens to refresh property change events from KML network link
			// nodes. Upon receiving such an event this
			// expands any tree paths that represent open KML containers. When a
			// KML network link refreshes, its tree
			// node replaces its children with new nodes created form the
			// refreshed content, then sends a refresh
			// property change event through the layer tree. By expanding open
			// containers after a network link refresh,
			// we ensure that the network link tree view appearance is
			// consistent with the KML specification.
			layerNode.addPropertyChangeListener(
					AVKey.RETRIEVAL_STATE_SUCCESSFUL,
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent event) {
							if (event.getSource() instanceof KMLNetworkLinkTreeNode)
								((KMLNetworkLinkTreeNode) event.getSource())
										.expandOpenContainers(layerTree);
						}
					});
		}

		protected void removeKMLLayer() {
			// layerlist.
			for (RenderableLayer layer : layerlist) {
				this.getWwd().getModel().getLayers().remove(layer);
			}
			// this.layerTree.getModel().refresh(this.getWwd().getModel().getLayers());
		}
	}

	/**
	 * A <code>Thread</code> that loads a KML file and displays it in an
	 * <code>AppFrame</code>.
	 */
	public static class WorkerThread extends Thread {
		/**
		 * Indicates the source of the KML file loaded by this thread.
		 * Initialized during construction.
		 */
		protected Object kmlSource;
		/**
		 * Indicates the <code>AppFrame</code> the KML file content is displayed
		 * in. Initialized during construction.
		 */
		protected AppFrame appFrame;

		/**
		 * Creates a new worker thread from a specified <code>kmlSource</code>
		 * and <code>appFrame</code>.
		 * 
		 * @param kmlSource
		 *            the source of the KML file to load. May be a {@link File},
		 *            a {@link URL}, or an {@link java.io.InputStream}, or a
		 *            {@link String} identifying a file path or URL.
		 * @param appFrame
		 *            the <code>AppFrame</code> in which to display the KML
		 *            source.
		 */
		public WorkerThread(Object kmlSource, AppFrame appFrame) {
			this.kmlSource = kmlSource;
			this.appFrame = appFrame;
		}

		/**
		 * Loads this worker thread's KML source into a new
		 * <code>{@link gov.nasa.worldwind.ogc.kml.KMLRoot}</code>, then adds
		 * the new <code>KMLRoot</code> to this worker thread's
		 * <code>AppFrame</code>. The <code>KMLRoot</code>'s
		 * <code>AVKey.DISPLAY_NAME</code> field contains a display name created
		 * from either the KML source or the KML root feature name.
		 * <p/>
		 * If loading the KML source fails, this prints the exception and its
		 * stack trace to the standard error stream, but otherwise does nothing.
		 */
		public void run() {
			try {
				final KMLRoot kmlRoot = CustomKMLRoot.create(this.kmlSource);
				if (kmlRoot == null) {
					String message = Logging
							.getMessage(
									"generic.UnrecognizedSourceTypeOrUnavailableSource",
									this.kmlSource.toString());
					throw new IllegalArgumentException(message);
				}

				kmlRoot.parse();
				kmlRoot.setField(AVKey.DISPLAY_NAME,
						formName(this.kmlSource, kmlRoot));

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						appFrame.addKMLLayer(kmlRoot);
						// appFrame.removeKMLLayer();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected static String formName(Object kmlSource, KMLRoot kmlRoot) {
		KMLAbstractFeature rootFeature = kmlRoot.getFeature();

		if (rootFeature != null && !WWUtil.isEmpty(rootFeature.getName()))
			return rootFeature.getName();

		if (kmlSource instanceof File)
			return ((File) kmlSource).getName();

		if (kmlSource instanceof URL)
			return ((URL) kmlSource).getPath();

		if (kmlSource instanceof String
				&& WWIO.makeURL((String) kmlSource) != null)
			return WWIO.makeURL((String) kmlSource).getPath();

		return "KML Layer";
	}

	protected static void makeMenu(final AppFrame appFrame) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"KML/KMZ File", "kml", "kmz"));

		JMenuBar menuBar = new JMenuBar();
		appFrame.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem openFileMenuItem = new JMenuItem(new AbstractAction(
				"Open File...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int status = fileChooser.showOpenDialog(appFrame);
					if (status == JFileChooser.APPROVE_OPTION) {
						new WorkerThread(fileChooser.getSelectedFile(),
								appFrame).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		fileMenu.add(openFileMenuItem);

		JMenuItem openURLMenuItem = new JMenuItem(new AbstractAction(
				"Open URL...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					String status = JOptionPane
							.showInputDialog(appFrame, "URL");
					if (!WWUtil.isEmpty(status)) {
						new WorkerThread(status.trim(), appFrame).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		fileMenu.add(openURLMenuItem);
	}

	final static String DEFAULT_FILE = "D:/China_Pavilion_5.kmz";

	// final static String DEFAULT_FILE =
	// "testData/KML/LineStringPlacemark.kml";
	// final static String DEFAULT_FILE =
	// "testData/KML/LinearRingPlacemark.kml";
	// final static String DEFAULT_FILE =
	// "testData/KML/GoogleTutorialExample01.kml";
	// final static String DEFAULT_FILE = "testData/KML/PointPlacemark.kml";
	// final static String DEFAULT_FILE =
	// "testData/KML/MultiGeometryPlacemark.kml";
	// final static String DEFAULT_FILE = "demodata/KML/PolygonPlacemark.kml";
	// final static String DEFAULT_FILE =
	// "http://code.google.com/apis/kml/documentation/KML_Samples.kml";
	// final static String DEFAULT_FILE = "testData/KML/NetworkLinksRemote.kml";

	public static void main(String[] args) {

		// noinspection UnusedDeclaration
		final AppFrame af = (AppFrame) start("World Wind KML Viewer",
				AppFrame.class);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ViewMoveListener viewMoveListener = new ViewMoveListener() {

					@Override
					public void moved(ViewMoveEvent event) {
						Long currentTime = System.currentTimeMillis();
						if (af.preTime == null) {
							final Position currentPosition = af.getWwd()
									.getView().getCurrentEyePosition();
							KmlFactory.loadKmls(af, "D:/RearrageGoogle3D",
									currentPosition);
							af.preTime = currentTime;
						} else if (af.preTime != null
								&& currentTime - af.preTime >= 2000) {
							final Position currentPosition = af.getWwd()
									.getView().getCurrentEyePosition();
							KmlFactory.loadKmls(af, "D:/RearrageGoogle3D",
									currentPosition);
							af.preTime = currentTime;
						}
//						System.out.println("moved...");

					}
				};
				af.getWwd().addViewMoveListener(viewMoveListener);
			}
		});

		// SwingUtilities.invokeLater(new Runnable()
		// {
		// public void run()
		// {
		// // new WorkerThread(DEFAULT_FILE, af).start();
		// // KmlFactory.loadKmls(af, "D:/google3D",
		// af.getWwd().getView().getCurrentEyePosition());
		// }
		// });
	}
}
