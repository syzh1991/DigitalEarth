package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 图钉标注Builder，用于绑定创建标注的鼠标操作，也用于创建标注
 * 
 * @author 陈亮<br>
 *         Burningcl@gmail.com
 * 
 */
public class PushPinAnnotationBuilder {
	private static int x;
	private static int y;

	/**
	 * 绑定绘制标注所需的鼠标操作
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public static void bindMouseListener(final DartEarthAppFrame frame) {

		MouseListener listener = new MouseAdapter() {

			public void mouseReleased(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
					Position curPos = frame.getWwd().getCurrentPosition();
					PushPinAnnotationBuilder.x = mouseEvent.getX();
					PushPinAnnotationBuilder.y = mouseEvent.getY();
					buildPushPinAnnotation(curPos, frame);
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
	 * @param curPos 当前的坐标
	 * @param frame DartEarthAppFrame
	 */
	public static void buildPushPinAnnotation(final Position curPos, final DartEarthAppFrame frame) {
		PushPinAnnotation annotation = new PushPinAnnotation(curPos.getLatitude().getDegrees(), curPos.getLongitude().getDegrees());
		PushPinAnnotationLayer layer = new PushPinAnnotationLayer(annotation);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().setLocation(PushPinAnnotationBuilder.x + 100, PushPinAnnotationBuilder.y);
		PushPinAnnotationProperPanel panel = new PushPinAnnotationProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, annotation.getLayerName(), layer);
		layer.setPanel(panel);
	}

}
