package org.gfg.poi;

import org.gfg.geohash.zxb.WGS84Point;
import org.gfg.geohash.zxb.util.VincentyGeodesy;


public class PointOperation {

	private final static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(Point p1, Point p2) {
		double radLat1 = rad(p1.getLatitude());
		double radLat2 = rad(p2.getLatitude());
		double a = radLat1 - radLat2;
		double b = rad(p1.getLongitude()) - rad(p2.getLongitude());

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000000) / 10000;
		return s;
	}

	public static void main(String[] args) {
	//	Point p1 = new Point(120.132825, 30.267327);// 120.132825,30.267327
	//	Point p2 = new Point(120.131175, 30.267214);// 120.131175,30.267214   --- 159
			Point p1 = new Point(113.2778673,23.8197065);
			Point p2 = new Point(114.4830881,22.9933198);
		System.out.println(GetDistance(p1, p2));// 159
		WGS84Point foo = new WGS84Point(23.8197065, 113.2778673);
		WGS84Point bar = new WGS84Point(22.9933198, 114.4830881);
		System.out.println(VincentyGeodesy.distanceInMeters(foo, bar));
	}
}
