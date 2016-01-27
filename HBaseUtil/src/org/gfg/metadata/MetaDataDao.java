package org.gfg.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.gfg.hbase.HBaseUtil;
import org.gfg.metadata.model.BasicDBObject;
import org.gfg.metadata.model.DBObject;
import org.gfg.metadata.model.EIndexField;
import org.gfg.metadata.model.ESearchField;
import org.gfg.util.metadata.JSONUtil;
import org.gfg.util.metadata.ResovleXml;

public class MetaDataDao {
	public static final String METADATA_TABLE_MDS = "MDS";
	public static final String METADATA_TABLE_QUALIFIER_A = "a";
	public static final String METADATA_TABLE_QUALIFIER_B = "b";
	public static final String METADATA_TABLE_SEARCH = "SEARCH";
	public static final String METADATA_THUMIMAGE = "ThumImage";

	public void insertIntoMds(String xmlPath, String key) {
		ResovleXml resovel = new ResovleXml();
		Map<String, Object> metaData = resovel.getXMLElement(xmlPath);
		File file = new File(xmlPath);
		String fileName = file.getName();
		// //////////////////////
		String level;
		HBaseUtil hbu = new HBaseUtil();
		if (xmlPath.contains("LEVEL1A")) {
			level = "LEVEL1A";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.lastIndexOf('.'));
		} else if (xmlPath.contains("LEVEL0_SCENE")) {
			level = "LEVEL0_SCENE";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.indexOf('.'))
			// + "LEVEL0_SCENE";
			// 添加产品级别
			metaData.put("ProductLevel", "LEVEL0_SCENE");
		} else if (xmlPath.contains("REAL1A")) {
			String fullName = file.getAbsolutePath();
			String preName = fullName.substring(0,fullName.lastIndexOf("."));
			level = "LEVEL1A";
			File file1 = new File(preName+"_thumb.jpg");
			InputStream inputStream1;
			byte[] bs1 = null;
			try {
				inputStream1 = new FileInputStream(file1);
				 bs1 = IOUtils.toByteArray(inputStream1);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String qualifier1 = "ThumImage";
			hbu.putImage(MetaDataDao.METADATA_TABLE_MDS,key,
					MetaDataDao.METADATA_TABLE_QUALIFIER_A, qualifier1,bs1);
			File file2 = new File(preName+".jpg");
			InputStream inputStream2;
			byte[] bs2 = null;
			try {
				inputStream2 = new FileInputStream(file2);
				bs2 = IOUtils.toByteArray(inputStream2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String qualifier2 = "BrowseImage";
			hbu.putImage(MetaDataDao.METADATA_TABLE_MDS,key,
					MetaDataDao.METADATA_TABLE_QUALIFIER_A, qualifier2,bs2);
		} else if (xmlPath.contains("REAL2A")) {
			String fullName = file.getAbsolutePath();
			String preName = fullName.substring(0,fullName.lastIndexOf("."));
			level = "LEVEL2A";
			File file1 = new File(preName+"_thumb.jpg");
			InputStream inputStream1;
			byte[] bs1 = null;
			try {
				inputStream1 = new FileInputStream(file1);
				 bs1 = IOUtils.toByteArray(inputStream1);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String qualifier1 = "ThumImage";
			hbu.putImage(MetaDataDao.METADATA_TABLE_MDS,key,
					MetaDataDao.METADATA_TABLE_QUALIFIER_A, qualifier1,bs1);
			File file2 = new File(preName+".jpg");
			InputStream inputStream2;
			byte[] bs2 = null;
			try {
				inputStream2 = new FileInputStream(file2);
				bs2 = IOUtils.toByteArray(inputStream2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String qualifier2 = "BrowseImage";
			hbu.putImage(MetaDataDao.METADATA_TABLE_MDS,key,
					MetaDataDao.METADATA_TABLE_QUALIFIER_A, qualifier2,bs2);
		} else {
			level = "LEVEL2";
			return;
		}
		// //////////////////////
		// if (!hbu.isExist(MetaDataDao.METADATA_TABLE_MDS, key)) {
		hbu.putMap(MetaDataDao.METADATA_TABLE_MDS,
				MetaDataDao.METADATA_TABLE_QUALIFIER_A, key, metaData);
		System.out.println(fileName);
		// }
		System.out.println("**************" + fileName + "***already in******");
	}

	// return the key
	public String insertIntoSearch(String xmlPath, ESUtil esm) {
		ResovleXml resovel = new ResovleXml();
		Map<String, Object> metaData = resovel.getXMLElement(xmlPath);
		File file = new File(xmlPath);
		String fileName = file.getName();
		// //////////////////////
		String level;
		String key = null;
		Map<String, Object> finalMap = new HashMap<String, Object>();
		if (xmlPath.contains("LEVEL1A")) {
			level = "LEVEL1A";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.lastIndexOf('.'));
			String rulePath = System.getProperty("user.dir")
					+ "/res/level1.json";
			Map<String, String> transformMap = JSONUtil
					.getMapFromJSON(rulePath);
			for (String midKey : transformMap.keySet()) {
				finalMap.put(transformMap.get(midKey), metaData.get(midKey));
			}
		} else if (xmlPath.contains("REAL1A")) {
			level = "LEVEL1A";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.lastIndexOf('.'));
			String rulePath = System.getProperty("user.dir")
					+ "/res/reallevel1.json";
			Map<String, String> transformMap = JSONUtil
					.getMapFromJSON(rulePath);
			for (String midKey : transformMap.keySet()) {
				finalMap.put(transformMap.get(midKey), metaData.get(midKey));
			}
		} else if (xmlPath.contains("LEVEL0_SCENE")) {
			level = "LEVEL0_SCENE";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.indexOf('.'))
			// + "LEVEL0_SCENE";
			// 添加产品级别
			// TODO O级数据没有产品id此处用景id替代
			metaData.put("ProductID", metaData.get("SceneID"));
			metaData.put("nresval", "0");
			metaData.put("ProductLevel", level);
			String rulePath = System.getProperty("user.dir")
					+ "/res/level0.json";
			Map<String, String> transformMap = JSONUtil
					.getMapFromJSON(rulePath);
			for (String midKey : transformMap.keySet()) {
				finalMap.put(transformMap.get(midKey), metaData.get(midKey));
			}

		}else if (xmlPath.contains("REAL2A")) {
			level = "LEVEL2A";
			// key = fileName.substring(fileName.lastIndexOf('_') + 1,
			// fileName.lastIndexOf('.'));
			String rulePath = System.getProperty("user.dir")
					+ "/res/reallevel1.json";
			Map<String, String> transformMap = JSONUtil
					.getMapFromJSON(rulePath);
			for (String midKey : transformMap.keySet()) {
				finalMap.put(transformMap.get(midKey), metaData.get(midKey));
			}
		} else {
			level = "LEVEL2";
			// TODO
		}
		DBObject dbObj = mapToDataSource(finalMap);
		Map<String, Object> hbaseMapData = dbObj.toMap();
		key = hbaseMapData.get(ESearchField.SATELLITENAME.getField())
				.toString()
				+ "_"
				+ hbaseMapData.get(ESearchField.SENSOR.getField()).toString()
				+ "_"
				+ hbaseMapData.get(ESearchField.PRODUCTDATE.getField())
						.toString().replace("-", "")
				+ hbaseMapData.get(ESearchField.PRODUCTID.getField())
						.toString();
		/*
		 * HBaseUtil hbu = new HBaseUtil();
		 * hbu.putMap(MetaDataDao.METADATA_TABLE_SEARCH,
		 * MetaDataDao.METADATA_TABLE_QUALIFIER_A, key, hbaseMapData);
		 */
		// /////////////////////////////////
		DBObject centerPosition = new BasicDBObject();

		centerPosition.put(EIndexField.SCENECENTERLONG.getField(),
				dbObj.get(EIndexField.SCENECENTERLONG.getField()));
		centerPosition.put(EIndexField.SCENECENTERLAT.getField(),
				dbObj.get(EIndexField.SCENECENTERLAT.getField()));
		dbObj.put(EIndexField.GPS.getField(), centerPosition);
		// /////////////////////////////////

		dbObj = addGeoMapping(dbObj);
		Map<String, Object> indexMap = dbObj.toMap();
		esm.addRecord(indexMap, key);
		// esm.buildClient()

		return key;
		// /////
	}

	public static void main(String[] args) {
		ESUtil esm = new ESUtil();
		esm.setClient(esm.buildClient());
	//	esm.buildIndex();

		MetaDataDao mdd = new MetaDataDao();
		File fileDir = new File("D:\\xx\\REAL2A");
		File[] secDir = fileDir.listFiles();
		int index = 0;
	//	esm.setClient(esm.buildClient());
		for (File sec : secDir) {
			String tmpName = sec.getName();
		//	System.out.println(tmpName.substring(tmpName.lastIndexOf('.')+1));
			if(!tmpName.substring(tmpName.lastIndexOf('.')+1).equals("xml")){
				continue;
			}
			String xmlPath = sec.getAbsolutePath();
			String key = mdd.insertIntoSearch(xmlPath, esm);
			System.out.println(key);
			mdd.insertIntoMds(xmlPath, key);
			index++;
			System.out.println(index + "@@@@" + sec.getName() + "####insert");
			// File[] thridDir = sec.listFiles();
			// for (File thrid : thridDir) {
			// for (File fourDir : thrid.listFiles()) {
			// for (File file : fourDir.listFiles()) {
			// String xmlPath = file.getAbsolutePath();
			// String key = mdd.insertIntoSearch(xmlPath, esm);
			// mdd.insertIntoMds(xmlPath,key);
			// index++;
			// System.out.println(index + "@@@@" + file.getName()
			// + "####insert");
			// }
			// }
			// }
		}
		esm.getClient().close();
	}

	// TODO change gps to geoPoint type
	private DBObject addGeoMapping(DBObject data) {
		Object p = data.get(EIndexField.GPS.getField());
		if (p != null && (p instanceof DBObject)) {
			DBObject gps = (DBObject) p;
			double lat = (Double) gps
					.get(EIndexField.SCENECENTERLAT.getField());
			double lon = (Double) gps.get(EIndexField.SCENECENTERLONG
					.getField());
			GeoPoint point = new GeoPoint(lat, lon);
			data.put(EIndexField.GPS.getField(), point);
		}
		return data;
	}

	private DBObject mapToDataSource(Map<String, Object> map) {
		DBObject object = new BasicDBObject(map);
		DBObject insertObject = new BasicDBObject();
		// 将景行、景路、平均云量转为DOUBLE类型
		if (object.get(EIndexField.SCENEPATH.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.SCENEPATH
						.getField())).trim())) {
			String scenePath = (String) object.get(EIndexField.SCENEPATH
					.getField());
			scenePath = scenePath.replaceAll(",", "");

			double value = Double.parseDouble(scenePath);
			object.put(EIndexField.SCENEPATH.getField(), value);
		}
		if (object.get(EIndexField.SCENEROW.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.SCENEROW
						.getField())).trim())) {
			String sceneRow = (String) object.get(EIndexField.SCENEROW
					.getField());
			sceneRow = sceneRow.replaceAll(",", "");

			double value = Double.parseDouble(sceneRow);
			object.put(EIndexField.SCENEROW.getField(), value);
		}
		if (object.get(EIndexField.AVGCLOUDCOVER.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.AVGCLOUDCOVER
						.getField())).trim())) {
			double value = Double.parseDouble((String) object
					.get(EIndexField.AVGCLOUDCOVER.getField()));
			object.put(EIndexField.AVGCLOUDCOVER.getField(), value);
		}
		// 将SCENEID转为LONG
		if (object.get(EIndexField.SCENEID.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.SCENEID
						.getField())).trim())) {
			String sceneID = (String) object
					.get(EIndexField.SCENEID.getField());
			sceneID = sceneID.replaceAll(",", "");
			Long value = Long.parseLong(sceneID);
			object.put(EIndexField.SCENEID.getField(), value);
		}
		// 把分辨率保存为double类型，否则按照分辨率搜索不出数据
		if (object.get(EIndexField.NRESVAL.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.NRESVAL
						.getField())).trim())) {
			double value = Double.parseDouble((String) object
					.get(EIndexField.NRESVAL.getField()));
			object.put(EIndexField.NRESVAL.getField(), value);
		}
		// 把时间保存为Long类型，
		if (object.get(EIndexField.PRODUCTDATE.getField()) != null
				&& !"".equals(((String) object.get(EIndexField.PRODUCTDATE
						.getField())).trim())) {

			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			String valueString = (String) object.get(EIndexField.PRODUCTDATE
					.getField());
			valueString = valueString.substring(0, 10);
			// Date date = null;
			// try {
			// date = sdf.parse(valueString);
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			// Long timeValue = DataParser.parseToLong((String) object
			// .get(EIndexField.PRODUCTDATE.getField()));
			object.put(EIndexField.PRODUCTDATE.getField(), valueString);
		}
		insertObject.putAll(object);
		// 以下8个字段不能为空
		if (object.get(EIndexField.DATAUPPERLEFTLAT.getField()) == null
				|| object.get(EIndexField.DATAUPPERLEFTLONG.getField()) == null
				|| object.get(EIndexField.DATALOWERLEFTLAT.getField()) == null
				|| object.get(EIndexField.DATALOWERLEFTLONG.getField()) == null
				|| object.get(EIndexField.DATAUPPERRIGHTLAT.getField()) == null
				|| object.get(EIndexField.DATAUPPERRIGHTLONG.getField()) == null
				|| object.get(EIndexField.DATALOWERRIGHTLAT.getField()) == null
				|| object.get(EIndexField.DATALOWERRIGHTLONG.getField()) == null)
			return null;

		try {
			double upLeftLat = Double.parseDouble((String) object
					.get(EIndexField.DATAUPPERLEFTLAT.getField()));
			insertObject
					.put(EIndexField.DATAUPPERLEFTLAT.getField(), upLeftLat);

			double upLeftLng = Double.parseDouble((String) object
					.get(EIndexField.DATAUPPERLEFTLONG.getField()));
			insertObject.put(EIndexField.DATAUPPERLEFTLONG.getField(),
					upLeftLng);

			double downLeftLat = Double.parseDouble((String) object
					.get(EIndexField.DATALOWERLEFTLAT.getField()));
			insertObject.put(EIndexField.DATALOWERLEFTLAT.getField(),
					downLeftLat);

			double downLeftLng = Double.parseDouble((String) object
					.get(EIndexField.DATALOWERLEFTLONG.getField()));
			insertObject.put(EIndexField.DATALOWERLEFTLONG.getField(),
					downLeftLng);

			double upRightLat = Double.parseDouble((String) object
					.get(EIndexField.DATAUPPERRIGHTLAT.getField()));
			insertObject.put(EIndexField.DATAUPPERRIGHTLAT.getField(),
					upRightLat);

			double upRightLng = Double.parseDouble((String) object
					.get(EIndexField.DATAUPPERRIGHTLONG.getField()));
			insertObject.put(EIndexField.DATAUPPERRIGHTLONG.getField(),
					upRightLng);

			double downRightLat = Double.parseDouble((String) object
					.get(EIndexField.DATALOWERRIGHTLAT.getField()));
			insertObject.put(EIndexField.DATALOWERRIGHTLAT.getField(),
					downRightLat);

			double downRightLng = Double.parseDouble((String) object
					.get(EIndexField.DATALOWERRIGHTLONG.getField()));
			insertObject.put(EIndexField.DATALOWERRIGHTLONG.getField(),
					downRightLng);

			// 不知道计算这四个有什么用，不过还是留着吧
			double maxLat = Math.max(upLeftLat,
					Math.max(upRightLat, Math.max(downLeftLat, downRightLat)));
			double maxLng = Math.max(upLeftLng,
					Math.max(upRightLng, Math.max(downLeftLng, downRightLng)));
			double minLat = Math.min(upLeftLat,
					Math.min(upRightLat, Math.min(downLeftLat, downRightLat)));
			double minLng = Math.min(upLeftLng,
					Math.min(upRightLng, Math.min(downLeftLng, downRightLng)));

			insertObject.put(EIndexField.MAXLAT.getField(), maxLat);
			insertObject.put(EIndexField.MAXLNG.getField(), maxLng);
			insertObject.put(EIndexField.MINLAT.getField(), minLat);
			insertObject.put(EIndexField.MINLNG.getField(), minLng);

			// /增加一个中心点的位置，用于建地理位置的索引
			double centerlng = 0.0;
			double centerlat = 0.0;
			if (object.get(EIndexField.SCENECENTERLONG.getField()) != null
					&& object.get(EIndexField.SCENECENTERLAT.getField()) != null) {
				centerlng = Double.parseDouble((String) object
						.get(EIndexField.SCENECENTERLONG.getField()));
				centerlat = Double.parseDouble((String) object
						.get(EIndexField.SCENECENTERLAT.getField()));
			} else {
				centerlng = (maxLng + minLng) / 2;
				centerlat = (maxLat + minLat) / 2;
			}
			insertObject.put(EIndexField.SCENECENTERLONG.getField(), centerlng);
			insertObject.put(EIndexField.SCENECENTERLAT.getField(), centerlat);
			/*
			 * DBObject centerPosition = new BasicDBObject();
			 * 
			 * centerPosition.put(EIndexField.SCENECENTERLONG.getField(),
			 * centerlng); centerPosition
			 * .put(EIndexField.SCENECENTERLAT.getField(), centerlat);
			 * 
			 * insertObject.put(EIndexField.GPS.getField(), centerPosition);
			 */

		} catch (Exception e) {
			// TODO: handle exception
		}
		return insertObject;
	}

}
