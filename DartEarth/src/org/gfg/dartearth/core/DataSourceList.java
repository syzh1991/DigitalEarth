/*
 * AlgorithmLib.java
 *
 * Created on __DATE__, __TIME__
 */

package org.gfg.dartearth.core;

import javax.swing.JRadioButton;

/**
 *
 * @author  __USER__
 */
public class DataSourceList extends javax.swing.JDialog {

	/** Creates new form AlgorithmLib */
	private DartEarthAppFrame parentFrame = null;
	public DataSourceList(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.parentFrame = ((DartEarthAppFrame) parent);
		initComponents();
		this.setLocation(0, 70);
		this.setSize(170, 900);
		this.setVisible(true);
		this.setTitle("云数据");
	}

	public DartEarthAppFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(DartEarthAppFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		libPanel = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel2 = new javax.swing.JLabel();
		jRadioButton1 = new javax.swing.JRadioButton();
		jRadioButton2 = new javax.swing.JRadioButton();
		jRadioButton3 = new javax.swing.JRadioButton();
		jRadioButton4 = new javax.swing.JRadioButton();
		jSeparator2 = new javax.swing.JSeparator();
		jRadioButton5 = new javax.swing.JRadioButton();
		jLabel3 = new javax.swing.JLabel();
		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton7 = new javax.swing.JRadioButton();
		jSeparator3 = new javax.swing.JSeparator();
		jRadioButton8 = new javax.swing.JRadioButton();
		jLabel4 = new javax.swing.JLabel();
		jRadioButton9 = new javax.swing.JRadioButton();
		jRadioButton10 = new javax.swing.JRadioButton();
		jRadioButton11 = new javax.swing.JRadioButton();
		jSeparator4 = new javax.swing.JSeparator();
		jLabel5 = new javax.swing.JLabel();
		jRadioButton12 = new javax.swing.JRadioButton();
		jRadioButton13 = new javax.swing.JRadioButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setName("algorithmLib");

		jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 24));
		jLabel1.setText("\u4e91\u6570\u636e");

		jLabel2.setText("\u536b\u661f\u6570\u636e");

		buttonGroup1.add(jRadioButton1);
		jRadioButton1.setText("GF1");
		jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton1ActionPerformed(evt);
			}
		});

		buttonGroup1.add(jRadioButton2);
		jRadioButton2.setText("HJ1A");

		buttonGroup1.add(jRadioButton3);
		jRadioButton3.setText("HJ1B");

		buttonGroup1.add(jRadioButton4);
		jRadioButton4.setText("GF2");

		buttonGroup1.add(jRadioButton5);
		jRadioButton5.setText("More");

		jLabel3.setText("\u57fa\u7840\u6570\u636e");

		buttonGroup1.add(jRadioButton6);
		jRadioButton6.setText("\u884c\u653f\u533a\u57df");

		buttonGroup1.add(jRadioButton7);
		jRadioButton7.setText("POI Data");

		buttonGroup1.add(jRadioButton8);
		jRadioButton8.setText("More");

		jLabel4.setText("\u4e92\u8054\u7f51\u6570\u636e");

		buttonGroup1.add(jRadioButton9);
		jRadioButton9.setText("Picture");

		buttonGroup1.add(jRadioButton10);
		jRadioButton10.setText("WIKI");

		buttonGroup1.add(jRadioButton11);
		jRadioButton11.setText("More");

		jLabel5.setText("\u5e94\u7528\u6570\u636e");

		buttonGroup1.add(jRadioButton12);
		jRadioButton12.setText("\u56fd\u571f");

		buttonGroup1.add(jRadioButton13);
		jRadioButton13.setText("\u73af\u5883");

		javax.swing.GroupLayout libPanelLayout = new javax.swing.GroupLayout(
				libPanel);
		libPanel.setLayout(libPanelLayout);
		libPanelLayout
				.setHorizontalGroup(libPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jSeparator1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 198,
								Short.MAX_VALUE)
						.addGroup(
								libPanelLayout.createSequentialGroup().addGap(
										48, 48, 48).addComponent(jLabel1)
										.addContainerGap(78, Short.MAX_VALUE))
						.addGroup(
								libPanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel2).addContainerGap(
												123, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								libPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jSeparator2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												168, Short.MAX_VALUE)
										.addContainerGap())
						.addGroup(
								libPanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel3).addContainerGap(
												123, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								libPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jSeparator3,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												168, Short.MAX_VALUE)
										.addContainerGap())
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGap(39, 39, 39)
										.addGroup(
												libPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jRadioButton8)
														.addComponent(
																jRadioButton7)
														.addComponent(
																jRadioButton6))
										.addContainerGap(68, Short.MAX_VALUE))
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGap(43, 43, 43)
										.addGroup(
												libPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jRadioButton1)
														.addComponent(
																jRadioButton4)
														.addComponent(
																jRadioButton2)
														.addComponent(
																jRadioButton3)
														.addComponent(
																jRadioButton5))
										.addContainerGap(86, Short.MAX_VALUE))
						.addGroup(
								libPanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel4).addContainerGap(
												108, Short.MAX_VALUE))
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGap(39, 39, 39)
										.addGroup(
												libPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jRadioButton11)
														.addComponent(
																jRadioButton10)
														.addComponent(
																jRadioButton9))
										.addContainerGap(80, Short.MAX_VALUE))
						.addGroup(
								libPanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel5).addContainerGap(
												123, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								libPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jSeparator4,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												168, Short.MAX_VALUE)
										.addContainerGap())
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGap(39, 39, 39)
										.addGroup(
												libPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jRadioButton13)
														.addComponent(
																jRadioButton12))
										.addContainerGap(100, Short.MAX_VALUE)));
		libPanelLayout
				.setVerticalGroup(libPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								libPanelLayout
										.createSequentialGroup()
										.addGap(21, 21, 21)
										.addComponent(jLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												2,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jRadioButton1)
										.addGap(8, 8, 8)
										.addComponent(jRadioButton4)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jRadioButton2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jRadioButton3)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jRadioButton5)
										.addGap(10, 10, 10)
										.addComponent(
												jSeparator2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel3)
										.addGap(18, 18, 18)
										.addComponent(jRadioButton6)
										.addGap(7, 7, 7)
										.addComponent(jRadioButton7)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jRadioButton8)
										.addGap(12, 12, 12)
										.addComponent(
												jSeparator3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel4)
										.addGap(18, 18, 18)
										.addComponent(jRadioButton9)
										.addGap(18, 18, 18)
										.addComponent(jRadioButton10)
										.addGap(18, 18, 18)
										.addComponent(jRadioButton11)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel5)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jRadioButton12).addGap(
												18, 18, 18).addComponent(
												jRadioButton13)
										.addContainerGap(211, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				libPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(libPanel,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		JRadioButton jrb = (JRadioButton)evt.getSource();
		if(jrb.isSelected() == true){
			this.parentFrame.getMetaDataSearchDialog().setVisible(true);
		}else{
			this.parentFrame.getMetaDataSearchDialog().setVisible(false);
		}
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DataSourceList dialog = new DataSourceList(
						new javax.swing.JFrame(), true);
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

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton10;
	private javax.swing.JRadioButton jRadioButton11;
	private javax.swing.JRadioButton jRadioButton12;
	private javax.swing.JRadioButton jRadioButton13;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JRadioButton jRadioButton4;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JRadioButton jRadioButton7;
	private javax.swing.JRadioButton jRadioButton8;
	private javax.swing.JRadioButton jRadioButton9;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JPanel libPanel;
	// End of variables declaration//GEN-END:variables

}