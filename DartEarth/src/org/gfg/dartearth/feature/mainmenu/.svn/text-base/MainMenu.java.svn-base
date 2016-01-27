package org.gfg.dartearth.feature.mainmenu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class MainMenu {
	private static JMenuBar menuBar;
	private static JMenu annotationMenu;
	private static JMenu fileMenu;
	private static JMenu drawMenu;
	private static JMenu otherMenu;
	private static JMenuItem searchMenu;
	private static JMenuItem algorithmMenu;

	public static JMenuBar createMainMenu(DartEarthAppFrame frame) {
		menuBar = new JMenuBar();

		fileMenu = FileMenu.createFileMenu(frame);
		menuBar.add(fileMenu);

		drawMenu = DrawMenu.createDrawMenu(frame);
		menuBar.add(drawMenu);

		annotationMenu = AnnotationMenu.createAnnotationMenu(frame);
		menuBar.add(annotationMenu);

		otherMenu = OtherMenu.createOtherMenu(frame);
		menuBar.add(otherMenu);

		
		searchMenu = MetaDataSearchMenu.createSearchMenu(frame);
		menuBar.add(searchMenu);
		
		algorithmMenu = AlgorithmMenu.createAlgorithmMenu(frame);
		menuBar.add(algorithmMenu);

		return menuBar;
	}

}
