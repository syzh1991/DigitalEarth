package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.Exportable;
import gov.nasa.worldwind.geom.Position;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.gfg.dartearth.core.DartEarthAppFrame;
/**
 * 气泡标注Builder，用于绑定创建标注的鼠标操作，也用于创建标注
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class BubbleAnnotationBuilder {
	private static  int x;
	private static int y;
	/**
	 * 绑定绘制标注所需的鼠标操作
	 * @param frame
	 */
	public static void bindMouseListener(final DartEarthAppFrame frame) {

		MouseListener listener = new MouseAdapter() {

			public void mouseReleased(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
					Position curPos = frame.getWwd().getCurrentPosition();
					 BubbleAnnotationBuilder.x=mouseEvent.getX();
					 BubbleAnnotationBuilder.y=mouseEvent.getY();
					 buildBubbleAnnotation("",curPos, frame,true);
					((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					mouseEvent.consume();
					frame.getWwd().getInputHandler().removeMouseListener(this);
				}
			}

		};
		frame.getWwd().getInputHandler().addMouseListener(listener);
	}
	
	/**
	 * 创建标注
	 * @param text 标注的初始内容
	 * @param curPos 标注的当前位置
	 * @param frame
	 * @param flag 是否显示属性面板，为true时显示
	 */
	public static void buildBubbleAnnotation(String text,final Position curPos, final DartEarthAppFrame frame,boolean flag) {
		BubbleAnnotation annotation = new BubbleAnnotation(text,curPos);
		BubbleAnnotationLayer layer = new BubbleAnnotationLayer(annotation);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().setLocation(BubbleAnnotationBuilder.x+100, BubbleAnnotationBuilder.y);
		BubbleAnnotationProperPanel panel=new BubbleAnnotationProperPanel(layer,frame);
		if(flag){
			frame.getPropertiesDialog().show(panel, annotation.getLayerName(), layer);
		}
		
		layer.setPanel(panel);
	}
	
	/**
	 * 创建标注
	 * @param curPos 标注的当前位置
	 * @param content 标注的内容
	 * @param frame
	 */	
	public static void buildBubbleAnnotation(final Position curPos, String content,final DartEarthAppFrame frame) {
		BubbleAnnotation annotation = new BubbleAnnotation(content,curPos);
		BubbleAnnotationLayer layer = new BubbleAnnotationLayer(annotation);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().setLocation(BubbleAnnotationBuilder.x+100, BubbleAnnotationBuilder.y);
		BubbleAnnotationProperPanel panel=new BubbleAnnotationProperPanel(layer,frame);
		layer.setPanel(panel);
	}

}
