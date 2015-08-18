package com.sinowel.netflow.common;

import java.io.BufferedReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {
	public static XStream init() {
		XStream xStream = new XStream(new DomDriver());
		return xStream;
	}
	public static String inputStream2String(BufferedReader reader) {

		String line;
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static Object simplexml2object(String xml, Object obj) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias(obj.getClass().getSimpleName(), obj.getClass());
		Object reobj = xStream.fromXML(xml);
		return reobj;
	}
	
	public static String simpleobject2xml(Object obj) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias(obj.getClass().getSimpleName(), obj.getClass());
		String xml = xStream.toXML(obj);
		return xml;
	}
}
