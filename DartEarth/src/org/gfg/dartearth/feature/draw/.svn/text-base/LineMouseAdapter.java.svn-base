package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import org.gfg.dartearth.core.DartEarthAppFrame;

/**
 * 线段鼠标动作
 * 
 * @author 陈亮<br>
 *         burningcl@gmail.com
 * 
 */
public class LineMouseAdapter extends MouseAdapter {
	private boolean active = false;
	private LineLayer layer;
	private PositionListener positionListener;
	private MouseMotionAdapter mouseMotionAdapter;
	private static DartEarthAppFrame frame;
	private static LineMouseAdapter currentLineMouseAdapter;

	public LineMouseAdapter(LineLayer layer, DartEarthAppFrame frame) {
		this.layer = layer;
		// this.positionListener = positionListener;
		// this.mouseMotionAdapter = mouseMotionAdapter;
		LineMouseAdapter.frame = frame;
	}

	/**
	 * 鼠标按下的动作。当鼠标按下时，并且按住了ctrl，增一点；如果没有按ctrl，则在抬起时，增加一个点。
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
			LineMouseAdapter.currentLineMouseAdapter = this;
			// System.out.println( frame.getWwd().getCurrentPosition());
		}
	}

	/**
	 * 鼠标抬起的动作
	 */
	public void mouseReleased(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (this.layer.getPositions().size() == 1)
				this.layer.removePosition();
			active = false;
			mouseEvent.consume();
			LineMouseAdapter.currentLineMouseAdapter = this;
			// System.out.println( frame.getWwd().getCurrentPosition());
		}
	}

	/**
	 * 鼠标点击的动作。如果点击右键时，绘制结束。
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		// 结束
		if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
			// System.out.println("BUTTON2");
			frame.getWwd().getInputHandler().removeMouseMotionListener(mouseMotionAdapter);
			frame.getWwd().removePositionListener(positionListener);
			frame.getWwd().getInputHandler().removeMouseListener(this);
			((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			LineMouseAdapter.currentLineMouseAdapter = null;
		}
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.isControlDown())
				this.layer.removePosition();
			mouseEvent.consume();
			LineMouseAdapter.currentLineMouseAdapter = this;
		}
	}

	public static void killCurrentLineMouseAdapter() {
		if (LineMouseAdapter.currentLineMouseAdapter != null) {
			LineMouseAdapter.frame.getWwd().getInputHandler()
					.removeMouseMotionListener(LineMouseAdapter.currentLineMouseAdapter.mouseMotionAdapter);
			LineMouseAdapter.frame.getWwd().removePositionListener(LineMouseAdapter.currentLineMouseAdapter.positionListener);
			LineMouseAdapter.frame.getWwd().getInputHandler().removeMouseListener(LineMouseAdapter.currentLineMouseAdapter);
			LineMouseAdapter.currentLineMouseAdapter = null;
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
