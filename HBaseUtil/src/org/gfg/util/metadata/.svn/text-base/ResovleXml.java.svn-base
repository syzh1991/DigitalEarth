package org.gfg.util.metadata;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ResovleXml {
	private static final String ELEMENT_SEPARATOR = "/";

	public Map<String, Object> getXMLElement(String xmlPath) {
		String xml = xmltoString(xmlPath);
		// System.out.println(xml);
		Map<String, Object> firstResovleMap = firstResovle(xml);
		Map<String, Object> secondResovleMap = secondResole(firstResovleMap);
		// Map<String, Object> res = toMap(firstResovleMap);
		// Set<String> keySet = secondResovleMap.keySet();
		// for (String key : keySet) {
		// System.out.println(key);
		// }
		// System.out.println(firstResovleMap.size());
		return secondResovleMap;
	}

	private Map<String, Object> secondResole(Map<String, Object> firstResovleMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> keySet = firstResovleMap.keySet();
		for (String key : keySet) {
			Object value = firstResovleMap.get(key);
			key = key.substring(key.lastIndexOf('/') + 1);
			map.put(key, value);
		}
		return map;
	}

	private Map<String, Object> toMap(Map<String, String> map) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Iterator<Entry<String, String>> ite = map.entrySet().iterator();
		while (ite.hasNext()) {
			Entry<String, String> obj = ite.next();
			resMap.put(obj.getKey(), obj.getValue());
		}
		return resMap;
	}

	private String xmltoString(String xmlPath) {
		Document data = null;
		SAXReader saxReader = new SAXReader();
		File dataFile = new File(xmlPath);
		try {
			data = saxReader.read(dataFile);
			return data.asXML();
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, Object> firstResovle(String xml) {
		Map<String, Object> metadate = null;
		// 初步解析xml
		try {
			Document doc = DocumentHelper.parseText(xml.substring(
					xml.indexOf('<'), xml.length()).trim());
			metadate = paserToMap(doc);
			return metadate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Object> paserToMap(Document doc) {
		Map<String, Object> pathValueMap = new HashMap<String, Object>();
		Element root = doc.getRootElement();
		putMap(root, root.getPath(), pathValueMap);
		return pathValueMap;
	}

	private void putMap(Element element, String currentPath,
			Map<String, Object> pathValueMap) {
		@SuppressWarnings("unchecked")
		Iterator<Node> nodes = element.elementIterator();

		if (nodes.hasNext()) {
			Map<String, LinkedList<Element>> elementMap = new HashMap<String, LinkedList<Element>>();
			@SuppressWarnings("unchecked")
			List<Element> list = element.elements();
			for (Element oneElement : list) {
				String pathOne = currentPath + ELEMENT_SEPARATOR
						+ oneElement.getName();
				LinkedList<Element> listElement = elementMap.get(pathOne);
				if (null == listElement || listElement.size() == 0) {
					listElement = new LinkedList<Element>();
				}
				listElement.addLast(oneElement);
				elementMap.put(pathOne, listElement);
			}
			// 解析map
			for (String path : elementMap.keySet()) {
				LinkedList<Element> elements = elementMap.get(path);
				putMap(elements.get(0), path, pathValueMap);
			}
		} else {
			pathValueMap.put(currentPath, element.getStringValue());

		}

	}

	public static void main(String[] args) {
		ResovleXml rx = new ResovleXml();
		String xmlPath = "D:\\xml文件\\2013-08-08\\LEVEL1A\\create\\15-55-18_GF1PMS158545LEVEL1A.xml";
		String level = "LEVEL1A";
		rx.getXMLElement(xmlPath);
	}
}
