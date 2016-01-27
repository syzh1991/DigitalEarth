package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.GlobeAnnotation;
/**
 * 气泡标注
 * @author 江琳
 *
 */
public class BubbleAnnotation extends GlobeAnnotation{
	/**
	 * 气泡总数
	 */
	private static int count;
	/**
	 * 气泡的纬度
	 */
	private double lat;
	/**
	 * 气泡的经度
	 */
	private double lng;
	/**
	 * 气泡的内容
	 */
	private String content;
	/**
	 * 气泡所在图层的图层名
	 */
	private String layerName;
	/**
	 * 气泡所在位置
	 */
	private Position position;
	/**
	 * 构造函数
	 * @param text
	 * @param position
	 */
	public BubbleAnnotation(String text, Position position){
		super(text,position);
		count++;
		layerName="气泡"+count;
		this.lat=position.getLatitude().getDegrees();
		this.lng=position.getLongitude().getDegrees();
		this.position=position;
		
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	


 

}
