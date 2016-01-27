package org.gfg.util.metadata;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据格式转换工具类
 * 
 * @author PANXUANFAN
 */
public class DataParser {
	private static final Log logger = LogFactory.getLog(DataParser.class);

	/**
	 * 从string转换成type指定的格式
	 * @param valueString
	 * @param type
	 * @return
	 */
	public static Object getValue(String valueString, String type) {
		if (valueString == null || valueString.equals("")
				|| "null".equals(valueString))
			return null;
		Object value = null;
		type = type.toLowerCase();
		if (type.equalsIgnoreCase(EDataForm.DECIMAL.toString())) {
			valueString = valueString.replaceAll(",", ""); // 去掉数字里面的逗号
			value = Double.parseDouble(valueString);
		} else if (type.equalsIgnoreCase(EDataForm.INTEGER.toString())) {
			valueString = valueString.replaceAll(",", ""); // 去掉数字里面的逗号
			value = Integer.parseInt(valueString);
		} else if (type.equalsIgnoreCase(EDataForm.DATE.toString())) {
			value = DateUtil.dateFormate(valueString).getTime();
		} else if (type.equalsIgnoreCase(EDataForm.DATETIME.toString())) {
			value = valueString;// 这个str就是2005-12-17 16:27:07这个形式的了。
		} else if (type.equalsIgnoreCase(EDataForm.TIME.toString())) {
			value = Time.valueOf(valueString);
		} else {
			value = valueString;
		}
		return value;
	}

	public static Long parseToLong(String valueString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(valueString);
			return date.getTime();
		} catch (ParseException e) {
			logger.info(e);
			return null;
		}
	}

}
