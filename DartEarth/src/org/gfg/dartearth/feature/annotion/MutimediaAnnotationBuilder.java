package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class MutimediaAnnotationBuilder {
	private static int x;
	private static int y;

	public static void bindMouseListener(final DartEarthAppFrame frame, final int type) {

		MouseListener listener = new MouseAdapter() {

			public void mouseReleased(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
					Position curPos = frame.getWwd().getCurrentPosition();
					MutimediaAnnotationBuilder.x = mouseEvent.getX();
					MutimediaAnnotationBuilder.y = mouseEvent.getY();
					buildMutimediaAnnotation(curPos, frame, type);
					((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					mouseEvent.consume();
					frame.getWwd().getInputHandler().removeMouseListener(this);
				}
			}

		};
		frame.getWwd().getInputHandler().addMouseListener(listener);
	}

	public static void buildMutimediaAnnotation(final Position curPos, final DartEarthAppFrame frame, final int type) {

		MutimediaAnnotationVO mvo = new MutimediaAnnotationVO(curPos.getLatitude().getDegrees(), curPos.getLongitude().getDegrees());
		mvo.setType(type);

		// mvo.setSource("gov/nasa/worldwindx/examples/data/spacemusic.au");

		switch (type) {
		case 0:
			// defult value
			mvo.setSource("gov/nasa/worldwindx/examples/data/spacemusic.au");
			break;
		case 1:
			// defult value
			mvo.setSource("gov/nasa/worldwindx/examples/images/ireland.jpg," + "gov/nasa/worldwindx/examples/images/new_zealand.gif,"
					+ "gov/nasa/worldwindx/examples/images/the_nut.jpg");
			break;
		default:
			break;
		}

		MutimediaAnnotationLayer layer = new MutimediaAnnotationLayer(mvo, frame);

		frame.getWwd().getModel().getLayers().add(0, layer);
		frame.getLayerPanelDialog().getLayerPanel().update();
		frame.getPropertiesDialog().setLocation(MutimediaAnnotationBuilder.x + 100, MutimediaAnnotationBuilder.y);
		frame.getPropertiesDialog().show(new MutimediaAnnotationProperPanel(layer, frame), mvo.getLayerName(), layer);
	}

}
