package org.gfg.dartearth.feature.goTo;

import gov.nasa.worldwind.WorldWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;

import org.gfg.dartearth.core.DartEarthAppFrame;

import com.sun.awt.AWTUtilities;

/**
 * 图层对话框
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class GoToPanelDialog extends JDialog {

	private String DEFAULT_TITLE = "前往";
	private int DEFAULT_WIDTH = 520;
	private int DEFAULT_HEIGHT = 690;
	private int DEFAULT_X_OFFSET = 32;
	private int DEFAULT_Y_OFFSET = 180;
	private GoToPanel goToPanel = null;

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6947991932620300072L;

	/**
	 * 构造函数
	 * 
	 * @param frame
	 * @param text 标题
	 * @param wwd
	 */
	public GoToPanelDialog(DartEarthAppFrame frame, String text, WorldWindow wwd) {
		super(frame, text);

		// 计算goToPanelDialog的location
		int x = 0 + DEFAULT_X_OFFSET;
		int y = 0 + DEFAULT_Y_OFFSET;

		// goToPanelDialog默认不显示出来
		setVisible(false);

		// goToPanelDialog的具体的一些参数
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setTitle(DEFAULT_TITLE);
		getContentPane().setLayout(new BorderLayout());
		setResizable(true);
		setModal(false);
		setLocation(x, y);

		// 装入goToPanel
		goToPanel = new GoToPanel(wwd, frame);
		add(goToPanel);
		pack();
		this.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(GoToPanelDialog.this, 1f);
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(GoToPanelDialog.this, 0.7f);
			}
		});
	}

	public GoToPanel getGoToPanel() {
		return goToPanel;
	}

	public void setGoToPanel(GoToPanel goToPanel) {
		this.goToPanel = goToPanel;
	}

}
