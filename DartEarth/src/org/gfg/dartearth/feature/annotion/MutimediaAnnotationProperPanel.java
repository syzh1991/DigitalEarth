package org.gfg.dartearth.feature.annotion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 多媒体的属性面板。只有当打开多媒体的属性时，该面板将会被加载到属性对话框（属于DartEarthAppFrame），该面板才会被显示出来。
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class MutimediaAnnotationProperPanel extends JPanel {

	private JTextField layerNameTextField;
	private JTextField latTextField;
	private JTextField lngTextField;
	
	private JLabel titleLabel;
	private JTextField titleTextField;
	
	private JRadioButton useUrlRadio;
	private JRadioButton useContentRadio;

	private JLabel pathLabel;
	private JTextField pathField;

	private JLabel contentLabel;
	private JTextArea contentTextArea;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4107769311570276380L;

//	public class UseUrlItemListener implements ItemListener {
//
//		@SuppressWarnings("static-access")
//		@Override
//		public void itemStateChanged(ItemEvent e) {
//			if (e.getStateChange() == e.SELECTED) {
//				if (e.getItemSelectable() == useContentRadio) {
//					urlLabel.setForeground(Color.GRAY);
//					urlField.setEnabled(false);
//					contentLabel.setForeground(Color.BLACK);
//					contentTextArea.setEnabled(true);
//				} else {
//					urlLabel.setForeground(Color.BLACK);
//					urlField.setEnabled(true);
//					contentLabel.setForeground(Color.GRAY);
//					contentTextArea.setEnabled(false);
//				}
//			}
//		}
//	}

	/**
	 * 构造方法，为图层layer构造一个 MutimediaAnnotationProperPanel
	 * 
	 * @param layer
	 *            图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public MutimediaAnnotationProperPanel(final MutimediaAnnotationLayer layer,final DartEarthAppFrame frame) {
		final MutimediaAnnotationVO avo =layer.getAnnotationVO();
		
		this.setLayout(new FlowLayout());

		JPanel panel = new JPanel(new GridLayout(9, 0, 2, 0));
		panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		JLabel layerNameLabel = new JLabel();
		layerNameLabel.setText("图层名");
		panel.add(layerNameLabel);
		layerNameTextField = new JTextField();
		layerNameTextField.setText(avo.getLayerName());
		panel.add(layerNameTextField);

		JLabel latLabel = new JLabel();
		latLabel.setText("纬度:");
		panel.add(latLabel);
		latTextField = new JTextField();
		latTextField.setText(avo.getLat() + "");
		panel.add(latTextField);

		JLabel lngLabel = new JLabel();
		lngLabel.setText("经度:");
		panel.add(lngLabel);
		lngTextField = new JTextField();
		lngTextField.setText(avo.getLng() + "");
		lngTextField.setPreferredSize(new Dimension(300, 1));
		panel.add(lngTextField);
		


		pathLabel = new JLabel();
		pathLabel.setText("资源地址:");
		pathLabel.setForeground(Color.GRAY);
		panel.add(pathLabel);
		pathField = new JTextField();
		pathField.setText(avo.getSource());
		pathField.setPreferredSize(new Dimension(350, 100));
		panel.add(pathField);

		JPanel okPanel = new JPanel();
		JButton okBtn=new JButton();
		okBtn.setText("确定");
		okPanel.setPreferredSize(new Dimension(100, 100));
		okPanel.add(okBtn);
		
		ActionListener okListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				avo.setLat(Double.valueOf(latTextField.getText()));
				avo.setLng(Double.valueOf(lngTextField.getText()));
				avo.setLayerName(layerNameTextField.getText());
				avo.setSource(pathField.getText());
				layer.setName(avo.getLayerName());
				layer.fresh();
				frame.gotoLatLon(avo.getLat(), avo.getLng());
				frame.getLayerPanelDialog().getLayerPanel().update();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		okBtn.addActionListener(okListener);
	
		
		panel.add(okPanel);
		panel.setPreferredSize(new Dimension(360, 400));
		this.add(panel);

	}
}
