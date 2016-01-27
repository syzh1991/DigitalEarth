package org.gfg.dartearth.feature.layerpanel;

import gov.nasa.worldwind.WorldWindow;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 图层对话框
 * 
 * @author 陈亮
 * 
 */
public class LayerPanelDailog2 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DEFAULT_TITLE = "时间轴";
	private int DEFAULT_WIDTH = 900;
	private int DEFAULT_HEIGHT = 100;
	private int DEFAULT_X_OFFSET = 500;
	private int DEFAULT_Y_OFFSET = 900;
//	private int DEFAULT_X_OFFSET = 200;
//	private int DEFAULT_Y_OFFSET = 700;
	
	private LayerPanel2 layerPanel2 = null;


	public LayerPanelDailog2(DartEarthAppFrame frame, String text, WorldWindow wwd) {
		super(frame, text);

		// 计算LayerPanelDialog的location
		int x = 0 + DEFAULT_X_OFFSET;
		int y = 0 + DEFAULT_Y_OFFSET;

		// LayerPanelDialog默认就显示出来
		setVisible(true);

		// LayerPanelDialog的具体的一些参数
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setTitle(DEFAULT_TITLE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setResizable(false);
		setModal(false);
		setLocation(x, y);
		
		// 装入LayerPanel
	//	System.out.println(wwd);
		layerPanel2 = new LayerPanel2(wwd,frame);
		add(layerPanel2);
		pack();
//		this.addWindowFocusListener(new WindowFocusListener() {
//
//			@Override
//			public void windowGainedFocus(WindowEvent arg0) {
//				AWTUtilities.setWindowOpacity(LayerPanelDailog2.this, 1f);
//			}
//
//			@Override
//			public void windowLostFocus(WindowEvent arg0) {
//				AWTUtilities.setWindowOpacity(LayerPanelDailog2.this, 0.7f);
//			}
//		});
	}

	public LayerPanel2 getLayerPanel() {
		return layerPanel2;
	}

	public void setLayerPanel(LayerPanel2 layerPanel2) {
		this.layerPanel2 = layerPanel2;
	}
}
