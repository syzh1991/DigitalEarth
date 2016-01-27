package org.gfg.dartearth.layer.model3d;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class Model3DPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DartEarthAppFrame frame;

	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(85, 24);
	private static Dimension deleteComponentDimension = new Dimension(60, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	private JButton okBtn;
	private JButton applyBtn;
	private JButton cancelBtn;

	private JLabel sizeLabel;
	private JTextField sizeText;
	private JLabel latLabel;
	private JTextField latText;
	private JLabel lngLabel;
	private JTextField lngText;
	private JLabel altLabel;
	private JTextField altText;

	public Model3DPanel(DartEarthAppFrame frame) {
		this.frame = frame;
		this.setLayout(new GridLayout(0, 1, 2, 2));

		initialDialogBtns();

	}

	private void initialPropers() {

	}

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
		cancelBtn.setPreferredSize(btnComponentDimension);
		panel.add(cancelBtn);
		this.add(panel);
	}

	private void applyModify() {

	}

	private void refresh() {

	}
}
