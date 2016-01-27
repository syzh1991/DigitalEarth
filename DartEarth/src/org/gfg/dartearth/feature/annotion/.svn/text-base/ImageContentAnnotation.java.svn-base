package org.gfg.dartearth.feature.annotion;

import org.gfg.dartearth.core.ApplicationTemplate.AppFrame;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwindx.examples.util.SlideShowAnnotation;
import gov.nasa.worldwindx.examples.util.SlideShowAnnotationController;

public class ImageContentAnnotation extends ContentAnnotation {
	public ImageContentAnnotation(AppFrame appFrame, SlideShowAnnotation annnotation,
			SlideShowAnnotationController controller, RenderableLayer layer) {
		super(appFrame, annnotation, controller, layer);
	}

	public void detach() {
		super.detach();

		// Stop any threads or timers the controller may be actively running.
		SlideShowAnnotationController controller = (SlideShowAnnotationController) this
				.getController();
		if (controller != null) {
			this.stopController(controller);
		}
	}

	@SuppressWarnings({ "StringEquality" })
	protected void stopController(SlideShowAnnotationController controller) {
		String state = controller.getState();
		if (state == AVKey.PLAY) {
			controller.stopSlideShow();
		}

		controller.stopRetrievalTasks();
	}
}
