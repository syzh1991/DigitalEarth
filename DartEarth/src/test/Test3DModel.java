package test;

import gov.nasa.worldwind.ogc.kml.KMLFolder;
import gov.nasa.worldwind.ogc.kml.KMLLookAt;
import gov.nasa.worldwind.ogc.kml.KMLRoot;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import sun.plugin.dom.views.AbstractView;

import kml3d.KmlFactory;

public class Test3DModel {

	public static void main(String arg[]) throws IOException, XMLStreamException{
		List<Object> kmls=KmlFactory.getKmls("D:\\DEML\\data\\google3D");
		for(Object kml:kmls){
			KMLRoot kmlRoot = KMLRoot.create(kmls.get(0));
			kmlRoot.parse();
			KMLFolder folder = (KMLFolder) kmlRoot.getFields().getValues().toArray()[0];
			KMLLookAt ka = (KMLLookAt) folder.getFields().getValues().toArray()[3];
			double lat = ka.getLatitude();
			double lng=ka.getLongitude();
			//System.out.println(kml.toString());
//		}
		
		//KMLRoot kmlRoot=new KMLRoot(kml);
		//GXLatLongQuad g=new GXLatLongQuad(kml);
//		KMZFile kmz=new KMZFile(kml);
		
	}
		
}
}
