package org.gfg.dartearth.feature.draw;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class PathProperPanel extends JPanel {
	private static final long serialVersionUID = 930710547300508185L;

	private PathLayer layer;

	private static Dimension labelDimension = new Dimension(80, 24);
	private static Dimension bigComponentDimension = new Dimension(240, 24);
	private static Dimension smallComponentDimension = new Dimension(85, 24);
	private static Dimension deleteComponentDimension = new Dimension(60, 24);
	private static Dimension btnComponentDimension = new Dimension(100, 24);

	private DartEarthAppFrame frame;

	private Path path;
	private JLabel layerNameLabel;
	private JTextField layerNameTextField;

	private List<Position> points;
	private JLabel colorLabel;
	private JButton colorBtn;
	private JLabel opacityLabel;
	private JTextField opacityTextField;
	private JLabel sizeLabel;
	private JTextField sizeTextField;
	private Color color;

	private JButton okBtn;
	private JButton applyBtn;
	private JButton reverseBtn;
	private JButton cancelBtn;
	private List<JPanel> pointPanels;
	private List<JButton> deleteBtns;
	private List<JTextField> latTexts;
	private List<JTextField> lngTexts;

	public PathProperPanel(final PathLayer layer, final DartEarthAppFrame frame) {
		this.setLayout(new GridLayout(0, 1, 2, 2));

		this.layer = layer;
		this.frame = frame;
		this.path = layer.getPath();
		deleteBtns = new ArrayList<JButton>();
		latTexts = new ArrayList<JTextField>();
		lngTexts = new ArrayList<JTextField>();
		pointPanels = new ArrayList<JPanel>();

		layerNameLabel = new JLabel("图层名:");
		layerNameTextField = new JTextField();
		layerNameTextField.setText(layer.getName());
		put(layerNameLabel, layerNameTextField);

		// this.buildPointPanels();

		colorLabel = new JLabel("颜色:");
		// this.add(colorLabel);
		// colorChooser = new JColorChooser();
		color = path.getAttributes().getOutlineMaterial().getDiffuse();
		// colorChooser.setColor(color);
		// colorChooser.set
		// color=JColorChooser.showDialog(this, "请选择颜色", color);
		colorBtn = new JButton();
		// colorBtn.setText("");
		colorBtn.setBackground(color);
		// final LineProperPanel panel = this;
		ActionListener colorChooseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color choosenColor = JColorChooser.showDialog(null, "请选择颜色", color);
				colorBtn.setBackground(choosenColor);
				// line.setColor(color);
				// layer.refresh();
			}
		};
		colorBtn.addActionListener(colorChooseListener);
		put(colorLabel, colorBtn);
		// this.add(colorBtn);

		opacityLabel = new JLabel("透明度:");
		// this.add(opacityLable);
		opacityTextField = new JTextField();
		opacityTextField.setText(layer.getPath().getAttributes().getOutlineOpacity() + "");
		// this.add(opacityTextField);
		put(opacityLabel, opacityTextField);

		sizeLabel = new JLabel("粗细:");
		// this.add(sizeLabel);
		sizeTextField = new JTextField();
		sizeTextField.setText(path.getAttributes().getOutlineWidth() + "");
		// this.add(sizeTextField);
		put(sizeLabel, sizeTextField);

		initialDialogBtns();
		buildPointPanels();
	}

	private void initialDialogBtns() {
		// final JPanel panel = this;
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		okBtn = new JButton("确定");
		ActionListener okListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applyModify();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		okBtn.addActionListener(okListener);
		okBtn.setPreferredSize(btnComponentDimension);
		panel.add(okBtn);

		applyBtn = new JButton("应用");
		ActionListener ApplyListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applyModify();
			}
		};
		applyBtn.addActionListener(ApplyListener);
		// applyBtn.addActionListener(okListener);
		applyBtn.setPreferredSize(btnComponentDimension);
		panel.add(applyBtn);
		
		reverseBtn = new JButton("反向");
		ActionListener ReverseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				layer.reverse();
			}
		};
		reverseBtn.addActionListener(ReverseListener);
		reverseBtn.setPreferredSize(btnComponentDimension);
		panel.add(reverseBtn);

		cancelBtn = new JButton("取消");
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh();
				frame.getPropertiesDialog().setVisible(false);
			}
		};
		cancelBtn.addActionListener(cancelListener);
		// cancelBtn.addActionListener(okListener);
		cancelBtn.setPreferredSize(btnComponentDimension);
		panel.add(cancelBtn);
		this.add(panel);
	}

	private void applyModify() {
		layer.setName(layerNameTextField.getText());
		Color choosenColor = colorBtn.getBackground();
		color = new Color(choosenColor.getRed(), choosenColor.getGreen(), choosenColor.getBlue());
		List<Position> positions = new ArrayList<Position>();
		for (int i = 0; i < latTexts.size(); i++) {
			Position p = layer.getPositions().get(i);
			double lat = Double.valueOf(latTexts.get(i).getText());
			double lng = Double.valueOf(lngTexts.get(i).getText());
			Position newP = new Position(Angle.fromDegrees(lat), Angle.fromDegrees(lng), p.getElevation());
			positions.add(newP);
		}
		layer.setPositions(positions);
		path.setPositions(positions);
		
        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        Material material = new Material(color);
        attrs.setOutlineMaterial(material);
        attrs.setOutlineWidth(Double.valueOf(sizeTextField.getText()));
        attrs.setOutlineOpacity(Double.valueOf(opacityTextField.getText()));
//		path.setColor(color);
//		path.setLineWidth(Double.valueOf(sizeTextField.getText()));
		path.setAttributes(attrs);
		
		layer.refresh();
		frame.getLayerPanelDialog().getLayerPanel().update();
	}

	private void put(JComponent... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Dimension componentDimension = null;
		if (components.length == 2) {
			componentDimension = bigComponentDimension;
		} else {
			componentDimension = smallComponentDimension;
		}
		for (JComponent c : components) {
			if (c instanceof JLabel) {
				c.setPreferredSize(labelDimension);
			} else {
				c.setPreferredSize(componentDimension);
			}
			panel.add(c);
		}
		this.add(panel);
	}

	private void buildPointPanels() {
		deleteBtns.clear();
		latTexts.clear();
		lngTexts.clear();
		points = layer.getPositions();
		if (pointPanels.size() != 0) {
			for (JPanel p : pointPanels) {
				this.remove(p);
			}
		}
		pointPanels.clear();

		if (points.size() > 1) {

			for (int i = 0; i < points.size(); i++) {
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout(FlowLayout.LEFT));
				Position p = points.get(i);
				final int index = i;
				JLabel label = new JLabel("点" + i + ":");
				label.setPreferredSize(labelDimension);
				panel.add(label);
				JTextField lngTextField = new JTextField();
				lngTextField.setText(p.getLongitude().getDegrees() + "");
				lngTextField.setPreferredSize(smallComponentDimension);
				panel.add(lngTextField);
				lngTexts.add(lngTextField);
				JTextField latTextField = new JTextField();
				latTextField.setText(p.getLatitude().getDegrees() + "");
				latTextField.setPreferredSize(smallComponentDimension);
				panel.add(latTextField);
				latTexts.add(latTextField);
				JButton deleteBtn = new JButton();
				deleteBtn.setText("删除");
				deleteBtn.setPreferredSize(deleteComponentDimension);
				ActionListener deleteListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if (deleteBtns.size() > 2) {
							deleteBtns.remove(index);
							points.remove(index);
							refreshPointsPanel();
							layer.refresh();
							if (deleteBtns.size() <= 2) {
								for (JButton btn : deleteBtns) {
									btn.setEnabled(false);
								}
							}
						}
					}
				};
				deleteBtn.addActionListener(deleteListener);
				deleteBtns.add(deleteBtn);
				panel.add(deleteBtn);
				pointPanels.add(panel);
				this.add(panel, pointPanels.size() + 1);
			}
		}
	}

	public void refreshPointsPanel() {
		buildPointPanels();
		if (deleteBtns.size() <= 2) {
			for (JButton btn : deleteBtns) {
				btn.setEnabled(false);
			}
		}
		this.revalidate();
		for (JPanel p : pointPanels) {
			p.repaint();
		}
		this.repaint();
	}

	public void refresh() {
		refreshPointsPanel();
		layerNameTextField.setText(layer.getName());
		colorBtn.setBackground(layer.getPath().getAttributes().getOutlineMaterial().getDiffuse());
		opacityTextField.setText(layer.getPath().getAttributes().getOutlineOpacity() + "");
		sizeTextField.setText(layer.getPath().getAttributes().getOutlineWidth() + "");
	}
}
