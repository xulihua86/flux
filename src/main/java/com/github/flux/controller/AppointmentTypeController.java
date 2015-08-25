package com.github.flux.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.entity.AppointmentType;
import com.github.flux.service.AppointmentTypeService;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/appointmentType")
public class AppointmentTypeController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(AppointmentTypeController.class);
	
	@Resource
	private AppointmentTypeService appointmentTypeService;
	
	/**
	 * 
	 * @param request
	 * @param name
	 * @param template
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(
			HttpServletRequest request,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "template", required = true) String template) {
		Map<String, Object> map = null;
		try {
			
			map = appointmentTypeService.save(name, template);
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	
	@ResponseBody
	@RequestMapping("/get")
	public Map<String, Object> get(HttpServletRequest request) {
		Map<String, Object> map = null;
		try {
			
			List<AppointmentType> list = appointmentTypeService.queryAll();
			map = MapResult.successMap();
			map.put("data", list);
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
}
