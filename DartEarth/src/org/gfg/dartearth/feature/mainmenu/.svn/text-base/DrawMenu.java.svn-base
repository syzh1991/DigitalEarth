package org.gfg.dartearth.feature.mainmenu;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.draw.CircleBuilder;
import org.gfg.dartearth.feature.draw.EllipseBuilder;
import org.gfg.dartearth.feature.draw.LineBuilder;
import org.gfg.dartearth.feature.draw.LineMouseAdapter;
import org.gfg.dartearth.feature.draw.PathBuilder;
import org.gfg.dartearth.feature.draw.PathMouseAdapter;
import org.gfg.dartearth.feature.draw.PolygonBuilder;


public class DrawMenu {
	public static JMenu createDrawMenu(DartEarthAppFrame frame) {
		JMenu drawMenu = new JMenu();
		drawMenu.setText("绘制");
		drawMenu.setPreferredSize(new Dimension(100,30));
		JMenuItem drawLineMenuItem = createDrawLineMenuItem(frame);
		drawMenu.add(drawLineMenuItem);
		JMenuItem drawPolygonMenuItem = createDrawPolygonMenuItem(frame);
		drawMenu.add(drawPolygonMenuItem);
		JMenuItem drawCircleMenuItem = createDrawCircleMenuItem(frame);
		drawMenu.add(drawCircleMenuItem);
		
		JMenuItem drawEllipseMenuItem = createDrawEllipseMenuItem(frame);
		drawMenu.add(drawEllipseMenuItem);
		
		JMenuItem drawPathMenuItem = createDrawPathMenuItem(frame);
		drawMenu.add(drawPathMenuItem);
		
		return drawMenu;
	
	}
	
	private static JMenuItem createDrawEllipseMenuItem(final DartEarthAppFrame frame){
		JMenuItem item=new JMenuItem();
		item.setText("椭圆");
		ActionListener actionListener = new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent event) {
//				for(MouseListener m:frame.getWwd().getMouseListeners()){
//					frame.getWwd().removeMouseListener(m);
//				}
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				EllipseBuilder.bindMouseListener(frame);
			}
		};
		item.addActionListener(actionListener);
		return item;
	}
	
	private static JMenuItem createDrawCircleMenuItem(final DartEarthAppFrame frame){
		JMenuItem item=new JMenuItem();
		item.setText("圆");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				CircleBuilder.bindMouseListener(frame);
			}
		};
		item.addActionListener(actionListener);
		return item;
	}
	
	private static JMenuItem createDrawPolygonMenuItem(final DartEarthAppFrame frame){
		JMenuItem item=new JMenuItem();
		item.setText("多边形");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				PolygonBuilder.bindMouseListener(frame);
			}
		};
		item.addActionListener(actionListener);
		return item;
	}
	
	private static JMenuItem createDrawLineMenuItem(final DartEarthAppFrame frame){
		JMenuItem item=new JMenuItem();
		item.setText("线段");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				LineBuilder.bindMouseListener(frame);
				LineMouseAdapter.killCurrentLineMouseAdapter();
			}
		};
		item.addActionListener(actionListener);
		return item;
	}
	
	private static JMenuItem createDrawPathMenuItem(final DartEarthAppFrame frame){
		JMenuItem item=new JMenuItem();
		item.setText("路径");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				PathBuilder.bindMouseListener(frame);
				PathMouseAdapter.killCurrentPathMouseAdapter();
			}
		};
		item.addActionListener(actionListener);
		return item;
	}
}
