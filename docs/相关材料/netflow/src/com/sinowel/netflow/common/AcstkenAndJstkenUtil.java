package com.sinowel.netflow.common;

import java.util.Map;

public class AcstkenAndJstkenUtil {
	private static String accessToken = null;
	private static String jsticket = null;
	private long timestamp = 0;
	private static AcstkenAndJstkenUtil instance = null;
	private AcstkenAndJstkenUtil(){
		initdata();
	}
	public static synchronized AcstkenAndJstkenUtil getInstance() {
		if (instance == null){
			instance = new AcstkenAndJstkenUtil();
		}
		return instance;
	}
	public String getAccessToken() {
		initdata();
		return accessToken;
	}
	public String getJsticket() {
		initdata();
		return jsticket;
	}
	
	public void initdata(){
		String accesstoken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
		String js_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESSTOKEN&type=jsapi";
		String appid = ConfigManager.getValue("wechat.appId");
		String appsecret = ConfigManager.getValue("wechat.secret");
		String useRedis = ConfigManager.getValue("useRedis");
		accesstoken_url = accesstoken_url.replace("APPID", appid).replace("SECRET", appsecret);
		String resultStr = "";
		String js_resultStr = "";
		if(Boolean.valueOf(useRedis)){
			accessToken = JedisUtil.getToken();
			jsticket = JedisUtil.getJsTicket();
		}else{
			if(accessToken == null){
				resultStr = HttpUtil.httpsRequest(accesstoken_url, "GET", null);
				timestamp = System.currentTimeMillis() / 1000;
				System.out.println(resultStr);
				Map<String,String> resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(resultStr);
				accessToken = resultMap.get("access_token");
				System.out.println("accessToken is "+accessToken);
				js_ticket_url = js_ticket_url.replace("ACCESSTOKEN", accessToken);
				js_resultStr = HttpUtil.httpsRequest(js_ticket_url, "GET", null);
				System.out.println(js_resultStr);
				Map<String,String> js_resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(js_resultStr);
				jsticket = js_resultMap.get("ticket");
				System.out.println("jsticket is "+jsticket);
			}else{
				if(((System.currentTimeMillis() / 1000)-timestamp)>7000){//accessToken过期重新获取
					resultStr = HttpUtil.httpsRequest(accesstoken_url, "GET", null);
					timestamp = System.currentTimeMillis() / 1000;
					System.out.println(resultStr);
					Map<String,String> resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(resultStr);
					accessToken = resultMap.get("access_token");
					System.out.println("accessToken is "+accessToken);
					js_ticket_url = js_ticket_url.replace("ACCESSTOKEN", accessToken);
					js_resultStr = HttpUtil.httpsRequest(js_ticket_url, "GET", null);
					System.out.println(js_resultStr);
					Map<String,String> js_resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(js_resultStr);
					jsticket = js_resultMap.get("ticket");
					System.out.println("jsticket is "+jsticket);
				}
			}
		}
	}
}
