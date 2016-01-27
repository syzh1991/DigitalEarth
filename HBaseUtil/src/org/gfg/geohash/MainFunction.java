package org.gfg.geohash;


import java.text.DecimalFormat;
import java.util.List;

import org.gfg.geohash.zxb.GeoHash;
import org.gfg.geohash.zxb.WGS84Point;
import org.gfg.geohash.zxb.util.VincentyGeodesy;
import org.gfg.poi.Point;

public class MainFunction {
	public static void main(String[] args) {
		/* store data* */
		/*
		 * DataSourceUtil dsu = new DataSourceUtil(); List<Point> pointList =
		 * dsu.getPointList(); DBUtil dbUtil = new DBUtil();
		 * dbUtil.insertData(pointList);
		 */
		double latitude=39.8794293536;
		double longitude=117.9763599167;
		int numberOfBits = 60;
		DecimalFormat df1 = new DecimalFormat("000000.000");
		DecimalFormat df2 = new DecimalFormat("000.000000000");
		DecimalFormat df3 = new DecimalFormat("000");

		int dis = 000;// m
		DBUtil dbUtil = new DBUtil();
		Point point = new Point(longitude, latitude);
		WGS84Point centerPoint = pointtoWGSPoint(point);
		GeoHash geoHash = GeoHash.withBitPrecision(latitude, longitude, numberOfBits);
		List<Point> pointList0 = dbUtil.getNearData(geoHash.toBase32());
		
		System.out.println("������Ŀ:" + pointList0.size());
		int index = 0;
		
		/////////////////////////////////////////
		
		GeoHash[] geoHashArray = geoHash.getAdjacent();
		System.out.println("geoHash.toBase32():"+geoHash.toBase32());
		for (GeoHash adjGeoHash : geoHashArray) {
			System.out.println(adjGeoHash.toBase32());
			//pointList0.addAll(dbUtil.getNearData(adjGeoHash.toBase32()));
		}
		/////////////////////////////////////////
		
		for (Point point2 : pointList0) {
			// System.out.println(point2);
			WGS84Point wsgPoint = pointtoWGSPoint(point2);
			double realDis = VincentyGeodesy.distanceInMeters(centerPoint, wsgPoint);
			if (realDis < dis) {
				index++;
				System.out.println("index "+ df3.format(index)+" realDis  " + df1.format(realDis)
						+ "     lontitude,latitude   "
						+ df2.format(point2.getLongitude()) + ","
						+ df2.format(point2.getLatitude()));
			}
		}
	}

	private static WGS84Point pointtoWGSPoint(Point point) {
		WGS84Point p = new WGS84Point(point.getLatitude(), point.getLongitude());
		return p;
	}
}
