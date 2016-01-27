package org.gfg.dartearth.feature.KML;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.draw.AbstractBuilder;
import org.gfg.dartearth.feature.file.WmsDemlLayerImporter;

/**
 * DEML导入类
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class DEMLImportor {
	/**
	 * 由本地DEML文件导入图层
	 * 
	 * @param file
	 *            DEML所在文件
	 * @param frame
	 */
	public static void importByDeml(File file, DartEarthAppFrame frame) {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			// InputStreamReader insr = new InputStreamReader(stream, "UTF-8");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(inputStream);
			Element root = doc.getRootElement();
			Iterator<Element> featuresIterator = root.element("Document").elementIterator("Feature");
			importFeatures(featuresIterator, frame);
			Iterator<Element> wmsLayersIterator = root.element("Document").elementIterator("WmsLayer");
			WmsDemlLayerImporter.importWmsLayer(frame, wmsLayersIterator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 由远程DEML文件导入图层
	 * 
	 * @param urlStr
	 *            远程DEML的url
	 * @param frame
	 */
	public static void importByDeml(String urlStr, DartEarthAppFrame frame) {

		URLConnection httpConn = null;
		try {
			URL url = new URL(urlStr);
			httpConn = url.openConnection();
			httpConn.setConnectTimeout(16000);
			httpConn.setReadTimeout(12000);
			InputStream content = (InputStream) httpConn.getContent();
			InputStreamReader insr = new InputStreamReader(content, "UTF-8");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(insr);
			Element root = doc.getRootElement();
			Iterator<Element> featuresIterator = root.element("Document").elementIterator("Feature");
			importFeatures(featuresIterator, frame);
			Iterator<Element> wmsLayersIterator = root.element("Document").elementIterator("WmsLayer");
			WmsDemlLayerImporter.importWmsLayer(frame, wmsLayersIterator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 由Element迭代器导入图层
	 * 
	 * @param iterator Element迭代器
	 * @param frame
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static void importFeatures(Iterator<Element> iterator, DartEarthAppFrame frame) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String type = e.element("Type").getText();
			Class<?> builder = Class.forName("org.gfg.dartearth.feature.draw." + type + "Builder");
			((AbstractBuilder) builder.newInstance()).buildByDEML(e, frame);
		}
	}
}
