package org.gfg.dartearth;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.gfg.dartearth.core.ApplicationTemplate;
import org.gfg.dartearth.core.ApplicationTemplate.AppFrame;
import org.gfg.dartearth.core.DartEarthAppFrame;

public class DartEarth {
	public DartEarth() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					// UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel());
					// UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");//木质感+xp风格
					// UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");//普通swing风格+蓝色边框
					// UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");//还不错
					 UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");//还不错
					//UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");// 椭圆按钮+黄色按钮背景
					//UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");// 椭圆按钮+黄色按钮背景
					//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");// 纯XP风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");//  布质感+swing纯风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");// 黄色风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");// 黑色风格
				} catch (Exception e) {
					System.out.println(e);
				}
				// SubstanceLookAndFeel.setSkin(new RavenSkin());
				final AppFrame af = ApplicationTemplate.start("高分云平台",
						DartEarthAppFrame.class);
			}
		});
	}
	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					// UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel());
					// UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");//木质感+xp风格
					// UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");//普通swing风格+蓝色边框
					// UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					 UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");//还不错
					//UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");// 椭圆按钮+黄色按钮背景
					//UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");// 椭圆按钮+黄色按钮背景
					//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");// 纯XP风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");//  布质感+swing纯风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");// 黄色风格
					//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");// 黑色风格
				} catch (Exception e) {
					System.out.println(e);
				}
				// SubstanceLookAndFeel.setSkin(new RavenSkin());
				final AppFrame af = ApplicationTemplate.start("GF-Cloud Earth",
						DartEarthAppFrame.class);
			}
		});

	}
}
