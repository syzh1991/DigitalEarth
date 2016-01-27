package org.gfg.dartearth.feature.layerpanel;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationLayer;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationProperPanel;
import org.gfg.dartearth.feature.annotion.MutimediaAnnotationLayer;
import org.gfg.dartearth.feature.annotion.MutimediaAnnotationProperPanel;
import org.gfg.dartearth.feature.annotion.PushPinAnnotationLayer;
import org.gfg.dartearth.feature.annotion.PushPinAnnotationProperPanel;
import org.gfg.dartearth.feature.draw.CircleLayer;
import org.gfg.dartearth.feature.draw.EllipseLayer;
import org.gfg.dartearth.feature.draw.LineLayer;

import org.gfg.dartearth.feature.draw.PathLayer;

import org.gfg.dartearth.feature.draw.PolygonLayer;
import org.gfg.dartearth.feature.rightclickmenu.RightClickMenuItem;
import org.gfg.dartearth.layer.movablemodel3d.Model3DAnimatorLayer;

public class LayerPanelMenuFactory {

	private static String[] COULD_NOT_DELETE_LAYERS = { "Stars", "Atmosphere", "NASA Blue Marble Image", "Blue Marble (WMS) 2004",
			"i-cubed Landsat", "World Map", "Scale bar", "View Controls", "Compass" };

	public static List<RightClickMenuItem> createRightClickMenuItems(Layer layer, WorldWindow wwd, LayerPanel panel,
			DartEarthAppFrame frame, int index, int layersNum) {
		List<RightClickMenuItem> items = new ArrayList<RightClickMenuItem>();
		boolean enableUpMenu = false;
		if (index > 0) {
			enableUpMenu = true;
		}

		RightClickMenuItem toTopMenu = new RightClickMenuItem("至顶", getToTopActionListener(wwd, layer, panel), enableUpMenu);
		items.add(toTopMenu);

	
		RightClickMenuItem upMenu = new RightClickMenuItem("上移", getUpActionListener(wwd, layer, panel), enableUpMenu);
		items.add(upMenu);

		boolean enableDownMenu = true;
		if (index == layersNum - 1) {
			enableDownMenu = false;
		}
		RightClickMenuItem downMenu = new RightClickMenuItem("下移", getDownActionListener(wwd, layer, panel), enableDownMenu);
		items.add(downMenu);

		RightClickMenuItem toButtomMenu = new RightClickMenuItem("至底", getToButtomActionListener(wwd, layer, panel), enableDownMenu);
		items.add(toButtomMenu);
		
		
		RightClickMenuItem deleteMenu = getDeleteMenu(frame, wwd, layer, panel);
		items.add(deleteMenu);

		RightClickMenuItem propertiesMenu = getPropertiesMenu(wwd, layer, frame);
		items.add(propertiesMenu);

		RightClickMenuItem measureMenu = getMeasureMenu(wwd, layer, frame);
		items.add(measureMenu);
		
		return items;
	}

	private static RightClickMenuItem getMeasureMenu(final WorldWindow wwd, final Layer layer, final DartEarthAppFrame frame) {
		RightClickMenuItem menu = null;
		if (layer instanceof LineLayer) {
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getMeasurer().measureLine((LineLayer) layer);
				}
			};
			menu = new RightClickMenuItem("测量", propertiesActionListener, true);
		} else if (layer instanceof PathLayer) {
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getMeasurer().measurePath((PathLayer) layer);
				}
			};
			menu = new RightClickMenuItem("测量", propertiesActionListener, true);
		} else if (layer instanceof PolygonLayer) {
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getMeasurer().measurePolygon((PolygonLayer) layer);
				}
			};
			menu = new RightClickMenuItem("测量", propertiesActionListener, true);
		} else if (layer instanceof EllipseLayer) {
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getMeasurer().measureEllipse((EllipseLayer) layer);
				}
			};
			menu = new RightClickMenuItem("测量", propertiesActionListener, true);
		} else if (layer instanceof CircleLayer) {
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getMeasurer().measureCircle((CircleLayer) layer);
				}
			};
			menu = new RightClickMenuItem("测量", propertiesActionListener, true);
		} else {
			menu = new RightClickMenuItem("测量", null, false);
		}
		return menu;
	}

	private static RightClickMenuItem getPropertiesMenu(final WorldWindow wwd, final Layer layer, final DartEarthAppFrame frame) {
		RightClickMenuItem propertiesMenu = null;
		if (PushPinAnnotationLayer.class.isInstance(layer)) {
			final PushPinAnnotationLayer pLayer = (PushPinAnnotationLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(pLayer.getPanel(),
							pLayer.getAnnotation().getLayerName(), (RenderableLayer) layer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (BubbleAnnotationLayer.class.isInstance(layer)) {
			final BubbleAnnotationLayer bLayer = (BubbleAnnotationLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(bLayer.getPanel(), bLayer.getAnnotation().getLayerName(),
							(RenderableLayer) layer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (MutimediaAnnotationLayer.class.isInstance(layer)) {
			final MutimediaAnnotationLayer mLayer = (MutimediaAnnotationLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(new MutimediaAnnotationProperPanel(mLayer, frame),
							mLayer.getAnnotationVO().getLayerName(), (RenderableLayer) layer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (LineLayer.class.isInstance(layer)) {
			final LineLayer lLayer = (LineLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(lLayer.getPanel(), lLayer.getName(), (RenderableLayer) lLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (PathLayer.class.isInstance(layer)) {
			final PathLayer pLayer = (PathLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(pLayer.getPanel(), pLayer.getName(), (RenderableLayer) pLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (EllipseLayer.class.isInstance(layer)) {
			final EllipseLayer eLayer = (EllipseLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(eLayer.getPanel(), eLayer.getName(), (RenderableLayer) eLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (PolygonLayer.class.isInstance(layer)) {
			final PolygonLayer lLayer = (PolygonLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(lLayer.getPanel(), lLayer.getName(), (RenderableLayer) lLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if (CircleLayer.class.isInstance(layer)) {
			final CircleLayer lLayer = (CircleLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(lLayer.getPanel(), lLayer.getName(), (RenderableLayer) lLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		} else if(layer instanceof Model3DAnimatorLayer){
			final Model3DAnimatorLayer mLayer = (Model3DAnimatorLayer) layer;
			ActionListener propertiesActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					frame.getPropertiesDialog().show(mLayer.getAnimatorPanel(), mLayer.getName(), (RenderableLayer) mLayer);
				}
			};
			propertiesMenu = new RightClickMenuItem("属性", propertiesActionListener, true);
		}else{
			propertiesMenu = new RightClickMenuItem("属性", null, false);
		}
		return propertiesMenu;
	}

	private static RightClickMenuItem getDeleteMenu(final DartEarthAppFrame frame, final WorldWindow wwd, final Layer layer,
			final LayerPanel panel) {
		String name = layer.getName();
		boolean couldDelete = true;
		for (String l : COULD_NOT_DELETE_LAYERS) {
			if (l.equals(name)) {
				couldDelete = false;
				break;
			}
		}
		RightClickMenuItem deleteMenu = null;
		if (couldDelete) {

			ActionListener deleteActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					LayerList layers = wwd.getModel().getLayers();
					layers.remove(layer);
					panel.update();
					if (frame.getPropertiesDialog().getLayer().equals(layer)) {
						frame.getPropertiesDialog().clear();
						frame.getPropertiesDialog().setVisible(false);
					}
				}
			};

			deleteMenu = new RightClickMenuItem("删除", deleteActionListener, true);
		} else {
			deleteMenu = new RightClickMenuItem("删除", null, false);
		}
		return deleteMenu;

	}

	private static ActionListener getToTopActionListener(final WorldWindow wwd, final Layer layer, final LayerPanel panel) {
		ActionListener toTopActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LayerList layers = wwd.getModel().getLayers();
				layers.remove(layer);
				layers.add(0, layer);
				panel.update();
			}
		};
		return toTopActionListener;
	}

	private static ActionListener getUpActionListener(final WorldWindow wwd, final Layer layer, final LayerPanel panel) {
		ActionListener toTopActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LayerList layers = wwd.getModel().getLayers();

				int index = layers.indexOf(layer);
				if (index > 0) {
					index = index - 1;
				}

				layers.remove(layer);
				layers.add(index, layer);

				panel.update();
			}
		};
		return toTopActionListener;
	}

	private static ActionListener getDownActionListener(final WorldWindow wwd, final Layer layer, final LayerPanel panel) {
		ActionListener toTopActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LayerList layers = wwd.getModel().getLayers();

				int index = layers.indexOf(layer);
				if (index < layers.size() - 1) {
					index = index + 1;
				}

				layers.remove(layer);
				layers.add(index, layer);

				panel.update();
			}
		};
		return toTopActionListener;
	}

	private static ActionListener getToButtomActionListener(final WorldWindow wwd, final Layer layer, final LayerPanel panel) {
		ActionListener toTopActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LayerList layers = wwd.getModel().getLayers();

				int index = layers.size() - 1;
				layers.remove(layer);
				layers.add(index, layer);

				panel.update();
			}
		};
		return toTopActionListener;
	}
}
