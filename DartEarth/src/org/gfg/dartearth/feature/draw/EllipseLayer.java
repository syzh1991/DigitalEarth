package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceEllipse;
import gov.nasa.worldwind.util.WWUtil;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.ExportableLayer;
import org.gfg.dartearth.util.DistanceCaculator;

/**
 * 椭圆图层
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class EllipseLayer extends ShapeLayer implements ExportableLayer {
	/**
	 * DartEarthAppFrame
	 */
	private DartEarthAppFrame frame;
	/**
	 * 椭圆的左下点与右上点的位置
	 */
	private List<Position> positions;
	/**
	 * 椭圆图形
	 */
	private SurfaceEllipse ellipse;
	/**
	 * 椭圆的计数器，用于生成图层名
	 */
	private static int count;
	/**
	 * 中心纬 度
	 */
	private double centLat;
	/**
	 * 中心经度
	 */
	private double centLng;
	/**
	 * 短轴
	 */
	private double minorRadius;
	/**
	 * 长轴
	 */
	private double majorRadius;
	/**
	 * 右上角的纬度
	 */
	private double minorRadiusLat;
	/**
	 * 右上角的经度
	 */
	private double majorRadiusLng;
	/**
	 * 属性panel
	 */
	private EllipseProperPanel panel;

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public EllipseLayer(DartEarthAppFrame frame) {
		this.frame = frame;
		this.positions = new ArrayList<Position>();
		this.ellipse = new SurfaceEllipse();
		this.ellipse.setIntervals(64);
		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setInteriorMaterial(Material.WHITE);
		attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.RED)));
		attrs.setInteriorOpacity(1);
		attrs.setOutlineOpacity(1);
		attrs.setOutlineWidth(2);
		// attrs.set
		this.ellipse.setAttributes(attrs);
		// this.ellipse..setFollowTerrain(true);
		this.setName("椭圆" + (++count));
		this.addRenderable(ellipse);
		this.frame.getWwd().getModel().getLayers().add(0, this);
		this.frame.getLayerPanelDialog().getLayerPanel().update();
	}

	/**
	 * 构造方法
	 * 
	 * @param frame
	 *            DatEarthAppFrame
	 * @param lName
	 *            图层名
	 */
	public EllipseLayer(DartEarthAppFrame frame, String lName) {
		this.frame = frame;
		this.setName(lName);
	}

	/**
	 * 根据用户鼠标输入，也就是左下角与右上角的经纬度位置，来生成一个椭圆图形
	 */
	private void genEllipse() {
		if (this.positions.size() == 2) {
			centLng = DistanceCaculator.getCenterLng(this.positions.get(0).getLongitude().degrees,
					this.positions.get(1).getLongitude().degrees);
			centLat = (this.positions.get(0).getLatitude().degrees + this.positions.get(1).getLatitude().degrees) / 2;

			double upLat = this.getPositions().get(1).getLatitude().getDegrees();
			double upLng = centLng;
			minorRadius = DistanceCaculator.caculateDistance(centLat, centLng, upLat, upLng);
			ellipse.setMinorRadius(minorRadius);

			double rightLat = centLat;
			double rightLng = this.getPositions().get(1).getLongitude().getDegrees();
			majorRadius = DistanceCaculator.caculateDistance(centLat, centLng, rightLat, rightLng);
			ellipse.setMajorRadius(majorRadius);
			ellipse.setCenter(LatLon.fromDegrees(centLat, centLng));

			minorRadiusLat = Math.abs(upLat - centLat);
			majorRadiusLng = Math.abs(rightLng - centLng);
			// if(minorRadiusLat>)
			if (majorRadiusLng > 180) {
				majorRadiusLng = 360.0 - majorRadiusLng;
			}
		}
	}

	/**
	 * 增加一个点，在输入一个椭圆时，也就只输入两个点（左下角与右上角），用这两个点来生成图形
	 */
	public void addPosition() {
		if (positions.size() >= 2) {
			positions.clear();
		}
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;
		this.positions.add(curPos);
		genEllipse();
		this.frame.getWwd().redraw();
		panel.refresh();
	}

	public void replacePosition() {
		Position curPos = this.frame.getWwd().getCurrentPosition();
		if (curPos == null)
			return;

		int index = this.positions.size() - 1;
		if (index < 0) {
			this.positions.add(index, curPos);
		} else {
			this.positions.set(index, curPos);
		}
		genEllipse();
		this.frame.getWwd().redraw();
		panel.refresh();
	}

	/**
	 * 删除顶点，目前 没有用到
	 * 
	 * @param index
	 *            顶点的序号
	 */
	public void removePosition(int index) {
		Position position = this.positions.get(index);
		this.positions.remove(position);
		this.frame.getWwd().redraw();
	}

	/**
	 * 删除顶点，目前 没有用到
	 */
	public void removePosition() {
		if (this.positions.size() == 0)
			return;
		this.positions.remove(this.positions.size() - 1);
		this.frame.getWwd().redraw();
	}

	/**
	 * 刷新图形，当用户输入完成后，系统自动地生成椭圆图形。再次刷新wwd的画布。
	 */
	public void refresh() {
		genEllipse();
		this.frame.getWwd().redraw();
	}

	/**
	 * 刷新属性。当用户改变图形的属性时，需要改变图形属性面板的内容。
	 */
	public void refresh(Position p) {
		this.positions.clear();
		LatLon cent = this.ellipse.getCenter();
		centLng = cent.getLongitude().degrees;
		centLat = cent.getLatitude().degrees;
		majorRadiusLng = this.ellipse.getMajorRadius();
		minorRadiusLat = this.ellipse.getMinorRadius();
		double rightUpPLng = centLng + majorRadiusLng;
		double rightUpPLat = centLat + minorRadiusLat;
		if (rightUpPLng > 180.0) {
			rightUpPLng = rightUpPLng - 360.0;
		} else if (rightUpPLng < -180.0) {
			rightUpPLng = rightUpPLng + 360.0;
		}
		if (rightUpPLat > 90.0) {
			rightUpPLat = 180.0 - rightUpPLat;
		} else if (rightUpPLat < -90.0) {
			rightUpPLat = -180.0 - rightUpPLat;
		}
		double leftDownPLng = centLng - majorRadiusLng;
		double leftDownPLat = centLat - minorRadiusLat;
		if (leftDownPLng > 180.0) {
			leftDownPLng = leftDownPLng - 360.0;
		} else if (leftDownPLng < -180.0) {
			leftDownPLng = leftDownPLng + 360.0;
		}
		if (leftDownPLat > 90.0) {
			leftDownPLat = 180.0 - leftDownPLat;
		} else if (rightUpPLat < -90.0) {
			leftDownPLat = -180.0 - leftDownPLat;
		}
		Position leftDownP = new Position(LatLon.fromDegrees(leftDownPLat, leftDownPLng), 0);
		this.positions.add(leftDownP);
		Position rightUpP = new Position(LatLon.fromDegrees(rightUpPLat, rightUpPLng), 0);
		this.positions.add(rightUpP);
		this.panel.refresh();
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public SurfaceEllipse getEllipse() {
		return ellipse;
	}

	public void setEllipse(SurfaceEllipse ellipse) {
		this.ellipse = ellipse;
	}

	public double getCentLat() {
		return centLat;
	}

	public void setCentLat(double centLat) {
		this.centLat = centLat;
	}

	public double getCentLng() {
		return centLng;
	}

	public void setCentLng(double centLng) {
		this.centLng = centLng;
	}

	public double getMinorRadius() {
		return minorRadius;
	}

	public void setMinorRadius(double minorRadius) {
		this.minorRadius = minorRadius;
	}

	public double getMajorRadius() {
		return majorRadius;
	}

	public void setMajorRadius(double majorRadius) {
		this.majorRadius = majorRadius;
	}

	public EllipseProperPanel getPanel() {
		return panel;
	}

	public void setPanel(EllipseProperPanel panel) {
		this.panel = panel;
	}

	/**
	 * 生成DEML，即将这个椭圆输出到DEML文件中
	 */
	public Element exportAsDeml() throws IOException {

		Document _document = DocumentHelper.createDocument();
		Element _feature = _document.addElement("Feature");

		Element _type = _feature.addElement("Type");
		_type.setText("Ellipse");

		Element _lName = _feature.addElement("LayerName");
		_lName.setText(this.getName());

		Element _pointList = _feature.addElement("PointList");

		for (Position pos : this.getPositions()) {
			Element _point = _pointList.addElement("Point");
			_point.addAttribute("Lat", pos.getLatitude().degrees + "");
			_point.addAttribute("Lng", pos.getLongitude().degrees + "");
		}
		Element _attributes = _feature.addElement("Attributes");

		Element _centerPoint = _attributes.addElement("CenterPoint");
		_centerPoint.addAttribute("Lat", this.getEllipse().getCenter().getLatitude().degrees + "");
		_centerPoint.addAttribute("Lng", this.getEllipse().getCenter().getLongitude().degrees + "");

		Element _majorRadius = _attributes.addElement("MajorRadius");
		_majorRadius.setText(this.ellipse.getMajorRadius() + "");

		Element _minorRadius = _attributes.addElement("MinorRadius");
		_minorRadius.setText(this.ellipse.getMinorRadius() + "");

		Element _outlineOpacity = _attributes.addElement("OutlineOpacity");
		_outlineOpacity.setText(this.getEllipse().getAttributes().getOutlineOpacity() + "");

		Element _outlineWidth = _attributes.addElement("OutlineWidth");
		_outlineWidth.setText(this.getEllipse().getAttributes().getOutlineWidth() + "");

		Element _outlineMaterial = _attributes.addElement("OutlineMaterial");
		_outlineMaterial.addAttribute("r", this.getEllipse().getAttributes().getOutlineMaterial().getDiffuse().getRed() + "");
		_outlineMaterial.addAttribute("g", this.getEllipse().getAttributes().getOutlineMaterial().getDiffuse().getGreen() + "");
		_outlineMaterial.addAttribute("b", this.getEllipse().getAttributes().getOutlineMaterial().getDiffuse().getBlue() + "");

		Element _interiorMaterial = _attributes.addElement("InteriorMaterial");
		_interiorMaterial.addAttribute("r", this.getEllipse().getAttributes().getInteriorMaterial().getDiffuse().getRed() + "");
		_interiorMaterial.addAttribute("g", this.getEllipse().getAttributes().getInteriorMaterial().getDiffuse().getGreen() + "");
		_interiorMaterial.addAttribute("b", this.getEllipse().getAttributes().getInteriorMaterial().getDiffuse().getBlue() + "");

		Element _interiorOpacity = _attributes.addElement("InteriorOpacity");
		_interiorOpacity.setText(this.getEllipse().getAttributes().getInteriorOpacity() + "");

		return _feature;
	}
}
