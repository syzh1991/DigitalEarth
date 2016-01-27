package org.gfg.dartearth.layer.movablemodel3d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class Model3DAnimatorProperPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Model3DAnimatorLayer layer;
	private DartEarthAppFrame frame;

	public Model3DAnimatorProperPanel(final Model3DAnimatorLayer layer, final DartEarthAppFrame frame) {
		this.layer = layer;
		this.frame = frame;
		JButton trackButton = new JButton();
		trackButton.setText("追踪此Model");
		ActionListener trackListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Model3DAnimatorLayer.setDefaultModel3d(layer.getModel());
				//frame.gotoLatLon(layer.getModel().getPosition().getLatitude().degrees, layer.getModel().getPosition().getLongitude().degrees);
			}
		};
		trackButton.addActionListener(trackListener);
		this.add(trackButton);
	}
}
