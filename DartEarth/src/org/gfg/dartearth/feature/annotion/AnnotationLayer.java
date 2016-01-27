package org.gfg.dartearth.feature.annotion;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;

/**
 * 标识类
 * @author 江琳<br>
 *         lim.chiang.zju@gmail.com
 */
public abstract class AnnotationLayer extends RenderableLayer {
	/**
	 * 刷新图层中属性面板的内容。位置可以用鼠标拖动来改变，当位置改变后，属性面板内容也应该相应地改变。
	 *
	 * @param p 位置
	 */
	public void fresh(Position p) {

	}

	/**
	 * 获得经度
	 * @return
	 */
	public Double getLng() {
		return null;
	}

	/**
	 * 获得纬度
	 * @return
	 */
	public Double getLat() {
		return null;
	}
}
