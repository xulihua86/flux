package com.sinowel.netflow.common.user;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.sinowel.netflow.common.AcstkenAndJstkenUtil;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.JsonUtil;

public class CheckOpenIdUtil {
	public static boolean checkOpenId(String openid) throws Exception{
		WechatUserResult list = BaseUtil.getUserList(openid);
		if(list.getNext_openid()==null){
			return false;
		}else{
			//此处调用微信接口获得昵称
			String interfaceIP = ConfigManager.getValue("interfaceIP");
			String appId = ConfigManager.getValue("wechat.appId");
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			String access_token = AcstkenAndJstkenUtil.getInstance().getAccessToken();
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
//			String interfaceUrl = interfaceIP+"/sfip/community/attentionCommunity";
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
			return true;
		}
	}
}
