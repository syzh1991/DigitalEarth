package org.gfg.geohash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gfg.poi.Point;

public class DBUtil {
	private static final String className = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/geo?";
	private static final String user = "root";
	private static final String password = "123456";

	public static Connection getDBConnection() {
		Connection coon = null;
		try {
			Class.forName(className);
			coon = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return coon;
	}

	public void insertData(List<Point> pointList) {
		Connection conn = getDBConnection();
		try {
			int index = 0;
			Statement statement = conn.createStatement();
			for (Point point : pointList) {
				index++;
				String geohash = getGeoHash(point);
				String sql = "insert into geohashtable values(" + index + ",'"
						+ geohash + "'," + point.getLongitude() + ","
						+ point.getLatitude() + ")";
				statement.execute(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getGeoHash(Point point) {
		return new Geohash().encode(point.getLatitude(), point.getLongitude());
	}

	public List<Point> getNearData(String geohash) {
		List<Point> pointList = new ArrayList<Point>();
		Connection conn = getDBConnection();
		try {
			
		//	String geohash = getGeoHash(point);
		//	System.out.println(geohash);
			geohash = geohash.substring(0, 4);

			String sql = "select * from geohashtable where geohash like '"+geohash+"%' ";
			System.out.println(sql);
			Statement stmt = conn.createStatement();
			System.out.println(geohash);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				double longitude = rs.getDouble("longitude");
				double latitude = rs.getDouble("latitude");
				Point tmp = new Point(longitude, latitude);
				pointList.add(tmp);
			}
		} catch (Exception e) {
		}
		return pointList;
	}

}
