package org.gfg.dartearth.feature.mainmenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.core.MetaDataSearchUI;

public class MetaDataSearchMenu {
	public static JMenuItem createSearchMenu(final DartEarthAppFrame frame) {
		JMenuItem item = new JMenuItem();
		item.setText("数据获取");
		item.setSize(new Dimension(100,30));
		//item.setPreferredSize(new Dimension(100,30));
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				MetaDataSearchUI searchPanelDialog = frame
						.getMetaDataSearchDialog();
				if (searchPanelDialog.isVisible() == false) {
					searchPanelDialog.setVisible(true);
				}
			}
		};
		item.addActionListener(listener);
		return item;
	}

}
