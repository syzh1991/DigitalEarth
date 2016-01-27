package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.GlobeAnnotation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class BubbleAnnotationProperPanel extends JPanel {

	/**
	 * 气泡的属性面板。只有当打开气泡的属性时，该面板将会被加载到属性对话框（属于DartEarthAppFrame），该面板才会被显示出来。
	 * 
	 * @author 江琳<br>
	 *         lim.chiang.zju@gmail.com
	 *         
	 */
	private static final long serialVersionUID = -4107769311570276380L;
	private DartEarthAppFrame frame;
	private final JTextField layerTextFiled;
	private final JTextField latTextFiled;
	private final JTextField lngTextFiled;
	private final JTextField contentTextFiled;
	private JButton okBtn;
	private JButton applyBtn;
	private JButton cancelBtn;

	private BubbleAnnotation annotation;

	/**
	 * 标签的分辨率真为80*24
	 */
	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(110, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	/**
	 * 构造方法，为图层layer构造一个BubbleAnnotationProperPanel
	 * 
	 * @param layer
	 *            图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public BubbleAnnotationProperPanel(BubbleAnnotationLayer layer, final DartEarthAppFrame frame) {
		this.frame = frame;
		this.annotation = layer.getAnnotation();
		// this.setLayout(new GridLayout(0, 1, 0, 1));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 0, 1));
		panel.setPreferredSize(new Dimension(360, 320));

		JLabel layerLabel = new JLabel();
		layerLabel.setText("图层:");
		layerTextFiled = new JTextField();
		layerTextFiled.setText(annotation.getLayerName() + "");
		buildPanel(panel, layerLabel, layerTextFiled);

		JLabel latLabel = new JLabel();
		latLabel.setText("纬度:");
		latTextFiled = new JTextField();
		latTextFiled.setText(annotation.getLat() + "");
		buildPanel(panel, latLabel, latTextFiled);

		JLabel lngLabel = new JLabel();
		lngLabel.setText("经度:");
		lngTextFiled = new JTextField();
		lngTextFiled.setText(annotation.getLng() + "");
		buildPanel(panel, lngLabel, lngTextFiled);

		JLabel contentLabel = new JLabel();
		contentLabel.setText("内容:");
		contentLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentTextFiled = new JTextField();
		contentTextFiled.setText("");
		if(annotation.getText()!=null){
			contentTextFiled.setText(annotation.getText());
		}
		
		buildPanel(panel, contentLabel, contentTextFiled);

		initialDialogBtns(panel);
		this.add(panel);

	}

	/**
	 * 刷新气泡的属性，当气泡的位置由于用户的拖动而改变时，气泡的属性应该改变。
	 * 
	 */
	public void refresh() {
		latTextFiled.setText(annotation.getLat() + "");
		lngTextFiled.setText(annotation.getLng() + "");

	}

	/**
	 * 私有方法，用于生成面板内容
	 * 
	 * @param jPanel
	 *            增添控件的panel
	 * @param jcs
	 *            将被增添的Component
	 */
	private void buildPanel(JPanel jPanel, JComponent... jcs) {
		if (jcs.length >= 1) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(320, 28));
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));

			JComponent label = jcs[0];
			label.setPreferredSize(labelDimension);
			panel.add(label);

			Dimension dimension = null;
			if (jcs.length == 2) {
				dimension = bigComponentDimension;
			} else if (jcs.length == 3) {
				dimension = smallComponentDimension;
			}
			for (int i = 1; i < jcs.length; i++) {
				JComponent component = jcs[i];
				component.setPreferredSize(dimension);
				panel.add(component);
			}

			jPanel.add(panel);
		}
	}

	/**
	 * 确认修改，也就是应用修改
	 */
	private void applyModify() {
		String layerName = layerTextFiled.getText();
		float lat = Float.parseFloat(latTextFiled.getText().toString());
		float lng = Float.parseFloat(lngTextFiled.getText().toString());
		String content = contentTextFiled.getText();
		frame.getPropertiesDialog().getLayer().removeAllRenderables();
		frame.getPropertiesDialog().getLayer().setName(layerName);
		Position position = Position.fromDegrees(lat, lng);
		GlobeAnnotation bubble = new GlobeAnnotation(content, position);
		frame.getPropertiesDialog().getLayer().addRenderable(bubble);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.gotoLatLon(lat, lng);
	}

	/**
	 * 初始化对话框最后的“确认，应用，取消”按钮
	 * 
	 * @param jPanel
	 *            这三个按钮所在的panel
	 */
	private void initialDialogBtns(JPanel panel) {
		// final JPanel panel = this;
		JPanel dialogBtnsPanel = new JPanel();
		dialogBtnsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dialogBtnsPanel.setPreferredSize(new Dimension(320, 28));
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
		dialogBtnsPanel.add(okBtn);

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
		dialogBtnsPanel.add(applyBtn);

		cancelBtn = new JButton("取消");
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// refresh();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		cancelBtn.addActionListener(cancelListener);
		// cancelBtn.addActionListener(okListener);
		cancelBtn.setPreferredSize(btnComponentDimension);
		dialogBtnsPanel.add(cancelBtn);
		panel.add(dialogBtnsPanel);
		// put(okBtn, applyBtn, cancelBtn);
	}

}
