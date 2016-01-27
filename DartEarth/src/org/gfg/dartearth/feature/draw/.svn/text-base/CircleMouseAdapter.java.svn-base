package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.LatLon;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.util.DistanceCaculator;

/**
 * 
 * 绘制圆的鼠标动作
 * 
 * @author 江琳 <br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class CircleMouseAdapter extends MouseAdapter {
	private boolean active = false;
	// private boolean flag=false;
	private CircleLayer layer;
	private PositionListener positionListener;
	private MouseMotionAdapter mouseMotionAdapter;
	private DartEarthAppFrame frame;

	/**
	 * 构造方法，为layer绑定一个鼠标动作
	 * 
	 * @param layer
	 *            圆图层
	 * @param frame
	 *            DartEarthAppFrame
	 */
	public CircleMouseAdapter(CircleLayer layer, DartEarthAppFrame frame) {
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

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * 鼠标按下的动作。用户从圆的直径的左端点开始画起。
	 */
	public void mousePressed(MouseEvent mouseEvent) {

		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if ((mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
				if (!mouseEvent.isControlDown()) {
					if (!active) {
						this.layer.setStart(LatLon.fromDegrees(this.frame.getWwd().getCurrentPosition().latitude.degrees, this.frame
								.getWwd().getCurrentPosition().longitude.degrees));
						this.layer.setCenter(this.layer.getStart());
					}

					else {
						this.layer.setRadius(DistanceCaculator.caculateDistance(this.layer.getCenter().latitude.degrees,
								this.layer.getCenter().longitude.degrees, this.frame.getWwd().getCurrentPosition().latitude.degrees,
								this.frame.getWwd().getCurrentPosition().longitude.degrees));
					}

					active = true;
					this.layer.getPanel().applyModify();
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
			this.layer.resetRadius();
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
			// System.out.println("BUTTON2");
			frame.getWwd().getInputHandler().removeMouseMotionListener(mouseMotionAdapter);
			frame.getWwd().removePositionListener(positionListener);
			frame.getWwd().getInputHandler().removeMouseListener(this);
			((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			this.layer.createCircle();
			frame.gotoLatLon(this.layer.getCenter().getLatitude().degrees, this.layer.getCenter().getLongitude().degrees);
		}
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.isControlDown())
				this.layer.createCircle();
			;
			mouseEvent.consume();
		}
	}
}
