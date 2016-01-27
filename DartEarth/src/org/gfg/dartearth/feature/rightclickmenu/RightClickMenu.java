package org.gfg.dartearth.feature.rightclickmenu;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu {

	// 这个函数用于将一个JComponent与一个菜单列表items绑定在一起
	@SuppressWarnings("static-access")
	public static void bindRightClickMenu(JComponent component, List<RightClickMenuItem> items) {

		JPopupMenu popup = new JPopupMenu();

		for (int i = 0; i < items.size(); i++) {
			RightClickMenuItem rItem = items.get(i);

			JMenuItem item = new JMenuItem(rItem.getText());
			// 将JMenuItem加入popup菜单
			popup.add(item);
			// 设置文字显示位置，居右
			item.setHorizontalTextPosition(item.RIGHT);
			item.addActionListener(rItem.getActionListener());
			item.setEnabled(rItem.isEnable());
		}

		// 导入鼠标监听
		component.addMouseListener(new MouseListener(component, popup));
	}

	// 鼠标监听函数
	static class MouseListener extends MouseAdapter {
		// 捕获鼠标各种行为
		private JComponent component;
		private JPopupMenu menu;
		private Color defaultBackground = null;
		private Color defaultForeground = null;

		public void focus() {
			component.setBackground(Color.GRAY);
			// component.setFont(new Font("黑体",Font.PLAIN,12));
			component.setForeground(Color.WHITE);
			component.setOpaque(true);
		}

		public void blur() {
			component.setBackground(defaultBackground);
			// component.setFont(new Font("黑体",Font.PLAIN,12));
			component.setForeground(defaultForeground);
			component.setOpaque(true);
			// menu.setVisible(true);
		}

		public MouseListener(JComponent component, JPopupMenu menu) {
			this.component = component;
			this.menu = menu;
			this.defaultBackground = component.getBackground();
			this.defaultForeground = component.getForeground();
		}

		// 单击
		public void mouseClicked(MouseEvent e) {
			focus();
			check(e);
		}

		// 鼠标进入到组件（进入菜单）
		public void mouseEntered(MouseEvent e) {
			focus();
			check(e);
		}

		// 鼠标离开组件
		public void mouseExited(MouseEvent e) {
			blur();
			check(e);
		}

		// 鼠标在组件上按下
		public void mousePressed(MouseEvent e) {
			focus();
			check(e);
		}

		// 鼠标按钮在组件上释放
		public void mouseReleased(MouseEvent e) {
			focus();
			check(e);
		}

		private void check(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// 在指定位置显示右键弹出菜单
				menu.show(component, e.getX(), e.getY());
			}
		}
	}
}
