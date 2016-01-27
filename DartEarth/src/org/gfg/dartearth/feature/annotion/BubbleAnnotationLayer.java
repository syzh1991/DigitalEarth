package org.gfg.dartearth.feature.annotion;

import java.awt.Font;

import javax.swing.JPanel;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AbstractBrowserBalloon;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BalloonAttributes;
import gov.nasa.worldwind.render.BasicBalloonAttributes;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.GlobeBrowserBalloon;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.Size;

/**
 * 气泡标注图层
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class BubbleAnnotationLayer extends AnnotationLayer {

	private BubbleAnnotation annotation;
	// private PointPlacemark mark;
	private GlobeAnnotation bubble;
    private BubbleAnnotationProperPanel panel;
    //private Font titleFont=new Font("黑体",Font.BOLD,16);
	public BubbleAnnotationProperPanel getPanel() {
		return panel;
	}
	public void setPanel(BubbleAnnotationProperPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * 根据annotation来创建标注图层
	 * 
	 * @param annotation
	 *            标注内容，详见BubbleAnnotation类
	 */
	public BubbleAnnotationLayer(BubbleAnnotation annotation) {
		this.setName(annotation.getLayerName());
		this.annotation = annotation;
		Position position = Position.fromDegrees(annotation.getLat(), annotation.getLng());
		this.bubble = annotation;

		// this.mark = new PointPlacemark(position);
		// mark.setLabelText("");
		// mark.setValue(AVKey.BALLOON, bubble);
		//AnnotationAttributes arr=new AnnotationAttributes();
		//arr.setFont(titleFont);
		//this.bubble.setAttributes(arr);
		this.addRenderable(bubble);
		// this.addRenderable(mark);
	}
	public BubbleAnnotation getAnnotation() {
		return annotation;
	}
	/**
	 * 刷新图层中属性面板的内容。位置可以用鼠标拖动来改变，当位置改变后，属性面板内容也应该相应地改变。
	 */
	public void fresh(Position p){
		this.annotation.setLat(p.getLatitude().getDegrees());
		this.annotation.setLng(p.getLongitude().getDegrees());
		this.panel.refresh();	
		//AnnotationAttributes arr=new AnnotationAttributes();
		//arr.setFont(titleFont);
		//this.bubble.setAttributes(arr);
	}
	/**
	 * 刷新图层中的图形。当用户在属性面板中修改了图形的属性，图形也应该相应地发生改变。
	 */
	public void fresh() {
		this.removeAllRenderables();
		this.setName(annotation.getLayerName());
		Position position = Position.fromDegrees(annotation.getLat(), annotation.getLng());
		this.bubble = new BubbleAnnotation(annotation.getContent(), position);
		
		//AnnotationAttributes arr=new AnnotationAttributes();
		//arr.setFont(titleFont);
		//this.bubble.setAttributes(arr);
		this.addRenderable(bubble);
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
