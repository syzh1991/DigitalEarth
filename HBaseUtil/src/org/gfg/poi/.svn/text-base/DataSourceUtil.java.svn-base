package org.gfg.poi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataSourceUtil {
	public List<Point> getPointList() {
		// read the data from hbase
		List<Point> pointListRet = new ArrayList<Point>();
		//
		String fileName = "C:/Users/zxb/Desktop/POIDATA/poitestdata.txt";
		pointListRet = readFromFile(fileName);
		//
		return pointListRet;
	}

	private List<Point> readFromFile(String fileName) {
		List<Point> pointListRet = new ArrayList<Point>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] pointData = line.split("\t");
				if (pointData.length == 2) {
					Point point = new Point(Double.parseDouble(pointData[0]),
							Double.parseDouble(pointData[1]));
					pointListRet.add(point);
				}
			}
			reader.close();
		} catch (Exception e) {
		}
		return pointListRet;
	}
	public static void main(String[] args) {
		DataSourceUtil dsu = new DataSourceUtil();
		dsu.getPointList();
	}
}
