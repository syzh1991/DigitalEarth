package kml3d.util;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwind.render.ScreenAnnotation;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.OrbitView;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class Util {
	public static void scaleComponents(Container c) {
		for (Component comp : c.getComponents()) {
			if (comp instanceof JComponent) {
				JComponent jcomp = (JComponent) comp;
				jcomp.putClientProperty("JComponent.sizeVariant", "large");
			}
			if (comp instanceof Container) {
				Container cont = (Container) comp;
				scaleComponents(cont);
			}
		}
	}

	private final static String pathIcon = "/images/";

	public static ImageIcon getMyIcon(String iconName) {
		ImageIcon icon = null;
		URL imageURL = null;
		imageURL = Util.class.getResource(pathIcon + iconName);
		/*
		 * imageURL = new URL(this.getCodeBase()+pathIcon + iconName + ".gif");
		 */
		if (imageURL != null)
			icon = new ImageIcon(imageURL);
		return icon;
	}
	
	public static Image getImage(String imageName) {
		return getMyIcon(imageName).getImage();
	}

	public static void insertBeforeCompass(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition, layer);
	}

	public static void insertBeforePlacenames(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the placenames.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof PlaceNameLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition, layer);
	}

	public static void insertAfterPlacenames(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just after the placenames.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof PlaceNameLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition + 1, layer);
	}

	public static void insertAfterLayerName(WorldWindow wwd, Layer layer,
			String targetName) {
		// Insert the layer into the layer list just before the target layer.
		int targetPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l.getName().indexOf(targetName) != -1) {
				targetPosition = layers.indexOf(l);
				break;
			}
		}
		layers.add(targetPosition + 1, layer);
	}

	public static void insertBeforeLayerName(WorldWindow wwd, Layer layer,
			String targetName) {
		// Insert the layer into the layer list just before the target layer.
		int targetPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l.getName().indexOf(targetName) != -1) {
				targetPosition = layers.indexOf(l);
				break;
			}
		}
		layers.add(targetPosition, layer);
	}

	public static Layer getLayerByName(WorldWindow wwd, String targetName) {
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l.getName().indexOf(targetName) != -1) {
				return l;
			}
		}
		return null;
	}

	public static Layer getLayerByClassName(WorldWindow wwd, String classname) {
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l.getClass().getName().equals(classname)) {
				return l;
			}
		}
		return null;
	}
	
	public static void replaceLayer(WorldWindow wwd, Layer oldLayer, Layer newLayer) {
		LayerList layers = wwd.getModel().getLayers();
		int index = layers.indexOf(oldLayer);
		if(index != -1) {
			layers.set(index, newLayer);
		}		
	}

	public static void removeLayer(WorldWindow wwd, Layer layer) {
		LayerList layers = wwd.getModel().getLayers();
		layers.remove(layer);
	}

	public static Cursor getMyCursor(String cursorName, Point hotSpot) {
		ImageIcon imageIcon = getMyIcon(cursorName);
		if (imageIcon == null) {
			Logging.logger().severe("cursor not found");
			return null;
		}
		try {
			return Toolkit.getDefaultToolkit().createCustomCursor(
					imageIcon.getImage(), hotSpot, cursorName);
		} catch(HeadlessException e) {
			return new Cursor(0);
		}
	}

	private static final String JRE_PREFIX = "deployment.javapi.jre.";

	public static void updateDeploymentProperties(String prop) {
		String userHome = System.getProperty("user.home");

		try {
			// Must update deployment.properties
			File propsDir = new File(getDeploymentPropsDir());
			if (!propsDir.exists()) {
				// Don't know what's going on or how to set this permanently
				return;
			}

			File propsFile = new File(propsDir, "deployment.properties");
			if (!propsFile.exists()) {
				// Don't know what's going on or how to set this permanently
				return;
			}

			Properties props = new Properties();
			InputStream input = new BufferedInputStream(new FileInputStream(
					propsFile));
			props.load(input);
			input.close();
			// Search through the keys looking for JRE versions
			Set/* <String> */jreVersions = new HashSet/* <String> */();
			for (Iterator/* <String> */iter = props.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith(JRE_PREFIX)) {
					int idx = key.lastIndexOf(".");
					if (idx >= 0 && idx > JRE_PREFIX.length()) {
						String jreVersion = key.substring(JRE_PREFIX.length(),
								idx);
						jreVersions.add(jreVersion);
					}
				}
			}

			// Make sure the currently-running JRE shows up in this set to
			// avoid repeated displays of the dialog. It might not in some
			// upgrade scenarios where there was a pre-existing
			// deployment.properties and the new Java Control Panel hasn't
			// been run yet.
			jreVersions.add(System.getProperty("java.version"));

			// OK, now that we know all JRE versions covered by the
			// deployment.properties, check out the args for each and update
			// them
			for (Iterator/* <String> */iter = jreVersions.iterator(); iter
					.hasNext();) {
				String version = (String) iter.next();
				String argKey = JRE_PREFIX + version + ".args";
				String argVal = props.getProperty(argKey);
				if (argVal == null) {
					argVal = prop;
				} else if (argVal.indexOf(prop) < 0) {
					argVal = argVal + " " + prop;
				}
				props.setProperty(argKey, argVal);
			}

			OutputStream output = new BufferedOutputStream(
					new FileOutputStream(propsFile));
			props.store(output, null);
			output.close();

			/*
			 * if (!getBooleanParameter("noddraw.check.silent")) { // Tell user
			 * we're done JOptionPane.showMessageDialog(null, "For best
			 * robustness, we recommend you now exit and\n" + "restart your web
			 * browser. (Note: clicking \"OK\" will\n" + "not exit your
			 * browser.)", "Browser Restart Recommended",
			 * JOptionPane.INFORMATION_MESSAGE); }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getDeploymentPropsDir() {
		final String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer result = new StringBuffer();

		result.append(System.getProperty("user.home"));
		if (osName.startsWith("windows")) {
			if (osName.indexOf("vista") != -1) {
				result.append(File.separator).append("AppData").append(
						File.separator).append("LocalLow");
			} else {
				result.append(File.separator).append("Application Data");
			}
			result.append(File.separator).append("Sun").append(File.separator)
					.append("Java").append(File.separator).append("Deployment");
		} else if (osName.startsWith("mac")) {
			result.append(File.separator).append("Library").append(
					File.separator).append("Caches").append(File.separator)
					.append("Java");
		} else {
			result.append(File.separator).append(".java")
					.append(File.separator).append("deployment");
		}

		return result.toString();
	}

	// FULLSCREEN MODE

	private static GraphicsDevice dev;

	private static Frame wFullscrenn = null;

	private static DisplayMode oldMode;

	public static void setFullscreenMode(final Component comp,
			final Container mainPanel) {

		dev = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();

		oldMode = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDisplayMode();

		int initWidth = oldMode.getWidth();
		int initHeight = oldMode.getHeight();

		Logging.logger().fine("Fulscreen mode");
		Logging.logger().fine("Window Old Width : " + mainPanel.getWidth());
		Logging.logger().fine("Window Old Height : " + mainPanel.getHeight());

		System.out.println("Window New Width : " + initWidth);
		System.out.println("Window New Height : " + initHeight);

		if (wFullscrenn == null) {
			// newMode = ScreenResSelector.showSelectionDialog();
			wFullscrenn = new JFrame();

			/*
			 * wwd.addGLEventListener(new FullscreenWorkaround(initWidth,
			 * initHeight));
			 */
			wFullscrenn.setSize(initWidth, initHeight);
			wFullscrenn.setUndecorated(true);
			//if (dev.isFullScreenSupported()
					//&& !System.getProperty("os.name").startsWith("Windows"))
				//dev.setFullScreenWindow(wFullscrenn);
			wFullscrenn.add(comp);

			addKeyListenerFullscreen(comp, comp, mainPanel);

			wFullscrenn.setVisible(true);
			// WorldWindIcare.this.validate();
			wFullscrenn.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					wFullscrenn.dispose();
				}
			});

		} else {
			Logging.logger().severe("NOTE: full-screen mode not supported; running in window instead");
		}
	}

	private static void addKeyListenerFullscreen(final Component fcomp,
			final Component comp, final Container mainPanel) {
		comp.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// dev.setDisplayMode(oldMode);
					// dev.setFullScreenWindow(null);
					wFullscrenn.dispose();

					mainPanel.add(fcomp);

					wFullscrenn = null;
					comp.removeKeyListener(this);
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

		});

		if (comp instanceof Container) {
			Container cont = (Container) comp;
			for (Component co : cont.getComponents()) {
				addKeyListenerFullscreen(fcomp, co, mainPanel);
			}
		}
	}

	private static Dimension initialDimension;
	private static Point initialPoint;

	public static void setFullscreenMode(final Frame f) {

		dev = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();

		oldMode = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDisplayMode();

		int initWidth = oldMode.getWidth();
		int initHeight = oldMode.getHeight();

		Logging.logger().fine("Fulscreen mode");
		Logging.logger().fine("Window Old Width : " + f.getWidth());
		Logging.logger().fine("Window Old Height : " + f.getHeight());

		Logging.logger().fine("Window Width : " + initWidth);
		Logging.logger().fine("Window Height : " + initHeight);

		// newMode = ScreenResSelector.showSelectionDialog();

		/*
		 * wwd.addGLEventListener(new FullscreenWorkaround(initWidth,
		 * initHeight));
		 */
		initialDimension = f.getSize();
		initialPoint = f.getLocation();
		f.setResizable(false);
//		if (dev.isFullScreenSupported()) {
//			System.out.println("isFullScreenSupported");
//			dev.setFullScreenWindow(f);
//		} else {
			f.setLocation(0, 0);

			Dimension fullDim = new Dimension(initWidth, initHeight);
			f.setSize(fullDim);
			// f.setUndecorated(true);
			//f.setAlwaysOnTop(true);
			Logging.logger().fine("isDisplayable : " + f.isDisplayable());
//		}

		//addKeyListenerFullscreen(f, f);
		// WorldWindIcare.this.validate();

	}

	public static void addKeyListenerFullscreen(final Frame f,
			final Component comp) {
		comp.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (dev.isFullScreenSupported()) {
						dev.setFullScreenWindow(null);
					} else {
						// dev.setDisplayMode(oldMode);
						// dev.setFullScreenWindow(null);
						dev.setFullScreenWindow(null);
						f.setSize(initialDimension);
						f.setLocation(initialPoint);
						f.setResizable(true);
						f.setAlwaysOnTop(false);
						// mainPanel.add(fcomp);
						comp.removeKeyListener(this);
					}
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

		});

		if (comp instanceof Container) {
			Container cont = (Container) comp;
			for (Component co : cont.getComponents()) {
				addKeyListenerFullscreen(f, co);
			}
		}
	}

	/**
	 * Move the current view position to the specified sector
	 * 
	 */
	public static void zoomOnSector(WorldWindow wwd, Sector sector) {
		Globe globe = wwd.getModel().getGlobe();

		// TODO : improve this computation when the screen is not 4/3
		Vec4 center_vec4 = sector.computeCenterPoint(globe,1.0);

		Vec4[] points = sector.computeCornerPoints(globe,1.0);
		double distance = center_vec4.distanceTo3(points[0]);
		double altitude = distance
				/ Math.tan((wwd.getView().getFieldOfView().radians / 2));

		Util.gotoLatLon(wwd, sector.getCentroid().getLatitude().degrees, sector
				.getCentroid().getLongitude().degrees, altitude, 0, 0);
	}

	/**
	 * Move the current view position
	 * 
	 * @param lat
	 *            the target latitude in decimal degrees
	 * @param lon
	 *            the target longitude in decimal degrees
	 */
	public static void gotoLatLon(WorldWindow wwd, double lat, double lon) {
		gotoLatLon(wwd, lat, lon, 30000, 0, 0);
	}

	/**
	 * Move the current view position, zoom, heading and pitch
	 * 
	 * @param lat
	 *            the target latitude in decimal degrees
	 * @param lon
	 *            the target longitude in decimal degrees
	 * @param zoom
	 *            the target eye distance in meters
	 * @param heading
	 *            the target heading in decimal degrees
	 * @param pitch
	 *            the target pitch in decimal degrees
	 */
	public static void gotoLatLon(WorldWindow wwd, double lat, double lon,
			double zoom, double heading, double pitch) {
		OrbitView view = (OrbitView) wwd.getView();
		Globe globe = wwd.getModel().getGlobe();
		// view.setLatLon(new LatLon(Angle.fromDegrees(lat),
		// Angle.fromDegrees(lon)));
		// view.firePropertyChange(AVKey.VIEW, null, view);
		if (!Double.isNaN(lat) || !Double.isNaN(lon) || !Double.isNaN(zoom)) {
			lat = Double.isNaN(lat) ? view.getCenterPosition().getLatitude().degrees
					: lat;
			lon = Double.isNaN(lon) ? view.getCenterPosition().getLongitude().degrees
					: lon;
			zoom = Double.isNaN(zoom) ? view.getZoom() : zoom;
			heading = Double.isNaN(heading) ? view.getHeading().degrees
					: heading;
			pitch = Double.isNaN(pitch) ? view.getPitch().degrees : pitch;
			((BasicOrbitView)view).addPanToAnimator(Position.fromDegrees(lat,
							lon, 0), Angle.fromDegrees(heading), Angle
							.fromDegrees(pitch), zoom, false);
		}
	}

	public static List<Double> getDoubleValues(String stringValues) {
		String[] tokens = stringValues.trim().split("[ ,\n]");
		if (tokens.length < 1)
			return null;

		ArrayList<Double> arl = new ArrayList<Double>();
		for (String s : tokens) {
			if (s == null || s.length() < 1)
				continue;

			double d;
			try {
				d = Double.parseDouble(s);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// Logging.logger().log(Level.SEVERE,
				// "GeoRSS.NumberFormatException", s);
				continue;
			}

			arl.add(d);
		}

		return arl;
	}

	public static ScreenAnnotation makeScreenAnnotationFromXML(String xmlUrl) {
		ScreenAnnotation result = null;
		if (xmlUrl.startsWith("http://")) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(
						xmlUrl).openConnection();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String xmlState = "";
				// while (String line = reader.readLine()) {

				// }
				result = new ScreenAnnotation("", new Point(0, 0));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean isLayersEquals(Layer layer1, Layer layer2) {
		boolean equals = true;
		equals &= layer1.getName().equals(layer2.getName());
		return equals;
	}	
	
	public static String dom2String(Node node) {
	    try
	    {
	      // Set up the output transformer
	      TransformerFactory transfac = TransformerFactory.newInstance();
	      Transformer trans = transfac.newTransformer();
	      trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	      trans.setOutputProperty(OutputKeys.INDENT, "yes");

	      // Print the DOM node

	      StringWriter sw = new StringWriter();
	      StreamResult result = new StreamResult(sw);
	      DOMSource source = new DOMSource(node);
	      trans.transform(source, result);
	      String xmlString = sw.toString();

	      return xmlString;
	    }
	    catch (TransformerException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	}
	
	
	public static String getExtension(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if (i > 0 && i < s.length() - 1)
        {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    } // getExtension

	
}
