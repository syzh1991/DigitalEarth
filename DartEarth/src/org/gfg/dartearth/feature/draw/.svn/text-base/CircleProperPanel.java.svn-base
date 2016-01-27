package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfaceEllipse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.print.CancelablePrintJob;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gfg.dartearth.core.DartEarthAppFrame;

import p.ba;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;

/**
 * 圆属性面板
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class CircleProperPanel extends JPanel {

	private static final long serialVersionUID = 2634413586222717954L;

	private CircleLayer layer;
	private SurfaceCircle circle;
	private static DartEarthAppFrame frame;
	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(200, 24);
	private static Dimension smallComponentDimension = new Dimension(85, 24);
	private static Dimension jsliderComponentDimension = new Dimension(200, 36);

	/**
	 * 这里的信息是由用户输入的，圆的位置参数
	 */
	private JLabel layerNameLabel;
	private JTextField layerNameTextField;

	private JLabel centerLabel;
	private JTextField centerLatText;
	private JTextField centerLngText;
	private JLabel radiusLabel;
	private JTextField radiusText;

	/**
	 * 轮廓线的参数
	 */
	private JLabel outlineColorLabel;
	private JButton outlineColorBtn;
	private JLabel outlineOpacityLabel;
	private JLabel outlineOpacityResultLabel;

	/**
	 * 填充色的参数
	 */
	private JLabel innerColorLabel;
	private JButton innerColorBtn;

	private ShapeAttributes attrs;

	/**
	 * 老样子，还是确定、取消
	 */
	private JButton okBtn;
	private JButton cancelBtn;

	// 轮廓透明度 滑动条:
	private JSlider outlineOpacityJSlider;
	private double outlineOpacityValue = 0.5;
	// 轮廓粗细 滑动条
	private JLabel outlineSizeLabel;
	private JSlider outlineSizeJSlider;
	private JLabel outlineSizeResultLabel;
	private double outlineSizeValue = 5;
	// 填充透明度
	private JSlider innerOpacityJSlider;
	private JLabel innerOpacityLabel;
	private JLabel innerOpacityResultLabel;
	private double innerOpacityValue = 0.5;
	Map<String, Object> backup = null;

	public CircleProperPanel(final CircleLayer layer,
			final DartEarthAppFrame frame) {
		backup = layer.backup();
		this.layer = layer;
		CircleProperPanel.frame = frame;
		attrs = this.layer.getAttr();
		this.setLayout(new GridLayout(0, 1, 2, 2));
		initialPostionParams();
		initialOutlinePararms();
		initialInnerParams();
		initialDialogBtns();
	}

	public void refresh() {
		final Color innerColor = attrs.getInteriorMaterial().getDiffuse();
		final Color outlineColor = attrs.getOutlineMaterial().getDiffuse();
		outlineColorBtn.setBackground(outlineColor);
		outlineOpacityResultLabel
				.setText("  " + 10 * attrs.getOutlineOpacity());
		outlineSizeResultLabel.setText("  " + attrs.getOutlineWidth());
		this.layerNameTextField.setText(this.layer.getName());
		this.centerLatText.setText(this.layer.getCircle().getCenter()
				.getLatitude().degrees
				+ "");
		this.centerLngText.setText(this.layer.getCircle().getCenter()
				.getLongitude().degrees
				+ "");
		this.radiusText.setText(this.layer.getCircle().getRadius() + "");
	}

	private void initialDialogBtns() {
		// final JPanel panel = this;
		okBtn = new JButton("确定");
		ActionListener okListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backup = layer.backup();// 进行数据备件
				applyModify();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		okBtn.addActionListener(okListener);

		cancelBtn = new JButton("取消");
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cancel();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		cancelBtn.addActionListener(cancelListener);
		put(okBtn, cancelBtn);
	}

	private void initialInnerParams() {

		final Color innerColor = attrs.getInteriorMaterial().getDiffuse();

		innerColorLabel = new JLabel("填充色:");
		// innerColorLabel.setPreferredSize(new Dimension(100, 20));
		innerColorBtn = new JButton();
		innerColorBtn.setBackground(innerColor);
		// innerColorBtn.setPreferredSize(new Dimension(100, 20));
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(null, "请选择颜色",
						innerColor);
				innerColorBtn.setBackground(color);
				applyModify();
				// line.setColor(color);
				// attrs.setInteriorMaterial(new Material(color));
				// layer.refresh();
			}
		};
		innerColorBtn.addActionListener(colorChooseListener);
		put(innerColorLabel, innerColorBtn);

		innerOpacityLabel = new JLabel("填充透明度:");

		outlineSizeLabel = new JLabel("轮廓粗细:");
		innerOpacityJSlider = new JSlider(0, 10);
		innerOpacityJSlider.setPaintTicks(true);
		innerOpacityJSlider.setPaintLabels(true);
		innerOpacityJSlider.setMajorTickSpacing(2);
		innerOpacityJSlider.setMinorTickSpacing(1);
		innerOpacityJSlider
				.addChangeListener(new innerOpacityJSliderListenner());

		innerOpacityResultLabel = new JLabel();
		innerOpacityResultLabel.setText("  " + 10 * innerOpacityValue);
		put(innerOpacityLabel, innerOpacityJSlider, innerOpacityResultLabel);
	}

	private void initialOutlinePararms() {
		final Color outlineColor = attrs.getOutlineMaterial().getDiffuse();

		outlineColorLabel = new JLabel("轮廓色:");
		outlineColorBtn = new JButton();
		outlineColorBtn.setBackground(outlineColor);
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(null, "请选择颜色",
						outlineColor);
				outlineColorBtn.setBackground(color);
				applyModify();
				// line.setColor(color);
				// attrs.setOutlineMaterial(new Material(color));
				// layer.refresh();
			}
		};
		outlineColorBtn.addActionListener(colorChooseListener);
		put(outlineColorLabel, outlineColorBtn);

		outlineOpacityLabel = new JLabel("轮廓透明度:");
		outlineOpacityJSlider = new JSlider(0, 10);
		outlineOpacityJSlider.setPaintTicks(true);
		outlineOpacityJSlider.setPaintLabels(true);
		outlineOpacityJSlider.setMajorTickSpacing(2);
		outlineOpacityJSlider.setMinorTickSpacing(1);
		outlineOpacityJSlider
				.addChangeListener(new outlineOpacityJSliderListenner());
		outlineOpacityResultLabel = new JLabel();
		attrs.setOutlineOpacity(outlineOpacityValue);
		outlineOpacityResultLabel
				.setText("  " + 10 * attrs.getOutlineOpacity());
		put(outlineOpacityLabel, outlineOpacityJSlider,
				outlineOpacityResultLabel);

		outlineSizeLabel = new JLabel("轮廓粗细:");
		outlineSizeJSlider = new JSlider(0, 10);
		outlineSizeJSlider.setPaintTicks(true);
		outlineSizeJSlider.setPaintLabels(true);
		outlineSizeJSlider.setMajorTickSpacing(2);
		outlineSizeJSlider.setMinorTickSpacing(1);
		outlineSizeJSlider.addChangeListener(new outlineSizeJSliderListenner());
		outlineSizeResultLabel = new JLabel();
		attrs.setOutlineWidth(outlineSizeValue);
		outlineSizeResultLabel.setText("  " + outlineSizeValue);
		put(outlineSizeLabel, outlineSizeJSlider, outlineSizeResultLabel);
	}

	private void initialPostionParams() {
		layerNameLabel = new JLabel("图层名:");
		layerNameTextField = new JTextField();
		layerNameTextField.setText(layer.getName());
		put(layerNameLabel, layerNameTextField);
		centerLabel = new JLabel("圆心:");
		centerLatText = new JTextField();
		centerLngText = new JTextField();
		put(centerLabel, centerLatText, centerLngText);
		radiusLabel = new JLabel("半径:");
		radiusText = new JTextField();
		put(radiusLabel, radiusText);
	}

	public void applyModify() {
		this.layer.setName(layerNameTextField.getText());
		if (!centerLatText.getText().equals("")
				&& centerLatText.getText().length() != 0) {
			this.layer.setCenter(LatLon.fromRadians(
					Double.valueOf(centerLatText.getText()),
					Double.valueOf(centerLngText.getText())));
			this.layer.setRadius(Double.valueOf(radiusText.getText()));
		}
		attrs.setOutlineOpacity(outlineOpacityValue);
		attrs.setOutlineWidth(outlineSizeValue);
		attrs.setInteriorOpacity(innerOpacityValue);
		attrs.setOutlineMaterial(new Material(outlineColorBtn.getBackground()));
		outlineColorBtn.setBackground(outlineColorBtn.getBackground());
		attrs.setInteriorMaterial(new Material(innerColorBtn.getBackground()));
		innerColorBtn.setBackground(innerColorBtn.getBackground());
		layer.refresh1(attrs);
		layer.setAttr(attrs);
		if (this.layer.getCenter() != null) {
			
			frame.getLayerPanelDialog().getLayerPanel().update();
			refresh();
		}

	}

	/**
	 * 取消：<br>
	 * 数据还原
	 * 
	 * @author Colonel
	 */
	private void cancel() {
		// 图层名
		String last_name = (String) backup.get("last_name");
		layerNameTextField.setText(last_name);

		// 经度 纬度
		//初始化时 经纬度 半径都没有生成 都为空
		if (backup.containsKey("last_centerLat")) {
			double last_centerLat = (Double) backup.get("last_centerLat");
			double last_centerLng = (Double) backup.get("last_centerLng");
			centerLatText.setText("" + last_centerLat);
			centerLngText.setText("" + last_centerLng);
			
			// 半径
			double last_radius = (Double) backup.get("last_radius");
			radiusText.setText("" + last_radius);
		}


		// 轮廓色
		Color last_outlineColor = (Color) backup.get("last_outlineColor");
		outlineColorBtn.setBackground(last_outlineColor);

		// 轮廓透明度
		double last_outlineOpacity = (Double) backup.get("last_outlineOpacity");
		outlineOpacityValue = last_outlineOpacity;

		// 轮廓粗细
		double last_outlineSize = (Double) backup.get("last_outlineSize");
		outlineSizeValue = last_outlineSize;

		// 填充色
		Color last_innerColor = (Color) backup.get("last_innerColor");
		innerColorBtn.setBackground(last_innerColor);

		// 填充透明度
		double last_innerOpacity = (Double) backup.get("last_innerOpacity");
		innerOpacityValue = last_innerOpacity;

		// 应用
		applyModify();
	}

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
			} else if (c instanceof JSlider) {
				c.setPreferredSize(jsliderComponentDimension);
			} else {
				c.setPreferredSize(componentDimension);
			}
			panel.add(c);
		}
		this.add(panel);
	}

	/**
	 * 轮廓透明度
	 * 
	 * @author Colonel
	 * 
	 */
	class outlineOpacityJSliderListenner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			outlineOpacityValue = (double) slider.getValue() / 10;
			outlineOpacityResultLabel.setText("  " + slider.getValue());
			applyModify();
		}
	}

	/**
	 * 轮廓粗细
	 * 
	 * @author Colonel
	 * 
	 */
	class outlineSizeJSliderListenner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			outlineSizeValue = slider.getValue();
			outlineSizeResultLabel.setText("  " + outlineSizeValue);
			applyModify();
		}

	}

	/**
	 * 填充透明度
	 * 
	 * @author Colonel
	 * 
	 */
	class innerOpacityJSliderListenner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			innerOpacityValue = (double) slider.getValue() / 10;
			innerOpacityResultLabel.setText("  " + slider.getValue());
			applyModify();
		}

	}

}
