package org.gfg.dartearth.util;
/**
 * 计算两点间的地球表面距离
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 */
public class DistanceCaculator {

	/**
	 * 公式中的常量
	 */
	private final static double EARTH_RADIUS = 6378137.0;
	/**
	 * 通过A，B点间的经纬度计算A，B点间的距离
	 * @param lat_a 点A纬度
	 * @param lng_a 点A经度
	 * @param lat_b 点B纬度
	 * @param lng_b 点B经度
	 * @return A，B点间的地球表面距离
	 */
	public static double caculateDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
		double delt=lng_a-lng_b;
		if(delt>=180.0)
			delt=360.0-delt;
		if(delt<=-180.0)
			delt=360.0+delt;
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = delt * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		//System.out.println(s);
		return s;
	}
	/**
	 * 计算A，B点的中心点的经度
	 * @param lng_a 点A经度
	 * @param lng_b 点B经度
	 * @return A，B点的中心点的经度
	 */
	public static double getCenterLng(double lng_a, double lng_b) {
		double delt=lng_a-lng_b;
		double center=(lng_a+lng_b)/2;
		if(delt>=180.0){
			if(lng_a+lng_b<0)
				return (lng_a+lng_b)/2+180;
			else
				return (lng_a+lng_b)/2-180;
		}
		if(delt<=-180.0){
			if(lng_a+lng_b<0)
				return (lng_a+lng_b)/2+180;
			else
				return (lng_a+lng_b)/2-180;
		}
		return center;
	}
}

