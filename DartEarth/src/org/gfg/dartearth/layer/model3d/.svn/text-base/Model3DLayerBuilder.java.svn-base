package org.gfg.dartearth.layer.model3d;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class Model3DLayerBuilder {

	public static void buildModel3DLayer(DartEarthAppFrame frame, String filePath) {
		// WWModel3D model3d;
		// Model3DLayer layer;
		Model3DLayer layer = new Model3DLayer();
		layer.setName("3d模型");
		layer.setMaitainConstantSize(true);
		layer.setSize(1000000);
		//System.out.println(filePath);
		WWModel3D model3d = new WWModel3D(filePath, new Position(Angle.fromDegrees(30), Angle.fromDegrees(-130), 1000000));
		layer.setModel(model3d);
		frame.getWwd().getModel().getLayers().add(layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
	//	layer.setSize(10);
	}
}
