package org.gfg.dartearth.feature.goTo;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.json.JSONObject;

import org.gfg.dartearth.core.DartEarthAppFrame;
import org.gfg.dartearth.feature.annotion.BubbleAnnotation;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationBuilder;
import org.gfg.dartearth.feature.annotion.BubbleAnnotationLayer;
import org.gfg.dartearth.util.PropertiesUtil;

/**
 * 前往属性面板
 * 
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 * 
 */
public class GoToPanel extends JPanel {

	/**
	 * 总页数
	 */
	private static final String TOTAL_PAGE_JSON_KEY = "totalPage";
	/**
	 * 每页显示的记录数
	 */
	private static final String PAGE_SIZE = "pageSize";
	/**
	 * 编码方式
	 */
	private static final String ENCODING_TYPE = "UTF-8";
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2634413586222717954L;
	private static DartEarthAppFrame frame;
	/**
	 * 标签的大小
	 */
	private static Dimension labelDimension = new Dimension(80, 24);
	/**
	 * 一般组件的大小
	 */
	private static Dimension bigComponentDimension = new Dimension(180, 24);
	/**
	 * 小组件的大小
	 */
	private static Dimension smallComponentDimension = new Dimension(280, 24);
	/**
	 * 前往按钮的大小
	 */
	private static Dimension gotoComponentDimension = new Dimension(60, 24);
	private String urlPath;
	/**
	 * 这里的信息是由用户输入的，需要前往的地名
	 */
	private JLabel goToLabel;
	private JTextField goToText;
	private List<JButton> goToBtns;

	/**
	 * 老样子，还是确定、应用、取消
	 */
	private JButton applyBtn;
	JPanel mainPanel;
	JPanel pageBtnPanel;
	private int pageCount = 0;
	private int totalPage = 0;
	private boolean pageBtnExit = false;

	public GoToPanel(WorldWindow wwd, DartEarthAppFrame frame) {
		GoToPanel.frame = frame;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1, 10, 1));
		PropertiesUtil pu = new PropertiesUtil();
		urlPath = pu.getProperties("goTo.properties", "goTo.searchByName.url");
		// this.setLayout(new GridLayout(0, 1, 2, 2));
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(380, 100));
		initialDialogBtns();
	}

	/**
	 * 初始化用户输入界面
	 */
	private void initialDialogBtns() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		goToLabel = new JLabel("请输入地名:");
		goToText = new JTextField();
		goToText.setPreferredSize(new Dimension(300, 20));
		applyBtn = new JButton("前往");
		// applyBtn.setPreferredSize(new Dimension(10,0));
		ActionListener ApplyListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pageCount = 0;
				excuteQuery(pageCount);
				// if (!GoToPanel.this.pageBtnExit) {
				// createPageBtn();
				// }
			}
		};
		panel.add(goToLabel);
		panel.add(goToText);
		applyBtn.addActionListener(ApplyListener);
		panel.add(applyBtn);
		panel.setPreferredSize(new Dimension(600, 70));

		this.add(panel);
	}

	public void update() {
		this.revalidate();
		this.repaint();
	}

	/**
	 * 通过地名获得其经纬度并前往
	 * @param pageCount 当前页数
	 */
	private void excuteQuery(int pageCount) {

		this.setPageCount(pageCount);
		final String pName = goToText.getText().trim();
		URLConnection httpsConn = null;
		URL url = null;
		try {
			url = new URL(urlPath + "placeName=" + pName + "&pageCount=" + pageCount);
			httpsConn = url.openConnection();
			httpsConn.setConnectTimeout(16000);
			httpsConn.setReadTimeout(12000);
			//System.out.println(httpsConn.getHeaderField(null));
			InputStream content = (InputStream) httpsConn.getContent();
			InputStreamReader insr = new InputStreamReader(content, ENCODING_TYPE);
			BufferedReader br = new BufferedReader(insr);
			String data;
			if ((data = br.readLine()) != null) {
				JSONObject jsonObject = JSONObject.fromObject(data);
				this.totalPage = Integer.parseInt(jsonObject.get(TOTAL_PAGE_JSON_KEY).toString());
				int totalRecord=Integer.parseInt(jsonObject.get("total").toString());
				List<Map<String, Object>> list = (List<Map<String, Object>>) jsonObject.get("locations");
				mainPanel.removeAll();
				JLabel totalPageLabel = new JLabel();
	
				totalPageLabel.setText("共"+totalRecord+"条纪录,当前第"+(pageCount+1)+"/"+this.totalPage+"页");
				totalPageLabel.setForeground(new Color(100,100,100));
				mainPanel.add(totalPageLabel);
				for (int i = 0; i < list.size(); i++) {
					final Map<String, Object> map = list.get(i);

					mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					
					JLabel lngLabel = new JLabel();
					lngLabel.setText(map.get("name") + "");
					lngLabel.setPreferredSize(smallComponentDimension);
					
					mainPanel.add(lngLabel);
					mainPanel.setPreferredSize(new Dimension(420, 400));
					JButton goToBtn = new JButton();
					goToBtn.setText("前往");
					goToBtn.setPreferredSize(gotoComponentDimension);
					ActionListener deleteListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent event) {
							double west = Double.parseDouble(map.get("west").toString());
							double east = Double.parseDouble(map.get("east").toString());
							double south = Double.parseDouble(map.get("south").toString());
							double north = Double.parseDouble(map.get("north").toString());
							double centerLat = (north + south) / 2;
							double centerLng = (west + east) / 2;
							Position pos = new Position(LatLon.fromDegrees((north + south) / 2, (west + east) / 2), 0);

							BubbleAnnotationBuilder build = new BubbleAnnotationBuilder();
							build.buildBubbleAnnotation(map.get("name") + ":\n最大经度：" + west + ";\n最小经度：" + east + ";\n最大纬度：" + north
									+ ";\n最小纬度：" + south + ";\n中心经度：" + centerLng + ";\n中心纬度：" + centerLat + ";", pos,
									GoToPanel.this.frame, false);

							double zoomConfig = north - south;
							if (south > north) {
								zoomConfig = -zoomConfig;
							}

							frame.gotoLatLon(Double.parseDouble(map.get("north").toString()),
									Double.parseDouble(map.get("west").toString()), zoomConfig * 1000000, 0, 0);
						}
					};
					goToBtn.addActionListener(deleteListener);
					mainPanel.add(goToBtn);

				}
				mainPanel.add(createPageBtn());
				this.add(mainPanel);
				frame.getGoToPanelDialog().getGoToPanel().update();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化翻页界面
	 * @return 界面组件
	 */
	private JPanel createPageBtn() {
		JPanel panel = new JPanel();
		this.pageBtnExit = true;
		JButton pageBtn1 = new JButton("首页");
		ActionListener Listener1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				excuteQuery(0);
				frame.getGoToPanelDialog().getGoToPanel().update();
			}
		};
		pageBtn1.addActionListener(Listener1);
		panel.add(pageBtn1);

		JButton pageBtn2 = new JButton("上一页");
		ActionListener Listener2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GoToPanel.this.pageCount - 1 < 0) {
					GoToPanel.this.pageCount = 1;
				}
				excuteQuery(GoToPanel.this.pageCount - 1);
				frame.getGoToPanelDialog().getGoToPanel().update();
			}
		};
		pageBtn2.addActionListener(Listener2);
		panel.add(pageBtn2);

		JButton pageBtn3 = new JButton("下一页");
		ActionListener Listener3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GoToPanel.this.pageCount + 1 >= totalPage) {
					GoToPanel.this.pageCount = GoToPanel.this.pageCount - 1;
				}

				excuteQuery(GoToPanel.this.pageCount + 1);
				frame.getGoToPanelDialog().getGoToPanel().update();
			}
		};
		pageBtn3.addActionListener(Listener3);
		panel.add(pageBtn3);

		JButton pageBtn4 = new JButton("末页");
		ActionListener Listener4 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				excuteQuery(GoToPanel.this.totalPage - 1);
				GoToPanel.this.pageCount = GoToPanel.this.totalPage - 1;
				frame.getGoToPanelDialog().getGoToPanel().update();
			}
		};
		pageBtn4.addActionListener(Listener4);
		panel.add(pageBtn4);
		return panel;
	}

	/**
	 * 添加组件
	 * @param components 需要添加的组件
	 */
	private void put(JComponent... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
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

	private void buildPlaceNamePanels() {
		goToBtns.clear();
	}

	public void refreshGoToPanel() {
		buildPlaceNamePanels();
		this.repaint();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
