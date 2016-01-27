package org.gfg.dartearth.core;

import java.awt.Component;
import java.lang.reflect.Method;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.gfg.hbase.HBaseUtil;
import org.gfg.metadata.MetaDataDao;
import org.gfg.metadata.model.ESearchField;


public class MetaDataCellRenderer  extends JLabel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5728802812524860313L;

	/*
	 * 类CellRenderer继承JLabel并实作ListCellRenderer.由于我们利用JLabel易于插图的特性，
	 * 因此CellRenderer继承了JLabel可让JList中的每个项目都视为是一个JLabel.
	 */
	MetaDataCellRenderer() {
		setOpaque(true);
	}

	/* 从这里到结束：实作getListCellRendererComponent()方法 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		/*
		 * 我们判断list.getModel().getElementAt(index)所返回的值是否为null,例如上个例子中，若JList的标题是
		 * "你玩过哪些数据库软件"，则index>=4的项目值我们全都设为null.而在这个例子中，因为不会有null值，因此有没有加上这个判
		 * 断并没有关系.
		 */
		if (value != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			String showVal = "<html>";
//			for (String key : map.keySet()) {
//				showVal += key + "---" + map.get(key) + "<br>";
//				// System.out.println(key+"---"+map.get(key));
//			}
			
			showVal += "卫星:"+map.get(ESearchField.SATELLITENAME.getField())+"<br>";
			showVal += "传感器:"+map.get(ESearchField.SENSOR.getField())+"<br>";
			showVal += "条代号:"+map.get(ESearchField.SCENEPATH.getField())+"<br>";
			showVal += "行编号:"+map.get(ESearchField.SCENEROW.getField())+"<br>";
			showVal += "产品号:"+map.get(ESearchField.PRODUCTID.getField())+"<br>";
			showVal += "景号:"+map.get(ESearchField.SCENEID.getField())+"<br>";
			showVal += "<br></html>";
			
			setText(showVal);
			
			// ////////////////////////////////////////////
			String keyID = map.get(ESearchField._ID.getField()).toString();
			HBaseUtil hbaseUtil = new HBaseUtil();
			byte[] imageByte = hbaseUtil.getImage(
					MetaDataDao.METADATA_TABLE_MDS, keyID,
					MetaDataDao.METADATA_TABLE_QUALIFIER_A,
					MetaDataDao.METADATA_THUMIMAGE);
			String imageDataString = new String(imageByte);//ImageData.getData();
			
			// ////////////////////////////////////////////
			//元数据是加密的图片必须这样处理
//			Class<?> clazz;
//			Object retObj = null;
//			try {
//				clazz = Class
//						.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
//
//				Method mainMethod = clazz.getMethod("decode", String.class);
//				mainMethod.setAccessible(true);
//				retObj = mainMethod.invoke(null, imageDataString);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			// ////////////////////////////////////////////
//			byte[] imageData =  (byte[])retObj;
			/////
			byte[] imageData =  imageByte;
			ImageIcon imageIcon =new ImageIcon(imageData);
			imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, 12));
			setIcon(imageIcon);
			
			setVerticalTextPosition(JLabel.TOP);
			setHorizontalTextPosition(JLabel.RIGHT);
			setIconTextGap(50);
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}
}
