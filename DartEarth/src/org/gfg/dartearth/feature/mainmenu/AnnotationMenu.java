package org.gfg.dartearth.feature.mainmenu;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationBuilder;
import org.gfg.dartearth.feature.annotion.MutimediaAnnotationBuilder;
import org.gfg.dartearth.feature.annotion.PushPinAnnotationBuilder;

public class AnnotationMenu {

	public static JMenu createAnnotationMenu(DartEarthAppFrame frame) {
		JMenu annotationMenu = new JMenu();
		annotationMenu.setText("标识");
		annotationMenu.setPreferredSize(new Dimension(100,30));
		JMenuItem pushPinMenuItem = createPushPinMenuItem(frame);
		annotationMenu.add(pushPinMenuItem);

		JMenuItem bubbleMenuItem = createBubbleMenuItem(frame);
		annotationMenu.add(bubbleMenuItem);
		
		JMenuItem audioMenuItem = createAudioMenuItem(frame);
		annotationMenu.add(audioMenuItem);
		
		JMenuItem imageMenuItem = createImageMenuItem(frame);
		annotationMenu.add(imageMenuItem);

		return annotationMenu;
	}

	private static JMenuItem createPushPinMenuItem(final DartEarthAppFrame frame) {
		JMenuItem pushPinMenuItem = new JMenuItem();
		pushPinMenuItem.setText("图钉");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				PushPinAnnotationBuilder.bindMouseListener(frame);
			}
		};
		pushPinMenuItem.addActionListener(actionListener);
		return pushPinMenuItem;
	}

	private static JMenuItem createBubbleMenuItem(final DartEarthAppFrame frame) {
		JMenuItem bubbleMenuItem = new JMenuItem();
		bubbleMenuItem.setText("气泡(Bubble)");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				BubbleAnnotationBuilder.bindMouseListener(frame);
			}
		};
		bubbleMenuItem.addActionListener(actionListener);
		return bubbleMenuItem;
	}
	
	private static JMenuItem createAudioMenuItem(final DartEarthAppFrame frame) {
		JMenuItem audioMenuItem = new JMenuItem();
		audioMenuItem.setText("音频气泡(AudioBubble)");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				MutimediaAnnotationBuilder.bindMouseListener(frame, 0);
			}
		};
		audioMenuItem.addActionListener(actionListener);
		return audioMenuItem;
	}
	
	private static JMenuItem createImageMenuItem(final DartEarthAppFrame frame) {
		JMenuItem imageMenuItem = new JMenuItem();
		imageMenuItem.setText("相册气泡(ImageBubble)");
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				((Component) (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				MutimediaAnnotationBuilder.bindMouseListener(frame, 1);
			}
		};
		imageMenuItem.addActionListener(actionListener);
		return imageMenuItem;
	}
}
