package com.sinowel.netflow.exchange.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.card.util.CardUtil;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.MD5;

/**
 * 领取码兑换
 * 
 * @author fuhang
 */
@Controller
public class exchangeCodeController extends BaseController {

	/**
	 * 领取码首页
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/exchangeCheckCode", method = RequestMethod.GET)
	public ModelAndView exchangeCheckCode() throws Exception {
//		JsToken jstoken = null;
//		String openId = "";
//		try {
//			// 取token
//			jstoken = OpenIdManager.getOpenId(code);
//			openId = jstoken.getOpenid();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// String openId = "oDa1KuJj370Eoi3W04qmr9k9Lr9Y";// 临时模拟数据
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeCheckCode", data);
	}

	/**
	 * 领取码验证
	 * 
	 * @param accessCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/checkAccessCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAccessCode(String accessCode, String openId) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		String resultCode = "";

		if (accessCode.contains(" ")) {
			resultCode = "000001";
		} else {
			// 拼写httpurl
			String ip_url = ConfigManager.getValue("interfaceIP");
			String httpUrl = ip_url + "/sfip/community/verifyReceiveCode?receiveCode="
					+ accessCode + "&openId=" + openId;

			// 调用接口方法
//			String outputStr = "";
			resultMap = this.getDataFromURL(httpUrl);
			
			// 取得验证结果码
			resultCode = (String) resultMap.get("code");
//			String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
//			Map<String, Object> dataDetail = new HashMap<String, Object>();
//			dataDetail = parseJSON2Map(result);
//
//			// 取得验证结果码
//			resultCode = (String) dataDetail.get("code");
		}

		if ("000000".equalsIgnoreCase(resultCode)) {// 领取码正确
			data.put("success", "success");
		} else if ("311003".equalsIgnoreCase(resultCode)) {// 领取码过期
			data.put("success", "亲，您的领取码已过期，请咨询发码方了解详情");
		} else if ("311001".equalsIgnoreCase(resultCode)) {// 领取码已用
			data.put("success", "亲，您的领取码已使用，请咨询发码方了解详情");
		} else if ("311006".equalsIgnoreCase(resultCode)) {// 领取码已用
			data.put("success", "亲，不可领取用户自己赠送的领取码");
		} else {// 领取码错误
			data.put("success", "亲，领取码错误，请重新输入");
		}
		return data;
	}

	/**
	 * 领取码成功页面跳转
	 * 
	 * @param accessCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/exchangeCodeSuccess", method = RequestMethod.GET)
	public ModelAndView exchangeSuccess(String accessCode, String openId) {
		Map<String, Object> data = new HashMap<String, Object>();
		String resultCode = "";
		if (accessCode.contains(" ")) {
			resultCode = "000001";
		} else {
			// 拼写httpurl
			String ip_url = ConfigManager.getValue("interfaceIP");
			String httpUrl = ip_url
					+ "/sfip/community/verifyReceiveCode?receiveCode="
					+ accessCode + "&openId=" + openId;
			// 调用接口方法
			String outputStr = "";
			String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
			Map<String, Object> dataDetail = new HashMap<String, Object>();
			dataDetail = parseJSON2Map(result);

			String resultDetail = "";
			resultDetail = String.valueOf(dataDetail.get("result"));
			resultDetail = resultDetail.substring(1, resultDetail.length() - 1);
			data = parseJSON2Map(resultDetail);

			resultCode = (String) dataDetail.get("code");
			data.put("code", resultCode);
			// data.put("type", (String) dataDetail.get("type"));
		}
		data.put("openId", openId);
		if ("000000".equalsIgnoreCase(resultCode)) {
			return new ModelAndView("exchange/exchangeCodeSuccess", data);
		} else {
			return new ModelAndView("exchange/exchangeFailure", data);
		}
	}

	/**
	 * 领取码过期页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeCodeExpired", method = RequestMethod.GET)
	public ModelAndView exchangeExpired(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeCodeExpired", data);
	}

	/**
	 * 领取码使用页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeCodeUsed", method = RequestMethod.GET)
	public ModelAndView exchangeUsed(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeCodeUsed", data);
	}

	/**
	 * 领取码失败页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeCodeFailure", method = RequestMethod.GET)
	public ModelAndView exchangeFailure(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeCodeFailure", data);
	}

	/**
	 * 存到流量账户成功页面跳转
	 * 
	 * @param quantity
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeFlowCoinSuccess", method = RequestMethod.GET)
	public ModelAndView flowCoinSuccess(String quantity, String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("quantity", quantity);
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeFlowCoinSuccess", data);
	}

	/**
	 * 存到流量账户失败页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeFlowCoinFailure", method = RequestMethod.GET)
	public ModelAndView flowCoinFailure(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangeFlowCoinFailure", data);
	}

	/**
	 * 充值到手机页面跳转
	 * 
	 * @param accessCode
	 * @param operatorType
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangePhoneData", method = RequestMethod.GET)
	public ModelAndView phoneData(String accessCode,String operatorType, String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("accessCode", accessCode);
		data.put("operatorType", operatorType);
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangePhoneData", data);
	}

	/**
	 * 判断手机运营商
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/exchangePhoneType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangePhoneType(String phone) throws Exception {
		// 拼写httpurl
		String ip_url = ConfigManager.getValue("interfaceIP");
		String httpUrl = ip_url
				+ "/sfip/community/checkOperatorOfMobil?mobile=" + phone;

		// 调用接口方法
		String outputStr = "";
		String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = parseJSON2Map(result);

		// 取得结果
		String operatorType = "";
		if ("null".equalsIgnoreCase(dataDetail.get("operator").toString())
				|| dataDetail.get("operator") == null) {
			operatorType = "";
		} else {
			operatorType = (String) dataDetail.get("operator");
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("operatorType", operatorType);
		return data;
	}

	/**
	 * 充值到手机
	 * 
	 * @param accessCode
	 * @param activateType
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/exchangePhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangePhone(String phone, String openId,
			String activateType, String accessCode)
			throws Exception {
		// 拼写httpurl
		// openId = "oDa1KuJj370Eoi3W04qmr9k9Lr9Y";// 临时模拟数据
		String ip_url = ConfigManager.getValue("interfaceIP");
		String httpUrl = ip_url
				+ "/sfip/community/rechargeByReceiveCode?receiveCode="
				+ accessCode + "&openId=" + openId + "&mobile=" + phone
				+ "&activateType=" + activateType;
		// 调用接口方法
		String outputStr = "";
		String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = parseJSON2Map(result);

		// 取得结果
		String resultCode = "";
		resultCode = (String) dataDetail.get("code");

		Map<String, Object> data = new HashMap<String, Object>();
		if ("000000".equalsIgnoreCase(resultCode)) {// 存到流量账户成功
			data.put("success", "ture");
		} else {// 存到流量账户失败
			data.put("success", "failse");
		}
		return data;
	}

	/**
	 * 充值到手机提交页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangePhoneDataSuccess", method = RequestMethod.GET)
	public ModelAndView phoneDataSuccess(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangePhoneDataSuccess", data);
	}

	/**
	 * 充值到手机提交页面(下月)跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangePhoneDataSuccessN", method = RequestMethod.GET)
	public ModelAndView phoneDataSuccessN(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangePhoneDataSuccessN", data);
	}

	/**
	 * 充值到手机失败页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangePhoneDataFailure", method = RequestMethod.GET)
	public ModelAndView phoneDataFailure(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("exchange/exchangePhoneDataFailure", data);
	}

	/**
	 * 存到流量账户
	 * 
	 * @param accessCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange/exchangeFlowCoin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangeFlowCoin(String accessCode, String openId) throws Exception {
		// 拼写httpurl
		// openId = "oDa1KuJj370Eoi3W04qmr9k9Lr9Y";// 临时模拟数据
		String ip_url = ConfigManager.getValue("interfaceIP");
		String httpUrl = ip_url
				+ "/sfip/community/despositByReceiveCode?receiveCode="
				+ accessCode + "&openId=" + openId;

		// 调用接口方法
		String outputStr = "";
		String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = parseJSON2Map(result);

		// 取得结果
		String resultCode = "";
		resultCode = (String) dataDetail.get("code");

		Map<String, Object> data = new HashMap<String, Object>();
		if ("000000".equalsIgnoreCase(resultCode)) {// 存到流量账户成功
			data.put("success", "ture");
		} else {// 存到流量账户失败
			data.put("success", "failse");
		}
		return data;
	}

	/**
	 * 兑换微信卡券页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exchange/exchangeWechatCard", method = RequestMethod.GET)
	public ModelAndView exchangeWechatCard(HttpServletRequest request,
			String wechatCardId, String accessCode, String openId) throws Exception {
		// 拼写httpurl
		// openId = "oDa1KuJj370Eoi3W04qmr9k9Lr9Y";// 临时模拟数据
		String ip_url = ConfigManager.getValue("interfaceIP");
		String wechatCardCode = MD5.Md5(
				String.format("%020d", System.nanoTime())).toUpperCase();
		String httpUrl = ip_url
				+ "/sfip/community/exchangeWXCardByReceiveCode?receiveCode="
				+ accessCode + "&openId=" + openId + "&wxCardCode="
				+ wechatCardCode;
		// 调用接口方法
		String outputStr = "";
		String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
		Map<String, Object> dataDetail = new HashMap<String, Object>();
		dataDetail = parseJSON2Map(result);

		// 取得结果
		String resultCode = "";
		resultCode = (String) dataDetail.get("code");

		if ("000000".equalsIgnoreCase(resultCode)) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("qrcodeUrl", CardUtil.createQRCode(wechatCardId, wechatCardCode));
			return new ModelAndView("exchange/exchangeWechatCard", data);
		} else {
			return new ModelAndView("exchange/exchangePhoneDataFailure");
		}

	}

	// parseJSON2Map
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			map.put(k.toString(), v);
		}
		return map;
	}
}
