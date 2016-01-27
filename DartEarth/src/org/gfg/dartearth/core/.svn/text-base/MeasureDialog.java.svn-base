package org.gfg.dartearth.core;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

/**
 * 测量对话框。需要所需测量的图形的面板才能显示。所需测量的图形的面板在所需测量的图形的图层中。
 * 
 * @author 陈亮<br>
 *         E-Mail:burningcl@gmail.com
 * 
 */
public class MeasureDialog extends JDialog {

	private static final long serialVersionUID = -7311737781614327522L;
	/**
	 * panel面板，默认为空。在对话框显示时，需要面板。
	 */
	private JPanel panel;
	/**
	 * frame
	 */
	private DartEarthAppFrame frame;

/**
 * 构造函数。需要传入Frame。对话框的默认大小为480*320。
 * @param frame
 */
	public MeasureDialog(DartEarthAppFrame frame) {
		super(frame, "");
		this.frame = frame;
		setPreferredSize(new Dimension(480, 320));
		setResizable(true);
		setModal(false);
		this.addWindowFocusListener(new WindowFocusListener() {
			/**
			 * 选中时，透明度为1
			 */
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(MeasureDialog.this, 1f);
			}

			/**
			 * 失焦时，透明度为0.7。这样做是为了不阻碍用户查看对话框 后面的画面。
			 */
			@Override
			public void windowLostFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(MeasureDialog.this, 0.7f);
			}
		});
	}

	/**
	 * 显示对话框
	 * @param layerName 图层名
	 * @param panel 面板
	 */
	public void show(String layerName, JPanel panel) {
		if (this.panel != null) {
			this.remove(this.panel);
		}
		this.panel = panel;
		this.setTitle(layerName + " 测量");
		this.add(panel);
		this.setVisible(true);
		this.setLayout(new FlowLayout());
		this.pack();
	}

	/**
	 * 重写父类方法。<br>
	 * 当visible为false时，需要将DartEarthFrame中的测量面板清除。
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible == false) {
			frame.removeProfile();
			frame.getMeasurer().clear();
		}
		super.setVisible(visible);
	}
}
