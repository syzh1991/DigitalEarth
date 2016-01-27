package org.gfg.hbase.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gfg.hbase.HBaseUtil;

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {

	}

	@Override
	public String toString() {
		return "" + x + "_" + y;
	}
}

public class CreateFile {

	/*
     *
     *
    */
	public static Point getPoint(int x, int y, Point o, int m, int n) {
		int disX;
		int disY;
		if ((x - o.x) % m >= 0) {
			disX = (x - o.x) % m;
		} else {
			disX = (x - o.x) % m + m;
		}

		if ((y - o.y) % n >= 0) {
			disY = (y - o.y) % n;
		} else {
			disY = (y - o.y) % n + n;
		}

		Point ret = new Point(o.x + disX, o.y + disY);
		return ret;
	}

	public static void copyFile(File sourceFile, File destFile) {

		FileChannel source = null;
		FileChannel destination = null;
		try {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (source != null) {
					source.close();
				}
				if (destination != null) {
					destination.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyPic(String level, String parentPath, Point oldPoint,
			Point newPoint) {
		if (oldPoint.x == newPoint.x && oldPoint.y == newPoint.y) {
			return;
		}
		String oldPath = parentPath + "\\" + oldPoint.x + "\\" + oldPoint.x
				+ "_" + oldPoint.y + ".jpg";
		// String pathDir = parentPath + "\\" + newPoint.x;
		// String newPath = pathDir + "\\" + newPoint.x + "_" + newPoint.y +
		// ".jpg";
		File source = new File(oldPath);
		// File destination = new File(newPath);

		// ///////////////////////////////////////////

		String rowKey = level + "\\" + newPoint.x + "\\" + newPoint.x + "_"
				+ newPoint.y;

		File file = source;
		InputStream inputStream;
		byte[] bs = null;
		try {
			inputStream = new FileInputStream(file);
			bs = IOUtils.toByteArray(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HBaseUtil hbaseUtil = new HBaseUtil();
		String columnFamilies = "Test_Test";
		String qualifier = "20130806";
		if (!hbaseUtil.isExist(HBaseUtil.TABLE_NAME, rowKey, columnFamilies,
				qualifier)) {
			hbaseUtil.putImage(HBaseUtil.TABLE_NAME, rowKey, columnFamilies,
					qualifier, bs);
			System.out.println(rowKey + "insert done");
		} else {
			System.out.println(rowKey + "already exist");
		}
		// ////////////////////////////
		// 不需要生成直接插入
		// copyFile(source, destination);
	}

	public static final int X_BASE = 4;
	public static final int Y_BASE = 9;
	public static final int TIMES = 2;

	public void copyData(String path, Map<Integer, Point> pointLists,
			Map<Integer, Point> layToDis) {
		File baseDir = new File(path);
		File[] files = baseDir.listFiles();
		for (File file : files) {
			/*
			 * if(file.getName().equals("7")||file.getName().equals("11")
			 * ||file.getName().equals("12") ||file.getName().equals("13")
			 * ||file.getName().equals("14")){ continue; }
			 */
			if (!file.getName().equals("8")) {
				continue;
			}
			System.out.println(file.getAbsolutePath());

			int layer = Integer.parseInt(file.getName());
			String currentLayerPath = path + "\\" + layer;
			int currentX = (X_BASE + 1) * ((int) Math.pow(TIMES, layer)) - 1;
			int currentY = (Y_BASE + 1) * ((int) Math.pow(TIMES, layer)) - 1;

			Point oldPoint = pointLists.get(layer);
			Point dis = layToDis.get(layer);
			// 此处可以修改生成之后区域的范围
			for (int i = 0; i <= currentX; i++) {

				File currentLayerXDir = new File(currentLayerPath + "\\" + i);
				if (!currentLayerXDir.exists()) {
					currentLayerXDir.mkdir();
				}
				for (int j = 0; j <= currentY; j++) {
					Point newPoint = getPoint(i, j, oldPoint, dis.x, dis.y);
					copyPic(file.getName(), currentLayerPath, newPoint,
							new Point(i, j));
					System.out.println(file.getName() + "_" + i + "_" + j);
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		String path = "D:\\1\\GF1_PMS2_20130806\\";
		File baseDir = new File(path);
		File[] layers = baseDir.listFiles();
		Map<Integer, Point> pointLists = new HashMap<Integer, Point>();
		Map<Integer, Point> layToDis = new HashMap<Integer, Point>();

		List<Integer> X = new ArrayList<Integer>();
		X.add(-2);
		X.add(-1);
		X.add(0);
		X.add(1);
		X.add(2);
		HBaseUtil hbu = new HBaseUtil();
		String cf = "GF1_PMS2";
		String qualifier = "20130806";
		for (File file : layers) {
			
			String level = file.getName();
			File[] temp = file.listFiles();
			int m = temp.length;
			int n = temp[0].list().length;
			// layToDis.put(layer, new Point(m, n));
			// 间距已经求出来了

			System.out.println(m + "----------->" + n);
			String start = temp[0].listFiles()[0].getName();
			int a = Integer.parseInt(start.substring(0, start.indexOf('_')));
			int b = Integer.parseInt(start.substring(start.indexOf('_') + 1,
					start.indexOf('.')));
			for (int i : X) {
				for (int j : X) {
					if (i == 0 && j == 0) {
						continue;
					}
					for (int k = a; k < m + a; k++) {
						int newx = k + i * m;
						for (int l = b; l < b + n; l++) {
							int newy = l + j * n;
							String newPath = path + level + "\\" + newx + "\\"
									+ newx + "_" + newy + ".jpg";
							String oldPath = path + level + "\\" + k + "\\" + k
									+ "_" + l + ".jpg";
							String newDirName = path + level+"\\" + newx;
							String key = level + "\\" + newx + "\\" + newx
									+ "_" + newy;
							File newDir = new File(newDirName);
							System.out.println();
							

							File sourceFile = new File(oldPath);
							File destFile = new File(newPath);
							 byte[] bs = IOUtils.toByteArray(new
							 FileInputStream(sourceFile));
							 hbu.putImage(HBaseUtil.TABLE_NAME, key, cf,
							 qualifier, bs );
							 System.out.println(key);
							//copyFile(sourceFile, destFile);
						}

					}

				}
			}
			// pointLists.put(layer, new Point(a, b));
		}

		// mainObj.copyData(path, pointLists, layToDis);

	}
}
