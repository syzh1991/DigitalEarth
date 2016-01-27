package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Polyline;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 线段属性的面板
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class LineProperPanel extends JPanel {
	private static final long serialVersionUID = 930710547300508185L;

	private LineLayer layer;

	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(200, 24);
	private static Dimension smallComponentDimension = new Dimension(85, 24);
	private static Dimension deleteComponentDimension = new Dimension(60, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);
	private static Dimension jsliderComponentDimension = new Dimension(200, 36);

	private DartEarthAppFrame frame;

	private Polyline line;
	private JLabel layerNameLabel;
	private JTextField layerNameTextField;

	private List<Position> points;
	private JLabel colorLabel;
	private JButton colorBtn;
	// alpha滑动条
	private JLabel alphaJLabel;
	private JSlider alphaJSlider;
	private int alphaValue = 127;// alpha对应的值 0-255；
	private JLabel alphaValueLabel;// 值的显示

	// 线粗细的滑动条
	private JLabel sizeLabel;
	private JSlider sizeJSlider;
	private double sizeValue;// size对应的值为0-10;
	private JLabel sizeValueJLabel;// 值的显示

	private Color color;

	private JButton okBtn;
	private JButton cancelBtn;
	private List<JPanel> pointPanels;
	private List<JButton> deleteBtns;
	private List<JTextField> latTexts;
	private List<JTextField> lngTexts;
	private Map<String, Object> backup = null;

	/**
	 * 构造方法，几乎所有的面板的构造方法都是相同的功能
	 * 
	 * @param layer
	 *            图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public LineProperPanel(final LineLayer layer, final DartEarthAppFrame frame) {
		// 数据进行备份
		backup = layer.backup();

		this.setLayout(new GridLayout(0, 1, 2, 2));
		this.layer = layer;
		this.frame = frame;
		this.line = layer.getLine();

		deleteBtns = new ArrayList<JButton>();
		latTexts = new ArrayList<JTextField>();
		lngTexts = new ArrayList<JTextField>();
		pointPanels = new ArrayList<JPanel>();

		layerNameLabel = new JLabel("图层名:");
		layerNameTextField = new JTextField();
		String layerName = layer.getName();
		layerNameTextField.setName(layerName);
		layerNameTextField.setText(layerName);// 给textFiled命名
		layerNameTextField.addFocusListener(new textFieldFocusListenner());
		put(layerNameLabel, layerNameTextField);

		// this.buildPointPanels();

		colorLabel = new JLabel("颜色:");
		// this.add(colorLabel);
		// colorChooser = new JColorChooser();
		color = line.getColor();
		// colorChooser.setColor(color);
		// colorChooser.set
		// color=JColorChooser.showDialog(this, "请选择颜色", color);
		colorBtn = new JButton();
		// colorBtn.setText("");
		colorBtn.setBackground(color);
		// final LineProperPanel panel = this;
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color choosenColor = JColorChooser.showDialog(null, "请选择颜色", color);
				colorBtn.setBackground(choosenColor);
				if (choosenColor == null) {
					choosenColor = color;
				}
				line.setColor(choosenColor);
			}
		};
		colorBtn.addActionListener(colorChooseListener);
		put(colorLabel, colorBtn);
		// this.add(colorBtn);

		// alpha滑动条
		alphaJLabel = new JLabel("透明度:");
		alphaJSlider = new JSlider();
		alphaJSlider.setPaintTicks(true);
		alphaJSlider.setMaximum(255);
		alphaJSlider.setMajorTickSpacing(51);
		alphaJSlider.setMinorTickSpacing(5);
		alphaJSlider.setPaintLabels(true);
		alphaJSlider.addChangeListener(new alphaJSliderListenner());
		alphaValue = layer.getLine().getColor().getAlpha();
		alphaValueLabel = new JLabel("  " + alphaValue);
		alphaJSlider.setValue(alphaValue);
		put(alphaJLabel, alphaJSlider, alphaValueLabel);

		// 线粗细
		sizeLabel = new JLabel("粗细:");
		sizeJSlider = new JSlider();
		sizeJSlider.setPaintTicks(true);
		sizeJSlider.setMaximum(10);
		sizeJSlider.setMajorTickSpacing(2);
		sizeJSlider.setMinorTickSpacing(1);
		sizeJSlider.setPaintLabels(true);
		sizeJSlider.addChangeListener(new sizeJSliderListenner());
		sizeValue = (int) (layer.getLine().getLineWidth());
		sizeValueJLabel = new JLabel("  " + sizeValue);
		sizeJSlider.setValue((int) sizeValue);
		put(sizeLabel, sizeJSlider, sizeValueJLabel);

		initialDialogBtns();
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
				// 数据进行备份
				backup = layer.backup();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		okBtn.addActionListener(okListener);
		okBtn.setPreferredSize(btnComponentDimension);
		panel.add(okBtn);

		cancelBtn = new JButton("取消");
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cancel();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		cancelBtn.addActionListener(cancelListener);
		cancelBtn.setPreferredSize(btnComponentDimension);
		panel.add(cancelBtn);
		buildPointPanels();
		this.add(panel);
	}

	/**
	 * 应用修改
	 */
	private void applyModify() {

		Color choosenColor = colorBtn.getBackground();
		color = new Color(choosenColor.getRed(), choosenColor.getGreen(), choosenColor.getBlue(), alphaValue);
		// oldLineColor = color;
		List<Position> positions = new ArrayList<Position>();
		for (int i = 0; i < latTexts.size(); i++) {
			Position p = layer.getPositions().get(i);
			double lat = Double.valueOf(latTexts.get(i).getText());
			double lng = Double.valueOf(lngTexts.get(i).getText());
			Position newP = new Position(Angle.fromDegrees(lat), Angle.fromDegrees(lng), p.getElevation());
			positions.add(newP);
		}
		layer.setPositions(positions);
		line.setPositions(positions);
		line.setColor(color);
		line.setLineWidth(sizeValue);
		layer.refresh();
		frame.getLayerPanelDialog().getLayerPanel().update();
	}

	/**
	 * 放置各个控件到面板中去
	 * 
	 * @param components
	 *            各个控件
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
			}
			// 滑动条
			else if (c instanceof JSlider) {
				c.setPreferredSize(jsliderComponentDimension);
			} else {
				c.setPreferredSize(componentDimension);
			}
			panel.add(c);
		}
		this.add(panel);
	}

	private void buildPointPanels() {
		deleteBtns.clear();
		latTexts.clear();
		lngTexts.clear();
		points = layer.getPositions();
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
				lngTextField.setPreferredSize(smallComponentDimension);
				panel.add(lngTextField);
				lngTexts.add(lngTextField);
				JTextField latTextField = new JTextField();
				latTextField.setText(p.getLatitude().getDegrees() + "");
				latTextField.setPreferredSize(smallComponentDimension);
				panel.add(latTextField);
				latTexts.add(latTextField);
				JButton deleteBtn = new JButton();
				deleteBtn.setText("删除");
				deleteBtn.setPreferredSize(deleteComponentDimension);
				ActionListener deleteListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if (deleteBtns.size() > 2) {
							deleteBtns.remove(index);
							points.remove(index);
							refreshPointsPanel();
							layer.refresh();
							if (deleteBtns.size() <= 2) {
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
	 * 刷新，当用户修改了线段的属性，就会刷新此线段。
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
	 * 在线段的属性框里点击“取消” 恢复到最近保存的一次
	 */
	@SuppressWarnings("unchecked")
	public void cancel() {
		// 移除
		points = this.layer.getPositions();
		for (int i = 0; i < points.size(); i++) {
			final int index = i;
			deleteBtns.remove(index);
			points.remove(index);
			refreshPointsPanel();
			layer.refresh();
		}
		// 恢复成原来的
		points.clear();
		points.addAll((List<Position>) backup.get("last_positions"));
		line.setPositions(points);
		layer.setPositions(points);
		buildPointPanels();
		// 名称
		layer.setName((String) backup.get("last_name"));
		// 颜色
		Color newColor = (Color) backup.get("last_color");
		line.setColor(newColor);
		colorBtn.setBackground(newColor);
		// 透明度
		alphaJSlider.setValue((int) (newColor.getAlpha()));
		// 粗细
		double newWidth = (Double) backup.get("last_width");
		line.setLineWidth(newWidth);
		sizeJSlider.setValue((int) (newWidth));

		refreshPointsPanel();
		layer.refresh();
		frame.getLayerPanelDialog().getLayerPanel().update();
	}

	// 点击取消 返回原先的值
	public void refresh() {
		refreshPointsPanel();

		layerNameTextField.setText(layer.getName());
		colorBtn.setBackground(layer.getLine().getColor());
		alphaValueLabel.setText(layer.getLine().getColor().getAlpha() + "");
		sizeValueJLabel.setText(layer.getLine().getLineWidth() + "");
	}

	/**
	 * alpha 滑动条事件响应
	 * 
	 * @author Colonel
	 * 
	 */
	class alphaJSliderListenner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			alphaValue = slider.getValue();
			alphaValueLabel.setText("  " + alphaValue);
			applyModify();
		}

	}

	/**
	 * 线条粗细
	 * 
	 * @author Colonel
	 * 
	 */
	class sizeJSliderListenner implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			sizeValue = slider.getValue();
			sizeValueJLabel.setText("  " + sizeValue);
			applyModify();
		}

	}

	/**
	 * textfield焦点捕捉
	 * 
	 * @author Colonel
	 * 
	 */
	class textFieldFocusListenner implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			layer.setName(layerNameTextField.getText());
		}

	}

}
