package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceEllipse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 椭圆属性面板
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class EllipseProperPanel extends JPanel {

	private static final long serialVersionUID = 2634413586222717954L;

	private EllipseLayer layer;
	private SurfaceEllipse ellipse;
	private static DartEarthAppFrame frame;
	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(110, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	/**
	 * 这里的信息是由用户输入的，椭圆的位置参数
	 */
	private JLabel p1Label;
	private JTextField p1LatText;
	private JTextField p1LngText;
	private JLabel p2Label;
	private JTextField p2LatText;
	private JTextField p2LngText;

	/**
	 * 这些也是椭圆的位置参数，只是这些参数是由上面的参数计算而得来的
	 */
	private JLabel centPLabel;
	private JTextField centPLatText;
	private JTextField centPLngText;
	private JLabel minorRadiusLabel;
	private JTextField minorRadiusText;
	private JLabel minorRadiusUnit;
	private JLabel majorRadiusLabel;
	private JTextField majorRadiusText;
	private JLabel majorRadiusUnit;

	/**
	 * 轮廓线的参数
	 */
	private JLabel outlineColorLabel;
	private JButton outlineColorBtn;
	private JLabel outlineOpacityLabel;
	private JTextField outlineOpacityText;
	private JLabel outlineSizeLabel;
	private JTextField outlineSizeText;

	/**
	 * 填充色的参数
	 */
	private JLabel innerColorLabel;
	private JButton innerColorBtn;
	private JLabel innerOpacityLabel;
	private JTextField innerOpacityText;
	private ShapeAttributes attrs;

	/**
	 * 老样子，还是确定、应用、取消
	 */
	private JButton okBtn;
	private JButton applyBtn;
	private JButton cancelBtn;

	/**
	 * 构造方法，那指定的layer构造一个属性面板。
	 * 
	 * @param layer
	 *            需要属性面板的图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public EllipseProperPanel(final EllipseLayer layer, final DartEarthAppFrame frame) {
		this.layer = layer;
		attrs = this.layer.getEllipse().getAttributes();
		EllipseProperPanel.frame = frame;
		this.setLayout(new GridLayout(0, 1, 2, 2));
		initialPostionParams();
		initialGenedPositionParams();
		initialOutlinePararms();
		initialInnerParams();
		initialDialogBtns();
		this.refresh();
	}

	/**
	 * 刷新。当图形的属性修改时，属性面板上的内容也应该相应地修改。
	 */
	public void refresh() {
		if (layer.getPositions().size() == 2) {
			this.p1LatText.setText(layer.getPositions().get(0).getLatitude().getDegrees() + "");
			this.p1LngText.setText(layer.getPositions().get(0).getLongitude().getDegrees() + "");
			this.p2LatText.setText(layer.getPositions().get(1).getLatitude().getDegrees() + "");
			this.p2LngText.setText(layer.getPositions().get(1).getLongitude().getDegrees() + "");
			this.centPLatText.setText(layer.getEllipse().getCenter().getLatitude().degrees + "");
			this.centPLngText.setText(layer.getEllipse().getCenter().getLongitude().degrees + "");
			this.majorRadiusText.setText(layer.getEllipse().getMajorRadius() + "");
			this.minorRadiusText.setText(layer.getEllipse().getMajorRadius() + "");
			final Color innerColor = attrs.getInteriorMaterial().getDiffuse();
			innerColorBtn.setBackground(innerColor);
			innerOpacityText.setText(attrs.getInteriorOpacity() + "");

			final Color outlineColor = attrs.getOutlineMaterial().getDiffuse();

			outlineColorBtn.setBackground(outlineColor);
			outlineOpacityText.setText(attrs.getOutlineOpacity() + "");

			outlineSizeText.setText(attrs.getOutlineWidth() + "");
		}
	}

	/**
	 * 初始化“确定，应用，取消”三个按键
	 */
	private void initialDialogBtns() {
		// final JPanel panel = this;
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
		// applyBtn.addActionListener(okListener);
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
		// cancelBtn.addActionListener(okListener);
		cancelBtn.setPreferredSize(btnComponentDimension);
		panel.add(cancelBtn);
		this.add(panel);
		// put(okBtn, applyBtn, cancelBtn);
	}

	/**
	 * 初始化内部参数面板。
	 */
	private void initialInnerParams() {

		final Color innerColor = attrs.getInteriorMaterial().getSpecular();

		innerColorLabel = new JLabel("填充色:");
		// innerColorLabel.setPreferredSize(new Dimension(100, 20));
		innerColorBtn = new JButton();
		innerColorBtn.setBackground(innerColor);
		// innerColorBtn.setPreferredSize(new Dimension(100, 20));
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(null, "请选择颜色", innerColor);
				innerColorBtn.setBackground(color);
				// line.setColor(color);
				// attrs.setInteriorMaterial(new Material(color));
				// layer.refresh();
			}
		};
		innerColorBtn.addActionListener(colorChooseListener);
		put(innerColorLabel, innerColorBtn);

		innerOpacityLabel = new JLabel("填充透明度:");
		innerOpacityText = new JTextField();
		innerOpacityText.setText(attrs.getInteriorOpacity() + "");
		put(innerOpacityLabel, innerOpacityText);
	}

	private void initialOutlinePararms() {
		final Color outlineColor = attrs.getOutlineMaterial().getDiffuse();

		outlineColorLabel = new JLabel("轮廓色:");
		outlineColorBtn = new JButton();
		outlineColorBtn.setBackground(outlineColor);
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(null, "请选择颜色", outlineColor);
				outlineColorBtn.setBackground(color);
				// line.setColor(color);
				// attrs.setOutlineMaterial(new Material(color));
				// layer.refresh();
			}
		};
		outlineColorBtn.addActionListener(colorChooseListener);
		put(outlineColorLabel, outlineColorBtn);

		outlineOpacityLabel = new JLabel("轮廓透明度:");
		outlineOpacityText = new JTextField();
		outlineOpacityText.setText(attrs.getOutlineOpacity() + "");
		put(outlineOpacityLabel, outlineOpacityText);

		outlineSizeLabel = new JLabel("轮廓粗细:");
		outlineSizeText = new JTextField();
		outlineSizeText.setText(attrs.getOutlineWidth() + "");
		put(outlineSizeLabel, outlineSizeText);
	}

	private void initialPostionParams() {
		p1Label = new JLabel("顶点1:");
		p1LatText = new JTextField();
		p1LngText = new JTextField();
		put(p1Label, p1LatText, p1LngText);

		p2Label = new JLabel("顶点2:");
		p2LatText = new JTextField();
		p2LngText = new JTextField();
		put(p2Label, p2LatText, p2LngText);
	}

	private void initialGenedPositionParams() {
		centPLabel = new JLabel("中心顶点:");
		centPLatText = new JTextField();
		centPLatText.setEditable(false);
		// centPLatText.setEnabled(false);

		centPLngText = new JTextField();
		centPLngText.setEditable(false);
		// centPLngText.setEnabled(false);
		put(centPLabel, centPLatText, centPLngText);

		minorRadiusLabel = new JLabel("短轴:");
		minorRadiusText = new JTextField();
		minorRadiusText.setEditable(false);
		// minorRadiusText.setEnabled(false);
		minorRadiusUnit = new JLabel("米");
		put(minorRadiusLabel, minorRadiusText, minorRadiusUnit);

		majorRadiusLabel = new JLabel("长轴:");
		majorRadiusText = new JTextField();
		majorRadiusText.setEditable(false);
		// majorRadiusText.setEnabled(false);
		majorRadiusUnit = new JLabel("米");
		put(majorRadiusLabel, majorRadiusText, majorRadiusUnit);
	}

	/**
	 * 应用修改，当修改完成时，调用此方法，则图形会发生相应的变化
	 */
	private void applyModify() {

		// layer.getPositions().get(0).
		// System.out.println(layer.getPositions());
		Position p1 = new Position(LatLon.fromDegrees(Double.valueOf(p1LatText.getText()), Double.valueOf(p1LngText.getText())), layer
				.getPositions().get(0).getElevation());
		layer.getPositions().set(0, p1);
		Position p2 = new Position(LatLon.fromDegrees(Double.valueOf(p2LatText.getText()), Double.valueOf(p2LngText.getText())), layer
				.getPositions().get(1).getElevation());
		layer.getPositions().set(1, p2);
		attrs.setOutlineOpacity(Double.valueOf(outlineOpacityText.getText()));
		attrs.setOutlineWidth(Double.valueOf(outlineSizeText.getText()));
		attrs.setInteriorOpacity(Double.valueOf(innerOpacityText.getText()));
		attrs.setOutlineMaterial(new Material(outlineColorBtn.getBackground()));
		attrs.setInteriorMaterial(new Material(innerColorBtn.getBackground()));
		layer.refresh();
		refresh();
		// System.out.println(layer.getPositions());
	}

	/**
	 * 放置控件
	 * 
	 * @param components 所需在面板上放置的控件 
	 */
	private void put(JComponent... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
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

}
