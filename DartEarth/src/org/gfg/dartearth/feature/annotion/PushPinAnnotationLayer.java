package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.AbstractBrowserBalloon;
import gov.nasa.worldwind.render.BalloonAttributes;
import gov.nasa.worldwind.render.BasicBalloonAttributes;
import gov.nasa.worldwind.render.GlobeBrowserBalloon;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Size;

import java.awt.Font;

/**
 * 图钉标注图层
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class PushPinAnnotationLayer extends AnnotationLayer {

	private PushPinAnnotation annotation;
	private PointPlacemark mark;
	private AbstractBrowserBalloon balloon;
	private PushPinAnnotationProperPanel panel;
	private Font titleFont = new Font("黑体", Font.BOLD, 16);

	/**
	 * 根据annotation来创建标注图层
	 * 
	 * @param annotation
	 *            标注内容，详见PushPinAnnotation类
	 */
	public PushPinAnnotationLayer(PushPinAnnotation annotation) {
		this.setName(annotation.getLayerName());
		this.annotation = annotation;
		Position position = Position.fromDegrees(annotation.getLat(), annotation.getLng());
		this.balloon = new GlobeBrowserBalloon("", position);
		BalloonAttributes attrs = new BasicBalloonAttributes();
		attrs.setSize(new Size(Size.NATIVE_DIMENSION, 0d, null, Size.NATIVE_DIMENSION, 0d, null));
		balloon.setAttributes(attrs);

		this.mark = new PointPlacemark(position);
		mark.setLabelText("");
		mark.setValue(AVKey.BALLOON, balloon);

		this.addRenderable(balloon);
		this.addRenderable(mark);
	}

	public PushPinAnnotationProperPanel getPanel() {
		return panel;
	}

	public void setPanel(PushPinAnnotationProperPanel panel) {
		this.panel = panel;
	}

	/**
	 * 刷新图层中属性面板的内容。位置可以用鼠标拖动来改变，当位置改变后，属性面板内容也应该相应地改变。
	 */
	public void fresh(Position p) {
		this.annotation.setLat(p.getLatitude().getDegrees());
		this.annotation.setLng(p.getLongitude().getDegrees());
		this.panel.fresh(p);
	}

	/**
	 * 刷新图层中的图形。当用户在属性面板中修改了图形的属性，图形也应该相应地发生改变。
	 */
	public void fresh() {
		this.removeAllRenderables();
		this.setName(annotation.getLayerName());
		Position position = Position.fromDegrees(annotation.getLat(), annotation.getLng());
		this.balloon = new GlobeBrowserBalloon(annotation.getContent(), position);
		BalloonAttributes attrs = new BasicBalloonAttributes();
		attrs.setSize(new Size(Size.NATIVE_DIMENSION, 0d, null, Size.NATIVE_DIMENSION, 0d, null));
		balloon.setAttributes(attrs);

		this.mark = new PointPlacemark(position);
		mark.setLabelText(annotation.getTitle());
		PointPlacemarkAttributes attrs2 = new PointPlacemarkAttributes();
		attrs2.setLabelFont(titleFont);
		mark.setAttributes(attrs2);
		mark.setValue(AVKey.BALLOON, balloon);

		this.addRenderable(balloon);
		this.addRenderable(mark);
	}

	public PushPinAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(PushPinAnnotation annotation) {
		this.annotation = annotation;
	}

	
	@Override
	public Double getLng() {
		return this.getAnnotation().getLng();
	}

	@Override
	public Double getLat() {
		return this.getAnnotation().getLat();
	}

}
