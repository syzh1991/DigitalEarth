package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polygon;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfacePolygon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 多边形属性面板
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class PolygonProperPanel extends JPanel {
	private static final long serialVersionUID = 930710547300508185L;

	private PolygonLayer layer;

	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(85, 24);
	private static Dimension deleteComponentDimension = new Dimension(60, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	private DartEarthAppFrame frame;

	private SurfacePolygon polygon;

	public SurfacePolygon getPolygon() {
		return polygon;
	}

	public void setPolygon(SurfacePolygon polygon) {
		this.polygon = polygon;
	}

	private JLabel layerNameLabel;
	private JTextField layerNameTextField;

	/**
	 * 填充色的参数
	 */
	private List<Position> points;
	private JLabel colorLabel;
	private JButton colorBtn;
	private JLabel opacityLabel;
	private JTextField opacityTextField;
	// private JLabel sizeLabel;
	private JTextField sizeTextField;
	private Color color;

	/**
	 * 老样子，还是确定、应用、取消
	 */
	private JButton okBtn;
	private JButton applyBtn;
	private JButton cancelBtn;
	private List<JPanel> pointPanels;
	private List<JButton> deleteBtns;
	private List<JTextField> latTexts;
	private List<JTextField> lngTexts;

	/**
	 * 构造方法，那指定的layer构造一个属性面板。
	 * 
	 * @param layer
	 *            需要属性面板的图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public PolygonProperPanel(final PolygonLayer layer, final DartEarthAppFrame frame) {
		
		this.setLayout(new GridLayout(0, 1, 2, 2));

		this.layer = layer;
		this.frame = frame;
		this.polygon = this.layer.getPolygon();
		deleteBtns = new ArrayList<JButton>();
		latTexts = new ArrayList<JTextField>();
		lngTexts = new ArrayList<JTextField>();
		pointPanels = new ArrayList<JPanel>();

		layerNameLabel = new JLabel("图层名:");
		layerNameTextField = new JTextField();
		layerNameTextField.setText(layer.getName());
		put(layerNameLabel, layerNameTextField);
		colorLabel = new JLabel("颜色:");	
		colorBtn = new JButton();
		colorBtn.setBackground(color);
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color choosenColor = JColorChooser.showDialog(null, "请选择颜色", color);
				colorBtn.setBackground(choosenColor);
			}
		};
		colorBtn.addActionListener(colorChooseListener);
		put(colorLabel, colorBtn);
		opacityLabel = new JLabel("透明度:");
		opacityTextField = new JTextField();
		opacityTextField.setText("1");
		put(opacityLabel, opacityTextField);
		sizeTextField = new JTextField();
		initialDialogBtns();
	}

	/**
	 * 初始化“确定，应用，取消”三个按键
	 */
	private void initialDialogBtns() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		okBtn = new JButton("确定");
		ActionListener okListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applyModify();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		okBtn.addActionListener(okListener);
		okBtn.setPreferredSize(btnComponentDimension);
		panel.add(okBtn);

		applyBtn = new JButton("应用");
		ActionListener ApplyListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applyModify();
			}
		};
		applyBtn.addActionListener(ApplyListener);
		applyBtn.setPreferredSize(btnComponentDimension);
		panel.add(applyBtn);

		cancelBtn = new JButton("取消");
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		cancelBtn.addActionListener(cancelListener);
		cancelBtn.setPreferredSize(btnComponentDimension);
		panel.add(cancelBtn);
		this.add(panel);
	}

	/**
	 * 应用修改，当修改完成时，调用此方法，则图形会发生相应的变化
	 */
	private void applyModify() {
		layer.setName(layerNameTextField.getText());
		Color choosenColor = colorBtn.getBackground();
		color = new Color(choosenColor.getRed(), choosenColor.getGreen(), choosenColor.getBlue());
		List<Position> positions = new ArrayList<Position>();
		for (int i = 0; i < latTexts.size(); i++) {
			Position p = ((List<Position>) layer.getPolygon().getLocations()).get(i);
			double lat = Double.valueOf(latTexts.get(i).getText());
			double lng = Double.valueOf(lngTexts.get(i).getText());
			Position newP = new Position(Angle.fromDegrees(lat), Angle.fromDegrees(lng), p.getElevation());
			positions.add(newP);
		}
		layer.setPositions(positions);
		ShapeAttributes attrs = new BasicShapeAttributes();
		Material material = new Material(color);
		attrs.setInteriorMaterial(material);
		attrs.setInteriorOpacity(Double.valueOf(opacityTextField.getText()));
		layer.getPolygon().setAttributes(attrs);
		layer.refresh();
		frame.getLayerPanelDialog().getLayerPanel().update();
	}

	/**
	 * 放置控件
	 * 
	 * @param components 所需在面板上放置的控件 
	 */
	private void put(JComponent... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Dimension componentDimension = null;
		if (components.length == 2) {
			componentDimension = bigComponentDimension;
		} else {
			componentDimension = smallComponentDimension;
		}
		for (JComponent c : components) {
			if (c instanceof JLabel) {
				c.setPreferredSize(labelDimension);
			} else {
				c.setPreferredSize(componentDimension);
			}
			panel.add(c);
		}
		this.add(panel);
	}

	/**
	 * 构造多边形的点的panel
	 */
	void buildPointPanels() {
		deleteBtns.clear();
		latTexts.clear();
		lngTexts.clear();
		points = (List<Position>) layer.getPolygon().getLocations();
		if (pointPanels.size() != 0) {
			for (JPanel p : pointPanels) {
				this.remove(p);
			}
		}
		pointPanels.clear();
		
		if (points.size() > 1) {

			for (int i = 0; i < points.size(); i++) {
				 
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout(FlowLayout.LEFT));
				Position p = points.get(i);
				final int index = i;
				JLabel label = new JLabel("点" + i + ":");
				label.setPreferredSize(labelDimension);
				panel.add(label);
				JTextField lngTextField = new JTextField();
				lngTextField.setText(p.getLongitude().getDegrees() + "");
				lngTextField.setEditable(false);
				lngTextField.setPreferredSize(smallComponentDimension);
				panel.add(lngTextField);
				lngTexts.add(lngTextField);
				JTextField latTextField = new JTextField();
				latTextField.setText(p.getLatitude().getDegrees() + "");
				latTextField.setPreferredSize(smallComponentDimension);
				latTextField.setEditable(false);
				panel.add(latTextField);
				latTexts.add(latTextField);
				JButton deleteBtn = new JButton();
				deleteBtn.setText("删除");
				deleteBtn.setPreferredSize(deleteComponentDimension);
				ActionListener deleteListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if (deleteBtns.size() > 3) {
							deleteBtns.remove(index);
							points.remove(index);							
							layer.getPolygon().setLocations(points);
							layer.refresh();
							refreshPointsPanel();
							if (deleteBtns.size() <= 3) {
								for (JButton btn : deleteBtns) {
									btn.setEnabled(false);
								}
							}
						}
					}
				};
				deleteBtn.addActionListener(deleteListener);
				deleteBtns.add(deleteBtn);
				panel.add(deleteBtn);
				pointPanels.add(panel);
				this.add(panel, pointPanels.size() + 1);

			}
		}
	}

	/**
	 * 刷新多边形的点的panel
	 */
	public void refreshPointsPanel() {
		buildPointPanels();
		if (deleteBtns.size() <= 2) {
			for (JButton btn : deleteBtns) {
				btn.setEnabled(false);
			}
		}
		this.revalidate();
		for (JPanel p : pointPanels) {
			p.repaint();
		}
		this.repaint();
	}

	/**
	 * 刷新。当图形的属性修改时，属性面板上的内容也应该相应地修改。
	 */
	public void refresh() {
		refreshPointsPanel();
		layerNameTextField.setText(layer.getName());

	}
	
	/**
	 * 通过多边形的属性来构建属性panel,用于导入deml这一功能中
	 * @param attrs
	 */
	public void buildByAttrs(ShapeAttributes attrs){
		colorBtn.setBackground(attrs.getInteriorMaterial().getDiffuse());
		opacityTextField.setText(attrs.getInteriorOpacity()+"");
		refresh();
	}
}
