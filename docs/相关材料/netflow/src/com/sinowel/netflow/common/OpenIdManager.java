package com.sinowel.netflow.common;

import com.sinowel.netflow.common.model.JsToken;

public class OpenIdManager {
	
 
 
	public static JsToken getOpenId(String code)throws Exception {
		
		System.out.println("=========== get openID   ======================");
		System.out.println("get page weixin code :"+code);
		String appId = PropertiesUtil.getProperty("wechat.appId");
		String secret = PropertiesUtil.getProperty("wechat.secret");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		url = url.replace("APPID", appId);
		url = url.replace("SECRET", secret);
		url = url.replace("CODE", code);
		String resultStr = HttpUtil.httpsRequest(url, "GET", null);
		System.out.println("[oauth2]access_token str:"+resultStr);
		System.out.println("=============  get openID end ====================");
		return JsonUtil.getInstance().toBean(resultStr , JsToken.class);
	}

}
