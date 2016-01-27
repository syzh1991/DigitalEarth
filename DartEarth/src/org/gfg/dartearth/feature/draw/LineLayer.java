package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Polyline;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.ExportableLayer;

/**
 * 线段图层
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class LineLayer extends ShapeLayer implements ExportableLayer {
	private DartEarthAppFrame frame = null;
	private List<Position> positions = null;
	private Polyline line = null;
	private static int count = 0;
	private LineProperPanel panel;

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DartEarthAppFrame，整个系统中也就这一个DartEarthAppFrame
	 */
	public LineLayer(DartEarthAppFrame frame) {
		this.frame = frame;
		this.positions = new ArrayList<Position>();
		this.line = new Polyline();
		this.line.setFollowTerrain(true);
		this.setName("线段" + (++count));
		this.addRenderable(line);
		this.frame.getWwd().getModel().getLayers().add(0, this);
		this.frame.getLayerPanelDialog().getLayerPanel().update();
	}

	/**
	 * 构造方法，指定图层名
	 * @param frame DartEarthAppFrame
	 * @param lName 图层名
	 */
	public LineLayer(DartEarthAppFrame frame, String lName) {
		this.frame = frame;
		this.setName(lName);
	}

	/**
	 * 增加顶点
	 */
	public void addPosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;
		this.positions.add(curPos);
		this.line.setPositions(this.positions);
		this.firePropertyChange("LineBuilder.AddPosition", null, curPos);
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	/**
	 *替换顶点。 当用户输入的顶点还没结束，或者用户修改了某些基点的位置信息，则将修改后的顶点传入到line中，从而替换顶点。
	 */
	public void replacePosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;

		int index = this.positions.size() - 1;
		if (index < 0)
			index = 0;

		Position currentLastPosition = this.positions.get(index);
		this.positions.set(index, curPos);
		this.line.setPositions(this.positions);
		this.firePropertyChange("LineBuilder.ReplacePosition", currentLastPosition, curPos);
		// line.getMeasurer().getLength(frame.get)
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	/**
	 * 删除顶点
	 * @param index 所需删除的顶点的序号
	 */
	public void removePosition(int index) {
		if (this.positions.size() <= 2)
			return;
		Position position = this.positions.get(index);
		this.positions.remove(position);
		this.line.setPositions(this.positions);
		this.firePropertyChange("LineBuilder.RemovePosition", position, null);
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	/**
	 * 删除最后增加进来的那个顶点
	 */
	public void removePosition() {
		if (this.positions.size() == 0)
			return;
		Position currentLastPosition = this.positions.get(this.positions.size() - 1);
		this.positions.remove(this.positions.size() - 1);
		this.line.setPositions(this.positions);
		this.firePropertyChange("LineBuilder.RemovePosition", currentLastPosition, null);
		this.frame.getWwd().redraw();
		panel.refreshPointsPanel();
	}

	/**
	 * 刷新图形，根据现在的属性来修改图形
	 */
	public void refresh() {
		this.line.setPositions(this.positions);
		this.frame.getWwd().redraw();
	}

	/**
	 * 刷新图层属性面板的内容
	 */
	@Override
	public void refresh(Position p) {
		this.positions.clear();
		for (Position pp : this.line.getPositions()) {
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

	public Polyline getLine() {
		return line;
	}

	public void setLine(Polyline line) {
		this.line = line;
	}

	public LineProperPanel getPanel() {
		return panel;
	}

	public void setPanel(LineProperPanel panel) {
		this.panel = panel;
	}

	public Element exportAsDeml() throws IOException {

		Document _document = DocumentHelper.createDocument();
		Element _feature = _document.addElement("Feature");

		Element _type = _feature.addElement("Type");
		_type.setText("Line");

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
		_outlineOpacity.setText(this.getLine().getColor().getAlpha() + "");

		Element _outlineWidth = _attributes.addElement("OutlineWidth");
		_outlineWidth.setText(this.getLine().getLineWidth() + "");

		Element _outlineMaterial = _attributes.addElement("OutlineMaterial");
		_outlineMaterial.addAttribute("r", this.getLine().getColor().getRed() + "");
		_outlineMaterial.addAttribute("g", this.getLine().getColor().getGreen() + "");
		_outlineMaterial.addAttribute("b", this.getLine().getColor().getBlue() + "");

		return _feature;
	}

	/**
	 * 备份线段相关数据<br>
	 * last_name-->图层名称<br>
	 * last_positions-->线段位置<br>
	 * last_color-->线段颜色<br>
	 * last_width-->线段粗细
	 */
	@Override
	public Map<String, Object> backup() {
		Map<String, Object> backup = new HashMap<String, Object>();

		// 名称
		String last_name = super.getName();
		backup.put("last_name", last_name);

		// 位置
		List<Position> last_positions = new ArrayList<Position>();
		last_positions.addAll(this.getPositions());
		backup.put("last_positions", last_positions);

		// 颜色
		Color color = line.getColor();
		Color last_color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		backup.put("last_color", last_color);

		// 粗细
		double last_width = line.getLineWidth();
		backup.put("last_width", last_width);

		return backup;
	}

	//

}
