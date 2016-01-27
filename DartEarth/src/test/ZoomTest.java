package test;

import org.gfg.dartearth.core.DartEarthAppFrame;

import gov.nasa.worldwind.geom.Angle;

public class ZoomTest {
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {

		DartEarthAppFrame de=new DartEarthAppFrame();
		de.gotoLatLon(90, 80);
	}
}
