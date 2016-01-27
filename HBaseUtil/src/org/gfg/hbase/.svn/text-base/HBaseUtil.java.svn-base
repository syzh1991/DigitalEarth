package org.gfg.hbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.gfg.poi.Point;
import org.gfg.util.poi.POIDataUtil;

public class HBaseUtil {
	static Configuration conf = null;
	static HBaseAdmin admin = null;
	public final static String TABLE_NAME = "WMSDATA";
	public final static String LAY_INFO_KEY = "layer";
	public final static String LAYER_CONFIG_FILE_NAME_QUALIFIER = "name";
	public final static String LAYER_CONFIG_FILE_CONTENT_QUALIFIER = "content";
	public final static String LAYER_CONFIG_FILE_LEVEL_QUALIFIER = "level";
	public final static String LAY_INFO_CF = "layerInfo";
	public final static String SEPARATOR = ",";
	public final static String IDSEPARATOR = "_";

	static {
		conf = HBaseConfiguration.create();
		conf.addResource("hbase-site.xml");
		try {
			admin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creatTable
	 * 
	 * @param tableName
	 * @param colfamilies
	 * @throws IOException
	 */
	public void creatTable(String tableName, String... colfamilies)
			throws IOException {
		createTable(tableName, null, colfamilies);
	}

	public void createTable(String tableName, byte[][] splitKeys,
			String... colfamilies) throws IOException {
		HTableDescriptor descriptor = new HTableDescriptor(tableName);
		for (String cf : colfamilies) {
			HColumnDescriptor colDescriptor = new HColumnDescriptor(cf);
			descriptor.addFamily(colDescriptor);
		}
		if (splitKeys != null) {
			admin.createTable(descriptor, splitKeys);
		} else {
			admin.createTable(descriptor);
		}
	}

	/**
	 * isExist
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean isExist(String tableName) throws IOException {
		return admin.tableExists(tableName);
	}

	/**
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	public void disableTable(String tableName) throws IOException {
		admin.disableTable(tableName);
	}

	/**
	 * dropTable
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	public void dropTable(String tableName) throws IOException {
		admin.disableTable(tableName);
	}

	/**
	 * add one record
	 * 
	 * @param tableName
	 * @param cf
	 * @param rowKey
	 * @param qualifier
	 * @param value
	 * @throws IOException
	 */
	public void put(String tableName, String cf, String rowKey,
			String qualifier, String value) {
		try {
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(cf), null, Bytes.toBytes(value));
			hTable.put(put);
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * insert a record
	 */
	public void putMap(String tableName, String cf, String rowKey,
			Map<String, Object> map) {
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);

			Put put = new Put(Bytes.toBytes(rowKey));
			Set<String> set = map.keySet();
			for (String qualifier : set) {
				String value = map.get(qualifier).toString();
				// if(value.length() == 0 || value.equals("")){
				// value = "--";
				// }
				put.add(Bytes.toBytes(cf), Bytes.toBytes(qualifier),
						Bytes.toBytes(value));
			}

			hTable.put(put);
			// System.out.println("insert done");
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isExist(String tableName, String rowKey) {
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowKey));
			if (get == null) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public boolean isExist(String tableName, String rowKey, String cf,
			String qualifier) {
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowKey));
			if (get == null) {
				return false;
			}
			Result ret = hTable.get(get);
			KeyValue kv = ret.getColumnLatest(Bytes.toBytes(cf),
					Bytes.toBytes(qualifier));
			if (kv == null) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public void putImage(String tableName, String rowKey, String cf,
			String qualifier, byte[] bs) {
		try {
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(cf), Bytes.toBytes(qualifier), bs);
			hTable.put(put);
			hTable.close();
		} catch (RetriesExhaustedWithDetailsException e) {
			e.printStackTrace();
		} catch (InterruptedIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void putAllLayer(String tableName, List<String> layerList) {
		try {
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(HBaseUtil.LAY_INFO_KEY));
			String val = "";
			for (String layer : layerList) {
				val = val + layer + HBaseUtil.SEPARATOR;
			}
			val = val.substring(0, val.length() - 1);
			put.add(Bytes.toBytes(HBaseUtil.LAY_INFO_CF), null,
					Bytes.toBytes(val));
			hTable.put(put);
			hTable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAllLayer(String tableName) {
		List<String> allLayerInfo = new ArrayList<String>();
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(HBaseUtil.LAY_INFO_KEY));
			Result rs = hTable.get(get);
			KeyValue kv = rs.getColumnLatest(
					Bytes.toBytes(HBaseUtil.LAY_INFO_CF), null);
			String allLayer = new String(kv.getValue());
			for (String s : allLayer.split(HBaseUtil.SEPARATOR)) {
				allLayerInfo.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allLayerInfo;
	}

	public void putLayerConfigFileName(String tableName, String rowKey,
			String xmlName) {
		try {
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(HBaseUtil.LAY_INFO_CF),
					Bytes.toBytes(HBaseUtil.LAYER_CONFIG_FILE_NAME_QUALIFIER),
					Bytes.toBytes(xmlName));
			System.out.println(xmlName
					+ "-------------------------------------------");
			hTable.put(put);
			hTable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get Layer xml file name
	public String getLayerConfigFileName(String tableName, String rowKey) {
		HTable hTable;
		String ret = "";
		try {
			hTable = new HTable(conf, tableName);

			Get get = new Get(Bytes.toBytes(rowKey));
			Result rs = hTable.get(get);
			String family = HBaseUtil.LAY_INFO_CF;
			String qualifier = HBaseUtil.LAYER_CONFIG_FILE_NAME_QUALIFIER;
			KeyValue kv = rs.getColumnLatest(Bytes.toBytes(family),
					Bytes.toBytes(qualifier));
			ret = new String(kv.getValue());
			// FileUtils.writeByteArrayToFile(new File("d:\1.xml"), ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void putLayerConfigInfo(String tableName, String rowKey,
			String xmlFilePath) {
		try {
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			File file = new File(xmlFilePath);
			InputStream inputStream = new FileInputStream(file);
			byte[] bs = IOUtils.toByteArray(inputStream);
			put.add(Bytes.toBytes(HBaseUtil.LAY_INFO_CF), Bytes
					.toBytes(HBaseUtil.LAYER_CONFIG_FILE_CONTENT_QUALIFIER), bs);
			hTable.put(put);
			hTable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get the config xml content
	// rowkey format HJ1A_CCD2_200812
	// IMPORTANT : THE FORMAT MUST BE LIKE THIS ABOVE OTHERWISE IT WILL BE
	// WRONG!!!!!!!!!!!
	public byte[] getLayerConfigInfo(String tableName, String rowKey) {
		HTable hTable;
		byte[] ret = null;
		try {
			hTable = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowKey));
			Result rs = hTable.get(get);
			String family = HBaseUtil.LAY_INFO_CF;
			String qualifier = HBaseUtil.LAYER_CONFIG_FILE_CONTENT_QUALIFIER;
			KeyValue kv = rs.getColumnLatest(Bytes.toBytes(family),
					Bytes.toBytes(qualifier));
			ret = kv.getValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// FileUtils.writeByteArrayToFile(new File("d:\1.xml"), ret);
		return ret;
	}

	public void putLayerLevelInfo(String tableName, String rowKey,
			List<String> levels) {
		try {
			String val = "";
			for (int i = 0; i < levels.size() - 1; i++) {
				val = val + levels.get(i) + HBaseUtil.SEPARATOR;
			}
			val = val + levels.get(levels.size() - 1);
			HTable hTable = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(HBaseUtil.LAY_INFO_CF),
					Bytes.toBytes(HBaseUtil.LAYER_CONFIG_FILE_LEVEL_QUALIFIER),
					Bytes.toBytes(val));
			hTable.put(put);
			hTable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 0,1,2,3,4,5,6
	public List<String> getLayerInfo(String tableName, String rowKey) {
		List<String> layerList = new ArrayList<String>();
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);

			Get get = new Get(Bytes.toBytes(rowKey));
			Result ret = hTable.get(get);
			String family = HBaseUtil.LAY_INFO_CF;
			String qualifier = HBaseUtil.LAYER_CONFIG_FILE_LEVEL_QUALIFIER;
			KeyValue kv = ret.getColumnLatest(Bytes.toBytes(family),
					Bytes.toBytes(qualifier));
			String layer = new String(kv.getValue());
			for (String s : layer.split(HBaseUtil.SEPARATOR)) {
				layerList.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layerList;
	}

	public byte[] getImage(String tableName, String rowKey, String cf,
			String qualifier) {

		byte[] bs = null;
		HTable hTable;
		try {
			hTable = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowKey));
			Result ret = hTable.get(get);

			List<KeyValue> kvList = ret.getColumn(Bytes.toBytes(cf),
					Bytes.toBytes(qualifier));

			bs = kvList.get(0).getValue();
		//	System.out.println(cf + "_" + qualifier + "_" + rowKey+ ".png Get from HBase");
			// FileUtils.writeByteArrayToFile(new File("d:/1.png"), bs);
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bs;
	}

	public List<Point> rowKeyFilter(String geoHashVal, String tableName)
			throws IOException {
		List<Point> pointList = new ArrayList<Point>();
		HTable hTable = new HTable(conf, tableName);
		Scan scan = new Scan();
		Filter filter = new PrefixFilter(geoHashVal.getBytes());
		scan.setFilter(filter);
		ResultScanner rs = hTable.getScanner(scan);
		for (Result result : rs) {
			String name = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[0])), "utf-8");
			String classes = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[1])), "utf-8");
			String address = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[2])), "utf-8");
			String phone = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[3])), "utf-8");
			String telephone = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[4])), "utf-8");
			String longitudeStr = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[5])), "utf-8");
			String latitudeStr = new String(result.getValue(
					Bytes.toBytes(POIDataUtil.colfamilies),
					Bytes.toBytes(POIDataUtil.colNames[6])), "utf-8");
			double longitude = Double.parseDouble(longitudeStr);
			double latitude = Double.parseDouble(latitudeStr);
			Point point = new Point(name, classes, address, phone, telephone,
					longitude, latitude);
			pointList.add(point);
		}

		return pointList;

	}

	private static void getTableInfo(String tableName) throws Exception {
		HTableDescriptor tableDescriptor = admin.getTableDescriptor(Bytes
				.toBytes(tableName));
		byte[] name = tableDescriptor.getName();
		System.out.println("---------ret-----------");
		System.out.println("table name---" + new String(name));
		System.out.println("Families keys:");
		Set<byte[]> familiesKeys = tableDescriptor.getFamiliesKeys();
		for (byte[] bs : familiesKeys) {
			System.out.println(new String(bs));
		}
		for (HColumnDescriptor hd : tableDescriptor.getColumnFamilies()) {
			System.out.println("column families" + hd.getNameAsString());
		}
	}

	private static void printConfig() {
		Iterator<Entry<String, String>> it = conf.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			System.out.println(entry.getKey() + "----" + entry.getValue());
		}
	}

	public static void main(String[] args) {
		HBaseUtil h = new HBaseUtil();
		System.out.println(h.getLayerConfigFileName("WMSDATA","BlueMarbleMNG_2004-12"));
	}

}
