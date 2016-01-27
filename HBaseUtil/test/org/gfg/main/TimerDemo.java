package org.gfg.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimerDemo implements ActionListener, ChangeListener {
	JFrame f = null;
	ImageIcon[] icons;
	JSlider slider1;
	JLabel label;
	JToggleButton toggleb1, toggleb2;
	JButton b;
	javax.swing.Timer timer;
	int index = 0;

	public TimerDemo() {
		f = new JFrame("Timer Example");
		Container contentPane = f.getContentPane();
		icons = new ImageIcon[5];
		for (int i = 0; i < 5; i++)
			icons[i] = new ImageIcon(".\\icons\\" + (i + 1) + ".jpg");

		label = new JLabel(icons[0]);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2, 1));
		slider1 = new JSlider();
		slider1.setPaintTicks(true);
		slider1.setMajorTickSpacing(20);
		slider1.setMinorTickSpacing(10);
		slider1.setPaintLabels(true);
		slider1.addChangeListener(this);
		panel1.add(slider1);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		toggleb1 = new JToggleButton("Start");
		toggleb1.addActionListener(this);
		buttonPanel.add(toggleb1);
		b = new JButton("Restart");
		b.addActionListener(this);
		buttonPanel.add(b);
		toggleb2 = new JToggleButton("Don't Repeat");
		toggleb2.addActionListener(this);
		buttonPanel.add(toggleb2);
		panel1.add(buttonPanel);

		Hashtable table = new Hashtable();
		table.put(new Integer(0), new JLabel("1"));
		table.put(new Integer(50), new JLabel("2"));
		table.put(new Integer(100), new JLabel("3"));
		slider1.setLabelTable(table);
		/*
		 * 由于java的Timer组件有两种，一种是javax.swing.Timer,一种是java.util.Timer,若我们在程序中import了这两种
		 * package,则系统将不晓得到底要产生哪种Timer组件，就如同本范例一般，因此我们必须在new Timer组件的同时，指
		 * 定要new出哪一种类型的Timer组件。因此我们必须在new Timer组件的同时，指定要new出哪一种类型的Timer组件，在
		 * 此我们当然是要产生Swing的Timer组件，在此我们当然是要产生Swing的Timer组件。
		 */
		timer = new javax.swing.Timer(slider1.getValue() * 10, this);

		contentPane.add(label, BorderLayout.CENTER);
		contentPane.add(panel1, BorderLayout.SOUTH);

		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new TimerDemo();
	}

	// 处理按钮事件与Timer事件。
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toggleb1) {// 当用户按下"start"按钮时，Timer开始运行，且"Start"按钮会变成"Stop",若用户再次按下"stop"按钮，则Timer暂停
			// 运行，且"stop"按钮变成"start".
			if (e.getActionCommand().equals("Start")) {
				timer.start();
				toggleb1.setText("Stop");
			}
			if (e.getActionCommand().equals("Stop")) {
				timer.stop();
				toggleb1.setText("Start");
			}
		}
		// 当用户按下"Don't Repeat"按钮时，则Timer事件只触发一次，若再按一次"Don't Repeat"按钮，则Timer继续运行。
		if (e.getSource() == toggleb2) {
			if (timer.isRepeats()) {
				timer.setRepeats(false);
			} else {
				timer.setRepeats(true);
				timer.start();
			}
		}
		// 当用户按下"Restart"按钮时，则Timer组件的delay值恢复成初如值，并重新运行Timer.
		if (e.getSource() == b) {
			slider1.setValue(50);
			timer.restart();
		}
		// 处理Timer产生的ActionEvent事件，每次时间一到delay所设置的时间,label上的图片就会更换一次。
		if (e.getSource() == timer) {
			if (index == 5)
				index = 0;
			label.setIcon(icons[index]);
			label.repaint();
			// f.pack(); //若要窗口随图形大小变化，可加入此行.
			index++;
		}
	}

	// 处理slider所产生的ChangeEvent事件，当用户移动slider1的滑动杆时，等于从新设置Timer的delay时间。
	public void stateChanged(ChangeEvent e1) {
		timer.setDelay(slider1.getValue() * 10);
	}
}