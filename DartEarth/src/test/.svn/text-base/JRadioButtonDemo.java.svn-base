package test;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class JRadioButtonDemo extends JFrame implements ItemListener {
	private JRadioButton r1;
	private JRadioButton r2;
	private JRadioButton r3;
	private JRadioButton r4;
	private JRadioButton r5;
	private JRadioButton r6;
	private JRadioButton r7;

	public JRadioButtonDemo(String title) {
		super(title);
		setSize(400, 500);
		// 获得容器
		Container container = this.getContentPane();

		// 设置布局为6行一列
		container.setLayout(new GridLayout(2, 1));

		// 创建面板一,文字式问卷调查
		JPanel pane = new JPanel(new GridLayout(1, 4));

		// 设置面板标题
		pane.setBorder(BorderFactory.createTitledBorder("你最喜欢的人是谁??"));

		// 创建选择的选项

		r1 = new JRadioButton("老婆");
		r2 = new JRadioButton("情人");
		r3 = new JRadioButton("二奶");
		// 创建默认选项
		r4 = new JRadioButton("以上都不喜欢", true);

		// 创建ButtonGroup对象，不然就可以多选了，我们要的时单选
		ButtonGroup bg = new ButtonGroup();

		// 在ButtonGroup中加入JRadioButton
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);
		bg.add(r4);

		// 添加到面板
		pane.add(r1);
		pane.add(r2);
		pane.add(r3);
		pane.add(r4);

		// 把面板加入到容器中
		container.add(pane);

		// 创建面板二,图像式JRadioButton

		JPanel pane2 = new JPanel(new GridLayout(3, 1));
		pane2.setBorder(BorderFactory.createTitledBorder("你最喜欢的明星是谁??"));
		// 创建带有Icon的JRadioButton
		r5 = new JRadioButton("柳真", new ImageIcon("E://Java//JCreator2.5//picture//liuzhen.jpg"));
		r6 = new JRadioButton("全智贤", new ImageIcon("E://Java//JCreator2.5//picture//quan.jpg"));
		r7 = new JRadioButton("宋慧乔", new ImageIcon("E://Java//JCreator2.5//picture//song2.jpg"));

		// 创建ButtonGroup对象，不然就可以多选了，我们要的时单选
		ButtonGroup bg2 = new ButtonGroup();

		// 在ButtonGroup中加入JRadioButton
		bg.add(r5);
		bg.add(r6);
		bg.add(r7);

		r5.addItemListener(this);
		r6.addItemListener(this);
		r7.addItemListener(this);
		// 加入面板
		pane2.add(r5);
		pane2.add(r6);
		pane2.add(r7);

		// 把面板加入到容器中
		container.add(pane2);

	}

	public void itemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() == evt.SELECTED) {
			if (evt.getSource() == r5)
				JOptionPane.showMessageDialog(this, "好小子，有眼光，柳真是我偶像", "柳真", JOptionPane.OK_OPTION);
			if (evt.getSource() == r6)
				JOptionPane.showMessageDialog(this, "眼光一般，没有柳真好", "全知贤", JOptionPane.OK_OPTION);
			if (evt.getSource() == r7)
				JOptionPane.showMessageDialog(this, "眼光一般，没有柳真好", "宋慧乔", JOptionPane.OK_OPTION);
		}
	}

	public static void main(String[] args) throws Exception {
		JRadioButtonDemo frame = new JRadioButtonDemo("JRadioButtonDemo");
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}