package org.gfg.dartearth.feature.file;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.wms.Capabilities;
import gov.nasa.worldwind.wms.CapabilitiesRequest;
import gov.nasa.worldwind.wms.WMSTiledImageLayer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.xml.sax.SAXException;

public class WmsDemlLayerImporter {

	public static boolean importByUrl(DartEarthAppFrame frame, String urlStr) {
		try {
			URL url = new URL(urlStr);
			return importByUrl(frame, url);
		} catch (MalformedURLException excpetion) {
			excpetion.printStackTrace();
			// do nothing,just ignore it
			return false;
		}

	}

	public static boolean importByUrl(DartEarthAppFrame frame, URL url) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(url);
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
		if (doc != null) {
			Iterator<Element> iterator = doc.getRootElement().element("Document").elementIterator("WmsLayer");
			importWmsLayer(frame, iterator);
			return true;
		} else {
			return false;
		}
	}

	public static boolean importByFile(DartEarthAppFrame frame, String fileStr) {
		File file = new File(fileStr);
		return importByFile(frame, file);
	}

	public static boolean importByFile(DartEarthAppFrame frame, File file) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
		if (doc != null) {
			Iterator<Element> iterator = doc.getRootElement().element("Document").elementIterator("WmsLayer");
			importWmsLayer(frame, iterator);
			return true;
		} else {
			return false;
		}
	}

	public static void importWmsLayer(DartEarthAppFrame frame, Iterator<Element> iterator) {
		while (iterator.hasNext()) {
			Element wmsLayerInfo = iterator.next();
			String wmsUrlStr = wmsLayerInfo.elementText("WmsUrl");
			String layerName = wmsLayerInfo.elementText("LayerName");
			int tileHeight = Integer.valueOf(wmsLayerInfo.elementText("TileHeight"));
			int tileWidth = Integer.valueOf(wmsLayerInfo.elementText("TileWidth"));
			String formatSuffix = wmsLayerInfo.elementText("FormatSuffix");
			int numEmptyLevels = Integer.valueOf(wmsLayerInfo.elementText("NumEmptyLevels"));
			int numLevels = Integer.valueOf(wmsLayerInfo.elementText("NumLevels"));
			boolean isMercator = Boolean.valueOf(wmsLayerInfo.elementText("IsMercator"));
			double levelZeroTileDeltaLat = Double.valueOf(wmsLayerInfo.elementText("LevelZeroTileDeltaLat"));
			double levelZeroTileDeltaLng = Double.valueOf(wmsLayerInfo.elementText("LevelZeroTileDeltaLng"));

			try {
//				System.out.println("wmsUrlStr:" + wmsUrlStr);
//				System.out.println("layerName:" + layerName);
//				System.out.println("tileHeight:" + tileHeight);
//				System.out.println("tileWidth:" + tileWidth);
//				System.out.println("formatSuffix:" + formatSuffix);
//				System.out.println("numEmptyLevels:" + numEmptyLevels);
//				System.out.println("numLevels:" + numLevels);

				Capabilities caps = getCapbilities(new URI(wmsUrlStr));
				AVList layerParams = new AVListImpl();
				layerParams.setValue(AVKey.GET_CAPABILITIES_URL, wmsUrlStr);
				layerParams.setValue(AVKey.LAYER_NAMES, layerName);
				layerParams.setValue(AVKey.TILE_HEIGHT, tileHeight);
				layerParams.setValue(AVKey.TILE_WIDTH, tileWidth);
				layerParams.setValue(AVKey.STYLE_NAMES, "");
				layerParams.setValue(AVKey.VERSION, "1.3.0");
				layerParams.setValue(AVKey.FORMAT_SUFFIX, formatSuffix);
				layerParams.setValue(AVKey.NUM_EMPTY_LEVELS, numEmptyLevels);
				layerParams.setValue(AVKey.NUM_LEVELS, numLevels);
				layerParams.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, LatLon.fromDegrees(levelZeroTileDeltaLat, levelZeroTileDeltaLng));

				Layer layer = WmsLayerFactory.newLayer(caps, layerParams, isMercator);
				if (layer != null) {
					frame.getWwd().getModel().getLayers().add(5, layer);
					frame.getLayerPanelDialog().getLayerPanel().update();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Capabilities getCapbilities(URI uri) throws ParserConfigurationException, URISyntaxException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		CapabilitiesRequest req = new CapabilitiesRequest(uri);
		// System.out.println(req.toString());
		org.w3c.dom.Document doc = builder.parse(req.toString());
		Capabilities caps = Capabilities.parse(doc);
		return caps;
	}
}
