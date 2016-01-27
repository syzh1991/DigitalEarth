package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwindx.examples.util.DirectedPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.ExportableLayer;

public class PathLayer extends ShapeLayer implements ExportableLayer {

	private DartEarthAppFrame frame = null;
	private List<Position> positions = null;
	private DirectedPath path = null;
	private static int count = 0;
	private PathProperPanel panel;

	public PathLayer(DartEarthAppFrame frame, String lName) {
		this.frame = frame;
		this.setName(lName);
	}

	public PathLayer(DartEarthAppFrame frame) {
		this.frame = frame;
		this.positions = new ArrayList<Position>();

		this.path = new DirectedPath();
		this.path.setFollowTerrain(true);
		this.path.setArrowLength(200000d);

		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineMaterial(Material.RED);
		attrs.setOutlineWidth(2d);
		this.path.setAttributes(attrs);

		//this.path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
		this.path.setPathType(AVKey.GREAT_CIRCLE);
		this.path.setVisible(true);
		// this.path.setPositions(positions);

		this.setName("路径" + (++count));
		this.addRenderable(path);

		this.frame.getWwd().getModel().getLayers().add(0, this);
		this.frame.getLayerPanelDialog().getLayerPanel().update();
	}

	public void addPosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;
		// System.out.println(curPos.getElevation());
		this.positions.add(curPos);
		this.path.setPositions(this.positions);
		this.firePropertyChange("PathBuilder.AddPosition", null, curPos);

		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	public void replacePosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;

		int index = this.positions.size() - 1;
		if (index < 0)
			index = 0;

		Position currentLastPosition = this.positions.get(index);
		this.positions.set(index, curPos);
		this.path.setPositions(this.positions);
		this.firePropertyChange("PathBuilder.ReplacePosition", currentLastPosition, curPos);

		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	public void removePosition(int index) {
		if (this.positions.size() <= 2)
			return;
		Position position = this.positions.get(index);
		this.positions.remove(position);
		this.path.setPositions(this.positions);
		this.firePropertyChange("PathBuilder.RemovePosition", position, null);

		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	public void removePosition() {
		if (this.positions.size() == 0)
			return;
		Position currentLastPosition = this.positions.get(this.positions.size() - 1);
		this.positions.remove(this.positions.size() - 1);
		this.path.setPositions(this.positions);
		this.firePropertyChange("PathBuilder.RemovePosition", currentLastPosition, null);
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	public void refresh() {
		this.path.setPositions(this.positions);
		this.frame.getWwd().redraw();
	}

	@Override
	public void refresh(Position p) {
		this.positions.clear();
		for (Position pp : this.path.getPositions()) {
			this.positions.add(pp);
		}
		this.panel.refreshPointsPanel();
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public DirectedPath getPath() {
		return path;
	}

	public void setPath(DirectedPath path) {
		this.path = path;
	}

	public PathProperPanel getPanel() {
		return panel;
	}

	public void setPanel(PathProperPanel panel) {
		this.panel = panel;
	}

	public void reverse() {
		int len = positions.size();
		for (int i = 0; i <= (len - 1) / 2; i++) {
			Position tmpPosition = positions.get(i);
			positions.set(i, positions.get(len - 1 - i));
			positions.set(len - 1 - i, tmpPosition);
		}
		// this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
		refresh();
	}

	public Element exportAsDeml() throws IOException {

		Document _document = DocumentHelper.createDocument();
		Element _feature = _document.addElement("Feature");

		Element _type = _feature.addElement("Type");
		_type.setText("Path");

		Element _lName = _feature.addElement("LayerName");
		_lName.setText(this.getName());

		Element _pointList = _feature.addElement("PointList");

		for (Position pos : this.getPositions()) {
			Element _point = _pointList.addElement("Point");
			_point.addAttribute("Lat", pos.getLatitude().degrees + "");
			_point.addAttribute("Lng", pos.getLongitude().degrees + "");
		}

		Element _attributes = _feature.addElement("Attributes");

		Element _outlineOpacity = _attributes.addElement("OutlineOpacity");
		_outlineOpacity.setText(this.getPath().getAttributes().getOutlineOpacity() + "");

		Element _outlineWidth = _attributes.addElement("OutlineWidth");
		_outlineWidth.setText(this.getPath().getAttributes().getOutlineWidth() + "");

		Element _outlineMaterial = _attributes.addElement("OutlineMaterial");
		_outlineMaterial.addAttribute("r", this.getPath().getAttributes().getOutlineMaterial().getDiffuse().getRed() + "");
		_outlineMaterial.addAttribute("g", this.getPath().getAttributes().getOutlineMaterial().getDiffuse().getGreen() + "");
		_outlineMaterial.addAttribute("b", this.getPath().getAttributes().getOutlineMaterial().getDiffuse().getBlue() + "");

		return _feature;
	}

}
