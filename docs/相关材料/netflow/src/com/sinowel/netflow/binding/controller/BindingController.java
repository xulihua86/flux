package com.sinowel.netflow.binding.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.ConfigManager;

@Controller
@RequestMapping(value = "/binding")
public class BindingController extends BaseController {

	  /**
     * 页面初始化，判断当前OPENID是否有过号码绑定
     * @param openId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/initBinding", method = RequestMethod.GET)
	public ModelAndView init() throws Exception {
//		if(!CheckOpenIdUtil.checkOpenId(openId)){
//			return new ModelAndView("errorOpenId");
//		} else {
			String interfaceUrl = ConfigManager.getValue("interfaceIP")+
					"/sfip/community/getUserStatus?openId="+openId;
			
			Map<String, Object> data = this.getDataFromURL(interfaceUrl);
			String isBindingMobile = data.get("isBindingMobile").toString();
		    data.put("openId", openId);
		    session.removeAttribute("bindId");
    		session.setAttribute("bindId", openId);
			//判断当前用户是否进行过手机绑定
			if("N".equals(isBindingMobile)){
				return new ModelAndView("binding/binding", data);
			}else{
				return new ModelAndView("binding/unbunding", data);
			}
//		}
		
	}
	  /**
     * 手机号码绑定
     * @param openId,phoneNum,code
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/bindingPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bindingPhone(String phone, String code) throws Exception {
		String appId = ConfigManager.getValue("wechat.appId");
		String openId = "";
		Map<String,Object> data = new HashMap<String, Object>();
		Object openIdB = session.getAttribute("bindId");
		if(openIdB != null){
			openId = openIdB.toString();
			session.removeAttribute("bindId");
			session.setAttribute("bindId", openId);
			
			String interfaceUrl = ConfigManager.getValue("interfaceIP")+
					"/sfip/community/bindTelephoneNumber?openId="+openId+"&mobileNo="+phone
					+"&code="+code + "&appId=" + appId;
			
			data = this.getDataFromURL(interfaceUrl);
			data.put("phone", phone);
			//0为绑定手机号码
			data.put("bindingState","0");
			data.put("openId", openId);
		}
		return data;
	}
	  /**
     * 手机号码解绑
     * @param openId,phoneNum,code
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/unbundingPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unbundingPhone(String phone, String code) throws Exception {
		String openId = "";
		Map<String,Object> data = new HashMap<String, Object>();
		Object openIdB = session.getAttribute("bindId");
		if(openIdB != null){
			openId = openIdB.toString();
			session.removeAttribute("bindId");
			session.setAttribute("bindId", openId);
			String interfaceUrl = ConfigManager.getValue("interfaceIP")+
					"/sfip/community/unbindTelephoneNumber?openId="+openId+"&mobileNo="+phone
					+"&code="+code;
			
			data = this.getDataFromURL(interfaceUrl);
			data.put("phone", phone);
			//1为解绑手机号码
			data.put("bindingState","1");
			data.put("openId", openId);
		}
		return data;
	}
	 /**
     * 发送验证短信
     * @param openId,phoneNum,codeType
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/smscheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SMSCheck(String phone, String codeType) throws Exception {
		String appId = ConfigManager.getValue("wechat.appId");
		String openId = "";
		Map<String,Object> data = new HashMap<String, Object>();
		Object openIdB = session.getAttribute("bindId");
		if(openIdB != null){
			openId = openIdB.toString();
			session.removeAttribute("bindId");
			session.setAttribute("bindId", openId);
			String interfaceUrl = ConfigManager.getValue("interfaceIP")+
					"/sfip/community/refreshVerifyCode?openId="+openId+"&mobileNo="+phone+
					"&codeType="+codeType+"&appId="+appId;
				
			data = this.getDataFromURL(interfaceUrl);
		}
		return data;
	}
	  /**
     * 成功页面消息跳转
     * @param bindingState,phone,result
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public ModelAndView message(String bindingState,String phone,String result,String isBoundMobile) throws Exception {
		Map<String,String> data = new HashMap<String, String>();
		String stateMessage="";
		if("0".equals(bindingState)){
			if("2".equals(result)){
				stateMessage="微信账号绑定手机号成功";
			}else if("1".equals(result)){
				stateMessage="验证码输入不正确";
			}else if("0".equals(result)){
				stateMessage="微信账号已绑定手机号";
			}else if("3".equals(result)){
				stateMessage="手机号码已被绑定";
			}else if("4".equals(result)){
				stateMessage="微信账号绑定手机号失败";
			}else if("5".equals(result)){
				stateMessage="绑定成功，由于手机号曾被他人绑定过，不再赠送流量币";
			}
		}else if("1".equals(bindingState)){
			if("2".equals(result)){
				stateMessage="微信账号解绑手机号成功";
			}else if("1".equals(result)){
				stateMessage="验证码输入不正确";
			}else if("0".equals(result)){
				stateMessage="微信账号已解绑手机号";
			}else if("3".equals(result)){
				stateMessage="微信账号解绑手机号失败";
			}
		}
		data.put("stateMessage", stateMessage);
        data.put("phone", phone);	
        data.put("openId", openId);
        data.put("bindingState", bindingState);
        data.put("result", result);
        data.put("isBoundMobile", isBoundMobile);
		return new ModelAndView("binding/message", data);
	}
	
	/**
	 * 校验号段
	 * @param quantity
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mobileCheck(String phone) throws Exception {
		//判断运营商
		String interfaceUrlphone = ConfigManager.getValue("interfaceIP") + "/sfip/community/checkOperatorOfMobil?mobile="+phone;
		// 接受返回值
		Map<String, Object> data = this.getDataFromURL(interfaceUrlphone);
		
		if(data.get("operator")==null || "null".equals(data.get("operator"))){
			data.clear();
			data.put("code", "100000");
		}
		
		return data;
	}
}
