package org.gfg.dartearth.feature.annotion;

/**
 * 图钉标注信息
 * 
 * @author 陈亮
 * 
 */
public class PushPinAnnotation {
	/**
	 * 图钉总数
	 */
	private static int count;
	/**
	 * 图钉的纬度
	 */
	private double lat;
	/**
	 * 图钉的经度
	 */
	private double lng;
	/**
	 * 图钉的标题
	 */
	private String title;
	/**
	 * 图钉的内容
	 */
	private String content;
	/**
	 * 图钉内容的Url。图钉的内容可以由用户输入，也可以填入一个Url，系统会去自己下载。
	 */
	private String url;
	/**
	 * 是否使用URL，用于区别用户输入的内容与URL
	 */
	private boolean useURL;
	/**
	 * 图层名
	 */
	private String layerName;

	/**
	 * 构造函数
	 * 
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 */
	public PushPinAnnotation(double lat, double lng) {
		count++;
		layerName = "图钉" + count;
		this.lat = lat;
		this.lng = lng;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUseURL() {
		return useURL;
	}

	public void setUseURL(boolean useURL) {
		this.useURL = useURL;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

}
