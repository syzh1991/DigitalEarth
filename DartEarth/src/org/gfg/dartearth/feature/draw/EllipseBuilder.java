package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceEllipse;
import gov.nasa.worldwind.render.SurfacePolygon;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;

import com.sun.org.apache.xpath.internal.operations.Minus;

/**
 * 椭圆图形的Builder，用于绑定绘制椭圆所需的鼠标动作，也用于生成一个椭圆（包括图形，图层，属性面板等）
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class EllipseBuilder extends AbstractBuilder {

	/**
	 * 绑定绘制椭圆所需的鼠标操作
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public static void bindMouseListener(DartEarthAppFrame frame) {

		final EllipseLayer layer = new EllipseLayer(frame);

		/**
		 * 绘制椭圆的鼠标动作，详见该类
		 */
		final EllipseMouseAdapter ellipseMouseAdapter = new EllipseMouseAdapter(layer, frame);

		/**
		 * 绘制椭圆的鼠标动作的鼠标运动监听器
		 */
		MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouseEvent) {
				if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					if (ellipseMouseAdapter.isActive())
						mouseEvent.consume();
				}
			}
		};
		frame.getWwd().getInputHandler().addMouseMotionListener(mouseMotionAdapter);

		/**
		 * 绘制椭圆的鼠标动作的位置监听器
		 */
		PositionListener positionListener = new PositionListener() {
			public void moved(PositionEvent event) {
				if (!ellipseMouseAdapter.isActive())
					return;
				if (layer.getPositions().size() == 1)
					layer.addPosition();
				else
					layer.replacePosition();
				// System.out.println(layer.getPositions());
			}
		};
		frame.getWwd().addPositionListener(positionListener);

		ellipseMouseAdapter.setMouseMotionAdapter(mouseMotionAdapter);
		ellipseMouseAdapter.setPositionListener(positionListener);
		frame.getWwd().getInputHandler().addMouseListener(ellipseMouseAdapter);

		EllipseProperPanel panel = new EllipseProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
	}

	
	/**
	 * 通过deml来生成一个椭圆
	 * @param  DEML中对这个椭圆的属性的描述
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

		String centerLat = e.element("Attributes").element("CenterPoint").attribute("Lat").getText();
		String centerLng = e.element("Attributes").element("CenterPoint").attribute("Lng").getText();
		String majorRadius = e.element("Attributes").element("MajorRadius").getText();
		String minorRadius = e.element("Attributes").element("MinorRadius").getText();
		SurfaceEllipse ellipse = new SurfaceEllipse(LatLon.fromDegrees(Double.valueOf(centerLat), Double.valueOf(centerLng)),
				Double.valueOf(majorRadius), Double.valueOf(minorRadius));

		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineOpacity(Double.valueOf(e.element("Attributes").element("OutlineOpacity").getText()));
		attrs.setOutlineWidth(Double.valueOf(e.element("Attributes").element("OutlineWidth").getText()));

		int red = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("r").getText());
		int green = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("g").getText());
		int blue = Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("b").getText());

		Material im = new Material(new Color(red, green, blue));
		attrs.setInteriorMaterial(im);

		red = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("r").getText());
		green = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("g").getText());
		blue = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("b").getText());
		Material om = new Material(new Color(red, green, blue));
		attrs.setOutlineMaterial(om);

		ellipse.setAttributes(attrs);

		EllipseLayer layer = new EllipseLayer(frame, LayerName);
		layer.addRenderable(ellipse);
		layer.setEllipse(ellipse);
		layer.setPositions(positions);
		EllipseProperPanel panel = new EllipseProperPanel(layer, frame);
		layer.setPanel(panel);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
	}
}
