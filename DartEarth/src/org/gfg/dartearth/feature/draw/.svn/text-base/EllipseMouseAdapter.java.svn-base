package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 
 * 绘制椭圆的鼠标动作
 * 
 * @author 陈亮 <br>
 *         burningcl@gmail.com
 * 
 */
public class EllipseMouseAdapter extends MouseAdapter {
	private boolean active = false;
	private EllipseLayer layer;
	private PositionListener positionListener;
	private MouseMotionAdapter mouseMotionAdapter;
	private static DartEarthAppFrame frame;

	// private static EllipseMouseAdapter currenteMouseAdapter;

	/**
	 * 构造方法，为layer绑定一个鼠标动作
	 * @param layer 椭圆图层
	 * @param frame DartEarthAppFrame
	 */
	public EllipseMouseAdapter(EllipseLayer layer, DartEarthAppFrame frame) {
		this.layer = layer;
		// this.positionListener = positionListener;
		// this.mouseMotionAdapter = mouseMotionAdapter;
		EllipseMouseAdapter.frame = frame;
	}

	/**
	 * 鼠标按下的动作。如果，用户在点击时按住了ctrl，就直接增加一个点。如果没有，则在鼠标抬起时增加一个点。
	 */
	public void mousePressed(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
				if (!mouseEvent.isControlDown()) {
					active = true;
					this.layer.addPosition();
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
		}
	}

	/**
	 * 鼠标点击时的动作。如果用户右击的鼠标，则停止绘制图形。
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		// 结束
		if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
			frame.getWwd().getInputHandler().removeMouseMotionListener(mouseMotionAdapter);
			frame.getWwd().removePositionListener(positionListener);
			frame.getWwd().getInputHandler().removeMouseListener(this);
			((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.isControlDown())
				this.layer.removePosition();
			mouseEvent.consume();
		}
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

	public void setActive(boolean active) {
		this.active = active;
	}

}
