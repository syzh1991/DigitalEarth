package kml3d;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.org.apache.xpath.internal.operations.Mod;

import kml3d.KMLViewer.AppFrame;
import kml3d.KMLViewer.WorkerThread;

public class KmlFactory {

	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	// private static ExecutorService pool =
	// Executors.newSingleThreadExecutor();

	private static double deltDegree = 36;

	private static int trimLevel = 8;

	private static DirRange preRange;

	protected static class DirRange {
		private int minX;
		private int maxX;
		private int minY;
		private int maxY;

		public DirRange(int minX, int maxX, int minY, int maxY) {
			this.maxX = maxX;
			this.minX = minX;
			this.maxY = maxY;
			this.minY = minY;
		}

		public boolean equalTo(DirRange range) {
			if (this.maxX == range.maxX && this.minX == range.minX
					&& this.maxY == range.maxY && this.minY == range.minY) {
				return true;
			} else {
				return false;
			}
		}
	}

	private static List<Object> getKmls(String address, DirRange range) {

		List<Object> kmlList = new ArrayList<Object>();
		File addr = new File(address);
		File[] files = null;
		// addr.listFiles();

		for (File fileX : getFitDirs(addr, range.minX, range.maxX)) {
			for (File fileY : getFitDirs(fileX, range.minY, range.maxY)) {
				if (fileY.isDirectory()) {
					FileFilter filter = new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							String filename = arg0.getAbsolutePath();
							if (arg0.isFile()
									&& (filename.endsWith(".kml") || filename
											.endsWith(".kmz"))) {
								return true;
							} else {
								return false;
							}

						}
					};
					files = fileY.listFiles(filter);
				}
			}
		}

		// if(addr.isDirectory()) {
		// FileFilter filter = new FileFilter() {
		//
		// @Override
		// public boolean accept(File arg0) {
		// String filename = arg0.getAbsolutePath();
		// if(arg0.isFile()&&(filename.endsWith(".kml")||filename.endsWith(".kmz")))
		// {
		// return true;
		// } else {
		// return false;
		// }
		//
		// }
		// };
		// files = addr.listFiles(filter);
		// }
		if (files != null) {
			for (File file : files) {
				String filestr = file.getAbsolutePath();
				kmlList.add(filestr);
				System.out.println(filestr);
			}
		}

		return kmlList;
	}

	public static List<Object> getKmls(String address) {

		List<Object> kmlList = new ArrayList<Object>();
		File addr = new File(address);
		File[] files = null;
		if (addr.isDirectory()) {
			FileFilter filter = new FileFilter() {

				@Override
				public boolean accept(File arg0) {
					String filename = arg0.getAbsolutePath();
					if (arg0.isFile()
							&& (filename.endsWith(".kml") || filename
									.endsWith(".kmz"))) {
						return true;
					} else {
						return false;
					}

				}
			};
			files = addr.listFiles(filter);
		}

		for (File file : files) {
			String filestr = file.getAbsolutePath();
			kmlList.add(filestr);
		}
		return kmlList;
	}

	private static File[] getFitDirs(File addr, final int min, final int max) {

		if (addr.isDirectory()) {
			FileFilter filter = new FileFilter() {

				@Override
				public boolean accept(File arg0) {
					String filename = arg0.getAbsolutePath().replace("\\", "/");
					String[] splitStr = filename.split("/");
					int num;
					try {
						num = Integer.parseInt(splitStr[splitStr.length - 1]);
					} catch (Exception e) {
						return false;
					}

					if (arg0.isDirectory() && num >= min && num <= max) {
						return true;
					} else {
						return false;
					}

				}
			};
			return addr.listFiles(filter);
		} else {
			return null;
		}

	}

	public static void loadKmls(AppFrame appFrame, String address,
			Position position) {

		DirRange range = Position2DirRange(position);
		if (preRange != null&&preRange.equalTo(range)) {
			return;
		}

		List<Object> kmlList = getKmls(address, range);

		for (Object kml : kmlList) {
			Thread kmlThread = new WorkerThread(kml, appFrame);
			pool.execute(kmlThread);
		}
		// pool.shutdown();
	}

	// private static DirRange Sector2DirRange(Sector sector) {
	//
	// double minLatitude = sector.getMinLatitude().getDegrees();
	// double maxLatitude = sector.getMaxLatitude().getDegrees();
	// double minLongitude = sector.getMinLongitude().getDegrees();
	// double maxLongitude = sector.getMaxLongitude().getDegrees();
	//
	// // 求2为底(maxLongitude - minLongitude)/deltDegree的对数
	// // int level = (int) ((Math.log10((maxLongitude - minLongitude)
	// // / deltDegree) / Math.log10(2)));
	//
	// int minX = (int) Math.floor(longitude2X(minLongitude));
	// int maxX = (int) Math.floor(longitude2X(maxLongitude));
	// int minY = (int) Math.floor(latitude2Y(minLatitude));
	// int maxY = (int) Math.floor(latitude2Y(maxLatitude));
	//
	// DirRange range = new DirRange(minX, maxX, minY, maxY);
	//
	// return range;
	//
	// }

	private static DirRange Position2DirRange(Position position) {

		// double minLatitude = sector.getMinLatitude().getDegrees();
		// double maxLatitude = sector.getMaxLatitude().getDegrees();
		// double minLongitude = sector.getMinLongitude().getDegrees();
		// double maxLongitude = sector.getMaxLongitude().getDegrees();

		double longitude = position.getLongitude().getDegrees();
		double latitude = position.getLatitude().getDegrees();

		// int minX = (int) Math.floor(longitude2X(minLongitude));
		// int maxX = (int) Math.floor(longitude2X(maxLongitude));
		// int minY = (int) Math.floor(latitude2Y(minLatitude));
		// int maxY = (int) Math.floor(latitude2Y(maxLatitude));

		int xDir = (int) Math.floor(longitude2X(longitude));
		int yDir = (int) Math.floor(latitude2Y(latitude));

		int maxXDir = (int) Math.floor(longitude2X(180));
		int maxYDir = (int) Math.floor(latitude2Y(90));

		DirRange range = new DirRange((xDir - 1) % maxXDir, (xDir + 1)
				% maxXDir, (yDir - 1) % maxYDir, (yDir + 1) % maxYDir);

		return range;

	}

	private static double latitude2Y(double latitude) {
		return (latitude + 90) / deltDegree * Math.pow(2, trimLevel);
	}

	private static double longitude2X(double longitude) {
		return (longitude + 180) / deltDegree * Math.pow(2, trimLevel);
	}

	public static void main(String[] args) {

		System.out.println(2 ^ 5);
		// File dir = new File("D:/");
		// for(File file:dir.listFiles())
		// System.out.println(file.getAbsolutePath());

		// Sector sector = new Sector(Angle.NEG90, Angle.POS90, Angle.NEG90,
		// Angle.POS90);
		// DirRange range = Sector2DirRange(sector);
		// System.out.println(range.minX+" "+range.maxX+" "+range.minY+" "+range.maxY);

		// Sector sector = new Sector(Angle.NEG90, Angle.POS90, Angle.NEG90,
		// Angle.POS90);
		// DirRange range = Position2DirRange(sector);
		// getKmls("D:/google3D", range);

	}

}
