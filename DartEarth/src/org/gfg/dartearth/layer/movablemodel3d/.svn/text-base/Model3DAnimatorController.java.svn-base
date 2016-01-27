package org.gfg.dartearth.layer.movablemodel3d;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.layer.model3d.WWModel3D;

public class Model3DAnimatorController {
	private WorldWindowGLCanvas wwd;
	private DartEarthAppFrame frame;
	private boolean animated = true;

	class Model3DRefreshTask extends TimerTask {

		@Override
		public void run() {
			List<Layer> layers = wwd.getModel().getLayers();
			boolean needToRefresh = false;
			for (Layer layer : layers) {
				if (layer instanceof Model3DAnimatorLayer && layer.isEnabled()) {
					needToRefresh = true;
					Model3DAnimatorLayer mLayer = (Model3DAnimatorLayer) layer;
					mLayer.refresh();
				}
			}
			long dTime = System.currentTimeMillis() - wwd.getRedrawTime();
			if (dTime < 40) {
				needToRefresh = false;
				// System.out.println("refresh too quickily");
			}
			if (wwd.getView().isAnimating()) {
				needToRefresh = false;
			} else {
				animated = false;
			}

			if (needToRefresh) {
				WWModel3D model3d = Model3DAnimatorLayer.getDefaultModel3d();

				if (model3d != null) {
					//if (animated == true) {
					//	frame.gotoLatLon(model3d.getPosition().getLatitude().degrees, model3d.getPosition().getLongitude().degrees);
					//} else {
					BasicOrbitView view = (BasicOrbitView) wwd.getView();
					view.setCenterPosition(model3d.getPosition());
					wwd.redraw();
				//	}
				} else {
					wwd.redraw();
				}
			}
		}
	};

	public Model3DAnimatorController(DartEarthAppFrame frame) {
		this.wwd = frame.getWwd();
		this.frame = frame;
	}

	public void start() {
		Timer timer = new Timer();
		timer.schedule(new Model3DRefreshTask(), 0, 40);
	}
}
