package org.gfg.poi;

/*
 *private double longitude;// 经度
 *private double latitude;// 纬度
 */
public class Point {

	private double longitude;// 经度
	private double latitude;// 纬度
	private String name;// 名称
	private String classes;// 类别
	private String address;// 地址
	private String phone;// 电话
	private String telephone;// 手机

	public Point() {
	}

	public Point(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Point( String name,
			String classes, String address, String phone, String telephone,double longitude, double latitude) {
		this.name = name;
		this.classes = classes;
		this.address = address;
		this.phone = phone;
		this.telephone = telephone;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return longitude + " " + latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}