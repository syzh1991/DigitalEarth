/*
 * AlgorithmLib.java
 *
 * Created on __DATE__, __TIME__
 */

package org.gfg.dartearth.core;

/*
 * AlgorithmLib.java
 *
 * Created on __DATE__, __TIME__
 */


import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.TerrainProfileLayer;
import gov.nasa.worldwindx.examples.LayerPanel;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author __USER__
 */
public class AlgorithmLibUI extends javax.swing.JDialog {
	
	public static String algorithm = "";
	private WorldWindow wwd;

	/** Creates new form AlgorithmLib */
	public AlgorithmLibUI(java.awt.Frame parent, boolean modal, WorldWindow wwd) {
		super(parent, modal);
		this.wwd = wwd;
		initComponents();
		this.setTitle("云处理");
		this.setLocation(1670, 70);
		this.setSize(250, 900);
		this.setVisible(true);
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		libPanel = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		resolve = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		TreeNode root = makeSampleTree();
		DefaultTreeModel model = new DefaultTreeModel(root);
		jTree1 = new javax.swing.JTree(model);
		retPanel = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jCheckBox1 = new javax.swing.JCheckBox();

		
		jTree1.addTreeSelectionListener(new TreeSelectionListener() {
			 
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1
                        .getLastSelectedPathComponent();
 
                if (node == null)
                    return;
 
                Object object = node.getUserObject();
                if (node.isLeaf()) {
                	String selected_algorithm = (String) object;
                    //System.out.println("你选择了：" + selected_algorithm);
                    algorithm = selected_algorithm;
                }
 
            }
        });
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setName("algorithmLib");

		jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 24));
		jLabel1.setText("\u4e91\u7b97\u6cd5");

		resolve.setText("\u5904\u7406");
		resolve.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resolveActionPerformed(evt);
			}
		});

		jScrollPane1.setViewportView(jTree1);

		jScrollPane2.setViewportView(jScrollPane1);

		javax.swing.GroupLayout libPanelLayout = new javax.swing.GroupLayout(
				libPanel);
		libPanel.setLayout(libPanelLayout);
		libPanelLayout
				.setHorizontalGroup(libPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGroup(
												libPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																libPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				51,
																				51,
																				51)
																		.addComponent(
																				jLabel1))
														.addGroup(
																libPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				28,
																				28,
																				28)
																		.addComponent(
																				jScrollPane2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				190,
																				Short.MAX_VALUE))
														.addGroup(
																libPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				79,
																				79,
																				79)
																		.addComponent(
																				resolve)))
										.addContainerGap()));
		libPanelLayout.setVerticalGroup(libPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				libPanelLayout.createSequentialGroup().addGap(21, 21, 21)
						.addComponent(jLabel1).addGap(18, 18, 18).addComponent(
								jScrollPane2,
								javax.swing.GroupLayout.PREFERRED_SIZE, 415,
								javax.swing.GroupLayout.PREFERRED_SIZE).addGap(
								33, 33, 33).addComponent(resolve)
						.addContainerGap(50, Short.MAX_VALUE)));

		jLabel4.setFont(new java.awt.Font("微软雅黑", 0, 24));
		jLabel4.setText("\u5904\u7406\u7ed3\u679c");

		jCheckBox1.setFont(new java.awt.Font("微软雅黑", 0, 18));
		jCheckBox1
				.setText("\u63a7\u5236\u56fe\u5c42\uff0c\u663e\u793a\u5143\u6570\u636e");

		javax.swing.GroupLayout retPanelLayout = new javax.swing.GroupLayout(
				retPanel);
		retPanel.setLayout(retPanelLayout);
		retPanelLayout.setHorizontalGroup(retPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				retPanelLayout.createSequentialGroup().addGap(63, 63, 63)
						.addComponent(jLabel4)).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				retPanelLayout.createSequentialGroup().addContainerGap(13,
						Short.MAX_VALUE).addComponent(jCheckBox1)
						.addContainerGap()));
		retPanelLayout.setVerticalGroup(retPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				retPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel4).addGap(48, 48, 48).addComponent(
								jCheckBox1)
						.addContainerGap(86, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																retPanel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																libPanel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(libPanel,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE).addGap(32, 32,
						32).addComponent(retPanel,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(32, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void resolveActionPerformed(java.awt.event.ActionEvent evt) {
		
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				AlgorithmLibUI dialog = new AlgorithmLibUI(
						new javax.swing.JFrame(), true, null);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setLocation(1650, 0);
				dialog.setVisible(true);
			}
		});
	}

	private TreeNode makeSampleTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("遥感影像算法");
		DefaultMutableTreeNode sec1 = new DefaultMutableTreeNode("基础算法");
		DefaultMutableTreeNode sec2 = new DefaultMutableTreeNode("应用算法");
		root.add(sec1);
		root.add(sec2);

		DefaultMutableTreeNode t1 = new DefaultMutableTreeNode("图像恢复");
		DefaultMutableTreeNode t2 = new DefaultMutableTreeNode("影像增强");
		DefaultMutableTreeNode t3 = new DefaultMutableTreeNode("信息提取");
		DefaultMutableTreeNode t4 = new DefaultMutableTreeNode("图像整饰");

		sec1.add(t1);
		sec1.add(t2);
		sec1.add(t3);
		sec1.add(t4);

		DefaultMutableTreeNode t11 = new DefaultMutableTreeNode("辐射校正");
		DefaultMutableTreeNode t12 = new DefaultMutableTreeNode("几何校正");
		t1.add(t11);
		t1.add(t12);

		DefaultMutableTreeNode t21 = new DefaultMutableTreeNode("彩色增强");
		DefaultMutableTreeNode t22 = new DefaultMutableTreeNode("反差增强");
		DefaultMutableTreeNode t23 = new DefaultMutableTreeNode("边缘增强");
		DefaultMutableTreeNode t24 = new DefaultMutableTreeNode("密度分割");
		DefaultMutableTreeNode t25 = new DefaultMutableTreeNode("比值运算");
		DefaultMutableTreeNode t26 = new DefaultMutableTreeNode("去模糊");
		t2.add(t21);
		t2.add(t22);
		t2.add(t23);
		t2.add(t24);
		t2.add(t25);
		t2.add(t26);

		DefaultMutableTreeNode t31 = new DefaultMutableTreeNode("特征提取");
		t3.add(t31);

		DefaultMutableTreeNode t41 = new DefaultMutableTreeNode("灰度增强");
		DefaultMutableTreeNode t42 = new DefaultMutableTreeNode("边缘增强");
		DefaultMutableTreeNode t43 = new DefaultMutableTreeNode("图像复原");
		DefaultMutableTreeNode t44 = new DefaultMutableTreeNode("图像镶嵌");
		DefaultMutableTreeNode t45 = new DefaultMutableTreeNode("图像上色");
		t4.add(t41);
		t4.add(t42);
		t4.add(t43);
		t4.add(t44);
		t4.add(t45);

		DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("植被识别");
		DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("军事目标探测");
		DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("地质检测");
		DefaultMutableTreeNode m4 = new DefaultMutableTreeNode("气象识别");
		DefaultMutableTreeNode m5 = new DefaultMutableTreeNode("资源考察");
		DefaultMutableTreeNode m6 = new DefaultMutableTreeNode("地图测绘");
		DefaultMutableTreeNode m7 = new DefaultMutableTreeNode("军事侦查");
		DefaultMutableTreeNode m8 = new DefaultMutableTreeNode("海洋调查");
		DefaultMutableTreeNode m9 = new DefaultMutableTreeNode("土地沙漠化检测");
		DefaultMutableTreeNode m10 = new DefaultMutableTreeNode("森林砍伐");

		sec2.add(m1);
		sec2.add(m2);
		sec2.add(m3);
		sec2.add(m4);
		sec2.add(m5);
		sec2.add(m6);
		sec2.add(m7);
		sec2.add(m8);
		sec2.add(m9);
		sec2.add(m10);
		return root;
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTree jTree1;
	private javax.swing.JPanel libPanel;
	private javax.swing.JButton resolve;
	private javax.swing.JPanel retPanel;
	// End of variables declaration//GEN-END:variables

}