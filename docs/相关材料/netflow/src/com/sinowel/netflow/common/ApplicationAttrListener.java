package com.sinowel.netflow.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**监听application，将urlconfig.properties所有属性加载到application作用域
 * @author lenovo
 *
 */
public class ApplicationAttrListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Properties pro = new Properties();
		try {
		    InputStream inStr = ApplicationAttrListener.class.getClassLoader().getResourceAsStream("urlconfig.properties");
		    pro.load(inStr);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Iterator it=pro.entrySet().iterator();
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    String key = entry.getKey().toString();
		    String value = entry.getValue().toString();
		    arg0.getServletContext().setAttribute(key,value);
		}
	}
}
