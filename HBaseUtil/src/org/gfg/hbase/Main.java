package org.gfg.hbase;

import java.util.ArrayList;
import java.util.List;

import org.gfg.hbase.HBaseUtil;

public class Main {
	public static void main(String[] args) throws Exception {
		// printConfig();
		HBaseUtil hu = new HBaseUtil();
		String[] colfamilies = { "ALL_ALL", "GF1_WFV1", "GF1_WFV2", "GF1_WFV3",
				"GF1_WFV4", "GF1_PMS1", "GF1_PMS2", "BlueMarbleMNG",
				"layerInfo" };
		// String qualifier = "2008-12";

		// 存储元数据信息
		// hu.creatTable(HBaseUtil.TABLE_NAME, colfamilies);

		List<String> layerList = new ArrayList<String>();
		//layerList.add("BlueMarbleMNG_2004-12");
		//layerList.add("Test_Test_20140317");
		//layerList.add("Test_Test_20140314");
		//layerList.add("Test_Test_20140711");
		
		layerList.add("ALL_ALL_20140412");
		layerList.add("ALL_ALL_20140413");
		layerList.add("ALL_ALL_20140429");
		layerList.add("ALL_ALL_20140501");
		layerList.add("ALL_ALL_20140507");
		layerList.add("ALL_ALL_20140708");
		layerList.add("ALL_ALL_20140709");
		layerList.add("ALL_ALL_20140710");
		layerList.add("ALL_ALL_20140711");
		layerList.add("ALL_ALL_20140716");
		layerList.add("ALL_ALL_20140717");
		layerList.add("ALL_ALL_20140718");
		layerList.add("ALL_ALL_20140721");
		layerList.add("ALL_ALL_20140722");
		//layerList.add("GF1_PMS1_20140501236313");
		//layerList.add("GF1_WFV1_20140711272770");
		//layerList.add("GF1_WFV2_20140412201973");
		//layerList.add("GF1_WFV2_20140709271108");
		//layerList.add("GF1_WFV2_20140709271111");
		//layerList.add("GF1_WFV2_20140716278684");
		//layerList.add("GF1_WFV2_20140716278685");
		//layerList.add("GF1_WFV2_20140716278691");
		//layerList.add("GF1_WFV2_20140716278692");
		//layerList.add("GF1_WFV2_20140716278695");
		//layerList.add("GF1_WFV2_20140717278105");
		//layerList.add("GF1_WFV2_20140718278746");
		//layerList.add("GF1_WFV2_20140718278748");
		//layerList.add("GF1_WFV2_20140718278750");
		//layerList.add("GF1_WFV2_20140718278757");
		//layerList.add("GF1_WFV2_20140721281107");
		//layerList.add("GF1_WFV2_20140722282732");
		//layerList.add("GF1_WFV3_20140429214079");
		//layerList.add("GF1_WFV4_20140413202803");
		//layerList.add("GF1_WFV4_20140506238811");
		//layerList.add("GF1_WFV4_20140506238812");
		//layerList.add("GF1_WFV4_20140708270567");
		//layerList.add("GF1_WFV4_20140708270568");
		//layerList.add("GF1_WFV4_20140708270569");
		//layerList.add("GF1_WFV4_20140708270570");
		//layerList.add("GF1_WFV4_20140708270571");
		//layerList.add("GF1_WFV4_20140708270572");
		//layerList.add("GF1_WFV4_20140709271081");
		//layerList.add("GF1_WFV4_20140709271082");
		//layerList.add("GF1_WFV4_20140709271083");
		layerList.add("GF1_PMS1_20130712121522");
		layerList.add("GF1_PMS1_20131121114541");
		layerList.add("GF1_PMS2_2013081469940");
		layerList.add("GF1_PMS2_2013081469958");
		layerList.add("GF1_PMS2_2013081469967");
		layerList.add("GF1_PMS2_20130627105500");
		layerList.add("GF1_PMS2_20130725174670");
		layerList.add("GF1_PMS2_20130806174668");
		
		//layerList.add("GF1_PMS2_20130806");
		//layerList.add("GF1_PMS2_20130808");
		// layerList.add("GF1_PMS2_20130807");
		//layerList.add("Test_Test_20130806");
		// layerList.add("Test_Test_20130807");
		
		layerList.add("GF2_PMS1_20141104523183");
		layerList.add("GF2_PMS1_20141104523181");
		layerList.add("GF2_PMS2_20141104523185");
		layerList.add("GF2_PMS2_20141104523184");
		layerList.add("GF2_PMS2_20141104523182");
		hu.putAllLayer(HBaseUtil.TABLE_NAME, layerList);

		String name2 = "20141104523182";
		String name3 = "GF2_PMS2";
		String path = "I:\\All100\\zjG2\\GF2_PMS2_E120.4_N30.4_20141104_L2A0000523182\\";
		String path2 = "I:\\All100\\zjG2\\GF2_PMS2_E120.4_N30.4_20141104_L2A0000523182\\";
		String name = name3 + "_" + name2;
		String name1 = name3;

		String rowKey = name;
		String xmlName = name + ".xml";
		hu.putLayerConfigFileName(HBaseUtil.TABLE_NAME, rowKey, xmlName);
		String xmlFilePath = path + xmlName;

		hu.putLayerConfigInfo(HBaseUtil.TABLE_NAME, rowKey, xmlFilePath);

		List<String> levels = new ArrayList<String>();
		levels.add("0");
		levels.add("1");
		levels.add("2");
		levels.add("3");
		levels.add("4");
		
		hu.putLayerLevelInfo(HBaseUtil.TABLE_NAME, rowKey, levels);
		// ////////////////////////////////////////

		TileDataOper tdo = new TileDataOper();

		tdo.storeInDB(path2 + name, name1, name2);

	}
}
