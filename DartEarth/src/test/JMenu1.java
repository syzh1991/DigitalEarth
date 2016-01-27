package test;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class JMenu1 extends JFrame {

	JTextArea theArea = null;

	public JMenu1() {

		super("JMenu1");
		theArea = new JTextArea();
		theArea.setEditable(false);
		getContentPane().add(new JScrollPane(theArea));
		JMenuBar MBar = new JMenuBar();
		// 调用自行编写的buildFileMenu()方法来构造JMenu.
		JMenu mfile = buildFileMenu();

		MBar.add(mfile); // 将JMenu加入JMenuBar中.
		setJMenuBar(MBar);// 将JMenuBar设置到窗口中.
	}// end of JMenu1()

	public JMenu buildFileMenu() {

		JMenu thefile = new JMenu("File");
		//thefile.setIcon(new ImageIcon("icons/file.gif"));
		return thefile;
	}// end of buildFileMenu()

	public static void main(String[] args) {
		SwingUtil.setLookAndFeel();
		JFrame F = new JMenu1();
		F.setSize(400, 200);
		F.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});// end of addWindowListener
		F.setVisible(true);
	} // end of main
}// end of class JMenu1

class SwingUtil {
	public static final void setLookAndFeel() {
		//try {
			Font font = new Font("JFrame", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();

			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
			//AlloyLookAndFeel.setProperty("alloy.isLookAndFeelFrameDecoration",
		//			"true");
			//AlloyTheme theme = new GlassTheme();
			//LookAndFeel alloyLnF = new AlloyLookAndFeel(theme);
			//UIManager.setLookAndFeel(alloyLnF);
	//	} catch (UnsupportedLookAndFeelException ex) {
		//	ex.printStackTrace();
		//}
	}
}