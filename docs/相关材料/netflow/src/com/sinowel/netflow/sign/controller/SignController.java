package com.sinowel.netflow.sign.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.PropertiesUtil;

@Controller
public class SignController extends BaseController {
	
	private String ip_url = PropertiesUtil.getProperty("interfaceIP");

	/**
	 * 签到页面跳转
	 * @return
	 */
	@RequestMapping(value="/sign/sign", method = RequestMethod.GET)
	public ModelAndView sign(HttpServletRequest request,HttpServletResponse response) throws Exception {
		/*System.out.println("openId is =================="+openId);
		if(!CheckOpenIdUtil.checkOpenId(openId)){
			return new ModelAndView("errorOpenId");
		} else {*/
		if(openId == null || "".equals(openId)) {
			return new ModelAndView("exchange/exchangeFailure");
		} else {
			//判断当前用户当天是否已签过到，决定跳转至那个页面
	        String interfaceUrl = ip_url + "/sfip/community/getCheckInStatus?openId=" + openId;
	        Map<String, Object> data = this.getDataFromURL(interfaceUrl);   
	        data.put("openId", openId);
	        
	        if("000000".equals(data.get("code"))){
	        	session.removeAttribute("signId");
	    		session.setAttribute("signId", openId);
		    	if("Y".equals(data.get("todayChecked").toString())){
		    		return new ModelAndView("sign/signOver", data);
		    	} else {
		    		return new ModelAndView("sign/sign", data);
		    	}
	        } else if("300008".equals(data.get("code"))){
	        	return new ModelAndView("sign/unbounded");
	        } else {
	        	data.put("message", "fail");
	        	return new ModelAndView("sign/sign", data);
	        }
		}
	}
	
	/**
	 * 已签到页面跳转
	 * @return
	 */
	@RequestMapping(value="/sign/signOver", method = RequestMethod.GET)
	public ModelAndView signOver(int awardByOne, int awardCumulatively, String checkCount){
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("awardByOne", awardByOne);
        data.put("awardCumulatively", awardCumulatively);
        data.put("checkCount", checkCount);
          		
		return new ModelAndView("sign/signOver",data);
	}
	
    /**
     * 签到过程处理
     * @param openId
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/sign/toSign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toSign() throws Exception {
		String openId = "";
		Object openIdO = session.getAttribute("signId");
		Map<String,Object> data = new HashMap<String, Object>();
		if(openIdO != null){
			openId = openIdO.toString();
			session.removeAttribute("signId");
			session.setAttribute("signId", openId);
			//判断当天是否已签到过，针对ios系统的返回按钮做判断
			String statusUrl = ip_url + "/sfip/community/getCheckInStatus?openId=" + openId;
			
			Map<String,Object> dataStatus = this.getDataFromURL(statusUrl);
			
			if("N".equals(dataStatus.get("todayChecked").toString())){
				//签到
				String interfaceUrl = ip_url + "/sfip/community/CheckIn?openId=" + openId;
				data = this.getDataFromURL(interfaceUrl);
			} else {
				data.putAll(dataStatus);
			}
			
			data.put("openId", openId);
		}		
        return data;
    } 
}
