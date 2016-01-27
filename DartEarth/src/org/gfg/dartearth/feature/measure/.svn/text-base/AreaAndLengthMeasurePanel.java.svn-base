package org.gfg.dartearth.feature.measure;

import gov.nasa.worldwind.WorldWindow;
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

public class AreaAndLengthMeasurePanel extends JPanel {

	private static final long serialVersionUID = -2631612469641785304L;

	private MeasureTool followTerrainMeasureTool;
	private MeasureTool unfollowTerrainMeasureTool;

	private JLabel followTerrainLengthLabel;
	private JLabel unfollowTerrainLengthLabel;
	private JComboBox followTerrainLengthUnitsCombo;
	private JComboBox unFollowTerrainLengthUnitsCombo;
	private MeasureTool followTerrainLengthMt;
	private MeasureTool unfollowTerrainLengthMt;

	private JLabel followTerrainAreaLabel;
	private JLabel unfollowTerrainAreaLabel;
	private JComboBox followTerrainAreaUnitsCombo;
	private JComboBox unFollowTerrainAreaUnitsCombo;
	private MeasureTool followTerrainAreaMt;
	private MeasureTool unfollowTerrainAreaMt;

	private JButton okButton;
	private JButton createAnnotationButton;
	private String text;

	public AreaAndLengthMeasurePanel(MeasureTool followTerrainMeasureTool, MeasureTool unfollowTerrainMeasureTool, WorldWindow wwd,
			final DartEarthAppFrame frame, final Position centPosition) {

		this.followTerrainMeasureTool = followTerrainMeasureTool;
		this.unfollowTerrainMeasureTool = unfollowTerrainMeasureTool;

		// 初始化周长的测量:开始
		// 初始化单位选项
		followTerrainLengthMt = new MeasureTool(wwd);
		unfollowTerrainLengthMt = new MeasureTool(wwd);

		followTerrainLengthUnitsCombo = initialLengthCombo(followTerrainLengthMt, followTerrainLengthLabel);
		unFollowTerrainLengthUnitsCombo = initialLengthCombo(unfollowTerrainLengthMt, unfollowTerrainLengthLabel);

		followTerrainLengthLabel = new JLabel();
		unfollowTerrainLengthLabel = new JLabel();

		// 样式
		this.setLayout(new GridLayout(0, 3, 2, 2));
		this.add(new JLabel("周长"));
		this.add(unfollowTerrainLengthLabel);
		this.add(unFollowTerrainLengthUnitsCombo);
		this.add(new JLabel("真实周长"));
		this.add(followTerrainLengthLabel);
		this.add(followTerrainLengthUnitsCombo);
		// 初始化周长的测量:结束

		// 初始化面积的测量:开始
		followTerrainAreaMt = new MeasureTool(wwd);
		unfollowTerrainAreaMt = new MeasureTool(wwd);

		followTerrainAreaUnitsCombo = initialAreaCombo(followTerrainAreaMt, followTerrainAreaLabel);
		unFollowTerrainAreaUnitsCombo = initialAreaCombo(unfollowTerrainAreaMt, unfollowTerrainAreaLabel);

		followTerrainAreaLabel = new JLabel();
		unfollowTerrainAreaLabel = new JLabel();

		// 样式
		this.setLayout(new GridLayout(0, 3, 2, 2));
		this.add(new JLabel("面积"));
		this.add(unfollowTerrainAreaLabel);
		this.add(unFollowTerrainAreaUnitsCombo);
		this.add(new JLabel("真实面积"));
		this.add(followTerrainAreaLabel);
		this.add(followTerrainAreaUnitsCombo);
		// 初始化面积的测量:结束

		refresh();

		okButton = new JButton();
		okButton.setText("确定");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.getMeasureDialog().setVisible(false);
			}
		};
		okButton.addActionListener(actionListener);
		this.add(okButton);

		createAnnotationButton = new JButton();
		createAnnotationButton.setText("创建标注");
		ActionListener createAnnotationAcctionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				BubbleAnnotationBuilder.buildBubbleAnnotation(centPosition, text, frame);
			}
		};
		createAnnotationButton.addActionListener(createAnnotationAcctionListener);
		this.add(createAnnotationButton);
	}

	private JComboBox initialAreaCombo(final MeasureTool mt, final JLabel label) {
		JComboBox cm = new JComboBox(new String[] { "M\u00b2", "KM\u00b2", "Feet\u00b2", "Miles\u00b2", "Yards\u00b2" });
		cm.setSelectedItem("KM\u00b2");
		cm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String item = (String) ((JComboBox) event.getSource()).getSelectedItem();
				if (item.equals("M\u00b2")) {
					mt.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_METERS);
				} else if (item.equals("KM\u00b2")) {
					mt.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_KILOMETERS);
				} else if (item.equals("Feet\u00b2")) {
					mt.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_FEET);
				} else if (item.equals("Miles\u00b2")) {
					mt.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_MILES);
				} else if (item.equals("Yards\u00b2")) {
					mt.getUnitsFormat().setAreaUnits(UnitsFormat.SQUARE_YARDS);
				}
				// System.out.println(mt.getUnitsFormat().getAreaUnits());
				refresh();
			}
		});
		return cm;
	}

	private JComboBox initialLengthCombo(final MeasureTool mt, final JLabel label) {
		JComboBox cm = new JComboBox(new String[] { "M", "KM", "Feet", "Miles", "Yards" });
		cm.setSelectedItem("KM");
		cm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String item = (String) ((JComboBox) event.getSource()).getSelectedItem();
				if (item.equals("M")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.METERS);
				} else if (item.equals("KM")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.KILOMETERS);
				} else if (item.equals("Feet")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.FEET);
				} else if (item.equals("Miles")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.MILES);
				} else if (item.equals("Yards")) {
					mt.getUnitsFormat().setLengthUnits(UnitsFormat.YARDS);
				}
				refresh();
			}
		});
		return cm;
	}

	public void refresh() {
		String followTerrainLength = followTerrainLengthMt.getUnitsFormat().length(null, followTerrainMeasureTool.getLength());
		followTerrainLengthLabel.setText(followTerrainLength);
		String unfollowTerrainLength = unfollowTerrainLengthMt.getUnitsFormat().length(null, unfollowTerrainMeasureTool.getLength());
		unfollowTerrainLengthLabel.setText(unfollowTerrainLength);

		String followTerrainArea = followTerrainAreaMt.getUnitsFormat().areaNL(null, followTerrainMeasureTool.getArea());
		followTerrainAreaLabel.setText(followTerrainArea);
		String unfollowTerrainArea = unfollowTerrainAreaMt.getUnitsFormat().area(null, unfollowTerrainMeasureTool.getArea());
		unfollowTerrainAreaLabel.setText(unfollowTerrainArea);

		// followTerrainArea = formatArea(followTerrainArea);
		// unfollowTerrainArea = formatArea(unfollowTerrainArea);
		text = "真实长度:";
		text += followTerrainLength;
		text += "<br>";
		text += "长度:";
		text += unfollowTerrainLength;
		text += "<br>";
		text += "真实面积:";
		text += followTerrainArea;
		text += "<br>";
		text += "面积:";
		text += unfollowTerrainArea;
		text += "<br>";
	}

	// TODO:好像没有用，可以试试别的方法来进行调整
	// 原本，想通过这个方法来把单位二次方的形式转化为平方单位
	private String formatArea(String area) {
		area = area.replaceAll("M\\u00b2", "Square M");
		area = area.replaceAll("KM\\u00b2", "Square KM");
		area = area.replaceAll("Feet\\u00b2", "Square Feet");
		area = area.replaceAll("Miles\\u00b2", "Square Miles");
		area = area.replaceAll("Yards\\u00b2", "Square Yards");
		// System.out.println(area);
		return area;
	}

}
