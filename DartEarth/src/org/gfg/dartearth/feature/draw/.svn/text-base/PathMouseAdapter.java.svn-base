package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class PathMouseAdapter extends MouseAdapter {
	private boolean active = false;
	private PathLayer layer;
	private PositionListener positionListener;
	private MouseMotionAdapter mouseMotionAdapter;
	private static DartEarthAppFrame frame;
	private static PathMouseAdapter currentPathMouseAdapter;

	public PathMouseAdapter(PathLayer layer, DartEarthAppFrame frame) {
		this.layer = layer;
		// this.positionListener = positionListener;
		// this.mouseMotionAdapter = mouseMotionAdapter;
		PathMouseAdapter.frame = frame;
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

	public void mousePressed(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
				if (!mouseEvent.isControlDown()) {
					active = true;
					this.layer.addPosition();
				}
			}
			mouseEvent.consume();
			PathMouseAdapter.currentPathMouseAdapter = this;
			//System.out.println( frame.getWwd().getCurrentPosition());
		}
	}

	public void mouseReleased(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (this.layer.getPositions().size() == 1)
				this.layer.removePosition();
			active = false;
			mouseEvent.consume();
			PathMouseAdapter.currentPathMouseAdapter = this;
			//System.out.println( frame.getWwd().getCurrentPosition());
		}
	}

	public void mouseClicked(MouseEvent mouseEvent) {
		// 结束
		if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
			// System.out.println("BUTTON2");
			frame.getWwd().getInputHandler().removeMouseMotionListener(mouseMotionAdapter);
			frame.getWwd().removePositionListener(positionListener);
			frame.getWwd().getInputHandler().removeMouseListener(this);
			((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			PathMouseAdapter.currentPathMouseAdapter = null;
		}
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.isControlDown())
				this.layer.removePosition();
			mouseEvent.consume();
			PathMouseAdapter.currentPathMouseAdapter = this;
		}
	}

	public static void killCurrentPathMouseAdapter() {
		if (PathMouseAdapter.currentPathMouseAdapter != null) {
			PathMouseAdapter.frame.getWwd().getInputHandler()
					.removeMouseMotionListener(PathMouseAdapter.currentPathMouseAdapter.mouseMotionAdapter);
			PathMouseAdapter.frame.getWwd().removePositionListener(PathMouseAdapter.currentPathMouseAdapter.positionListener);
			PathMouseAdapter.frame.getWwd().getInputHandler().removeMouseListener(PathMouseAdapter.currentPathMouseAdapter);
			PathMouseAdapter.currentPathMouseAdapter = null;
		}
	}
}
