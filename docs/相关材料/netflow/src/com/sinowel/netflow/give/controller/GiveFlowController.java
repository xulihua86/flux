package com.sinowel.netflow.give.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.AcstkenAndJstkenUtil;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.JsonUtil;
import com.sinowel.netflow.common.Oauth2Util;
import com.sinowel.netflow.common.OpenIdManager;
import com.sinowel.netflow.common.SignatureForNetFlow;
import com.sinowel.netflow.common.des.Des;
import com.sinowel.netflow.common.des.DesUtil;
import com.sinowel.netflow.common.model.JsToken;

@Controller
@RequestMapping(value = "/give")
public class GiveFlowController extends BaseController {
	/**点击进入转赠页面
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public static String key = "11112222";
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() throws Exception {
		//调用接口回去数据
		String interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceIP = interfaceIP + "/sfip/community/getUserStatus?openId=OPENID";
		String interfaceUrl = interfaceIP.replace("OPENID", openId);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String (responseBody);
		Map<String,String> resultMap = (Map<String, String>) JsonUtil.getInstance().toMap(resultStr);
		//获取jssdk的signature
		String requestURL = request.getRequestURL().toString();
		String params = request.getQueryString();
		if(!"".equals(params)&&params!=null){
			requestURL = requestURL+"?"+params;
		}
		Map<String,String> signdata = SignatureForNetFlow.getInstance().sign(requestURL);
		resultMap.putAll(signdata);
//		resultMap.put("openId", openId);
		session.removeAttribute("myId");
		session.setAttribute("myId", openId);
		return new ModelAndView("give/giveFlow", resultMap);
	}
	
	/**根据分享链接进入到流量币转赠领取码页面
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 * @param coin
	 * @param openId
	 * @param changeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/giveReceive", method = RequestMethod.GET)
	public ModelAndView receive(HttpServletRequest request,HttpServletResponse response
			,String coin,String receiveCode) throws Exception {
		//获取jssdk的signature
		Map<String,String> resultMap = new HashMap<String, String>();
		resultMap.put("coin", coin);
		resultMap.put("openId", openId);
		resultMap.put("receiveCode", receiveCode);
		return new ModelAndView("give/giveReceive", resultMap);
	}
	/**发送成功的回调函数
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 * @param coin
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/giveSuccess", method = RequestMethod.GET)
	public ModelAndView success(HttpServletRequest request,HttpServletResponse response,
			String coin,String receiveCode) throws Exception {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			// 调用接口修改领取码的状态
			String interfaceIP = ConfigManager.getValue("interfaceIP");
			// interfaceIP = interfaceIP +
			// "/sfip/community/turnGiveSend?openId=OPENID&receiveCode=RECEIVECODE";
			interfaceIP = interfaceIP
					+ "/sfip/community/giveFlowGift?openId=OPENID&receiveCode=RECEIVECODE";
			String interfaceUrl = interfaceIP.replace("OPENID", myId).replace(
					"RECEIVECODE", receiveCode);
			HttpClient httpClient = new HttpClient();
			GetMethod get = new GetMethod(interfaceUrl);
			int statusCode = httpClient.executeMethod(get);
			byte[] responseBody = get.getResponseBody();
			String resultStr = new String(responseBody);
			Map<String, String> resultMap = (Map<String, String>) JsonUtil
					.getInstance().toMap(resultStr);
			// Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put("coin", coin);// 向成功页面传递的赠送流量币参数

			return new ModelAndView("give/giveSuccess", resultMap);
		} else {
			return new ModelAndView("exchange/exchangeFailure");
		}
		
	}
	/**发送失败时候的回调函数
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/giveFailure", method = RequestMethod.GET)
	public ModelAndView failure(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		return new ModelAndView("give/giveFailure", null);
	}
	
	/**点击“选择好友”时异步调用接口获取领取码
	 * @param request
	 * @param response
	 * @param openId
	 * @param coin
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShareLink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getShareLink(HttpServletRequest request,
			HttpServletResponse response, String coin) throws Exception {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			// 调用接口获取数据
			String interfaceIP = ConfigManager.getValue("interfaceIP");
			// interfaceIP = interfaceIP +
			// "/sfip/community/selectFriends?openId=OPENID&quantity=COIN";
			interfaceIP = interfaceIP
					+ "/sfip/community/makeFlowGift?openId=OPENID&coinAmount=COIN";
			String interfaceUrl = interfaceIP.replace("OPENID", myId).replace(
					"COIN", coin);
			HttpClient httpClient = new HttpClient();
			GetMethod get = new GetMethod(interfaceUrl);
			int statusCode = httpClient.executeMethod(get);
			byte[] responseBody = get.getResponseBody();
			String resultStr = new String(responseBody);
			Map<String, String> resultMap = (Map<String, String>) JsonUtil
					.getInstance().toMap(resultStr);
			// 获取兑换码
			String receiveCode = resultMap.get("receiveCode");
			// 获取分享链接
			String path = request.getContextPath();
			String basePath2 = request.getScheme() + "://"
					+ request.getServerName() + path + "/";
			String link = "give/giveReceive?coin=" + coin + "&receiveCode="
					+ receiveCode;
			link = basePath2 + link;
			// String oauth2link = Oauth2Util.getInstance().getOauth2Url(link,
			// null);
			String oauth2link = link;
			resultMap.put("oauth2link", oauth2link);
			// Map<String,String> resultMap = new HashMap<String, String>();
			// String link = "give/giveReceive?coin="+coin+"&receiveCode=''";
			// resultMap.put("oauth2link", link);
			return resultMap;
		}
		return null;
	}
}
