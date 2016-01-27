package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceCircle;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 圆图形的Builder，用于绑定绘制圆所需的鼠标动作，也用于生成一个圆（包括图形，图层，属性面板等）
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class CircleBuilder extends AbstractBuilder {

	/**
	 * 绑定绘制圆所需的鼠标操作
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public static void bindMouseListener(DartEarthAppFrame frame) {
		final CircleLayer layer = new CircleLayer(frame);

		/**
		 * 绘制圆的鼠标动作，详见该类
		 */
		final CircleMouseAdapter polyMouseAdapter = new CircleMouseAdapter(layer, frame);

		/**
		 * 绘制圆的鼠标动作的鼠标运动监听器
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
		 * 绘制圆的鼠标动作的位置监听器
		 */
		PositionListener positionListener = new PositionListener() {
			public void moved(PositionEvent event) {

				if (!polyMouseAdapter.isActive())
					return;
				layer.resetRadius();
			}
		};
		frame.getWwd().addPositionListener(positionListener);

		polyMouseAdapter.setMouseMotionAdapter(mouseMotionAdapter);
		polyMouseAdapter.setPositionListener(positionListener);
		frame.getWwd().getInputHandler().addMouseListener(polyMouseAdapter);

		CircleProperPanel panel = new CircleProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
	}

	/**
	 * 通过deml来生成一个圆
	 * @param  DEML中对这个圆的属性的描述
	 * @param frame DartearthAppFrame
	 */
	@Override
	public void buildByDEML(Element e,DartEarthAppFrame frame) {
		String LayerName=e.element("LayerName").getText();
		String centerLat=e.element("PointList").element("Point").attributeValue("Lat");
		String centerLng=e.element("PointList").element("Point").attributeValue("Lng");
		String radious=e.element("Attributes").element("Radious").getText();
		
		SurfaceCircle circle=new SurfaceCircle(LatLon.fromRadians(new Double(centerLat),new Double(centerLng)),new Double(radious));
		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineOpacity(Double.valueOf(e.element("Attributes").element("OutlineOpacity").getText()));
		attrs.setOutlineWidth(Double.valueOf(e.element("Attributes").element("OutlineWidth").getText()));
		attrs.setInteriorOpacity(Double.valueOf(e.element("Attributes").element("InteriorOpacity").getText()));
		int red=Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("r").getText());
		int green=Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("g").getText());
		int blue=Integer.valueOf(e.element("Attributes").element("InteriorMaterial").attribute("b").getText());
	
		Material im=new Material(new Color(red,green,blue));
		attrs.setInteriorMaterial(im);
		
		red=Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("r").getText());
		green=Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("g").getText());
		blue=Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("b").getText());
		Material om=new Material(new Color(red,green,blue));
		attrs.setOutlineMaterial(om);
		
		circle.setAttributes(attrs);
		
		CircleLayer layer=new CircleLayer(frame,LayerName);
		layer.setAttr(attrs);
		layer.addRenderable(circle);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		CircleProperPanel panel = new CircleProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
		
	}
}
