package com.sinowel.netflow.common;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 微信核心服务类
 * 
 * @author linyaoyao
 * @version 1.0 </pre> Created on :下午3:15:25 LastModified: History: </pre>
 */
public class WechatService {

	private static Logger log = LoggerFactory.getLogger(WechatService.class);

	/**
	 * 解析腾讯微信请求信息.
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String strNull = "";
		String line;
		StringBuffer sb = new StringBuffer();
		try {
			// xml请求解析
//			Map<String, String> requestMap = MessageUtil.parseXml(request);
			Map<String, String> requestMap = new HashMap<String, String>();
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine())!=null){
				sb.append(line);
			}
			requestMap = (Map<String, String>)JsonUtil.getInstance().toMap(sb.toString());
			for (Map.Entry<String, String> entry : requestMap.entrySet()) {
			    String key = entry.getKey().toString();
			    String value = entry.getValue().toString();
			    System.out.println("key=" + key + ", value=" + value);
		    }
			
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String fromUserName = requestMap.get("FromUserName");
			// 推送事件
			if ("event".equals(msgType)) {
				String event = requestMap.get("Event");
				System.out.println("上行消息============="+event);
				// 卡券领取事件
				if ("user_get_card".equals(event)) {
					System.out.println("上行消息============="+event);
					String openId = requestMap.get("FromUserName");
					String wxCardId = requestMap.get("CardId");
					String weixinCode = requestMap.get("UserCardCode");
					System.out.println("openId====="+openId);
					System.out.println("wxCardId====="+wxCardId);
					System.out.println("weixinCode====="+weixinCode);
					// if("0".equals(requestMap.get("IsGiveByFriend"))){
						 String interfaceUrl = "/sfip/community/notifyWXCardTaken?openId=OPENID&wxCardCode=WEIXINCODE";
							String interfaceIP = ConfigManager.getValue("interfaceIP");
							interfaceUrl = interfaceIP + interfaceUrl;
							interfaceUrl = interfaceUrl.replace("OPENID", openId);
							interfaceUrl = interfaceUrl.replace("WEIXINCODE",
									weixinCode);
							HttpClient httpClient = new HttpClient();
							GetMethod get = new GetMethod(interfaceUrl);
							int statusCode = httpClient.executeMethod(get);
						
							System.out.println("输出调用接口结束");
					// }
					
				}
				if("subscribe".equals(event)){
					//此处调用微信接口获得昵称
					String interfaceIP = ConfigManager.getValue("interfaceIP");
					String appId = ConfigManager.getValue("wechat.appId");
					String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
					String access_token = AcstkenAndJstkenUtil.getInstance().getAccessToken();
					String openid = fromUserName;//openid
					url = url.replace("ACCESS_TOKEN", access_token);
					url = url.replace("OPENID", openid);
					String nickResult = HttpUtil.httpsRequest(url, "GET", null);
					Map<String,Object> infoResultMap = (Map<String, Object>) JsonUtil.getInstance().toMap(nickResult);
					String nickName = infoResultMap.get("nickname").toString();//昵称
					String gender = infoResultMap.get("sex").toString();//性别
					String city = infoResultMap.get("city").toString();//城市
					String province = infoResultMap.get("province").toString();//省份
					String area = infoResultMap.get("country").toString();//国家地区
					String language = infoResultMap.get("language").toString();//语言
					String headimgUrl = infoResultMap.get("headimgurl").toString();//头像url
					String remark = infoResultMap.get("remark").toString();//备注
//					String interfaceUrl = interfaceIP+"/sfip/community/attentionCommunity";
					String interfaceUrl = interfaceIP+"/sfip/community/joinCommunity";
					HttpClient httpClient = new HttpClient();
					PostMethod post = new PostMethod(interfaceUrl);
					post.addParameter("appId", appId);
					post.addParameter("openId", openid);
					post.addParameter("nickName", URLEncoder.encode(nickName, "UTF-8"));
					post.addParameter("gender", gender);
					post.addParameter("city", URLEncoder.encode(city, "UTF-8"));
					post.addParameter("province", URLEncoder.encode(province, "UTF-8") );
					post.addParameter("area", URLEncoder.encode(area, "UTF-8") );
					post.addParameter("language", language);
					post.addParameter("headimgUrl", headimgUrl);
					post.addParameter("remark", remark);
					int statusCode = httpClient.executeMethod(post);
					byte[] responseBody =post.getResponseBody();
					//此处调用接口，将关注人员信息保存到数据库
					String resultStr = new String (responseBody);
					Map<String,String> resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(resultStr);
					String code = resultMap.get("code");
				}
			}
			
		} catch (Exception e) {
			log.error("接收消息失败:{}", e);
			e.printStackTrace();
		}

		return strNull;
	}
}