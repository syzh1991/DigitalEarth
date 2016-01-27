package org.gfg.main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SliderDemo1 implements ChangeListener {
	JFrame f = null;
	JSlider slider1;
	JLabel label1;
	JLabel label2;
	JLabel label3;

	public SliderDemo1() {
		f = new JFrame("JSlider Example");
		Container contentPane = f.getContentPane();

		JPanel panel1 = new JPanel();
		slider1 = new JSlider();// 建立一个默认的JSlider组件.
		label1 = new JLabel("目前刻度：" + slider1.getValue());
		panel1.add(label1);
		panel1.add(slider1);
		panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(), "Slider 1", TitledBorder.LEFT,
				TitledBorder.TOP));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2, 1));
		/*
		 * 下面五行程序建立一个水平方向的JSlider组件,并设置其取大值,最小值,初始值与延伸区值,所谓的延伸区值我们在前面
		 * JScrollBar中也提到过,意思是限制JSlider刻度可变动的范围,也就是说延伸区就像是一个障碍区,是无法通行的.延伸区
		 * 设得越大,刻度可变动的范围就越小
		 * .例如若minimum值设为0,maximan值设为100,而extent值设为0,则JSlider刻度可变动
		 * 的区域大小为100-50-0=50刻度(从0-50).
		 */

		JPanel panel3 = new JPanel();

		slider1.addChangeListener(this);

		panel1.setPreferredSize(new Dimension(300, 100));

		GridBagConstraints c;
		int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
		double weightx, weighty;
		Insets inset;

		GridBagLayout gridbag = new GridBagLayout();
		contentPane.setLayout(gridbag);

		gridx = 0; // 第0行
		gridy = 0; // 第0列
		gridwidth = 2; // 占两单位宽度
		gridheight = 1; // 占一单位高度
		weightx = 0; // 窗口增大时组件宽度增大比率0
		weighty = 0; // 窗口增大时组件高度增大比率0
		anchor = GridBagConstraints.CENTER; // 容器大于组件size时将组件
		// 置于容器中央
		fill = GridBagConstraints.BOTH; // 窗口拉大时会填满水平与垂
		// 直空间
		inset = new Insets(0, 0, 0, 0); // 组件间间距
		ipadx = 0; // 组件内水平宽度
		ipady = 0; // 组件内垂直高度
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(panel1, c);
		contentPane.add(panel1);

		gridx = 0;
		gridy = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(panel2, c);
		contentPane.add(panel2);

		gridx = 2;
		gridy = 0;
		gridwidth = 1; // 占一单位宽度
		gridheight = 2; // 占两单位高度
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(panel3, c);
		contentPane.add(panel3);

		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new SliderDemo1();
	}

	// 处理ChangeEvent事件,当用户移动滑动杆时,label上的值会随着用户的移动而改变.
	public void stateChanged(ChangeEvent e) {
		if ((JSlider) e.getSource() == slider1)
			label1.setText("目前刻度：" + slider1.getValue());
	}
}