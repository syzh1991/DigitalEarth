package org.gfg.util.metadata;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将其他类型转化为Date类型
 */
public class DateUtil {
	/**
	 * 日期转换 
	 * 
	 * @param dateStr
	 *            日期值
	 * @return 日期类对象
	 */
	@SuppressWarnings({ "finally", "deprecation" })
	public static Date dateFormate(String dateStr) {
		SimpleDateFormat dateFormater;
		Date dateResult = null;
		String newDate = dateStr;
		dateStr = dateStr.replace("T", " ");
		Pattern p1 = Pattern
				.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}\\s*[0-9]{2}:[0-9]{2}:[0-9]{2}"); // 匹配yyyy/MM/dd
																						// hh:mm:ss型日期
		Pattern p2 = Pattern
				.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{2}:[0-9]{2}:[0-9]{2}"); // 匹配yyyy-MM-dd
																						// hh:mm:ss型日期

		Pattern p4 = Pattern
				.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{1}"); // 匹配yyyy-MM-dd
																								// hh:mm:ss.m型日期
		Pattern p5= Pattern
		.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{2}:[0-9]{2}:[0-9]+.[0-9]+"); // 匹配yyyy-MM-dd
																						// hh:mm:ss.m型日期
		Pattern p3 = Pattern.compile("[0-9]{8}\\s*[0-9]{6}");

		Matcher m1 = p1.matcher(dateStr);
		Matcher m2 = p2.matcher(dateStr);
		Matcher m3 = p3.matcher(dateStr);
		Matcher m4 = p4.matcher(dateStr);
		Matcher m5 = p5.matcher(dateStr);
		try {
			if (m1.find()) {
				dateFormater = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				dateResult = dateFormater.parse(dateStr);
			} else if (m2.find()||m5.find()) {
				dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				dateResult = dateFormater.parse(dateStr);
			} else if (m3.find()) {
				dateFormater = new SimpleDateFormat("yyyyMMdd hhmmss");
				dateResult = dateFormater.parse(dateStr);
			} else if (m4.find()) {
				dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.m");
				dateResult = dateFormater.parse(dateStr);
			} else {
				dateResult = new Date(Date.parse(newDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return dateResult;
		}
	}
}
