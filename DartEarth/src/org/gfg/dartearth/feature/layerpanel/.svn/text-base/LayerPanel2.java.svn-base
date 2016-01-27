package org.gfg.dartearth.feature.layerpanel;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.TerrainProfileLayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gfg.dartearth.core.DartEarthAppFrame;

public class LayerPanel2 extends JPanel {
	private static final long serialVersionUID = -5440743010287448290L;

	protected JPanel layersPanel;
	protected JPanel southPanel;
	private WorldWindow wwd;
	private DartEarthAppFrame frame;

	/**
	 * Create a panel with the default size.
	 * 
	 * @param wwd
	 *            WorldWindow to supply the layer list.
	 */
	public LayerPanel2(WorldWindow wwd, DartEarthAppFrame frame) {
		// Make a panel at a default size.
		this.wwd = wwd;
		this.frame = frame;
		this.makePanel();

	}

	/**
	 * Create a panel with a size.
	 * 
	 * @param wwd
	 *            WorldWindow to supply the layer list.
	 * @param size
	 *            Size of the panel.
	 */

	protected void makePanel() {
		// Make and fill the panel holding the layer titles.
		this.layersPanel = new JPanel();
		this.layersPanel
				.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
		this.fill();
		this.add(layersPanel);
	}

	protected void fill() {
		LayerList layerList = wwd.getModel().getLayers();
		final Map<String, Layer> showLayer = new HashMap<String, Layer>();
		final Map<String, Layer> timeToLayer = new HashMap<String, Layer>();
		// name and layer map
		List<String> valueList = new ArrayList<String>();
		// time list
		// ////////////////
		int index = 0;
		// ////////////////

		for (Layer layer : layerList) {
			if (layer instanceof TerrainProfileLayer) {
				continue;
			}
			String layerName = layer.getName();
			if (layerName.matches("ALL\\w*_\\w*")) {
				showLayer.put(layerName, layer);
				String key = layerName.substring(0, layerName.lastIndexOf('_'));
				String value = layerName
						.substring(layerName.lastIndexOf('_') + 1).substring(4);
			//	value = String.valueOf(index++);
				valueList.add(value);
				timeToLayer.put(value, layer);
			}
		}

		// //
		// ///专门用于显示的层格式名为DISPLAY_2008-12
		// TODO more work to do to make this dynamic
		// //
		final JSlider slider;
		slider = new JSlider();
		//slider.setSize(700, 40);
		slider.setPreferredSize(new Dimension(800,40));
		final int dis = 100 / (valueList.size() - 1);
		slider.setMajorTickSpacing(dis);
		// 设置绘制刻度
		slider.setPaintTicks(true);
		// 设置滑块必须停在刻度处
		slider.setSnapToTicks(true);
		// 设置绘制刻度标签
		slider.setPaintLabels(true);
		Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		for (int i = 0; i < valueList.size(); i++) {
			JLabel temp  = new JLabel(valueList.get(i));
			labelTable.put((int) (i * dis), temp);
		}
		// 指定刻度标签，标签是JLabel
		slider.setLabelTable(labelTable);
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				// 取出滑动条的值，并在文本中显示出来
				JSlider source = (JSlider) event.getSource();
				if (source.getValue() % dis == 0) {
					JLabel layerDateLable = (JLabel) source.getLabelTable()
							.get(source.getValue());
					String layerNameTime = layerDateLable.getText();
					// ////////////////////////////////////
					timeToLayer.get(layerNameTime).setEnabled(true);
					for (String time : timeToLayer.keySet()) {
						if (time != layerNameTime) {
							timeToLayer.get(time).setEnabled(false);
						}
					}
					wwd.redraw();
					// ////////////////////////////////////
				}
			}
		};
		slider.addChangeListener(listener);
		this.layersPanel.add(slider);
		// ///////////////////////////////////////////////
	}

	/**
	 * Update the panel to match the layer list active in a WorldWindow.
	 * 
	 * @param wwd
	 *            WorldWindow that will supply the new layer list.
	 */
	public void update() {
		// Replace all the layer names in the layers panel with the names of the
		// current layers.
		this.layersPanel.removeAll();
		this.fill();
		this.southPanel.revalidate();
		this.southPanel.repaint();
	}

	protected static class LayerAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2357261678993191484L;

		WorldWindow wwd;
		private Layer layer;
		private boolean selected;

		private Map<String, Layer> showLayer;

		public LayerAction(Map<String, Layer> showLayer, Layer layer,
				WorldWindow wwd, boolean selected) {
			super(layer.getName());
			this.showLayer = showLayer;
			this.wwd = wwd;
			this.layer = layer;
			this.selected = selected;
			this.layer.setEnabled(this.selected);
		}

		public void actionPerformed(ActionEvent actionEvent) {
			// Simply enable or disable the layer based on its toggle button.
			if (((JRadioButton) actionEvent.getSource()).isSelected()) {

				this.layer.setEnabled(true);
				for (String layerName : showLayer.keySet()) {
					if (layerName != this.layer.getName()) {
						showLayer.get(layerName).setEnabled(false);
					}
				}
			} else {
				this.layer.setEnabled(false);
			}
			wwd.redraw();
		}
	}
}
