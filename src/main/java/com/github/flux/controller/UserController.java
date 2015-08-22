package com.github.flux.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.cache.RedisCache;
import com.github.flux.entity.User;
import com.github.flux.service.UserService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.RandomUtil;
import com.github.flux.util.ValidUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;



@Controller
@RequestMapping("/rest/user")
public class UserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RedisCache redisCache;

	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param mobile
	 * @param uuid
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest request,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "uuid", required = true) String uuid,
			@RequestParam(value = "code", required = true) String code) {
		// 验证
		if (!ValidUtil.isMobile(mobile)) {
			return MapResult.initMap(BaseResult.ILLEGAL_MOBILE.getCode(), BaseResult.ILLEGAL_MOBILE.getMsg());
		}
		if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(code)) {
			return MapResult.initMap(BaseResult.INVALID_PARAMETER.getCode(), BaseResult.INVALID_PARAMETER.getMsg());
		}

		Map<String, Object> map = userService.login(mobile, uuid, code);
		if ((Integer) map.get("code") != 0) {
			return MapResult.initMap(Integer.parseInt(map.get("code") + ""), map.get("msg").toString());
		} else {
			return map;
		}

	}

	/**
	 * 返回验证码
	 * 
	 * @param req
	 * @param mobile
	 *            手机号码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getVaildCode")
	public Map<String, Object> getVaildCode(HttpServletRequest request,
			@RequestParam(value = "mobile", required = true) String mobile) {
		// 需要发送短信的验证码
		String code = RandomUtil.generateStringByNumberChar(6);
		// @TODO 发送短信
		String uuid = RandomUtil.getUUID();

		// 保存 30 分钟过期
		redisCache.setString("valid:" + mobile + ":" + uuid, code, 3 * 60);
		Map<String, Object> result = MapResult.successMap();
		result.put("code", code);
		result.put("uuid", uuid);
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> uploadLogo(HttpServletRequest request,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "industry", required = false) String industry,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "signature", required = false) String signature) {

		// 验证
		if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(industry) || StringUtils.isEmpty(gender)
				|| StringUtils.isEmpty(year) || StringUtils.isEmpty(signature)) {
			return MapResult.initMap(BaseResult.INVALID_PARAMETER.getCode(), BaseResult.INVALID_PARAMETER.getMsg());
		}

		Long userid = CookiesUtil.getInstance().getUserId(request);
		try {
			User user = new User();
			user.setUserid(userid);
			user.setNickname(nickname);
			user.setIndustry(industry);
			user.setGender(Integer.parseInt(gender));
			user.setYear(Integer.parseInt(year));
			user.setSignature(signature);

			userService.update(user);
			return MapResult.successMap();
			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}

	
	
}
