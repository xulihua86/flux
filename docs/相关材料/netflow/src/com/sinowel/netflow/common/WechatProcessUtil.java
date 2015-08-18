package com.sinowel.netflow.common;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sinowel.netflow.common.model.RequestMsg;
import com.thoughtworks.xstream.XStream;

public class WechatProcessUtil{
	public static String processRequest(HttpServletRequest request) throws Exception{
		XStream xs = XmlUtil.init();
		xs.alias("xml" , RequestMsg.class);//以微信接口形式读取xml
		String xmlMsg = "";
		xmlMsg = XmlUtil.inputStream2String(request.getReader());
		RequestMsg msg = (RequestMsg)xs.fromXML(xmlMsg);
		String MsgType = msg.getMsgType();
		String interfaceIP = ConfigManager.getValue("interfaceIP");
		if("event".equalsIgnoreCase(MsgType) && msg.getEvent().equalsIgnoreCase("subscribe")){
			//此处调用微信接口获得昵称
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			String access_token = AcstkenAndJstkenUtil.getInstance().getAccessToken();
			String openid = msg.getFromUserName();//openid
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
			String interfaceUrl = interfaceIP+"/sfip/community/attentionCommunity";
			HttpClient httpClient = new HttpClient();
			PostMethod post = new PostMethod(interfaceUrl);
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
//			String interfaceUrl = interfaceIP+"/sfip/community/attentionCommunity?openId=OPENID&nickName=NICKNAME&gender=GENDER&" +
//					"city=CITY&province=PROVINCE&area=AREA&language=LANGUAGE&headimgUrl=HEADIMGURL&remark=REMARK";
//			interfaceUrl = interfaceUrl.replace("OPENID", msg.getFromUserName())
//					.replace("NICKNAME", nickName)
//					.replace("GENDER", gender)
//					.replace("CITY", city)
//					.replace("PROVINCE", province)
//					.replace("AREA", area)
//					.replace("LANGUAGE", language)
//					.replace("HEADIMGURL", headimgUrl)
//					.replace("REMARK", remark);
//			GetMethod get = new GetMethod(interfaceUrl);
//			HttpClient httpClient = new HttpClient();
//			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//			int statusCode = httpClient.executeMethod(get);
			
//			byte[] responseBody = get.getResponseBody();
			String resultStr = new String (responseBody);
			Map<String,String> resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(resultStr);
			String code = resultMap.get("code");
		}
		return "";
	}
}
