package com.sinowel.netflow.record.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.HttpGetUtil;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.JsonUtil;
import com.sinowel.netflow.common.OpenIdManager;
import com.sinowel.netflow.common.PropertiesUtil;
import com.sinowel.netflow.common.model.JsToken;

/**
 * 充值记录
 * @author wanglei
 */
@SuppressWarnings("unchecked")
@Controller
public class ChargeRecordController extends BaseController{

    private static Logger logger = Logger.getLogger(ChargeRecordController.class);

    /**
     * 页面跳转到充值记录页面
     * @return
     */
    @RequestMapping(value = "chargeRecord", method = RequestMethod.GET)
    public ModelAndView chargeRecord(String payerId) {
    	session.removeAttribute("myId");
		session.setAttribute("myId", openId);
        Map<String, Object> data = new HashMap<String, Object>();
//        if(!"".equals(payerId)&&payerId!=null){
//        	data.put("payerId", myId);
//        }else{
//        	data.put("payerId", openId);
//        }
        //获取充值记录
        String url = PropertiesUtil.getProperty("interfaceIP") + "/sfip/community/getFlowRecharge?openId=PAYERID&start=START&length=LENGTH";
        url = url.replace("PAYERID", openId).replace("START", "0").replace("LENGTH", "10");
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        List list = new ArrayList();
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus is " + statusCode);
			}
			byte[] responseBody = getMethod.getResponseBody();
			String result = new String(responseBody,"UTF-8");
//			String result = "{\"result\":[{\"modifyTime\":\"2015-05-15 15:20:22\",\"id\":\"223\",\"status\":\"3\",\"activeTypeId\":\"0\",\"recchargeMobileNo\":\"18602474791\",\"rechargeSnap\":\"微信卡券充值30M\",\"operatorType\":\"cmcc\",\"sourceType\":\"2\"},{\"modifyTime\":\"2015-05-15 15:20:22\",\"id\":\"223\",\"status\":\"3\",\"activeTypeId\":\"0\",\"recchargeMobileNo\":\"18602474791\",\"rechargeSnap\":\"微信卡券充值30M\",\"operatorType\":\"cmcc\",\"sourceType\":\"2\"}],\"code\":\"000000\"}";
			System.out.println("first time interface used: " + result);
			Map<String, Object> resultMap = (Map<String, Object>) JsonUtil.getInstance().toMap(result);
			list = (List) resultMap.get("resultList");
			data.putAll(resultMap);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
        	// 释放连接
        	getMethod.releaseConnection();
        }
		if(list==null || list.size()==0){
			return new ModelAndView("record/noChargeRecord");
		}else{
			return new ModelAndView("record/chargeRecords", data);
		}
    }

    /**
     * 查询充值记录列表
     * @param openId
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "chargeRecord/getList", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> getList(String start, String length) {
		System.out
				.println("get in the second method named getList for data of list");
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		Map<String, Object> data = new HashMap<String, Object>();
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			String url = PropertiesUtil.getProperty("interfaceIP")
					+ "/sfip/community/getFlowRecharge?openId=PAYERID&start=START&length=LENGTH";
			url = url.replace("PAYERID", myId).replace("START", start)
					.replace("LENGTH", length);
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(url);
			try {
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("HttpStatus is " + statusCode);
				}

				byte[] responseBody = getMethod.getResponseBody();
				// 处理内容
				String result = new String(responseBody, "UTF-8");
				System.out.println("second time interface used: " + result);
				// String result =
				// "{\"result\":[{\"orderId\":\"800\",\"modifyTime\":\"2015-05-15 15:20:22\",\"status\":\"success\",\"recchargeMobileNo\":\"18602474791\",\"rechargeSnap\":\"微信卡券充值30M\",\"activeTypeId\":\"立即生效\"},{\"orderId\":\"900\",\"modifyTime\":\"2015-05-15 15:20:22\",\"status\":\"failed\",\"recchargeMobileNo\":\"18602474791\",\"rechargeSnap\":\"微信卡券充值30M\",\"activeTypeId\":\"立即生效\"}],\"code\":\"000000\"}";
				// System.out.println(java.net.URLDecoder.decode(getMethod.getResponseBodyAsString(),
				// "ISO-8859-1"));
				Map<String, Object> resultMap = (Map<String, Object>) JsonUtil
						.getInstance().toMap(result);
				List list = (List) resultMap.get("resultList");
				if (list != null && list.size() > 0) {
					data.put("result", list);
					data.put("status", "success");
				} else {
					data.put("status", "false");
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 释放连接
				getMethod.releaseConnection();
			}
			return data;
		}
		return data;
    }

    /**
     * 页面失败页面
     * @return
     */
    @RequestMapping(value = "chargeRecord/noChargeRecord", method = RequestMethod.GET)
    public ModelAndView noChargeRecord() {
        return new ModelAndView("record/noChargeRecord");
    }

    /**
     * 跳转到失败页面
     * @param openId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "chargeRecord/getOne", method = RequestMethod.GET)
	public ModelAndView getOne(String content, String modifyTime,
			String status, String recchargeMobileNo, String rechargeSnap,
			String activeTypeName, String orderId, String activeTypeId) {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			try {
				if (StringUtils.isNotEmpty(content)) {
					content = java.net.URLDecoder.decode(content, "UTF-8");
					Map<String, String> map = new HashMap<String, String>();
					// String[] array = content.split("\\|");
					String[] array = content.split(",");
					for (int i = 0; i < array.length; i++) {
						String[] temp = array[i].split("=");
						map.put(temp[0], temp[1]);
					}
					return new ModelAndView("record/failed", map);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
    }

    /**
     * 重新充值失败
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "chargeRecord/wrongAccount", method = RequestMethod.GET)
    public ModelAndView recharge()
            throws Exception {
        return new ModelAndView("record/wrongAccount");
    }

    /**
     * 重新充值成功 
     * @param coinPrice
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "chargeRecord/successAccount", method = RequestMethod.GET)
    public ModelAndView successAccount(String coinPrice)
            throws Exception {
    	Map<String, String> data = new HashMap<String, String>();
    	data.put("coinPrice", coinPrice);
        return new ModelAndView("record/account",data);
    }

    
    
    /**
     * 查询需要退存多少流量币
     * @return
     */
    @RequestMapping(value = "chargeRecord/account", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> account(String orderId) {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		Map<String, Object> data = new HashMap<String, Object>();
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			try {
				String url = PropertiesUtil.getProperty("interfaceIP")
						+ "/sfip/community/getRechargePrice?rechargeId=ORDERID&openId="
						+ myId;
				url = url.replace("ORDERID", orderId);
				data = HttpGetUtil.httpGet(url);
				if (!data.isEmpty() && data.get("code").equals("000000")) {
					data.put("status", "success");
				} else {
					data.put("status", "false");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}
		return data;
    }
    /**
     * 查询查询单条充值记录的充值状态
     * @return
     */
    @RequestMapping(value = "chargeRecord/getStatus", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> getStatus(String orderId) {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		Map<String, Object> data = new HashMap<String, Object>();
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			try {
				String url = PropertiesUtil.getProperty("interfaceIP")
						+ "/sfip/community/returnRechargeStatus?id=ORDERID&openId="
						+ myId;
				url = url.replace("ORDERID", orderId);
				data = HttpGetUtil.httpGet(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}
		return data;
    }

    /**
     * @param openId
     * @activeTypeId openId
     * @rechargeId openId
     * 退存
     * @return
     */
    @RequestMapping(value = "chargeRecord/restoreToFlowAccount", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> restoreToFlowAccount(String activeTypeId,
			String rechargeId) {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		Map<String, Object> data = new HashMap<String, Object>();
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			try {
				String url = PropertiesUtil.getProperty("interfaceIP")
						+ "/sfip/community/restoreToFlowAccount?openId=OPENID&rechargeId=RECHARGEID";
				url = url.replace("OPENID", myId).replace("RECHARGEID",
						rechargeId);
				System.out.println("requestUrl is " + url);
				data = HttpGetUtil.httpGet(url);
				if (!data.isEmpty() && data.get("code").equals("000000")) {
					data.put("status", "success");
				} else {
					data.put("status", "false");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}
		return data;
    }
    
    /**(废弃)
     * 页面跳转到退存流量账号页面
     * @return
     */
    @RequestMapping(value = "chargeRecord/getReturnBackAmount", method = RequestMethod.GET)
    public ModelAndView getReturnBackAmount(String orderId, String code, String coinPrice) {
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            String url = PropertiesUtil.getProperty("interfaceIP") + "/sfip/community/getReturnBackAmount?id=ORDERID&code=CODE";
            if(StringUtils.isEmpty(code) || code.equalsIgnoreCase("false")){
                code = null;
            }
            url = url.replace("ORDERID", orderId).replace("CODE", code);
            if(HttpGetUtil.httpGetBl(url)){
                resultMap.put("coinPrice", coinPrice);
                return new ModelAndView("record/account", resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("record/wrongAccount");
    }
    
    /**
     * 重新充值跳转
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "chargeRecord/rechargeAgain", method = RequestMethod.GET)
	public ModelAndView rechargeAgain(String content, String phone)
			throws Exception {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			Map<String, String> map = new HashMap<String, String>();
			if (StringUtils.isNotEmpty(content)) {
				content = java.net.URLDecoder.decode(content, "UTF-8");
				String[] array = content.split("\\|");
				for (int i = 0; i < array.length; i++) {
					String[] temp = array[i].split("=");
					map.put(temp[0], temp[1]);
				}
			}
			String orderId = map.get("orderId");
			String oldRecchargeMobileNo = map.get("oldRecchargeMobileNo");
			String activeTypeId = map.get("activeTypeId");
			// 获取手机运营商
			String ip_url = ConfigManager.getValue("interfaceIP");
			// String httpUrl = ip_url
			// + "/sfip/community/mobileOperatorTypeCheck?mobile=" +
			// oldRecchargeMobileNo;
			String httpUrl = ip_url
					+ "/sfip/community/checkOperatorOfMobil?mobile="
					+ oldRecchargeMobileNo;
			// 调用接口方法
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(httpUrl);
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus is " + statusCode);
			}
			byte[] responseBody = getMethod.getResponseBody();
			// {"operatorType":"ctcc","code":"000000"}
			Map<String, Object> dataDetail = new HashMap<String, Object>();
			// 处理内容
			String result = new String(responseBody, "UTF-8");
			dataDetail = (Map<String, Object>) JsonUtil.getInstance().toMap(
					result);
			// 取得结果
			String operatorType = "";
			// operatorType = (String) dataDetail.get("operatorType");
			operatorType = (String) dataDetail.get("operator");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("oldOperatorType", operatorType);// 充值失败的手机运营商
			data.put("orderId", orderId);// 订单记录id
			data.put("activeTypeId", activeTypeId);// 生效类型id
			data.put("oldRecchargeMobileNo", oldRecchargeMobileNo);// 生效类型id

			return new ModelAndView("record/rechargeAgain", data);
		} else {
			return new ModelAndView("exchange/exchangeFailure");
		}
    }
    
    /**
     * 重新充值
     * @param phone
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "chargeRecord/rechargeAgainCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rechargeAgainCommit(String phone,
			String orderId, String activeTypeId) throws Exception {
		String myId = "";
		Object myIdO = session.getAttribute("myId");
		Map<String, Object> data = new HashMap<String, Object>();
		if (myIdO != null) {
			myId = myIdO.toString();
			session.removeAttribute("myId");
			session.setAttribute("myId", myId);
			// 拼写httpurl
			String ip_url = ConfigManager.getValue("interfaceIP");
			String httpUrl = ip_url
					+ "/sfip/community/rechargeFlowAgain?rechargeId=" + orderId
					+ "&phone=" + phone + "&activateType=" + activeTypeId
					+ "&openId=" + myId;
			// 调用接口方法
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(httpUrl);
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpStatus is " + statusCode);
			}
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			String result = new String(responseBody, "UTF-8");
			Map<String, Object> dataDetail = new HashMap<String, Object>();
			dataDetail = (Map<String, Object>) JsonUtil.getInstance().toMap(
					result);
			// 取得结果
			String resultCode = "";
			resultCode = (String) dataDetail.get("code");

			if ("000000".equalsIgnoreCase(resultCode)) {// 存到流量账户成功
				data.put("success", "ture");
			} else {// 存到流量账户失败
				data.put("success", "failse");
			}
			return data;
		}
		return data;
    }

}
