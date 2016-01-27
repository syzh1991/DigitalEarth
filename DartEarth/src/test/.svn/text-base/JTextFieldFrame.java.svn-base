package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class JTextFieldFrame extends JFrame {
	private JLabel lblName = null;
	private JLabel lblSex = null;
	private JLabel lblHeight = null;
	private JLabel lblWeight = null;
	private JLabel lblPwd = null;

	private JTextField txtName = null;
	private JTextField txtSex = null;
	private JTextField txtHeight = null;
	private JTextField txtWeight = null;
	private JPasswordField pwd = null;

	public JTextFieldFrame(String title) {
		super(title);
		setSize(300, 200);
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 2, 2);

		pane.setBorder(BorderFactory.createTitledBorder("个人信息"));

		lblName = new JLabel("姓名");
		lblSex = new JLabel("性别");
		lblHeight = new JLabel("身高");
		lblWeight = new JLabel("体重");
		lblPwd = new JLabel("密码");

		// 构造JTextField的几种方式
		// 指定初始字段长度
		txtName = new JTextField(new JTextField_Filter(10), "luliuyan", 10);
		// 建立新的JTextField
		txtSex = new JTextField();
		// 指定初始字符串
		txtHeight = new JTextField("173cm");
		// 指定初始字符串和初始字段长度
		txtWeight = new JTextField("80kg,有点重了", 20);

		pwd = new JPasswordField(new JPasswordField_Filter(10), "", 10);

		// 设置字段显示的字符
		pwd.setEchoChar('$');

		gbc.gridx = 0;
		gbc.gridy = 0;
		pane.add(lblName, gbc);

		gbc.gridx = 1;
		pane.add(txtName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		pane.add(lblSex, gbc);

		gbc.gridx = 1;
		pane.add(txtSex, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		pane.add(lblHeight, gbc);

		gbc.gridx = 1;
		pane.add(txtHeight, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		pane.add(lblWeight, gbc);

		gbc.gridx = 1;
		pane.add(txtWeight, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		pane.add(lblPwd, gbc);

		gbc.gridx = 1;
		pane.add(pwd, gbc);

		container.add(pane);

	}
}

class JTextField_Filter extends PlainDocument {
	private int maxLength = 0;

	public JTextField_Filter(int max) {
		this.maxLength = max;
	}

	public void insertString(int offset, String str, AttributeSet att) throws BadLocationException {
		if (getLength() + str.length() > maxLength) {
			Toolkit.getDefaultToolkit().beep();
		} else {
			super.insertString(offset, str, att);
		}
	}
}

class JPasswordField_Filter extends PlainDocument {
	private int maxLength = 0;
	int result;

	public JPasswordField_Filter(int max) {
		this.maxLength = max;
	}

	public void insertString(int offset, String str, AttributeSet att) throws BadLocationException {
		for (int i = 0; i < 9; i++) {
			result = Integer.toString(i).compareTo(str);
			if (result == 0) {
				if (getLength() + str.length() > maxLength) {
					Toolkit.getDefaultToolkit().beep();
				} else {
					super.insertString(offset, str, att);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JTextFieldFrame frame = new JTextFieldFrame("JTextFieldDemo");

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}