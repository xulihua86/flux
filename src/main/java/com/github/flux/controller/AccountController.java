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

import com.github.flux.entity.User;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.AccountLogService;
import com.github.flux.service.UserService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.StringUtils;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/account")
public class AccountController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Resource
	private AccountLogService accountLogService;
	@Resource
	private UserService userService;
	
	/**
	 * 用户流量帐单
	 * @param request
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bill")
	public Map<String, Object> bill(HttpServletRequest request,
			@RequestParam(value = "pageNow", required = false) String pageNow,
			@RequestParam(value = "pageSize", required = false) String pageSize){
		
		long myuserid = CookiesUtil.getInstance().getUserId(request);

		int pageNow_int = 1;
		int pagesize_int = 10;

		if (!StringUtils.isEmpty(pageNow)) {
			pageNow_int = StringUtils.parseInt(pageNow);
			if (pageNow_int == 0) {
				pageNow_int = 1;
			}
		}
		if (StringUtils.isNotEmpty(pageSize)) {
			pagesize_int = StringUtils.parseInt(pageSize);
			if (pagesize_int == 0) {
				pagesize_int = 10;
			}
		}
		
		try{
			PageView pv = accountLogService.bill(pageNow_int, pageNow_int, myuserid);
			if(pv == null) return MapResult.failMap();
			User user = userService.getByIdWithCache(myuserid);
			Map<String, Object> map = MapResult.successMap();
			map.put("data", pv);
			map.put("user", user);
			return map;
		}catch(Exception ex) {
			logger.error("", ex);
			return MapResult.failMap();
		}
	}
	
}
