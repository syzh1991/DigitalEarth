package org.gfg.dartearth.feature.layerpanel;

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
 * @author 陈亮
 * 
 */
public class LayerPanelDailog extends JDialog {

	private String DEFAULT_TITLE = "图层";
	private int DEFAULT_WIDTH = 240;
	private int DEFAULT_HEIGHT = 560;
	private int DEFAULT_X_OFFSET = 32;
	private int DEFAULT_Y_OFFSET = 180;
	private LayerPanel layerPanel = null;

	/**
	 * 不多说
	 */
	private static final long serialVersionUID = 6947991932620300072L;

	public LayerPanelDailog(DartEarthAppFrame frame, String text, WorldWindow wwd) {
		super(frame, text);

		// 计算LayerPanelDialog的location
		int x = 0 + DEFAULT_X_OFFSET;
		int y = 0 + DEFAULT_Y_OFFSET;

		// LayerPanelDialog默认就显示出来
		//setVisible(true);

		// LayerPanelDialog的具体的一些参数
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setTitle(DEFAULT_TITLE);
		getContentPane().setLayout(new BorderLayout());
		setResizable(true);
		setModal(false);
		setLocation(x, y);

		// 装入LayerPanel
	//	System.out.println(wwd);
		layerPanel = new LayerPanel(wwd,frame);
		add(layerPanel);
		pack();
		this.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(LayerPanelDailog.this, 1f);
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(LayerPanelDailog.this, 0.7f);
			}
		});
	}

	public LayerPanel getLayerPanel() {
		return layerPanel;
	}

	public void setLayerPanel(LayerPanel layerPanel) {
		this.layerPanel = layerPanel;
	}
}
