package com.sinowel.netflow.common;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.PropertiesUtil;




public class Oauth2Util {

	private static Oauth2Util instance = null;

	private Oauth2Util() {
	}

	public static synchronized Oauth2Util getInstance() {
		if (instance == null)
			instance = new Oauth2Util();
		return instance;
	}		
	
	public static String scope = "snsapi_base";

	/**
	 * 
	 * 获取页面授权url
	 * 
	 */
	public String getOauth2Url(String url, String state) throws Exception {

		String appId = ConfigManager.getValue("wechat.appId");
		String oauth2_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		oauth2_url = oauth2_url.replace("APPID", appId);
		oauth2_url = oauth2_url.replace("REDIRECT_URI", getUrlEncode(url));
		oauth2_url = oauth2_url.replace("SCOPE", scope);
		if (state != null && !"".equals(state)) {
			oauth2_url = oauth2_url.replace("STATE", state);
		}
		return oauth2_url;
	}

	/**
	 * 
	 * url 转码
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @author linyaoyao
	 * @version 1.0 </pre> Created on :2015年1月22日 下午4:49:50 LastModified:
	 *          History: </pre>
	 */
	public static String getUrlEncode(String url) throws Exception {
		return java.net.URLEncoder.encode(url, "utf-8");
	}

	/**
	 * 
	 * 获取页面授权临时token url
	 * 
	 * @param code
	 * @return
	 * @author linyaoyao
	 * @version 1.0 </pre> Created on :2015年1月22日 下午4:50:08 LastModified:
	 *          History: </pre>
	 */
	public String getTempToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		
		String appId = PropertiesUtil.getProperty("wechat.appId");
		String secret = PropertiesUtil.getProperty("wechat.secret");
		
		url = url.replace("APPID", appId);
		url = url.replace("SECRET", secret);
		url = url.replace("CODE", code);

		return HttpUtil.httpsRequest(url, "POST", null);
	}

	public String refreshToken(String refreshToken, String appId) {

		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		url = url.replace("REFRESH_TOKEN", refreshToken);
		url = url.replace("APPID", appId);

		return HttpUtil.httpsRequest(url, "POST", null);
	}

	public String getDecodeURL(String url) throws Exception { 
		return java.net.URLDecoder.decode(url, "utf-8");
	}

		
}
