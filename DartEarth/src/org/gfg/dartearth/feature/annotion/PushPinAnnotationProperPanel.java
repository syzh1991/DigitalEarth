package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 图钉的属性面板。只有当打开图钉的属性时，该面板将会被加载到属性对话框（属于DartEarthAppFrame），该面板才会被显示出来。
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class PushPinAnnotationProperPanel extends JPanel {

	private static final long serialVersionUID = -4107769311570276380L;

	private JLabel layerNameLabel;
	private JTextField layerNameTextField;
	private JLabel latLabel;
	private JTextField latTextField;
	private JLabel lngLabel;
	private JTextField lngTextField;

	private JLabel titleLabel;
	private JTextField titleTextField;

	private JLabel useUrlLabel;
	private JRadioButton useUrlRadio;
	private JRadioButton useContentRadio;

	private JLabel urlLabel;
	private JTextField urlField;

	private JLabel contentLabel;
	private JTextArea contentTextArea;

	private JButton okBtn;
	private JButton applyBtn;
	private JButton cancelBtn;

	private DartEarthAppFrame frame;

	/**
	 * 标签的分辨率真为80*24
	 */
	private static Dimension labelDimension = new Dimension(80, 24);

	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(110, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	private PushPinAnnotationLayer layer;

	/**
	 * 刷新图钉的属性，当图钉的位置由于用户的拖动而改变时，图钉的属性应该改变。
	 * 
	 * @param p
	 *            现在图钉的位置
	 */
	public void fresh(Position p) {
		this.latTextField.setText(p.getLatitude().getDegrees() + "");
		this.lngTextField.setText(p.getLongitude().getDegrees() + "");

	}

	/**
	 * 是否使用URL为ItemListener，当用户选择url作为图钉的内容时，内容输入框应该disable。对于url也是同理。
	 * 
	 * @author 陈亮<br>
	 *         burningcl@gmail.com
	 * 
	 */
	private class UseUrlItemListener implements ItemListener {

		@SuppressWarnings("static-access")
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == e.SELECTED) {
				if (e.getItemSelectable() == useContentRadio) {
					urlLabel.setForeground(Color.GRAY);
					urlField.setEnabled(false);
					contentLabel.setForeground(Color.BLACK);
					contentTextArea.setEnabled(true);
				} else {
					urlLabel.setForeground(Color.BLACK);
					urlField.setEnabled(true);
					contentLabel.setForeground(Color.GRAY);
					contentTextArea.setEnabled(false);
				}
			}
		}
	}

	/**
	 * 构造方法，为图层layer构造一个PushPinAnntationProperPanel
	 * 
	 * @param layer
	 *            图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public PushPinAnnotationProperPanel(final PushPinAnnotationLayer layer, final DartEarthAppFrame frame) {
		this.frame = frame;
		this.layer = layer;

		final PushPinAnnotation annotation = layer.getAnnotation();

		// this.setLayout(new FlowLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 0, 1));
		panel.setPreferredSize(new Dimension(360, 480));
		// this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// JPanel panel = new JPanel(new GridLayout(0, 1, 0,0));
		// panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		layerNameLabel = new JLabel("图层名:");
		layerNameTextField = new JTextField();
		layerNameTextField.setText(annotation.getLayerName());
		buildPanel(panel, layerNameLabel, layerNameTextField);

		latLabel = new JLabel();
		latLabel.setText("纬度:");
		latTextField = new JTextField();
		latTextField.setText(annotation.getLat() + "");
		buildPanel(panel, latLabel, latTextField);

		lngLabel = new JLabel();
		lngLabel.setText("经度:");
		lngTextField = new JTextField();
		lngTextField.setText(annotation.getLng() + "");
		buildPanel(panel, lngLabel, lngTextField);

		titleLabel = new JLabel();
		titleLabel.setText("标题:");
		titleTextField = new JTextField();
		titleTextField.setText(annotation.getTitle());
		buildPanel(panel, titleLabel, titleTextField);

		useUrlLabel = new JLabel();
		useUrlLabel.setText("使用URL:");

		UseUrlItemListener listener = new UseUrlItemListener();
		ButtonGroup bg = new ButtonGroup();
		useUrlRadio = new JRadioButton();
		useUrlRadio.setText("是");
		useUrlRadio.setSelected(false);
		useUrlRadio.addItemListener(listener);
		bg.add(useUrlRadio);
		useContentRadio = new JRadioButton();
		useContentRadio.setText("否");
		useContentRadio.setSelected(true);
		useContentRadio.addItemListener(listener);
		bg.add(useContentRadio);
		buildPanel(panel, useUrlLabel, useUrlRadio, useContentRadio);

		urlLabel = new JLabel();
		urlLabel.setText("URL:");
		urlLabel.setForeground(Color.GRAY);
		urlField = new JTextField();
		urlField.setText(annotation.getUrl());
		urlField.setEnabled(false);
		buildPanel(panel, urlLabel, urlField);

		contentLabel = new JLabel();
		contentLabel.setText("内容");
		buildPanel(panel, contentLabel);
		// this.add(contentLabel);

		contentTextArea = new JTextArea();
		contentTextArea.setText(annotation.getContent());
		contentTextArea.setLineWrap(true);
		contentTextArea.setWrapStyleWord(true);
		JScrollPane textScrollPanel = new JScrollPane(contentTextArea);
		textScrollPanel.setPreferredSize(new Dimension(320, 40));
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPanel.setPreferredSize(new Dimension(320, 40));
		contentPanel.add(textScrollPanel);
		panel.add(contentPanel);
		// buildPanel(contentLabel,textScrollPanel);

		initialDialogBtns(panel);

		// this.add(panel);
		this.add(panel);

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
		PushPinAnnotation annotation = layer.getAnnotation();
		annotation.setLat(Double.valueOf(latTextField.getText()));
		annotation.setLng(Double.valueOf(lngTextField.getText()));
		annotation.setLayerName(layerNameTextField.getText());
		annotation.setTitle(titleTextField.getText());
		annotation.setContent(contentTextArea.getText());
		layer.fresh();
		frame.gotoLatLon(annotation.getLat(), annotation.getLng());
		frame.getLayerPanelDialog().getLayerPanel().update();
	}

	/**
	 * 初始化对话框最后的“确认，应用，取消”按钮
	 * 
	 * @param jPanel
	 *            这三个按钮所在的panel
	 */
	private void initialDialogBtns(JPanel jPanel) {
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
		jPanel.add(dialogBtnsPanel);
		// put(okBtn, applyBtn, cancelBtn);
	}
}
