package org.gfg.dartearth.feature.measure;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.util.UnitsFormat;
import gov.nasa.worldwind.util.measure.MeasureTool;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationBuilder;

public class LengthMeasurePanel extends JPanel {

	private static final long serialVersionUID = -1118152076830402973L;

	private JComboBox followTerrainUnitsCombo;
	private JComboBox unFollowTerrainUnitsCombo;
	private MeasureTool followTerrainMeasureTool;
	private MeasureTool unfollowTerrainMeasureTool;
	private JLabel followTerrainLengthLabel;
	private JLabel unfollowTerrainLengthLabel;
	private JButton okButton;
	private JButton createAnnotationButton;
	private static DartEarthAppFrame frame;
	private String text;

	public LengthMeasurePanel(MeasureTool followTerrainMeasureTool, MeasureTool unfollowTerrainMeasureTool,final DartEarthAppFrame frame,final Position centPosition) {

		this.followTerrainMeasureTool = followTerrainMeasureTool;
		this.unfollowTerrainMeasureTool = unfollowTerrainMeasureTool;
		LengthMeasurePanel.frame=frame;

		// 初始化单位选项
		followTerrainUnitsCombo = initialCombo(followTerrainMeasureTool, followTerrainLengthLabel);
		unFollowTerrainUnitsCombo = initialCombo(unfollowTerrainMeasureTool, unfollowTerrainLengthLabel);

		followTerrainLengthLabel = new JLabel();
		unfollowTerrainLengthLabel = new JLabel();

		// 样式
		this.setLayout(new GridLayout(0, 3, 2, 2));
		this.add(new JLabel("长度"));
		this.add(unfollowTerrainLengthLabel);
		this.add(unFollowTerrainUnitsCombo);
		this.add(new JLabel("真实长度"));
		this.add(followTerrainLengthLabel);
		this.add(followTerrainUnitsCombo);

		 refresh() ;
		
		okButton = new JButton();
		okButton.setText("确定");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LengthMeasurePanel.	frame.getMeasureDialog().setVisible(false);
			}
		};
		okButton.addActionListener(actionListener);
		this.add(okButton);
		
		createAnnotationButton = new JButton();
		createAnnotationButton.setText("创建标注");
		ActionListener createAnnotationAcctionListener=new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				BubbleAnnotationBuilder.buildBubbleAnnotation(centPosition, text,frame);
			}
		};
		createAnnotationButton.addActionListener(createAnnotationAcctionListener);
		this.add(createAnnotationButton);
	}

	public void refresh() {
		String followTerrainLegnth = followTerrainMeasureTool.getUnitsFormat().length(null, followTerrainMeasureTool.getLength());
		followTerrainLengthLabel.setText(followTerrainLegnth);
		String unfollowTerrainLegnth = unfollowTerrainMeasureTool.getUnitsFormat().length(null, unfollowTerrainMeasureTool.getLength());
		unfollowTerrainLengthLabel.setText(unfollowTerrainLegnth);
		text="真实长度:";
		text+=followTerrainLegnth;
		text+="<br>";
		text+="长度:";
		text+=unfollowTerrainLegnth;
		text+="<br>";
	}

	private JComboBox initialCombo(final MeasureTool mt, final JLabel label) {
		JComboBox cm = new JComboBox(new String[] { "M", "KM", "KM", "Feet", "Miles", "Yards" });
		cm.setSelectedItem("KM");
		cm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String item = (String) ((JComboBox) event.getSource()).getSelectedItem();
				if (item.equals("M")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.METERS);
					// measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_METERS);
				} else if (item.equals("KM")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.KILOMETERS);
					// measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_KILOMETERS);
				} else if (item.equals("Feet")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.FEET);
					// measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_FEET);
				} else if (item.equals("Miles")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.MILES);
					// measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_MILES);
				} else if (item.equals("Yards")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.YARDS);
					// measureTool.getUnitsFormat().setAreaUnits(UnitsFormat.ACRE);
				}

				refresh();
			}
		});
		return cm;
	}

}
