package org.gfg.dartearth.feature.annotion;

public class MutimediaAnnotationVO {
	private static int count;
	private double lat;
	private double lng;
	private String content;
	private String layerName;
	private String source;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public MutimediaAnnotationVO(double lat ,double lng){
		count++;
		layerName="多媒体气泡"+count;
		this.lat=lat;
		this.lng=lng;
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
