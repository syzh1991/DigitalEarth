package org.gfg.dartearth.layer.movablemodel3d;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.util.Date;

public class BasicModel3DTrack implements Model3DTrack {

	private long initialTime;
	private Position initialPosition;
	private long period;

	public BasicModel3DTrack(Position initialPostion, long period) {
		this.initialPosition = initialPostion;
		this.period = period;
		this.initialTime = new Date().getTime();
	}
	
	public BasicModel3DTrack(Position initialPostion, long period,long initialTime) {
		this.initialPosition = initialPostion;
		this.period = period;
		this.initialTime = initialTime;
	}

	private Position getPostion(long time) {
		long dTime = time - initialTime;
		double dLat = ((double)360 / period) * dTime;
		dLat = dLat % 360;
		double lat = initialPosition.getLatitude().getDegrees();
		double lng = initialPosition.getLongitude().getDegrees();
		double alt = initialPosition.getAltitude();
		return caculatePosition(lat, lng, dLat, alt);
	}

	private Position caculatePosition(double lat, double lng, double dLat, double alt) {
		// if (lat > 0 && lat <= 90) {
		// lat=lat+dLat;
		// lat=lat%360;
		// if(lat>0&&lat<=90){
		// //do nothing
		// }
		// else if(lat>90&&lat<=180){
		// lat=180-lat;
		// if(lng>0){
		// lng=lng-180;
		// }else{
		// lng=180+lng;
		// }
		// }else if(lat>180&&lat<=270){
		// lat=180-lat;
		// if(lng>0){
		// lng=lng-180;
		// }else{
		// lng=180+lng;
		// }
		// }else{
		// lat=lat-360;
		// }
		// } else if (lat <= 0 && lat >= -90) {
		// lat=lat+dLat;
		// lat=lat%360;
		//
		// if(lat>-90&&lat<=0){
		// //do nothing
		// }else if(lat>0&&lat<=90){
		// //do nothing
		// }
		// else if(lat>90&&lat<=180){
		// lat=180-lat;
		// if(lng>0){
		// lng=lng-180;
		// }else{
		// lng=180+lng;
		// }
		// }else if(lat>180&&lat<=270){
		// lat=180-lat;
		// if(lng>0){
		// lng=lng-180;
		// }else{
		// lng=180+lng;
		// }
		// }else{
		// lat=lat-360;
		// }
		// }
		lat = lat + dLat;
		lat = lat % 360;
		if (lat > -90 && lat <= 0) {
			// do nothing
		} else if (lat > 0 && lat <= 90) {
			// do nothing
		} else if (lat > 90 && lat <= 180) {
			lat = 180 - lat;
			if (lng > 0) {
				lng = lng - 180;
			} else {
				lng = 180 + lng;
			}
		} else if (lat > 180 && lat <= 270) {
			lat = 180 - lat;
			if (lng > 0) {
				lng = lng - 180;
			} else {
				lng = 180 + lng;
			}
		} else {
			lat = lat - 360;
		}
		Position p = new Position(Angle.fromDegrees(lat), Angle.fromDegrees(lng), alt);
		return p;
	}

	@Override
	public Position getPosition(Date time) {
		return this.getPostion(time.getTime());
	}

	@Override
	public Position getCurrentPosition() {
		long currentTime = System.currentTimeMillis();
		return this.getPostion(currentTime);
	}

//	public static void main(String[] str) {
//		System.out.println((-45) % 360);
//	}
}
