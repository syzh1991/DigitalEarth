package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.util.WWUtil;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.ExportableLayer;
import org.gfg.dartearth.util.DistanceCaculator;

import p.l;

/**
 * 圆图层
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class CircleLayer extends ShapeLayer implements ExportableLayer {
	/**
	 * DartEarthAppFrame
	 */
	private DartEarthAppFrame frame = null;
	/**
	 * 圆的半径
	 */
	private Double Radius;
	/**
	 * 圆的圆心位置
	 */
	private LatLon center;
	/**
	 * 圆的直径的左端点位置
	 */
	private LatLon start;
	/**
	 * 圆图形
	 */
	private SurfaceCircle circle;
	/**
	 * 属性panel
	 */
	private CircleProperPanel panel;
	/**
	 * 属性类
	 */
	private ShapeAttributes attr;

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public CircleLayer(DartEarthAppFrame frame) {
		this.circle = new SurfaceCircle();

		if (this.getAttr() == null) {
			ShapeAttributes attrs = new BasicShapeAttributes();
			attrs.setInteriorMaterial(Material.WHITE);
			attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.RED)));
			attrs.setInteriorOpacity(1);
			attrs.setOutlineOpacity(1);
			attrs.setOutlineWidth(2);
			this.circle.setAttributes(this.getAttr());
			this.setAttr(attrs);
			this.frame = frame;
			this.setName("圆" + (++count));
			this.frame.getWwd().getModel().getLayers().add(0, this);
			this.frame.getLayerPanelDialog().getLayerPanel().update();
		}
	}

	/**
	 * 重置圆的半径
	 */
	public void resetRadius() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos != null) {
			double dis = DistanceCaculator.caculateDistance(start.getLatitude().degrees, start.getLongitude().degrees,
					curPos.getLatitude().degrees, curPos.getLongitude().degrees);

			double radius = dis / 2;
			this.center = LatLon.fromDegrees((start.getLatitude().degrees + curPos.getLatitude().degrees) / 2,
					DistanceCaculator.getCenterLng(start.getLongitude().degrees, curPos.getLongitude().degrees));
			this.setRadius(radius);
			this.createCircle();
			this.panel.refresh();
		}
	}

	/**
	 * 刷新属性。当用户改变图形的属性时，需要改变图形属性面板的内容。
	 */
	public void refresh(Position p) {
		this.panel.refresh();
	}

	public void refresh1(ShapeAttributes attrs) {
		this.createCircle();
		this.circle.setAttributes(attrs);
	}

	public void createCircle() {
		this.removeAllRenderables();
		if (this.center != null && this.Radius != null) {
			this.circle = new SurfaceCircle(this.center, this.Radius);
			this.circle.setAttributes(this.getAttr());
			this.addRenderable(this.circle);
			this.panel.refresh();
		}

	}

	/**
	 * 生成DEML，即将这个圆输出到DEML文件中
	 */
	public Element exportAsDeml() throws IOException {

		Document _document = DocumentHelper.createDocument();
		Element _feature = _document.addElement("Feature");

		Element _type = _feature.addElement("Type");
		_type.setText("Circle");

		Element _lName = _feature.addElement("LayerName");
		_lName.setText(this.getName());

		Element _pointList = _feature.addElement("PointList");
		Element _point = _pointList.addElement("Point");
		_point.addAttribute("Lat", this.getCircle().getCenter().getLatitude().degrees + "");
		_point.addAttribute("Lng", this.getCircle().getCenter().getLongitude().degrees + "");

		Element _attributes = _feature.addElement("Attributes");

		Element _radious = _attributes.addElement("Radious");
		_radious.setText(this.getCircle().getRadius() + "");

		Element _outlineOpacity = _attributes.addElement("OutlineOpacity");
		_outlineOpacity.setText(this.getAttr().getOutlineOpacity() + "");

		Element _interiorOpacity = _attributes.addElement("InteriorOpacity");
		_interiorOpacity.setText(this.getAttr().getInteriorOpacity() + "");

		Element _outlineWidth = _attributes.addElement("OutlineWidth");
		_outlineWidth.setText(this.getAttr().getOutlineWidth() + "");

		Element _outlineMaterial = _attributes.addElement("OutlineMaterial");
		_outlineMaterial.addAttribute("r", this.getAttr().getOutlineMaterial().getDiffuse().getRed() + "");
		_outlineMaterial.addAttribute("g", this.getAttr().getOutlineMaterial().getDiffuse().getGreen() + "");
		_outlineMaterial.addAttribute("b", this.getAttr().getOutlineMaterial().getDiffuse().getBlue() + "");

		Element _interiorMaterial = _attributes.addElement("InteriorMaterial");
		_interiorMaterial.addAttribute("r", this.getAttr().getInteriorMaterial().getDiffuse().getRed() + "");
		_interiorMaterial.addAttribute("g", this.getAttr().getInteriorMaterial().getDiffuse().getGreen() + "");
		_interiorMaterial.addAttribute("b", this.getAttr().getInteriorMaterial().getDiffuse().getBlue() + "");
		return _feature;
	}

	/**
	 * 画圆 进行数据备件 点击“取消”返回到最近保存的数据<br>
	 * last_name-->图层名<br>
	 * last_centerLat-->经度<br>
	 * last_centerLng-->纬度<br>
	 * last_radius-->半径<br>
	 * last_outlineColor-->轮廓色<br>
	 * last_outlineOpacity-->轮廓透明度<br>
	 * last_outlineSize-->轮廓粗细<br>
	 * last_innerColor-->填充色<br>
	 * last_innerOpacity-->填充透明度<br>
	 * 
	 * @author Colonel
	 */
	@Override
	public Map<String, Object> backup() {
		Map<String, Object> backup = new HashMap<String, Object>();

		// 图层名
		String last_name = super.getName();
		backup.put("last_name", last_name);

		// 经纬度
		// 第一次经纬度和半径都是为空的
		if (this.getCenter() != null) {
			double last_centerLat = this.getCenter().getLatitude().degrees;
			double last_centerLng = this.getCenter().getLongitude().degrees;
			backup.put("last_centerLat", last_centerLat);
			backup.put("last_centerLng", last_centerLng);

			// 半径
			double last_radius = this.getRadius();
			backup.put("last_radius", last_radius);
		}

		// 轮廓色
		Color last_outlineColor = this.attr.getOutlineMaterial().getDiffuse();
		backup.put("last_outlineColor", last_outlineColor);

		// 轮廓透明度:0~1
		double last_outlineOpacity = this.attr.getOutlineOpacity();
		backup.put("last_outlineOpacity", last_outlineOpacity);

		// 轮廓粗细
		double last_outlineSize = this.attr.getOutlineWidth();
		backup.put("last_outlineSize", last_outlineSize);

		// 填充色
		Color last_innerColor = this.attr.getInteriorMaterial().getDiffuse();
		backup.put("last_innerColor", last_innerColor);

		// 填充透明度
		double last_innerOpacity = this.attr.getInteriorOpacity();
		backup.put("last_innerOpacity", last_innerOpacity);

		return backup;
	}

	public CircleProperPanel getPanel() {
		return panel;
	}

	public void setPanel(CircleProperPanel panel) {
		this.panel = panel;
	}

	public SurfaceCircle getCircle() {
		return circle;
	}

	public void setCircle(SurfaceCircle circle) {
		this.circle = circle;
	}

	public LatLon getStart() {
		return start;
	}

	public void setStart(LatLon start) {
		this.start = start;
	}

	public LatLon getCenter() {
		return center;
	}

	public void setCenter(LatLon center) {
		this.center = center;
	}

	public Double getRadius() {
		return Radius;
	}

	public void setRadius(Double radius) {
		Radius = radius;
	}

	public ShapeAttributes getAttr() {
		return attr;
	}

	public void setAttr(ShapeAttributes attr) {
		this.attr = attr;
	}

	private static int count = 0;

	public CircleLayer(DartEarthAppFrame frame, String lName) {
		this.frame = frame;
		this.setName(lName);
	}

}
