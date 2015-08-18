/**
 * Copyright ©2013-2014, beijing ow Co., Ltd.
 * 文件名:     PropertiesUtil.java
 */
package com.sinowel.netflow.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *  Properties 读取
 *  @author langkai
 *  @version 1.0.0
 *  </pre>
 *  Created on :上午11:18:20
 *  LastModified:
 *  History:
 *  </pre>
 */
public class PropertiesUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static String propName = "config.properties";
	
	private static Map<String, Properties> propsMap = new HashMap<String, Properties>();
	
	/**
	 *  通过properties文件名取得Properties对象
	 *  @param fileName properties文件名
	 *  @return Properties对象
	 *  @throws IOException exception
	 *  @author langkai
	 *  @version 1.0.0
	 *  </pre>
	 *  Created on :2014年5月4日 上午11:18:50
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static Properties getProperties(String fileName) throws IOException {

		if (propsMap.containsKey(fileName)) {
			return propsMap.get(fileName);
		}
		Resource resource = new ClassPathResource(File.separatorChar + fileName);
		Properties props;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			
			propsMap.put(fileName, props);
		} catch (IOException e) {
			logger.error("BUS_读取properties失败！", e);
			throw e;
		}
		
		return propsMap.get(fileName);
	}
	
	/**
	 *  通过 properties 名取得值
	 *  @param fileName properties 文件名
	 *  @param key properties名
	 *  @return properties值
	 *  @author langkai
	 *  @version 1.0.0
	 *  </pre>
	 *  Created on :2014年5月4日 上午11:19:44
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static String getPropertiesValue(String fileName, String key) {
		
	String value = null;
		try {
			value = getProperties(fileName).getProperty(key);
		} catch (IOException e) {
			logger.error("没有找到指定缺省的config_bus.properties文件！", e);
		}
		
		if (value == null) {
			value = "";
		}
		
		logger.debug("读取配置文件" + fileName + " Key:" + key + " value:" + value);
		return value.trim();
	}

	/**
	 *  用默认properties文件名 通过properties名取得值
	 *  @param key properties名
	 *  @return properties值
	 *  @author langkai
	 *  @version 1.0.0
	 *  </pre>
	 *  Created on :2014年5月4日 上午11:20:09
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static String getProperty(String key) {
		
		String value = null;
		try {
			value = getProperties(propName).getProperty(key);
		} catch (IOException e) {
			logger.error("没有找到缺省的config_bus.properties文件！", e);
		}
		
		if (value == null) {
			value = "";
		}
		return value.trim();
	}
}
