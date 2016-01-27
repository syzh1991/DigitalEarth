package org.gfg.dartearth.feature.mainmenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.goTo.GoToPanelDialog;
import org.gfg.dartearth.feature.layerpanel.LayerPanelDailog;

public class OtherMenu {
	public static JMenu createOtherMenu(DartEarthAppFrame frame) {
		JMenu menu = new JMenu();

		menu.setText("其它");
		menu.setPreferredSize(new Dimension(100, 30));
		JMenuItem layerMenuItem = initialLayerMenuItem(frame
				.getLayerPanelDialog());
		menu.add(layerMenuItem);

		JMenuItem gotoMenuItem = initialGoToMenuItem(frame.getGoToPanelDialog());
		menu.add(gotoMenuItem);
		return menu;

	}


	private static JMenuItem initialLayerMenuItem(
			final LayerPanelDailog layerPanelDialog) {
		JMenuItem layerMenuItem = new JMenuItem();
		layerMenuItem.setText("图层");
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LayerPanelDailog a = layerPanelDialog;
			
				if (layerPanelDialog.isVisible() == false) {
					layerPanelDialog.setVisible(true);
				}
			}
		};
		layerMenuItem.addActionListener(listener);
		return layerMenuItem;

	}

	private static JMenuItem initialGoToMenuItem(
			final GoToPanelDialog goToPanelDialog) {
		JMenuItem goToMenuItem = new JMenuItem();
		goToMenuItem.setText("前往");
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (goToPanelDialog.isVisible() == false) {
					goToPanelDialog.setVisible(true);
				}
			}
		};
		goToMenuItem.addActionListener(listener);
		return goToMenuItem;
	}
}
