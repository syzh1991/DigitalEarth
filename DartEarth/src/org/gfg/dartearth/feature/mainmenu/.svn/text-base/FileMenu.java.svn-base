package org.gfg.dartearth.feature.mainmenu;

import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwindx.examples.util.ScreenShotAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.KML.DEMLExportor;
import org.gfg.dartearth.feature.KML.DEMLImportor;
import org.gfg.dartearth.feature.KML.ExportKML;
import org.gfg.dartearth.feature.KML.KMLBuilder.WorkerThread;
import org.gfg.dartearth.layer.model3d.Model3DLayerBuilder;

public class FileMenu {
	private static JMenu menu;
	private static DartEarthAppFrame frame;

	// private static JMenuItem ScreenShotMenuItem;
	// private static JMenu annotationMenu;

	public static JMenu createFileMenu(DartEarthAppFrame frame) {
		FileMenu.frame = frame;
		menu = new JMenu();
		menu.setPreferredSize(new Dimension(100,30));
		menu.setText("文件");

		JMenuItem snapItem = createSnapMenuItem();
		menu.add(snapItem);

		JMenuItem import3DModelItem = createImport3DModelMenuItem();
		menu.add(import3DModelItem);

		// JMenuItem impoortWmsLayerMenuItem= createImportWmsLayerMenuItem();
		// menu.add(impoortWmsLayerMenuItem);

		JMenuItem localKMLMenuItem = createLocalKMLMenuItem(frame);
		menu.add(localKMLMenuItem);

		JMenuItem remoteKMLMenuItem = createRemoteKMLMenuItem(frame);
		menu.add(remoteKMLMenuItem);

		JMenuItem exportKMLMenuItem = createExportKMLMenuItem(frame);
		menu.add(exportKMLMenuItem);

		JMenuItem exportDEMLMenuItem = createExportDEMLMenuItem(frame);
		menu.add(exportDEMLMenuItem);

		JMenuItem importDEMLMenuItem = createImportDEMLMenuItem(frame);
		menu.add(importDEMLMenuItem);

		return menu;
	}

	private static JMenuItem createSnapMenuItem() {
		JMenuItem snapItem = new JMenuItem("保存截图");
		snapItem.addActionListener(new ScreenShotAction(frame.getWwd()));
		return snapItem;
	}

	private static JMenuItem createImport3DModelMenuItem() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("OBJ/3DS File", "obj", "3ds"));

		@SuppressWarnings("serial")
		JMenuItem import3DModelMenuItem = new JMenuItem(new AbstractAction("导入3D模型.") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int status = fileChooser.showOpenDialog(frame);
					if (status == JFileChooser.APPROVE_OPTION) {
						String filePath = fileChooser.getSelectedFile().getAbsolutePath();
						Model3DLayerBuilder.buildModel3DLayer(frame, filePath);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return import3DModelMenuItem;
	}

	private static JMenuItem createLocalKMLMenuItem(final DartEarthAppFrame appFrame) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("KML/KMZ File", "kml", "kmz"));

		JMenuItem openFileMenuItem = new JMenuItem(new AbstractAction("导入KML...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int status = fileChooser.showOpenDialog(appFrame);
					if (status == JFileChooser.APPROVE_OPTION) {
						new WorkerThread(fileChooser.getSelectedFile(), appFrame).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return openFileMenuItem;
	}

	private static JMenuItem createRemoteKMLMenuItem(final DartEarthAppFrame frame2) {
		JMenuItem openURLMenuItem = new JMenuItem(new AbstractAction("打开 URL...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					String status = JOptionPane.showInputDialog(frame2, "URL");
					if (!WWUtil.isEmpty(status)) {
						new WorkerThread(status.trim(), frame2).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return openURLMenuItem;
	}

	private static JMenuItem createExportKMLMenuItem(final DartEarthAppFrame frame2) {
		JMenuItem exportKmlMenuItem = new JMenuItem(new AbstractAction("导出KML...") {
			public void actionPerformed(ActionEvent actionEvent) {
				ExportKML ek = new ExportKML(frame2);
				ek.exportKml();
			}
		});
		return exportKmlMenuItem;

	}

	// private static JMenuItem createImportWmsLayerMenuItem() {
	// final JFileChooser fileChooser = new JFileChooser();
	// fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("DEML",
	// "deml"));
	//
	// @SuppressWarnings("serial")
	// JMenuItem importWmsLayerMenuItem = new JMenuItem(new
	// AbstractAction("导入WMS图层.") {
	// public void actionPerformed(ActionEvent actionEvent) {
	// try {
	// int status = fileChooser.showOpenDialog(frame);
	// if (status == JFileChooser.APPROVE_OPTION) {
	// String filePath=fileChooser.getSelectedFile().getAbsolutePath();
	// WmsDemlLayerImporter.importByFile(frame, filePath);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// return importWmsLayerMenuItem;
	// }

	private static JMenuItem createExportDEMLMenuItem(final DartEarthAppFrame frame) {
		final JFileChooser fileChooser=new JFileChooser();
		JMenuItem exportDEMLMenuItem = new JMenuItem(new AbstractAction("导出DEML...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int status=fileChooser.showSaveDialog(frame);
					if(status==JFileChooser.APPROVE_OPTION){
						String fileName=fileChooser.getSelectedFile().getPath();
						DEMLExportor.exportAsDeml(fileName,frame);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return exportDEMLMenuItem;

	}

	private static JMenuItem createImportDEMLMenuItem(final DartEarthAppFrame appFrame) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("DEML File", "DEML", "deml"));

		JMenuItem openFileMenuItem = new JMenuItem(new AbstractAction("导入DEML...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int status = fileChooser.showOpenDialog(appFrame);
					if (status == JFileChooser.APPROVE_OPTION) {
				        
						DEMLImportor.importByDeml(fileChooser.getSelectedFile(), appFrame);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return openFileMenuItem;
	}

	private static JMenuItem createRemoteDEMLMenuItem(final DartEarthAppFrame frame) {
		JMenuItem openURLMenuItem = new JMenuItem(new AbstractAction("打开远程 DEML...") {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					String status = JOptionPane.showInputDialog(frame, "URL");					
					if (!WWUtil.isEmpty(status)) {
						DEMLImportor.importByDeml(status.trim(),frame);	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return openURLMenuItem;
	}

}
