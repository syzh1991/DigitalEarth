package org.gfg.dartearth.core;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.RenderableLayer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gfg.dartearth.feature.annotion.AnnotationLayer;
import org.gfg.dartearth.util.BasicDragger;

import com.sun.awt.AWTUtilities;
/**
 * 属性对话框，整個系統中只有一個属性对话框。当查看某一个图形的属性时，属性对话框显示这个图形的属性。
 * 
 * @author 陈亮
 *
 */
public class PropertiesDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3722125319977186661L;
	private JPanel panel;
	private String layerName;
	private RenderableLayer layer;
	private WorldWindow wwd;
	protected JScrollPane scrollPane;
	private BasicDragger dragger;
	private DartEarthAppFrame frame;

	/**
	 * 构造方法，修改属性时会修改图形，因而需要传入Worldwindow。默认大小 为400*600。
	 * 
	 * @param frame DartEarthAppFrame
	 * @param wwd WorldWindow
	 */
	public PropertiesDialog(Frame frame, WorldWindow wwd) {
		super(frame, "");
		this.wwd = wwd;
		setPreferredSize(new Dimension(400, 600));
		// getContentPane().setLayout(new FlowLayout());
		setResizable(true);
		setModal(false);
		// new ArrayList(layer.getRenderables());
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(PropertiesDialog.this, 1f);
			}
			@Override
			public void windowLostFocus(WindowEvent arg0) {
				AWTUtilities.setWindowOpacity(PropertiesDialog.this, 0.7f);
			}
		});
		this.frame = (DartEarthAppFrame) frame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public WorldWindow getWwd() {
		return wwd;
	}

	public void setWwd(WorldWindow wwd) {
		this.wwd = wwd;
	}

	public RenderableLayer getLayer() {
		return layer;
	}

	public void setLayer(RenderableLayer layer) {
		this.layer = layer;
	}

	/**
	 * 清理当前的内容
	 */
	public void clear() {
		if (this.scrollPane != null) {
			this.remove(this.scrollPane);
		}
		this.layer = null;
		this.layerName = null;
		this.panel = null;
	}

	/**
	 * 显示对话框。当调用这个方法时，属性对话框就会显示出来。
	 * @param panel 所需显示的图形的面板
	 * @param layerName 所需显示的图形所在图层的图层名
	 * @param layer 图层
	 */
	public void show(JPanel panel, String layerName, RenderableLayer layer) {
		if (this.scrollPane != null) {
			this.remove(this.scrollPane);
		}
		JPanel jpanel=new JPanel();
		jpanel.setLayout(new FlowLayout());
		this.panel = panel;
		this.layerName = layerName;
		this.layer = layer;
		this.scrollPane = new JScrollPane(panel);
	//	this.setLayout(new FlowLayout());
		this.add(this.scrollPane);
		this.setTitle(layerName + " 属性");
		this.pack();
		// this.r
		if (this.dragger != null) {
			wwd.removeSelectListener(this.dragger);
			// ((Component)
			// (frame.getWwd())).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		this.dragger = new BasicDragger(this.wwd, this.layer);
		this.setVisible(true);
		if(AnnotationLayer.class.isInstance(layer)){
		 frame.gotoLatLon(((AnnotationLayer) layer).getLat(),((AnnotationLayer)layer).getLng());
		 }
	}

	/**
	 * 重写父类方法。<br>
	 * 当visible为false时，图形不可拖动。当visible为true时，图形可拖动。
	 */
	@Override
	public void setVisible(boolean visible) {
		if (this.dragger != null) {
			if (visible == true) {
				wwd.addSelectListener(this.dragger);
			} else {
				wwd.removeSelectListener(this.dragger);
			}
		}
		super.setVisible(visible);
	}

	public BasicDragger getDragger() {
		return dragger;
	}

	public void setDragger(BasicDragger dragger) {
		this.dragger = dragger;
	}
}
