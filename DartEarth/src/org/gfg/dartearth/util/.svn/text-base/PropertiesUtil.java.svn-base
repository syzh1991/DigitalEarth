package org.gfg.dartearth.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取properties文件的工具类
 * @author shenan4321
 */

public class PropertiesUtil {
	private File file;
	private Properties properties;
	private FileInputStream is;
	private FileOutputStream os;
	
	{
		properties =new Properties();
	}
	
	/**
	 * 取得对应propertyName的值
	 * @param propertyName
	 * @return 对应propertyName的值
	 */
	public synchronized String getProperties(String filename,String propertyName) {
		file = new File(this.getClass().getClassLoader().getResource("//").getPath()+filename);
		try {
			is = new FileInputStream(file);
			properties.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (properties.getProperty(propertyName)==null)?"0":properties.getProperty(propertyName);
	}
	
	/**
	 * 设置对应propertyName的值
	 * @param propertyName 属性
	 * @param value 对应值
	 * @return 设置成功则返回true
	 */
	public synchronized Boolean setProperties(String filename,String propertyName ,String value) {
		file = new File(this.getClass().getClassLoader().getResource("//").getPath()+filename);
		try {
			is = new FileInputStream(file);
			properties.load(is);
			os = new FileOutputStream(file);
			properties.getProperty(propertyName);
			properties.setProperty(propertyName, value);
			properties.store(os, "");
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}	
	

}
