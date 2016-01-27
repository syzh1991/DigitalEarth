package org.gfg.poi;

/*
 *private double longitude;// ����
 *private double latitude;// γ��
 */
public class Point {

	private double longitude;// ����
	private double latitude;// γ��
	private String name;// ����
	private String classes;// ���
	private String address;// ��ַ
	private String phone;// �绰
	private String telephone;// �ֻ�

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