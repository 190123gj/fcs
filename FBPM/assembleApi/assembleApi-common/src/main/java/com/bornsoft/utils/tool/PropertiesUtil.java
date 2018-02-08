package com.bornsoft.utils.tool;

import java.io.IOException;
import java.util.Properties;

import com.bornsoft.utils.exception.BornApiException;

/**
 * 读取.config文件类
 * @author xiaohui@yiji.com   
 * @date 2014-11-20 下午1:30:59 
 * @version V1.0
 */
public class PropertiesUtil {

	private static Properties properties = null;
	
	/**
	 * 根据key读取.config文件中的值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(PropertiesUtil.class.getResourceAsStream("/spring/sys." + System.getProperty("spring.profiles.active") + ".config"));
			} catch (IOException e) {
				throw new BornApiException("读取config文件失败!");
			}
		}
		return (String) properties.get(key);
	}
}
