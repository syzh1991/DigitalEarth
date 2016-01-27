package org.gfg.poi;


import java.text.DecimalFormat;
import java.util.List;

import org.gfg.geohash.zxb.WGS84Point;
import org.gfg.geohash.zxb.util.VincentyGeodesy;

public class PointMain {
	public static void main(String[] args) {
		DataSourceUtil dsu = new DataSourceUtil();
		List<Point> pointList = dsu.getPointList();
		double radiu = 2000.0;

		DecimalFormat df1 = new DecimalFormat("000000.000");
		DecimalFormat df2 = new DecimalFormat("000.000000000");
		DecimalFormat df3 = new DecimalFormat("000");
		
		
		if (pointList.size() > 0 && pointList != null) {
			
			Point centerPoint = new Point(117.9763599167, 39.8794293536);;
			int index = 0;
			for (Point point : pointList) {
			//	double dis = PointOperation.GetDistance(centerPoint, point);
					double dis = VincentyGeodesy.distanceInMeters(
							new WGS84Point(centerPoint.getLatitude(), centerPoint.getLongitude()), 
							new WGS84Point(point.getLatitude(), point.getLongitude())
							);
				if (dis < radiu) {
					index++;
					System.out.println("index "+ df3.format(index)+" realDis  " + df1.format(dis)
							+ "     lontitude,latitude   "
							+ df2.format(point.getLongitude()) + ","
							+ df2.format(point.getLatitude()));
				}
			}
		}
		
		
	}
}
