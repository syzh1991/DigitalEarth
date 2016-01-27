package org.gfg.hbase;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
//		System.out.println(list.toString());
//		HBaseUtil hbu = new HBaseUtil();
//		hbu.getLayerConfigFileName("WMSDATA", "ALL_ALL_20140412");
//		hbu.getLayerConfigFileName("WMSDATA", "ALL_ALL_20140412");
//		hbu.getAllLayer("WMSDATA");
		int i = 'a';
		System.out.println(i);
		String s = new String("A");
		String b = s;
		System.out.println(s);
		System.out.println("--------");
		String a = "ABD";
		replacea(a);
		System.out.println(a);
	}

	private static void replacea(String a) {
		a = a.replace('A', 'E');
		a = a.toLowerCase();
	}
	
}
