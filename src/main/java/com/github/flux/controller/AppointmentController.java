package com.github.flux.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/appointment")
public class AppointmentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(HttpServletRequest request,
			@RequestParam(value = "typeId", required = true) Long typeId,
			@RequestParam(value = "logo", required = true) String logo,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "targetNum", required = true) Long targetNum,
			@RequestParam(value = "face", required = true) Integer face,
			@RequestParam(value = "beginTime", required = true) Long beginTime,
			@RequestParam(value = "endTime", required = true) Long endTime,
			@RequestParam(value = "pushFriend", required = true) Integer pushFriend,
			@RequestParam(value = "standard", required = true) String standard,
			@RequestParam(value = "declaration", required = true) String declaration,
			@RequestParam(value = "fluxNum", required = true) Long fluxNum,
			@RequestParam(value = "rule", required = true) String rule) {

		return null;

	}

}
