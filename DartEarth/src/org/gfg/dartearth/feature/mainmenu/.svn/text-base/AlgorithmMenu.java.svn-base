package org.gfg.dartearth.feature.mainmenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.gfg.dartearth.core.AlgorithmLibUI;
import org.gfg.dartearth.core.DartEarthAppFrame;

public class AlgorithmMenu {
	public static JMenuItem createAlgorithmMenu(final DartEarthAppFrame frame) {
		JMenuItem item = new JMenuItem();
		item.setText("云算法");
		item.setSize(new Dimension(100,30));
		//item.setPreferredSize(new Dimension(100,30));
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AlgorithmLibUI algorithmPanelDialog = frame
						.getAlgorithmLibDialog();
				if (algorithmPanelDialog.isVisible() == false) {
					algorithmPanelDialog.setVisible(true);
				}
			}
		};
		item.addActionListener(listener);
		return item;
	}

}
