package com.sinowel.netflow.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**读取配置文件的工具
 * @author lenovo
 *
 */
public class ConfigManager {
	public static final String PROPPATH = "config.properties";
	public static String getValue(String key){
		 InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(PROPPATH);
		 Properties p = new Properties();
		 try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
}
