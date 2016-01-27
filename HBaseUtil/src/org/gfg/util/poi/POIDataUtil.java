package org.gfg.util.poi;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.gfg.geohash.zxb.GeoHash;
import org.gfg.geohash.zxb.WGS84Point;
import org.gfg.geohash.zxb.util.VincentyGeodesy;
import org.gfg.hbase.HBaseUtil;
import org.gfg.poi.Point;

public class POIDataUtil {
	private static final int INFO_LENGTH = 7;
	private static final String tableName = "POIDATA";
	public static final String colfamilies = "a";
	public static final String[] colNames = { "name", "classes", "address",
			"phone", "telephone", "longitude", "latitude" };
	private static final int numberOfBits = 60;
	private HBaseUtil hbutil;

	public List<Point> getPointDataFromCSV(String pathname) {
		List<Point> pointList = new ArrayList<Point>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					pathname)));
			reader.readLine();
			String line = null;
			while ((line = reader.readLine()) != null) {
				String item[] = line.split("\",\"");
				for (String string : item) {
					System.out.println(string);
				}
				Point point = arrayToPoint(item);
				pointList.add(point);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pointList;
	}

	private Point arrayToPoint(String[] item) {
		if (item.length != INFO_LENGTH) {
			return null;
		}
		Point point = new Point(item[0].substring(1), item[1], item[2],
				item[3], item[4], Double.parseDouble(item[5]),
				Double.parseDouble(item[6].substring(0, item[6].length() - 1)));
		return point;
	}

	public void createPOIDB() {
		hbutil = new HBaseUtil();
		try {
			hbutil.creatTable(tableName, colfamilies);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stroeInToHBase(List<Point> pointList) {
		int index = 0;
		long beginTime = System.currentTimeMillis();
		for (Point point : pointList) {
			double longitude = point.getLongitude();
			double latitude = point.getLatitude();
			GeoHash geoHash = GeoHash.withBitPrecision(latitude, longitude,
					numberOfBits);
			String geoHashString = geoHash.toBase32();
			System.out.println(index++);
			storeAnPointObject(tableName, colfamilies, geoHashString, point);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("beginTime---" + beginTime);
		System.out.println("endTime---" + endTime);
		System.out.println("endTime-beginTime---" + (endTime - beginTime));
	}

	private void storeAnPointObject(String tableName, String colfamilies,
			String geoHash, Point point) {
		hbutil = new HBaseUtil();
		String rowkey = geoHash + "_" + MD5Util.getMD5(point.getName());
		hbutil.put(tableName, colfamilies, rowkey, colNames[0],
				point.getName());
		hbutil.put(tableName, colfamilies, rowkey, colNames[1],
				point.getClasses());
		hbutil.put(tableName, colfamilies, rowkey, colNames[2],
				point.getAddress());
		hbutil.put(tableName, colfamilies, rowkey, colNames[3],
				point.getPhone());
		hbutil.put(tableName, colfamilies, rowkey, colNames[4],
				point.getTelephone());
		hbutil.put(tableName, colfamilies, rowkey, colNames[5],
				String.valueOf(point.getLongitude()));
		hbutil.put(tableName, colfamilies, rowkey, colNames[6],
				String.valueOf(point.getLatitude()));
		System.out.println(point.getName());

	}

	public List<Point> getNearPoint(Point centerPoint, double radius) {
		List<Point> pointList = new ArrayList<Point>();
		double longitude = centerPoint.getLongitude();
		double latitude = centerPoint.getLatitude();
		GeoHash geoHash = GeoHash.withBitPrecision(latitude, longitude,
				numberOfBits);
		String geoHashVal = geoHash.toBase32();
		geoHashVal = geoHashVal.substring(0, 5);
		HBaseUtil hbu = new HBaseUtil();
		try {
			pointList = hbu.rowKeyFilter(geoHashVal, tableName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pointList.size());
		// ///
		DecimalFormat df1 = new DecimalFormat("000000.000");
		DecimalFormat df2 = new DecimalFormat("000.000000000");
		DecimalFormat df3 = new DecimalFormat("00000");
		// ///

		int index = 0;
		WGS84Point WSGCenterPoint = pointtoWGSPoint(centerPoint);

		for (Point tmpPoint : pointList) {
			WGS84Point wsgPoint = pointtoWGSPoint(tmpPoint);
			double realDis = VincentyGeodesy.distanceInMeters(WSGCenterPoint,
					wsgPoint);
			if (realDis < radius) {
				index++;
				System.out.println("index " + df3.format(index) + " realDis  "
						+ df1.format(realDis) + "     lontitude,latitude   "
						+ df2.format(tmpPoint.getLongitude()) + ","
						+ df2.format(tmpPoint.getLatitude()) + " name"
						+ tmpPoint.getName());
			}
		}
		//
		return pointList;
	}

	private static WGS84Point pointtoWGSPoint(Point point) {
		WGS84Point p = new WGS84Point(point.getLatitude(), point.getLongitude());
		return p;
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		POIDataUtil util = new POIDataUtil();
		/*
		 * String pathname =
		 * "C:\\Users\\zxb\\Desktop\\POIDATA\\baidupoi_113_23.csv";
		 * List<Point> pointList = util.getPointDataFromCSV(pathname);
		 * util.createPOIDB(); util.stroeInToHBase(pointList);
		 */

		Point centerPoint = new Point(113.277867254284, 23.8197065004199);
		double radius = 500;
		util.getNearPoint(centerPoint, radius);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - beginTime);
	}
}
