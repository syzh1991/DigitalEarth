package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Polyline;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 线段的builder
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class LineBuilder extends AbstractBuilder {

	/**
	 * 为DartEarthAppFrame绑定鼠标动作
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public static void bindMouseListener(DartEarthAppFrame frame) {

		final LineLayer layer = new LineLayer(frame);
		final LineMouseAdapter lineMouseAdapter = new LineMouseAdapter(layer, frame);

		/**
		 * 鼠标运动
		 */
		MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouseEvent) {
				if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					if (lineMouseAdapter.isActive())
						mouseEvent.consume();
				}
			}
		};
		frame.getWwd().getInputHandler().addMouseMotionListener(mouseMotionAdapter);

		/**
		 * 鼠标位置监听器
		 */
		PositionListener positionListener = new PositionListener() {
			public void moved(PositionEvent event) {
				if (!lineMouseAdapter.isActive())
					return;
				if (layer.getPositions().size() == 1)
					layer.addPosition();
				else
					layer.replacePosition();
			}
		};
		frame.getWwd().addPositionListener(positionListener);

		lineMouseAdapter.setMouseMotionAdapter(mouseMotionAdapter);
		lineMouseAdapter.setPositionListener(positionListener);
		frame.getWwd().getInputHandler().addMouseListener(lineMouseAdapter);

		LineProperPanel panel = new LineProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
	}

	/**
	 * 通过DEML来生成图形
	 * 
	 * @param e
	 *            所需生成图形的deml
	 * @param frame
	 *            DartEarthAppFrame
	 */
	@Override
	public void buildByDEML(Element e, DartEarthAppFrame frame) {
		String LayerName = e.element("LayerName").getText();

		Element pointList = e.element("PointList");
		Iterator<Element> i = pointList.elementIterator("Point");

		List<Position> positions = new ArrayList<Position>();
		while (i.hasNext()) {
			Element el = i.next();
			String lat = el.attribute("Lat").getText();
			String lng = el.attribute("Lng").getText();
			Position p = Position.fromDegrees(Double.valueOf(lat), Double.valueOf(lng));
			positions.add(p);
		}
		Polyline line = new Polyline();
		// line.setFollowTerrain(true);
		line.setPositions(positions);

		int opacity = Integer.valueOf(e.element("Attributes").element("OutlineOpacity").getText());
		line.setLineWidth(Double.valueOf(e.element("Attributes").element("OutlineWidth").getText()));

		int red = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("r").getText());
		int green = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("g").getText());
		int blue = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("b").getText());

		Color color = new Color(red, green, blue, opacity);
		line.setColor(color);

		LineLayer layer = new LineLayer(frame, LayerName);
		layer.addRenderable(line);
		layer.setLine(line);
		layer.setPositions(positions);

		LineProperPanel panel = new LineProperPanel(layer, frame);
		layer.setPanel(panel);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
	}
}
