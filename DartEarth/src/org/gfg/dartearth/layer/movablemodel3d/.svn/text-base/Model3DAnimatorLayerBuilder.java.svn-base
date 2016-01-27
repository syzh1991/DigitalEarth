package org.gfg.dartearth.layer.movablemodel3d;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.layer.model3d.WWModel3D;

public class Model3DAnimatorLayerBuilder {
	public static void build(DartEarthAppFrame frame, Position initailPosition, long period, long initialDate, String model3dPath,
			int modelSize, double coneRadius, Material coneMaterial, float coneOpacity) {
		WWModel3D model3d = new WWModel3D(model3dPath, initailPosition);
		Model3DTrack track = new BasicModel3DTrack(initailPosition, period, initialDate);
		Model3DAnimatorLayer layer = new Model3DAnimatorLayer(frame,track, model3d, modelSize, "3d模型", coneRadius, coneMaterial, coneOpacity);
		
		Model3DAnimatorProperPanel panel=new Model3DAnimatorProperPanel(layer,frame);
		layer.setAnimatorPanel(panel);
		
		frame.getWwd().getModel().getLayers().add(layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		
		
	}

	public static void build(DartEarthAppFrame frame) {
		Position p = new Position(Angle.fromDegrees(45), Angle.fromDegrees(135), 1000000);
		Model3DAnimatorLayerBuilder.build(frame, p, 20*60 * 1000, System.currentTimeMillis(), "D:\\data\\model3d\\globestar\\Globalstar.3ds",
				1000000, 1000000, Material.YELLOW, 0.6f);
	}
}
