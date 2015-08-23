package com.github.flux.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.service.DonationService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/donation")
public class DonationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

	@Resource
	private DonationService donationService;
	
	// 转赠
	@ResponseBody
	@RequestMapping("/doing")
	public Map<String, Object> doing(HttpServletRequest request,
			@RequestParam(value = "num", required = true) int num,
			@RequestParam(value = "touserid", required = true) long touserid) {
		logger.info("doing num:{}, touserid:{}", num, touserid);
		if(num <= 0 || touserid <= 0) return MapResult.initMap(BaseResult.INVALID_PARAMETER.getCode(), BaseResult.INVALID_PARAMETER.getMsg());
		long fromuserid = CookiesUtil.getInstance().getUserId(request);
		
		
		return null;
	}
	
}
