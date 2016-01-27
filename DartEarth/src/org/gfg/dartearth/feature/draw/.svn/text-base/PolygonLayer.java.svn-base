package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.Exportable;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfacePolygon;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.ExportableLayer;

/**
 * 多边形图层
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class PolygonLayer extends ShapeLayer implements ExportableLayer {
	/**
	 * DartEarthAppFrame
	 */
	private DartEarthAppFrame frame = null;
	/**
	 * 多边形的各个顶点的位置
	 */
	private List<Position> positions = null;
	/**
	 * 多边形图形
	 */
	private SurfacePolygon polygon = null;
	/**
	 * 用于标记图层
	 */
	private static int count = 0;
	/**
	 * 属性panel
	 */
	private PolygonProperPanel panel;
	/**
	 * 多边形外围的边框（只在绘制时有）
	 */
	private Polyline line;

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public PolygonLayer(DartEarthAppFrame frame) {
		this.polygon = new SurfacePolygon();
		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setInteriorMaterial(new Material(new Color(255, 255, 255)));
		this.polygon.setAttributes(attrs);
		this.frame = frame;
		this.positions = new ArrayList<Position>();
		this.setName("多边形" + (++count));
		this.frame.getWwd().getModel().getLayers().add(0, this);
		this.frame.getLayerPanelDialog().getLayerPanel().update();
		line = new Polyline();
		line.setLineWidth(3);
		line.setColor(new Color(205, 85, 10));

	}

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DatEarthAppFrame
	 * @param lName
	 *            图层名
	 */
	public PolygonLayer(DartEarthAppFrame frame, String lName) {
		this.setName(lName);
		this.frame = frame;
	}

	/**
	 * 增加一个点
	 */
	public void addPosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;

		this.positions.add(curPos);
		this.polygon.setOuterBoundary(this.positions);
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
		// List<Position> pos = new ArrayList<Position>();
		// if (this.positions.size() >= 2) {
		// pos.add(this.positions.get(this.positions.size() - 2));
		// pos.add(this.positions.get(this.positions.size() - 1));
		// }
		line.setPositions(this.positions);
		this.addRenderable(line);

	}

	/**
	 * 刷新图形，当用户输入完成后，系统自动地生成多边形图形。再次刷新wwd的画布。
	 */
	public void refresh() {
		this.positions = (List<Position>) this.getPolygon().getLocations();

		this.frame.getWwd().redraw();
	}

	/**
	 * 刷新属性。当用户改变图形的属性时，需要改变图形属性面板的内容。
	 */
	public void refresh(Position p) {
		this.positions = (List<Position>) this.polygon.getOuterBoundary();
		this.panel.refresh();
		// this.addRenderable(line);
		this.frame.getWwd().redraw();
	}

	/**
	 * 根据顶点集合来生成多边形
	 * @param flag 为true时去掉外围的边框，为false时显示外围的边框
	 */
	public void buildPolygon(boolean flag) {
		ArrayList<Position> pathPositions = new ArrayList<Position>();
		for (Position pos : this.positions) {
			pathPositions.add(pos);
		}
		polygon.setOuterBoundary(pathPositions);
		this.addRenderable(polygon);
		if (!flag) {
			line.setPositions(this.positions);
			this.addRenderable(line);
		}

	}

	/**
	 * 删除本图层上的多边形
	 */
	public void removePolygon() {
		this.removeAllRenderables();
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
		this.polygon.setOuterBoundary(this.positions);
		this.line.setPositions(this.positions);
		this.firePropertyChange("LineBuilder.ReplacePosition", currentLastPosition, curPos);
		this.frame.getWwd().redraw();

	}

	/**
	 * 删除顶点
	 * 
	 * @param index
	 *            顶点的序号
	 */
	public void removePosition(int index) {
		if (this.positions.size() <= 2)
			return;
		Position position = this.positions.get(index);
		this.positions.remove(position);
		this.firePropertyChange("LineBuilder.RemovePosition", position, null);
		this.frame.getWwd().redraw();
	}

	/**
	 * 删除顶点
	 */
	public void removePosition() {
		if (this.positions.size() < 4)
			return;
		this.positions.remove(this.positions.size() - 1);
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public Exportable getExportElem() {
		return this.polygon;

	}

	/**
	 * 生成DEML，即将这个多边形输出到DEML文件中
	 */
	public Element exportAsDeml() throws IOException {

		Document _document = DocumentHelper.createDocument();
		Element _feature = _document.addElement("Feature");

		Element _type = _feature.addElement("Type");
		_type.setText("Polygon");

		Element _lName = _feature.addElement("LayerName");
		_lName.setText(this.getName());

		Element _pointList = _feature.addElement("PointList");

		for (Position pos : this.getPositions()) {
			Element _point = _pointList.addElement("Point");
			_point.addAttribute("Lat", pos.getLatitude().degrees + "");
			_point.addAttribute("Lng", pos.getLongitude().degrees + "");
		}

		Element _attributes = _feature.addElement("Attributes");

		Element _outlineMaterial = _attributes.addElement("InteriorMaterial");
		_outlineMaterial.addAttribute("r", this.getPolygon().getAttributes().getInteriorMaterial().getDiffuse().getRed() + "");
		_outlineMaterial.addAttribute("g", this.getPolygon().getAttributes().getInteriorMaterial().getDiffuse().getGreen() + "");
		_outlineMaterial.addAttribute("b", this.getPolygon().getAttributes().getInteriorMaterial().getDiffuse().getBlue() + "");

		return _feature;
	}

	public PolygonProperPanel getPanel() {
		return panel;
	}

	public void setPanel(PolygonProperPanel panel) {
		this.panel = panel;
	}

	public Polyline getLine() {
		return line;
	}

	public void setLine(Polyline line) {
		this.line = line;
	}

	public SurfacePolygon getPolygon() {
		return polygon;
	}

	public void setPolygon(SurfacePolygon polygon) {
		this.polygon = polygon;
	}
}
