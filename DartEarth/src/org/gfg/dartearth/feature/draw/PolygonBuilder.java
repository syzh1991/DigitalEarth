package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfacePolygon;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 多边形图形的Builder，用于绑定绘制多边形所需的鼠标动作，也用于生成一个多边形（包括图形，图层，属性面板等）
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class PolygonBuilder extends AbstractBuilder {

	/**
	 * 绑定绘制多边形所需的鼠标操作
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public static void bindMouseListener(DartEarthAppFrame frame) {
		final PolygonLayer layer = new PolygonLayer(frame);

		/**
		 * 绘制多边形的鼠标动作，详见该类
		 */
		final PolygonMouseAdapter polyMouseAdapter = new PolygonMouseAdapter(layer, frame);

		/**
		 * 绘制多边形的鼠标动作的鼠标运动监听器
		 */
		MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouseEvent) {
				if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					if (polyMouseAdapter.isActive())
						mouseEvent.consume();
				}
			}
		};
		frame.getWwd().getInputHandler().addMouseMotionListener(mouseMotionAdapter);

		/**
		 * 绘制多边形的鼠标动作的位置监听器
		 */
		PositionListener positionListener = new PositionListener() {
			public void moved(PositionEvent event) {
				if (!polyMouseAdapter.isActive())
					return;
				if (layer.getPositions().size() == 1)
					layer.addPosition();
				else
					layer.replacePosition();
			}
		};
		frame.getWwd().addPositionListener(positionListener);

		polyMouseAdapter.setMouseMotionAdapter(mouseMotionAdapter);
		polyMouseAdapter.setPositionListener(positionListener);
		frame.getWwd().getInputHandler().addMouseListener(polyMouseAdapter);

		PolygonProperPanel panel = new PolygonProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
	}

	/**
	 * 通过deml来生成一个多边形
	 * @param  DEML中对这个多边形的属性的描述
	 * @param frame DartearthAppFrame
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
		SurfacePolygon poly = new SurfacePolygon();
		poly.setLocations(positions);
		poly.setOuterBoundary(positions);
		ShapeAttributes attrs = new BasicShapeAttributes();

		int red = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("r").getText());
		int green = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("g").getText());
		int blue = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("b").getText());

		Material im = new Material(new Color(red, green, blue));
		attrs.setInteriorMaterial(im);

		poly.setAttributes(attrs);
		PolygonLayer layer = new PolygonLayer(frame, LayerName);
		layer.addRenderable(poly);
		layer.setPolygon(poly);
		PolygonProperPanel panel = new PolygonProperPanel(layer, frame);
		panel.buildByAttrs(attrs);
		layer.setPanel(panel);		
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
	}
}
