package org.gfg.dartearth.feature.KML;

import gov.nasa.worldwind.layers.Layer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.gfg.dartearth.core.DartEarthAppFrame;

public class DEMLExportor {

	public static void exportAsDeml(String fileName,DartEarthAppFrame frame) throws IOException {
		XMLWriter writer = null;
		String filePath = fileName+".deml";
		File file = new File(filePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");// 设置XML文件的编码格式
		Document _document = DocumentHelper.createDocument();
		_document.setXMLEncoding("UTF-8");
		Element _root = _document.addElement("DEML");
		Element _doc = _root.addElement("Document");

		for (Layer layer : frame.getWwd().getModel().getLayers()) {
			if (layer.isEnabled()) {
				if (layer instanceof ExportableLayer) {
					Element _feature = ((ExportableLayer) layer).exportAsDeml();
					_doc.add(_feature);
					// System.out.println(_feature.element("LayerName").getText());
				}
			}

		}

		writer = new XMLWriter(new FileWriter(file), format);
		writer.write(_document);
		writer.close();

	}
}
