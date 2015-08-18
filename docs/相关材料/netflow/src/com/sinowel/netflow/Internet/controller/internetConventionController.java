package com.sinowel.netflow.Internet.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.HttpUtil;

/**
 * 领取码兑换
 * 
 * @author fuhang
 */
@Controller
public class internetConventionController extends BaseController {

	/**
	 * 互联网大会领取码首页
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/InternetConvention/internetConventionCode", method = RequestMethod.GET)
	public ModelAndView exchangeCheckCode()
			throws Exception {
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
		return new ModelAndView("Internet/internetConventionCheckCode", data);
	}

	/**
	 * 领取码验证
	 * 
	 * @param accessCode
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/InternetConvention/checkAccessCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAccessCode(String accessCode, String openId)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		String resultCode = "";

		if (accessCode.contains(" ")) {
			resultCode = "000001";
		} else {
			String ip_url = ConfigManager.getValue("interfaceIP");
			String outputStr = "";
			String httpUrlInternet = ip_url
					+ "/sfip/community/isActivityCode?code=" + accessCode;
			String resultInternet = HttpUtil.httpRequest(httpUrlInternet,
					"POST", outputStr);
			Map<String, Object> dataInternet = new HashMap<String, Object>();
			dataInternet = parseJSON2Map(resultInternet);
			resultCode = (String) dataInternet.get("code");

			if ("100000".equalsIgnoreCase(resultCode)) {
				String httpUrl = ip_url
						+ "/sfip/community/verifyReceiveCode?receiveCode=" + accessCode
						+ "&openId=" + openId;
				String result = HttpUtil
						.httpRequest(httpUrl, "POST", outputStr);
				Map<String, Object> dataDetail = new HashMap<String, Object>();
				dataDetail = parseJSON2Map(result);
				resultCode = (String) dataDetail.get("code");
			}
		}

		if ("000000".equalsIgnoreCase(resultCode)) {// 领取码正确
			data.put("success", "success");
		} else if ("311003".equalsIgnoreCase(resultCode)) {// 领取码过期
			data.put("success", "亲，您的领取码已过期，请咨询发码方了解详情");
		} else if ("311001".equalsIgnoreCase(resultCode)) {// 领取码已用
			data.put("success", "亲，您的领取码已使用过，请咨询发码方了解详情");
		} else {// 领取码错误
			data.put("success", "亲，领取码错误或为非活动领取码，请重新输入");
		}
		return data;
	}

	/**
	 * 领取码成功页面跳转
	 * 
	 * @param accessCode
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/InternetConvention/exchangeCodeSuccess", method = RequestMethod.GET)
	public ModelAndView exchangeSuccess(String accessCode, String openId) {
		String resultCode = "";

		Map<String, Object> data = new HashMap<String, Object>();

		if (accessCode.contains(" ")) {
			resultCode = "000001";
		} else {
			String ip_url = ConfigManager.getValue("interfaceIP");
			String outputStr = "";
			String httpUrlInternet = ip_url
					+ "/sfip/community/isActivityCode?code=" + accessCode;
			String resultInternet = HttpUtil.httpRequest(httpUrlInternet,
					"POST", outputStr);
			Map<String, Object> dataInternet = new HashMap<String, Object>();
			dataInternet = parseJSON2Map(resultInternet);
			resultCode = (String) dataInternet.get("code");

			if ("100000".equalsIgnoreCase(resultCode)) {
				// 拼写httpurl
				String httpUrl = ip_url
						+ "/sfip/community/verifyReceiveCode?receiveCode=" + accessCode
						+ "&openId=" + openId;
				// 调用接口方法
				String result = HttpUtil
						.httpRequest(httpUrl, "POST", outputStr);
				Map<String, Object> dataDetail = new HashMap<String, Object>();
				dataDetail = parseJSON2Map(result);

				String resultDetail = "";
				resultDetail = String.valueOf(dataDetail.get("result"));
				resultDetail = resultDetail.substring(1,
						resultDetail.length() - 1);
				data = parseJSON2Map(resultDetail);
				
				resultCode = (String) dataDetail.get("code");
			}
		}

		data.put("openId", openId);
		
		if ("000000".equalsIgnoreCase(resultCode)) {
			return new ModelAndView("Internet/internetConventionPhoneData",data);
		} else {
			return new ModelAndView("Internet/internetConventionPhoneDataFailure", data);
		}
	}

	/**
	 * 判断手机运营商
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/InternetConvention/exchangePhoneType", method = RequestMethod.POST)
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
	 * @param code
	 * @param openId
	 * @param phone
	 * @param activeTypeId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/InternetConvention/exchangePhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangePhone(String openId, String phone,
			String code, String productId) throws Exception {

		String resultCode = "";

		String ip_url = ConfigManager.getValue("interfaceIP");
		String outputStr = "";
		String httpUrlInternet = ip_url
				+ "/sfip/community/isActivityCode?code=" + code;
		String resultInternet = HttpUtil.httpRequest(httpUrlInternet,
				"POST", outputStr);
		Map<String, Object> dataInternet = new HashMap<String, Object>();
		dataInternet = parseJSON2Map(resultInternet);
		resultCode = (String) dataInternet.get("code");

		if ("100000".equalsIgnoreCase(resultCode)) {
			// 拼写httpurl
			String httpUrl = ip_url
					+ "/sfip/community/receiveCodeFlowRechargeMobileCmcc?code="
					+ code + "&openId=" + openId + "&mobile=" + phone
					+ "&activeTypeId=0";

			// 调用接口方法
			String result = HttpUtil.httpRequest(httpUrl, "POST", outputStr);
			Map<String, Object> dataDetail = new HashMap<String, Object>();
			dataDetail = parseJSON2Map(result);

			// 取得结果
			resultCode = (String) dataDetail.get("code");
		}

		Map<String, Object> data = new HashMap<String, Object>();
		if ("000000".equalsIgnoreCase(resultCode)) {// 充值到手机成功
			data.put("success", "ture");
		} else {// 充值到手机失败
			data.put("success", "failse");
		}
		return data;
	}

	/**
	 * 充值到手机提交页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/InternetConvention/internetConventionPhoneDataSuccess", method = RequestMethod.GET)
	public ModelAndView phoneDataSuccess(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("Internet/internetConventionPhoneDataSuccess", data);
	}

	/**
	 * 充值到手机失败页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/InternetConvention/internetConventionPhoneDataFailure", method = RequestMethod.GET)
	public ModelAndView phoneDataFailure(String openId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("openId", openId);
		return new ModelAndView("Internet/internetConventionPhoneDataFailure", data);
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
