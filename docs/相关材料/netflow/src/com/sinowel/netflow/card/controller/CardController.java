package com.sinowel.netflow.card.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.card.model.CardConsume;
import com.sinowel.netflow.card.util.CardUtil;
import com.sinowel.netflow.card.util.ResultConsume;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.JsonUtil;
import com.sinowel.netflow.common.MD5;
import com.sinowel.netflow.common.SignatureForNetFlow;

/**
 * 优惠券充值
 * 
 * @author wanglei
 */
@RequestMapping(value = "cards")
@Controller
public class CardController extends BaseController {

	private static Logger logger = Logger.getLogger(CardController.class);

	/**
	 * 页面跳转到输入手机号和生效类型页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "phoneAndEffecttype", method = RequestMethod.GET)
	public ModelAndView phoneAndEffecttype(HttpServletRequest request,
			HttpServletResponse response, String encrypt_code, String card_id,
			String signature, String openid) throws Exception {
		session.removeAttribute("myId");
		session.setAttribute("myId", openid);
		System.out.println("encrypt_code=========="+encrypt_code);
		System.out.println("card_id=========="+card_id);
		System.out.println("signature=========="+signature);
		System.out.println("openid=========="+openid);
		
		Map<String, String> map = new HashMap<String, String>();
		// 解密微信传来的的card_code
//		String card_code = CardUtil.getCode(encrypt_code);
		String card_code=CardUtil.getCode(encrypt_code);
		//发现黑卡，核销卡券
		String interfaceUrl = "/sfip/community/isBlackCard?wxCode=WXCODE&openId=OPENID";
		interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceUrl = interfaceIP + interfaceUrl;
		interfaceUrl = interfaceUrl.replace("WXCODE", card_code);
		interfaceUrl = interfaceUrl.replace("OPENID", openid);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String(responseBody, "utf-8");
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = (Map<String, Object>) JsonUtil.getInstance().toMap(resultStr);
		String code = (String) dataDetail.get("code");
		System.out.println("输出调用接口开始");
		System.out.println("code======="+code);
		System.out.println("输出调用接口结束");
		if("111111".equals(code)){
			CardConsume card = new CardConsume();
			card.setCard_id(card_id);
			card.setCode(card_code);
			ResultConsume resultconsume = CardUtil.consumeCard(card);
			System.out.println("核销卡券开始");
			System.out.println("resultconsume返回码========"+resultconsume.getErrcode());
			System.out.println("核销卡券结束");
			if ("40099".equals(resultconsume.getErrcode())) {
				map.put("msg", "此卡券已经失效，微信核销卡券失败。");
				return new ModelAndView("recharge/rechargeFailure", map);
			}
			map.put("msg","此卡券已经失效，已被核销！");
			return new ModelAndView("recharge/rechargeFailure", map);
		}
		
		System.out.println("返回微信解密的code========"+card_code);
		System.out.println("返回结束");
		map.put("card_code", card_code);
		map.put("card_id", card_id);
		String myId = session.getAttribute("myId").toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		session.setAttribute("openId", openid);
//		map.put("openId", openid);
		//return new ModelAndView("recharge/rechargePhone", map);
		
		//调用接口获取卡券相关信息
		String url = interfaceIP+"/sfip/community/verifyCard?openId=@openId&cardCode=@cardCode" //&cardId=@cardId
				.replaceAll("@openId", openid)
				//.replaceAll("@cardId", card_id)
				.replaceAll("@cardCode", card_code);
		resultMap = this.getDataFromURL(url);
		resultMap.putAll(map);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^ phoneAndEffecttype begin ^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(map);
		System.out.println(resultMap);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^ phoneAndEffecttype end ^^^^^^^^^^^^^^^^^^^^^^^^");
		
		String resultCode = String.valueOf(resultMap.get("code"));
		String operateMessage = "";
		if("320008".equals(resultCode)){ //非本人操作
			
			operateMessage="非本人操作";
			resultMap.put("operateMessage", operateMessage);
			return new ModelAndView("user/toCardError", resultMap);
			
		}else if("320001".equals(resultCode)){ //参数不对
			
			operateMessage="参数不对";
			resultMap.put("operateMessage", operateMessage);
			return new ModelAndView("user/toCardError", resultMap);
			
		}else if("111111".equals(resultCode)){ //系统异常
			
			operateMessage="系统异常";
			resultMap.put("operateMessage", operateMessage);
			return new ModelAndView("user/toCardError", resultMap);
			
		}else{
			return new ModelAndView("user/toWechatTicketOperate", resultMap);     //跳转到卡券操作页面(充值到手机、兑换流量币)
		}
		
//		if("000000".equals(resultCode)){ //正常
//		}
		
	}
	
	/**
	 * 跳转到充值到手机界面
	 * @param request
	 * @param response
	 * @param card_id
	 * @param card_code
	 * @param openId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toRechargePhone", method = RequestMethod.POST)
	public ModelAndView toRechargePhone(HttpServletRequest request,
			HttpServletResponse response, String card_id,String card_code,String openId) throws Exception {
		String myId = session.getAttribute("myId").toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
	
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ run:toRechargePhone begin ^^^^^^^^^^^^^^^^^^");
		System.out.println("card_id=========="+card_id);
		System.out.println("card_code=========="+card_code);
		System.out.println("openid=========="+myId);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("card_code", card_code);
		map.put("card_id", card_id);
		map.put("openId", openId);
		
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ run:toRechargePhone end ^^^^^^^^^^^^^^^^^^");
		return new ModelAndView("recharge/rechargePhone", map);
		
	}

	/**
	 * 充值
	 * 
	 * @param openId
	 * @param card_id
	 *            卡券id
	 * @param encrypt_code卡券code
	 * @param phone
	 *            手机号
	 * @param effective_date
	 *            0当月生效,1下月生效
	 * @return
	 */
	@RequestMapping(value = "/consume", method = RequestMethod.GET)
	public ModelAndView consume(String openId, String card_id,
			String card_code, String phone, String effect_type, String operatorType)
			throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		Object myIdsession = session.getAttribute("myId");
		if(myIdsession==null){
			System.out.println("未取到openId");
			return new ModelAndView("recharge/rechargeFailure", data);
		}
		
		String myId = myIdsession.toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		
//		String interfaceUrlTypeCheck = "/sfip/community/mobileOperatorTypeCheck?mobile=MOBILE";
//		String interfaceIPTypeCheck = ConfigManager.getValue("interfaceIP");
//		interfaceUrlTypeCheck = interfaceIPTypeCheck + interfaceUrlTypeCheck;
//		interfaceUrlTypeCheck = interfaceUrlTypeCheck.replace("MOBILE", phone);
//		HttpClient httpClientTypeCheck = new HttpClient();
//		GetMethod getTypeCheck = new GetMethod(interfaceUrlTypeCheck);
//		int statusCodeTypeCheck = httpClientTypeCheck.executeMethod(getTypeCheck);
//		byte[] responseBodyTypeCheck = getTypeCheck.getResponseBody();
//		String resultStrTypeCheck = new String(responseBodyTypeCheck,"utf-8");
//		System.out.println(resultStrTypeCheck);
//		JSONObject jsonobj = new JSONObject(resultStrTypeCheck);
//		if("000000".equals(jsonobj.get("code").toString())){
//			operatorType=(String) jsonobj.get("operatorType");
//		}else{
//			data.put("msg", "您输入的手机号不是正确的运营商");
//			return new ModelAndView("recharge/rechargeFailure", data);
//		}
		System.out.println("phone======="+phone);
		System.out.println("openId======="+myId);
		System.out.println("card_id======="+card_id);
		System.out.println("card_code======="+card_code);
		System.out.println("operatorType======="+operatorType);
		System.out.println("effect_type======="+effect_type);
		// 调用微信卡券充值到手机接口
		String interfaceUrl = "/sfip/community/rechargeByWxCard?wxCardId=WXID&openId=OPENID&mobile=MOBILE&activateType=ACTIVETYPEID&wxCardCode=WXCODE";
		interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceUrl = interfaceIP + interfaceUrl;
		interfaceUrl = interfaceUrl.replace("WXID", card_id);
		interfaceUrl = interfaceUrl.replace("OPENID", myId);
		interfaceUrl = interfaceUrl.replace("MOBILE", phone);
		interfaceUrl = interfaceUrl.replace("ACTIVETYPEID", effect_type);
		interfaceUrl = interfaceUrl.replace("WXCODE", card_code);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String(responseBody, "utf-8");
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = (Map<String, Object>) JsonUtil.getInstance().toMap(resultStr);
		String code = (String) dataDetail.get("code");
		System.out.println("输出调用接口开始");
		System.out.println("code======="+code);
		System.out.println("输出调用接口结束");

//		// 调用充值接口
		if ("0".equals(effect_type)) {
			data.put("effect_type", "立即生效");
		} else {
			data.put("effect_type", "次月生效");
		}
//		String comname="未知运营商";
//		if(operatorType.equals("ctcc")){
//			comname = "中国电信";
//			data.put("comname", comname);
//		}
//		if(operatorType.equals("cucc")){
//			comname = "中国联通";
//			data.put("comname", comname);
//		}
//		if(operatorType.equals("cmcc")){
//			comname = "中国移动";
//			data.put("comname", comname);
//		}
		if ("000000".equals(code)) {
			// 核销卡券
			CardConsume card = new CardConsume();
			card.setCard_id(card_id);
			card.setCode(card_code);
			ResultConsume resultconsume = CardUtil.consumeCard(card);
			System.out.println("核销卡券开始");
			System.out.println("resultconsume返回码========"+resultconsume.getErrcode());
			System.out.println("核销卡券结束");
			if ("40099".equals(resultconsume.getErrcode())) {
				data.put("msg", "微信核销卡券失败");
				return new ModelAndView("recharge/rechargeFailure", data);
			}
//			data.put("payerId",openId);
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			return new ModelAndView("record/success", data);
		}else if("320004".equals(code)){
			data.put("msg", "该卡券不能给此手机号充值");
			return new ModelAndView("recharge/rechargeFailure", data);
		}else{
			data.put("success", "fail");
			data.put("msg", "调用充值接口失败");
			return new ModelAndView("recharge/rechargeFailure", data);
		}

	}

	/**
	 * 页面跳转兑换微信卡券 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "coupons", method = RequestMethod.GET)
	public ModelAndView coupons(HttpServletRequest request,
			HttpServletResponse response, String code, String state)
			throws Exception {
		session.removeAttribute("myId");
		session.setAttribute("myId", openId);
		 // 调用查询流量币接口
//		 System.out.println("openId is ====="+openId);
		 Map<String, Object> dataDetail = new HashMap<String, Object>();
//		// 根据openid调用https://192.168.25.95:8443/sfip/community/getWXCardExchangeMessage
		 Map<String, String> map = new HashMap<String, String>();
		 String wxid = ConfigManager.getValue("wechat.appId");
		
		 String interfaceIP = ConfigManager.getValue("interfaceIP");
		 interfaceIP = interfaceIP +
		 "/sfip/community/selectWXCardInfos?openId=OPENID&wxId=WXID";
		 String interfaceUrl = interfaceIP.replace("OPENID", openId);
		 interfaceUrl = interfaceUrl.replace("WXID", wxid);
		 HttpClient httpClient = new HttpClient();
		 GetMethod get = new GetMethod(interfaceUrl);
		 int statusCode = httpClient.executeMethod(get);
		 byte[] responseBody = get.getResponseBody();
		 String weixinresult = new String(responseBody,"utf-8");
//		 String weixinresult = "{\"availableAmount\":\"125\",\"resultList\":[{\"wxProductId\":50,\"name\":\"三网流量包 飞流3元流量卡\",\"description\":\"移动10M/联通20M/电信10M\",\"wxCardId\":\"pRMqosxppVCiWw6HEfyBFgT5IU9E\",\"flowProductId\":null,\"operatorTypes\":\"CMCC CUCC CTCC\",\"sellPrice\":15,\"id\":50}"
//		 		+ ",{\"wxProductId\":50,\"name\":\"三网流量包 飞流3元流量卡\",\"description\":\"移动10M/联通20M/电信10M\",\"wxCardId\":\"pRMqosxppVCiWw6HEfyBFgT5IU9E\",\"flowProductId\":null,\"operatorTypes\":\"CMCC CUCC CTCC\",\"sellPrice\":15,\"id\":50}"
//		 		+ ",{\"wxProductId\":50,\"name\":\"三网流量包 飞流3元流量卡\",\"description\":\"移动10M/联通20M/电信10M\",\"wxCardId\":\"pRMqosxppVCiWw6HEfyBFgT5IU9E\",\"flowProductId\":null,\"operatorTypes\":\"CMCC CUCC CTCC\",\"sellPrice\":15,\"id\":50}]}";
//		 String weixinresult = "{\"availableAmount\":\"125\",\"resultList\":[]}";
		 dataDetail = (Map<String, Object>) JsonUtil.getInstance().toMap(
				weixinresult);
		 String weixincode = MD5.Md5(String.format("%020d", System.nanoTime()))
				.toUpperCase();
		 dataDetail.put("weixincode", weixincode);
//		 dataDetail.put("openId", openId);
		 return new ModelAndView("card/coupons", dataDetail);
	}

	/**
	 * 页面跳转二维码领取卡页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "getcard", method = RequestMethod.GET)
	public ModelAndView getcard(HttpServletRequest request,
			HttpServletResponse response, String cardId, String code,
			String openId,String wxProductId,String coins) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		Object myIdsession = session.getAttribute("myId");
		if(myIdsession==null){
			System.out.println("未取到openId");
			return new ModelAndView("card/cardGetcard", data);
		}
		String myId = myIdsession.toString();
		session.removeAttribute("myId");
		session.setAttribute("myId", myId);
		
		// 获取二维码
		System.out.println(cardId);
		System.out.println(code);
		System.out.println(myId);
		System.out.println(wxProductId);
		String requestURL = request.getRequestURL().toString();
		String params = request.getQueryString();
		if(!"".equals(params)&&params!=null){
			requestURL = requestURL+"?"+params;
		}
		Map<String,String> signdata = SignatureForNetFlow.getInstance().sign(requestURL);
		data.putAll(signdata);
		System.out.println("data=============="+data);
		System.out.println("signdata=============="+signdata);
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
		
		if(Integer.parseInt(availablAmount)==0||Integer.parseInt(availablAmount)<Integer.parseInt(coins)){
			data.put("qrcodeUrl", CardUtil.createQRCode(cardId, code));
			return new ModelAndView("card/cardGetcard", data);
		}
		
		// 根据openid调用https://192.168.25.95:8443/sfip/community/coinsToweixinCardInfoSave
				 String interfaceIP = ConfigManager.getValue("interfaceIP");
				 interfaceIP = interfaceIP +
				 "/sfip/community/exchangeWXCardByCoins?openId=OPENID&productId=PRODUCTID&wxCardCode=WEIXINCODE";
				 String interfaceUrl = interfaceIP.replace("OPENID", myId);
				 interfaceUrl = interfaceUrl.replace("PRODUCTID", wxProductId);
				 
				 interfaceUrl = interfaceUrl.replace("WEIXINCODE", code);
				 System.out.println("PRODUCTID======"+wxProductId);
				
				 System.out.println("WEIXINCODE======"+code);
				 System.out.println("OPENID======"+openId);
				 HttpClient httpClient = new HttpClient();
				 GetMethod get = new GetMethod(interfaceUrl);
				 int statusCode = httpClient.executeMethod(get);
				 System.out.println("输出返回状态码");
				 System.out.println("statusCode======"+statusCode);
				 System.out.println("输出返回状态码结束");
				 byte[] responseBody = get.getResponseBody();
				 String weixinresult = new String(responseBody,"utf-8");
				 System.out.println("输出调用接口返回码");
				 System.out.println("weixinresult========"+weixinresult);
				 Map<String, String> dataDetail = new HashMap<String, String>();
					dataDetail = (Map<String, String>) JsonUtil.getInstance().toMap(weixinresult);
				System.out.println(dataDetail);	
				String resultcode=dataDetail.get("code");
				System.out.println(resultcode);
				if("111111".equals(resultcode)){
					data.put("msg", "调用流量币兑换微信卡券接口失败，无法显示二维码！");
					return new ModelAndView("card/cardGetcard", data);
				}
				if("111112".equals(resultcode)){
					data.put("qrcodeUrl", CardUtil.createQRCode(cardId, code));
					return new ModelAndView("card/cardGetcard", data);
				}
				if("111113".equals(resultcode)){
					data.put("msg", "抱歉，您领取的卡券的流量币个数不正确！");
					return new ModelAndView("card/cardGetcard", data);
				}
				if("320007".equals(resultcode)){
					data.put("msg", "抱歉，您的流量币余额不足，请及时充值！");
					return new ModelAndView("card/cardGetcard", data);
				}

				
		data.put("qrcodeUrl", CardUtil.createQRCode(cardId, code));

		return new ModelAndView("card/cardGetcard", data);
	}
	/**
	 * 返回手机运营商类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "mobileOperatorTypeCheck", method = RequestMethod.POST)
	public ModelAndView mobileOperatorTypeCheck(HttpServletRequest request,
			HttpServletResponse response,String phone)throws Exception {
		//杩愯惀鍟�
		String interfaceUrl = "/sfip/community/checkOperatorOfMobil?mobile=MOBILE";
		interfaceIP = ConfigManager.getValue("interfaceIP");
		interfaceUrl = interfaceIP + interfaceUrl;
		interfaceUrl = interfaceUrl.replace("MOBILE", phone);
		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod(interfaceUrl);
		int statusCode = httpClient.executeMethod(get);
		byte[] responseBody = get.getResponseBody();
		String resultStr = new String(responseBody,"utf-8");
		System.out.println(resultStr);
		JSONObject jsonobj = new JSONObject(resultStr);
		if("000000".equals(jsonobj.get("code").toString())){
			writeJson(resultStr);
		}else{
			writeJson("{'operatorType':'error'}");
		}
		
		return null;
	}
}
