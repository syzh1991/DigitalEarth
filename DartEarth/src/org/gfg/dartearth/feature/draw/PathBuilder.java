package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwindx.examples.util.DirectedPath;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;

public class PathBuilder extends AbstractBuilder {

	public static void bindMouseListener(DartEarthAppFrame frame) {
		final PathLayer layer = new PathLayer(frame);

		final PathMouseAdapter pathMouseAdapter = new PathMouseAdapter(layer, frame);

		MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouseEvent) {
				if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					if (pathMouseAdapter.isActive())
						mouseEvent.consume();
				}
			}
		};
		frame.getWwd().getInputHandler().addMouseMotionListener(mouseMotionAdapter);

		PositionListener positionListener = new PositionListener() {
			public void moved(PositionEvent event) {
				if (!pathMouseAdapter.isActive())
					return;
				if (layer.getPositions().size() == 1)
					layer.addPosition();
				else
					layer.replacePosition();
			}
		};
		frame.getWwd().addPositionListener(positionListener);

		pathMouseAdapter.setMouseMotionAdapter(mouseMotionAdapter);
		pathMouseAdapter.setPositionListener(positionListener);
		frame.getWwd().getInputHandler().addMouseListener(pathMouseAdapter);

		PathProperPanel panel = new PathProperPanel(layer, frame);
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
		layer.setPanel(panel);
	}

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
		DirectedPath path = new DirectedPath();
		path.setFollowTerrain(true);
		path.setArrowLength(200000d);
		path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
		path.setPathType(AVKey.GREAT_CIRCLE);
		path.setVisible(true);
		path.setPositions(positions);

		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineOpacity(Double.valueOf(e.element("Attributes").element("OutlineOpacity").getText()));
		attrs.setOutlineWidth(Double.valueOf(e.element("Attributes").element("OutlineWidth").getText()));

		int red = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("r").getText());
		int green = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("g").getText());
		int blue = Integer.valueOf(e.element("Attributes").element("OutlineMaterial").attribute("b").getText());

		Material om = new Material(new Color(red, green, blue));
		attrs.setOutlineMaterial(om);

		path.setAttributes(attrs);

		PathLayer layer = new PathLayer(frame, LayerName);
		layer.addRenderable(path);
		layer.setPath(path);
		layer.setPositions(positions);

		PathProperPanel panel = new PathProperPanel(layer, frame);
		layer.setPanel(panel);
		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().show(panel, layer.getName(), layer);
	}
}
