package org.gfg.geohash;

public class GeoTool {
	private static final double EARTH_RADIUS = 6378.137;

	/**
	 * ���������룬��������ľ�γ��,
	 */
	public static double getPointDistance(double lat1, double lng1,
			double lat2, double lng2) {
		double result = 0;

		double radLat1 = radian(lat1);

		double ratlat2 = radian(lat2);
		double a = radian(lat1) - radian(lat2);
		double b = radian(lng1) - radian(lng2);

		result = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(ratlat2)
				* Math.pow(Math.sin(b / 2), 2)));
		result = result * EARTH_RADIUS;

		result = Math.round(result * 1000); // ���صĵ�λ���ף���������

		return result;
	}

	/** �ɽǶ�ת��Ϊ���� */
	private static double radian(double d) {
		return (d * Math.PI) / 180.00;
	}

	public static void main(String[] args) {
		GeoTool tool = new GeoTool();
		System.out.println(tool.getPointDistance(30.27872, 120.12161, 30.27911,
				120.12161));

	}

}