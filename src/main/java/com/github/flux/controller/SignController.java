package com.github.flux.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.entity.Sign;
import com.github.flux.service.SignService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/sign")
public class SignController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SignController.class);

	@Resource
	private SignService signService;

	/**
	 * 保存用户签到信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request) {

		Map<String,Object> map = null;

		try {
			// cookie获取用户id
			long userId = CookiesUtil.getInstance().getUserId(request);
			logger.debug("userId:"+userId);
/*			if (userId == 0) {
				// 没有登录
				map = MapResult.initMap(BaseResult.NO_LOGIN.getCode(),
						BaseResult.NO_LOGIN.getMsg());
			}
*/		map = signService.save(userId);// 保存签到信息

		} catch (Exception e) {

			logger.error("用户签到系统出现异常",e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;
	}
	
	/**
	 * 获取用户当月签到信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Map<String, Object> get(HttpServletRequest request) {

		Map<String,Object> map = new HashMap<String,Object>();

		try {
			// cookie获取用户id
			long userId = CookiesUtil.getInstance().getUserId(request);
			logger.debug("userId:"+userId);
/*			if (userId == 0) {
				// 没有登录
				map = MapResult.initMap(BaseResult.NO_LOGIN.getCode(),
						BaseResult.NO_LOGIN.getMsg());
			}*/
			List<Sign> list = signService.get(userId);
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
			map.put("data", list);
		} catch (Exception e) {

			logger.error("用户签到系统出现异常");
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;
	}
	

}
