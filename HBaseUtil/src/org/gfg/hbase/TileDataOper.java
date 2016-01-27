package org.gfg.hbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class TileDataOper {
	// �洢�����Ϣ
	public void storeLayerInfo(String dir, String columnFamilies,
			String qualifier) throws IOException {
		List<String> layerList = findLayerInfo(dir);// layer -- rowKey
		HBaseUtil hu = new HBaseUtil();
		String value = "";
		for (int i = 0; i < layerList.size() - 1; i++) {
			value = value + layerList.get(i) + HBaseUtil.SEPARATOR;
		}
		value += layerList.get(layerList.size() - 1);
		// TODO
		// hu.put(HBaseUtil.TABLE_NAME, columnFamilies,
		// HBaseUtil.LAY_INFO,qualifier, value);

	}

	// �洢ͼ��hbase��
	public void storeInDB(String dir, String columnFamilies, String qualifier)
			throws IOException {
		System.out.println("In");
		// Map<String, String> map = findAllTileDataFile(dir);

		//
		File dirFile = new File(dir);
		// Map<String, String> ret = new HashMap<String, String>();
		// Ŀ¼�ṹ�ǹ̶��ģ����������ַ�ʽ����
		for (File layerNumDir : dirFile.listFiles()) {
			// if(layerNumDir.getName().equals("0")){
			// continue;
			// }
			for (File rowNumDir : layerNumDir.listFiles()) {
				File tileDataFile;
				String tileDataFilePath;
				for (String tileDataFileName : rowNumDir.list()) {
					tileDataFilePath = rowNumDir + "\\" + tileDataFileName;
					tileDataFile = new File(tileDataFilePath);
					if (!tileDataFile.isDirectory()) {
						if (tileDataFile.getName().endsWith(".jpg")
								|| tileDataFile.getName().endsWith(".png")) {
							String filePath = tileDataFile.getAbsolutePath();
							String newDir = "";
							if (dir.contains("/")) {
								newDir = dir.replaceAll("/", "\\");
							} else {
								newDir = dir;
							}
							filePath = filePath.substring(newDir.length());

							if (filePath.startsWith("\\")) {
								filePath = filePath.substring(1,
										filePath.indexOf('.'));
							}
							// get the id
							String rowKey = filePath;
							File file = tileDataFile;
							InputStream inputStream = new FileInputStream(file);
							byte[] bs = IOUtils.toByteArray(inputStream);
							HBaseUtil hbaseUtil = new HBaseUtil();
							// System.out.println("shit");
							if (!hbaseUtil.isExist(HBaseUtil.TABLE_NAME,
									rowKey, columnFamilies, qualifier)) {
								hbaseUtil.putImage(HBaseUtil.TABLE_NAME,
										rowKey, columnFamilies, qualifier, bs);
								System.out.println(rowKey + "insert done");
							} else {
								System.out.println(rowKey + "already exist");
							}
						}
					}
				}
			}
		}
		// /

	}

	// ���ļ���Ѱ�Ҳ�μ�����Ϣ
	private List<String> findLayerInfo(String dir) {
		List<String> ret = new ArrayList<String>();
		File dirFile = new File(dir);
		for (File layerNumDir : dirFile.listFiles()) {
			System.out.println(layerNumDir.getName());
			ret.add(layerNumDir.getName());
		}
		return ret;
	}

	// 2/344/344_847.png
	private Map<String, String> findAllTileDataFile(String dir) {
		File dirFile = new File(dir);
		Map<String, String> ret = new HashMap<String, String>();
		// Ŀ¼�ṹ�ǹ̶��ģ����������ַ�ʽ����
		for (File layerNumDir : dirFile.listFiles()) {
			for (File rowNumDir : layerNumDir.listFiles()) {
				for (String tileDataFileName : rowNumDir.list()) {
					String tileDataFilePath = rowNumDir + "\\"
							+ tileDataFileName;
					File tileDataFile = new File(tileDataFilePath);
					if (!tileDataFile.isDirectory()) {
						if (tileDataFile.getName().endsWith(".jpg")) {
							String filePath = tileDataFile.getAbsolutePath();
							String newDir = "";
							if (dir.contains("/")) {
								newDir = dir.replaceAll("/", "\\");
							} else {
								newDir = dir;
							}
							System.out.println();
							filePath = filePath.substring(newDir.length());

							if (filePath.startsWith("\\")) {
								filePath = filePath.substring(1,
										filePath.indexOf('.'));
							}
							// get the id
							ret.put(filePath, tileDataFile.getAbsolutePath());
							System.out.println(filePath);

						}
					}
				}
			}
		}
		return ret;
	}
}
