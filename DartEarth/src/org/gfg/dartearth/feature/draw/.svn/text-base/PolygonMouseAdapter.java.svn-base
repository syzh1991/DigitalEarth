package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 
 * 绘制多边形的鼠标动作
 * 
 * @author 江琳 <br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class PolygonMouseAdapter extends MouseAdapter {
	private boolean active = false;
	private PolygonLayer layer;
	private PositionListener positionListener;
	private MouseMotionAdapter mouseMotionAdapter;
	private DartEarthAppFrame frame;
	private static PolygonMouseAdapter currentPolygonMouseAdapter;

	/**
	 * 构造方法，为layer绑定一个鼠标动作
	 * 
	 * @param layer
	 *            多边形图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public PolygonMouseAdapter(PolygonLayer layer, DartEarthAppFrame frame) {
		this.layer = layer;
		// this.positionListener = positionListener;
		// this.mouseMotionAdapter = mouseMotionAdapter;
		this.frame = frame;
	}

	public PositionListener getPositionListener() {
		return positionListener;
	}

	public void setPositionListener(PositionListener positionListener) {
		this.positionListener = positionListener;
	}

	public MouseMotionAdapter getMouseMotionAdapter() {
		return mouseMotionAdapter;
	}

	public void setMouseMotionAdapter(MouseMotionAdapter mouseMotionAdapter) {
		this.mouseMotionAdapter = mouseMotionAdapter;
	}

	public boolean isActive() {
		return active;
	}

	/**
	 * 设置激活属性，当该属性为true时，鼠标为十字形，多边形处于正在绘制状态。
	 * @param active
	 */
	public void setActive(boolean active) {
		this.frame.getPropertiesDialog().getDragger().setActive(!active);
		this.active = active;
	}

	/**
	 * 鼠标按下的动作。当鼠标按下并很快释放时，增加一点；如果鼠标按下但不释放同时又有拖动动作时，则在释放时添加一点。
	 */
	public void mousePressed(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
				if (!mouseEvent.isControlDown()) {
					this.setActive(true);
					this.layer.addPosition();
					if (this.layer.getPositions().size() > 2) {
						this.layer.removePolygon();
						this.layer.buildPolygon(false);
					}
				}
			}
			mouseEvent.consume();
		}
	}

	/**
	 * 鼠标抬起的动作。修改之前增加的那个点。
	 */
	public void mouseReleased(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (this.layer.getPositions().size() == 1)
				this.layer.removePosition();
			active = false;
			mouseEvent.consume();
			PolygonMouseAdapter.currentPolygonMouseAdapter = this;
			// System.out.println( frame.getWwd().getCurrentPosition());
		}
	}

	/**
	 * 鼠标点击时的动作。如果用户右击的鼠标，则停止绘制图形。
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		// 结束
		if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
			// System.out.println("BUTTON2");
			if (this.layer.getPositions().size() < 3) {
				JOptionPane.showMessageDialog(null, "多边形至少要3个点!");

			} else {
				frame.getWwd().getInputHandler().removeMouseMotionListener(mouseMotionAdapter);
				frame.getWwd().removePositionListener(positionListener);
				frame.getWwd().getInputHandler().removeMouseListener(this);
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.layer.removeAllRenderables();
				this.layer.buildPolygon(true);
			}
			PolygonMouseAdapter.currentPolygonMouseAdapter = null;
		}
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.isControlDown()) {
				this.layer.removePosition();
				this.layer.buildPolygon(true);
			}
			PolygonMouseAdapter.currentPolygonMouseAdapter = this;
			mouseEvent.consume();
		}
		this.frame.getPropertiesDialog().getDragger().setActive(true);
	}
}
