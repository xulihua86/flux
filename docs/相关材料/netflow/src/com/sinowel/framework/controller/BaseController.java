package com.sinowel.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.sinowel.netflow.common.JsonUtil;
import com.sinowel.netflow.common.PropertiesUtil;

/**
 * 基础controller
 * @author chendawei
 *
 */
public class BaseController {
	
	protected String interfaceIP = PropertiesUtil.getProperty("interfaceIP");
	
	protected String openId;
	
	protected Map<String,Object> resultMap = new HashMap<String,Object>();;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	public void init(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		this.request = request;
		this.response = response;
		this.session = session;
	}
	
	
	/**
	 * 调用数据接口
	 * @param url
	 * @return
	 */
	public Map<String,Object> getDataFromURL(String url){
		Map<String,Object> returnMap = null;
		try{
			HttpClient httpClient = new HttpClient();
			GetMethod get = new GetMethod(url);
			
			int statusCode = httpClient.executeMethod(get);
			byte[] responseBody = get.getResponseBody();
			String jsonStr = new String (responseBody,"utf-8");
			
			returnMap = (Map<String,Object>)JsonUtil.getInstance().toMap(jsonStr);
			
		}catch(Exception ex){
			returnMap = new HashMap<String,Object>();
			returnMap.put("operateFlag", "0");
			returnMap.put("operateMessage", "接口发生错误，原因："+ex.getMessage());
			ex.printStackTrace();
		}
		
		return returnMap;
	}
	
	/**
	 * 异步返回
	 * @param jsonStr
	 */
	public void writeJson(String jsonStr){
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(jsonStr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}//end class
