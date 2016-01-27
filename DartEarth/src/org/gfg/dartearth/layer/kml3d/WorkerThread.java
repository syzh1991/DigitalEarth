package org.gfg.dartearth.layer.kml3d;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.KMLAbstractFeature;
import gov.nasa.worldwind.ogc.kml.KMLRoot;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwind.util.WWUtil;

import java.io.File;
import java.net.URL;

import javax.swing.SwingUtilities;

import org.gfg.dartearth.core.ApplicationTemplate.AppFrame;

import kml3d.ogc.kml.model.CustomKMLRoot;

/**
 * A <code>Thread</code> that loads a KML file and displays it in an
 * <code>AppFrame</code>.
 */
public class WorkerThread extends Thread {
	/**
	 * Indicates the source of the KML file loaded by this thread. Initialized
	 * during construction.
	 */
	protected Object kmlSource;
	/**
	 * Indicates the <code>AppFrame</code> the KML file content is displayed in.
	 * Initialized during construction.
	 */
	protected AppFrame appFrame;
	
	protected Kml3DLayer layer;
	
	protected String ctrlKey;

	/**
	 * Creates a new worker thread from a specified <code>kmlSource</code> and
	 * <code>appFrame</code>.
	 * 
	 * @param kmlSource
	 *            the source of the KML file to load. May be a {@link File}, a
	 *            {@link URL}, or an {@link java.io.InputStream}, or a
	 *            {@link String} identifying a file path or URL.
	 * @param appFrame
	 *            the <code>AppFrame</code> in which to display the KML source.
	 */
	public WorkerThread(String ctrlKey, Object kmlSource, AppFrame appFrame, Kml3DLayer layer) {
		this.ctrlKey = ctrlKey;
		this.kmlSource = kmlSource;
		this.appFrame = appFrame;
		this.layer = layer;
	}

	/**
	 * Loads this worker thread's KML source into a new
	 * <code>{@link gov.nasa.worldwind.ogc.kml.KMLRoot}</code>, then adds the
	 * new <code>KMLRoot</code> to this worker thread's <code>AppFrame</code>.
	 * The <code>KMLRoot</code>'s <code>AVKey.DISPLAY_NAME</code> field contains
	 * a display name created from either the KML source or the KML root feature
	 * name.
	 * <p/>
	 * If loading the KML source fails, this prints the exception and its stack
	 * trace to the standard error stream, but otherwise does nothing.
	 */
	public void run() {
		try {
			final KMLRoot kmlRoot = CustomKMLRoot.create(this.kmlSource);
			if (kmlRoot == null) {
				String message = Logging.getMessage(
						"generic.UnrecognizedSourceTypeOrUnavailableSource",
						this.kmlSource.toString());
				throw new IllegalArgumentException(message);
			}

			kmlRoot.parse();
			kmlRoot.setField(AVKey.DISPLAY_NAME,
					formName(this.kmlSource, kmlRoot));

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					KMLController kmlController = new KMLController(kmlRoot);
					layer.addKmlController(ctrlKey, kmlController);

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
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
}
