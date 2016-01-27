package org.gfg.dartearth.layer.movablemodel3d;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Cone;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.layer.model3d.Model3DLayer;
import org.gfg.dartearth.layer.model3d.WWModel3D;

public class Model3DAnimatorLayer extends Model3DLayer {

	private Model3DTrack track;
	private Cone cone;
	private WWModel3D model;
	private Model3DAnimatorProperPanel animatorPanel;
	private static WWModel3D defaultModel3d=null;

	public static WWModel3D getDefaultModel3d() {
		return defaultModel3d;
	}

	public static void setDefaultModel3d(WWModel3D defaultModel3d) {
		Model3DAnimatorLayer.defaultModel3d = defaultModel3d;
	}

	public Model3DAnimatorProperPanel getAnimatorPanel() {
		return animatorPanel;
	}

	public void setAnimatorPanel(Model3DAnimatorProperPanel animatorPanel) {
		this.animatorPanel = animatorPanel;
	}

	public Model3DAnimatorLayer(DartEarthAppFrame frame, Model3DTrack track, WWModel3D model3d, int modelSize, String layerName,
			double coneRadius, Material coneMaterial, float coneOpacity) {
		this.setModel(model3d);
		this.model = model3d;
		this.track = track;
		this.setName(layerName);
		this.setMaitainConstantSize(true);
		this.setSize(modelSize);

		this.createModelAttachments(model3d, modelSize, coneRadius, coneMaterial, coneOpacity);
	}

	private void createModelAttachments(WWModel3D model3d, int modelSize, double coneRadius, Material coneMaterial, float coneOpacity) {
		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setInteriorMaterial(coneMaterial);
		attrs.setInteriorOpacity(coneOpacity);
		attrs.setEnableLighting(true);
		attrs.setOutlineMaterial(Material.RED);
		attrs.setOutlineWidth(2d);
		attrs.setDrawInterior(true);
		attrs.setDrawOutline(false);

		Position modelCurrentPosition = model3d.getPosition();
		Position coneCenterPostion = new Position(modelCurrentPosition.getLatitude(), modelCurrentPosition.getLongitude(),
				modelCurrentPosition.getAltitude() / 3);
		this.cone = new Cone(coneCenterPostion, coneRadius, model3d.getPosition().getAltitude() / 3 * 2, coneRadius);
		cone.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
		//cone.setValue(AVKey.DISPLAY_NAME, "Cone with equal axes, RELATIVE_TO_GROUND altitude mode");
		cone.setAttributes(attrs);
		cone.setHighlightAttributes(attrs);

		this.addRenderable(cone);
	}

	public void refresh() {
		Position modelCurrentPosition = track.getCurrentPosition();
		this.model.setPosition(modelCurrentPosition);
		Position coneCenterPostion = new Position(modelCurrentPosition.getLatitude(), modelCurrentPosition.getLongitude(),
				modelCurrentPosition.getAltitude() / 3);
		this.cone.setCenterPosition(coneCenterPostion);
	}

}
