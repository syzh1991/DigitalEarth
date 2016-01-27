package org.gfg.dartearth.feature.KML;

import java.io.IOException;

import org.dom4j.Element;
import org.gfg.dartearth.feature.draw.ShapeLayer;
/**
 * 每个可导出为DEML的类都需继承此类
 * 
 * @author 江琳<br>
 *       lim.chiang.zju@gmail.com
 *
 */
public interface ExportableLayer{
	/**
	 * 导出为DEML
	 * @return 可解析的Element
	 * @throws IOException
	 */
	public   Element exportAsDeml()throws IOException;
}
