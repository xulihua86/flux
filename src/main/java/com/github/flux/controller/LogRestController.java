package com.github.flux.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.entity.Log;
import com.github.flux.service.LogService;
import com.github.flux.util.IpUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/log/")
public class LogRestController extends BaseController {

	@Resource
	private LogService logService;

	@ResponseBody
	@RequestMapping("save")
	public Map<String, Object> save(HttpServletRequest req,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "action", required = true) String action) {
		try {
			Log log = new Log();
			log.setUsername(username);
			log.setModule(module);
			log.setAction(action);
			log.setActionTime(System.currentTimeMillis() + "");
			log.setUserIP(IpUtil.getClientIP(req));
			log.setOperTime(new Date());
			logService.add(log);
			return MapResult.successMap();
		} catch (Exception ex) {
			return MapResult.failMap();
		}
	}

	@ResponseBody
	@RequestMapping("get")  // 1497
	public Map<String, Object> get(HttpServletRequest req,
			@RequestParam(value = "id", required = true) String id
			) {
		try {
			Log log = logService.getById(id);
			Map<String, Object> map = MapResult.initMap(BaseResult.SUCCESS.getCode(), BaseResult.SUCCESS.getMsg());
			map.put("data", log);
			return map;
		} catch (Exception ex) {
			return MapResult.failMap();
		}
	}
	
	
}
