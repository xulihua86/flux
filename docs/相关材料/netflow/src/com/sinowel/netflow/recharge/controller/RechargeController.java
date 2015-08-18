package com.sinowel.netflow.recharge.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.ConfigManager;

@Controller
@RequestMapping(value = "/recharge")
public class RechargeController extends BaseController {
	/*
	 * 进入充值到手机主页 张萌涛 2015-5-11
	 */
	private String interfaceIP;

	@RequestMapping(value = "/toexchange", method = RequestMethod.GET)
	public ModelAndView toexchange() throws Exception {
//		// 获取openid
////		JsToken jstoken = null;
////		String openId = "";
////		try {
////			// 取token
////			jstoken = OpenIdManager.getOpenId(code);
////			openId = jstoken.getOpenid();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
		if(openId==null){
			return new ModelAndView("exchange/exchangeFailure");
		}
		System.out.println("openId is ====="+openId);
//		// 根据openid调用https://192.168.25.95:8443/sfip/community/getFlowAccountsDetail接口，获取流量币
		Map<String, String> map = new HashMap<String, String>();
		String interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceIP = interfaceIP + "/sfip/community/getUserStatus?openId=OPENID";
		String interfaceUrl = interfaceIP.replace("OPENID", openId);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String(responseBody,"utf-8");
		JSONObject jsonobj = new JSONObject(resultStr);
		String returncode=jsonobj.getString("code");
		if("300001".equals(returncode)){
			map.put("availablAmount","0");
			map.put("openid", openId);
			map.put("msg", "还未绑定微信");
			return new ModelAndView("recharge/rechargePhonecoins", map);
		}
		String availablAmount = jsonobj.getString("availableAmount");
//		String availablAmount = "500";
		map.put("availablAmount", availablAmount);
		session.removeAttribute("myId");
		session.setAttribute("myId", openId);
//		map.put("openid", openId);
		return new ModelAndView("recharge/rechargePhonecoins", map);
	}

	/*
	 * 进入充值成功 张萌涛 2015-5-11
	 */
	@RequestMapping(value = "/rechargeSuccess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rechargeSuccess(String phone, String effectType,
			 String operator, String quantity, String flowId,String openId)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		Object myIdsession = session.getAttribute("myId");
		if(myIdsession==null){
			data.put("success", "failse");
			data.put("msg", "2");
			return data;
		}
		String myId = myIdsession.toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		
		
		//判断流量币个数
		String interfaceIPAccounts = ConfigManager.getValue("interfaceIP");
		interfaceIPAccounts = interfaceIPAccounts + "/sfip/community/getUserStatus?openId=OPENID";
		String interfaceAccounts = interfaceIPAccounts.replace("OPENID", myId);
		HttpClient httpClientAccounts = new HttpClient();
		GetMethod getAccounts = new GetMethod(interfaceAccounts);
		int statusCodeAccounts = httpClientAccounts.executeMethod(getAccounts);
		byte[] responseBodyAccounts = getAccounts.getResponseBody();
		String resultStrAccounts = new String(responseBodyAccounts,"utf-8");
		JSONObject jsonobjAccounts = new JSONObject(resultStrAccounts);
		String returncode=jsonobjAccounts.getString("code");
		String availablAmount = jsonobjAccounts.getString("availableAmount");
		
		if(Integer.parseInt(availablAmount)==0||Integer.parseInt(availablAmount)<Integer.parseInt(quantity)){
			data.put("success", "failse");
			data.put("msg", "1");
			return data;
			//return new ModelAndView("recharge/rechargeFailure", data);
		}
		
		
		// 调用充值接口
		// https://192.168.25.95:8443/sfip/community/flowCurrencyRecharge
		String interfaceUrl = "/sfip/community/rechargeByFlowCoin?"
				+ "openId=OPENID" + "&effectType=EFFECTTYPE" + "&phone=PHONE"
				+ "&productId=PRODUCTID";
		String interfaceIP= ConfigManager.getValue("interfaceIP");
		interfaceUrl = interfaceIP + interfaceUrl;
		interfaceUrl = interfaceUrl.replace("OPENID", myId);
		interfaceUrl = interfaceUrl.replace("EFFECTTYPE", effectType);
		interfaceUrl = interfaceUrl.replace("PHONE", phone);
		interfaceUrl = interfaceUrl.replace("PRODUCTID", flowId);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		System.out.println("调用充值接口1");
		int statusCode = httpClient.executeMethod(get);
		System.out.println("调用充值接口2");
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String(responseBody,"utf-8");
		JSONObject jsonobj = new JSONObject(resultStr);
		String code = jsonobj.getString("code");
		if ("111111".equals(code)) {
			data.put("success", "failse");
			data.put("msg", "2");
			return data;
			//return new ModelAndView("recharge/rechargeFailure", data);

		}
		// 判断运营商
		String comname = "";
		comname = "未知运营商";
		if ("cmcc".equals(operator)) {
			comname = "中国移动";

		}
		if ("cucc".equals(operator)) {
			comname = "中国联通";
		}
		if ("ctcc".equals(operator)) {
			comname = "中国电信";
		}

		if ("0".equals(effectType)) {
			data.put("effect_type", "0");
		} else {
			data.put("effect_type", "1");
		}
		data.put("comname", comname);
		data.put("code", code);
		//data.put("payerId", openId);
		data.put("success", "ture");

		return data;
		//return new ModelAndView("record/success", data);
	}
	/*
	 * 充值成功页面跳转
	 * */
	@RequestMapping(value = "/rechargeSuccessTopage", method = RequestMethod.GET)
	public ModelAndView rechargeSuccessTopage(String payerId,String effect_type) {
		Map<String, Object> data = new HashMap<String, Object>();
		Object myIdsession = session.getAttribute("myId");
		if(myIdsession==null){
			System.out.println("未取到openId");
			return new ModelAndView("recharge/rechargeFailure", data);
		}
		String myId = myIdsession.toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		
		
		data.put("payerId", myId);
		if("0".equals(effect_type)){
			
			data.put("effect_type", "立即生效");
		}
		if("1".equals(effect_type)){
			
			data.put("effect_type", "次月生效");
		}
		return new ModelAndView("record/success", data);
	}
	/*
	 * 充值失败页面跳转
	 * */
	@RequestMapping(value = "/rechargeFailTopage", method = RequestMethod.GET)
	public ModelAndView rechargeFailTopage(String msg) {
		Map<String, String> data = new HashMap<String, String>();
		if("1".equals(msg)){
			data.put("msg", "流量币不足，请充值流量币！");
		}
		if("2".equals(msg)){
			data.put("msg", "调用充值接口失败");
		}
		return new ModelAndView("recharge/rechargeFailure", data);
	}

	/*
	 * 选择充值档位
	 */
	@RequestMapping(value = "/selectRange", method = RequestMethod.POST)
	public ModelAndView selectRange(String phone,String openId)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		Object myIdsession = session.getAttribute("myId");
		if(myIdsession==null){
			System.out.println("未取到openId");
			return new ModelAndView("recharge/rechargeFailure", data);
		}
		String myId = myIdsession.toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		
		
		
		String interfaceUrl = "/sfip/community/selectFlowPackGears?openId=OPENID&phone=PHONE";
		String interfaceUrlphone = "/sfip/community/checkOperatorOfMobil?mobile=PHONE";
		//判断运营商
		
		// 调用选择充值档位接口
		interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceUrl = interfaceIP + interfaceUrl;
		interfaceUrl = interfaceUrl.replace("OPENID", myId);
		interfaceUrl = interfaceUrl.replace("PHONE", phone);
		
		interfaceUrlphone = interfaceIP + interfaceUrlphone;
		interfaceUrlphone = interfaceUrlphone.replace("PHONE", phone);
		
		// 接受返回值
		HttpClient httpClient = new HttpClient();
//		判断运营商
		GetMethod get1 = new GetMethod(interfaceUrlphone);
		int statusCode1 = httpClient.executeMethod(get1);
		byte[] responseBody1 = get1.getResponseBody();
		String result1 = new String(responseBody1, "utf-8");
		JSONObject jsonobj1 = new JSONObject(result1);
		if("null".equals(jsonobj1.get("operator").toString())||jsonobj1.get("operator")==null){
			writeJson("{'code':'100000'}");
			return null;
		}
		
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String result = new String(responseBody, "utf-8");
//		 String result ="{'code':'000000','resultList':[{'id':'123456','operator':'移动','flowSize':'3M','flowAmount':'30','chargeBeginDate':'2015-01-04','chargeEndDate':'2015-01-05'}"
//		 		+ ",{'id':'123456','operator':'移动','flowSize':'300M','flowAmount':'300','chargeBeginDate':'2015-01-04','chargeEndDate':'2015-01-05'}"
//		 		+ ",{'id':'123456','operator':'移动','flowSize':'3M','flowAmount':'30','chargeBeginDate':'2015-01-04','chargeEndDate':'2015-01-05'}]}";
		JSONObject jsonobj = new JSONObject(result);
		if (jsonobj.get("code").equals("000000")) {
			String jsonresult = jsonobj.getJSONArray("resultList").toString();
			writeJson(jsonresult);
			return null;
		}
		writeJson(result);
		return null;
	}

}
