package org.gfg.dartearth.layer.kml3d;


import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.layer.movablemodel3d.Model3DAnimatorLayer;

public class Kml3DLayerBuilder {
	
	public static void buildDefaultLayer(DartEarthAppFrame appFrame) {
		
		Kml3DLayer layer = new Kml3DLayer(appFrame);
		layer.setName("3d模型");
		appFrame.getWwd().getModel().getLayers().add(layer);
		appFrame.getLayerPanelDialog().getLayerPanel().update();
	}
	
}
