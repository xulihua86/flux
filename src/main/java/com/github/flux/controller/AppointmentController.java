package com.github.flux.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.entity.Appointment;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.AppointmentService;
import com.github.flux.service.MyAppointmentService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/appointment")
public class AppointmentController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(AppointmentController.class);
	
	@Resource
	private AppointmentService appointmentService;
	@Resource
	private MyAppointmentService myAppointmentService;

	/**
	 * 保存约会
	 * @param request
	 * @param typeId
	 * @param logo
	 * @param name
	 * @param targetNum
	 * @param face
	 * @param beginTime
	 * @param endTime
	 * @param pushFriend
	 * @param standard
	 * @param declaration
	 * @param fluxNum
	 * @param rule
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(
			HttpServletRequest request,
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
		Map<String, Object> map = null;
		try {
			
			
			
			
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	/**
	 * 更新约会
	 * @param request
	 * @param appointmentId
	 * @param typeId
	 * @param logo
	 * @param name
	 * @param targetNum
	 * @param face
	 * @param beginTime
	 * @param endTime
	 * @param pushFriend
	 * @param standard
	 * @param declaration
	 * @param fluxNum
	 * @param rule
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(
			HttpServletRequest request,
			@RequestParam(value = "appointmentId", required = true) Long appointmentId,
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
		Map<String, Object> map = null;
		try {
			
			
			
			
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	
	/**
	 * 删除约会
	 * @param request
	 * @param appointmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del/{appointmentId}")
	public Map<String, Object> del(HttpServletRequest request,@PathVariable Long appointmentId) {
		
		logger.debug("appointmentId:"+appointmentId);
		Map<String, Object> map = null;
		try {
			
			
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	/**
	 * 约流量
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/flow/get/{pageNo}/{pageSize}")
	public Map<String, Object> getFlow(HttpServletRequest request,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
		
		logger.debug("pageNo:"+pageNo+"|pageSize:"+pageSize);
		Map<String, Object> map = null;
		try {
			
			PageView page = new PageView(pageNo, pageSize);
			page.setRowCount(1);
			List<Appointment> list = new ArrayList<Appointment>();
			Appointment app = appointmentService.get(1l);
			list.add(app);
			page.setRecords(list);
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
			map.put("data", page);
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	
	/**
	 * 我参与的
	 * @param request
	 * @param appointmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/join/get/{pageNo}/{pageSize}")
	public Map<String, Object> getJoin(HttpServletRequest request,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
		
		logger.debug("pageNo:"+pageNo+"|pageSize:"+pageSize);
		Map<String, Object> map = null;
		try {
			
			PageView page = new PageView(pageNo, pageSize);
			page.setRowCount(1);
			List<Appointment> list = new ArrayList<Appointment>();
			Appointment app = appointmentService.get(1l);
			list.add(app);
			page.setRecords(list);
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
			map.put("data", page);
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	
	/**
	 * 获取约会信息
	 * @param request
	 * @param id        本次约会id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/{appointmentId}")
	public Map<String, Object> get(HttpServletRequest request,@PathVariable Long appointmentId) {
		
		logger.debug("appointmentId:"+appointmentId);
		Map<String, Object> map = null;
		try {
			
			
			Appointment app = appointmentService.get(appointmentId);
			
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
			map.put("data", app);
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	
	
	/**
	 * 关注
	 * @param request
	 * @param AppointmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/follow/{appointmentId}")
	public Map<String, Object> follow(HttpServletRequest request,@PathVariable Long appointmentId) {
		
		logger.debug("appointmentId:"+appointmentId);
		Map<String, Object> map = null;
		try {
			
			long userId = CookiesUtil.getInstance().getUserId(request);
			logger.debug("userId:"+userId);
	    	myAppointmentService.save(userId, appointmentId, 1);
	    	map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
	    					BaseResult.SUCCESS.getMsg());
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}
	
	/**
	 * 报名
	 * @param request
	 * @param appointmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/apply/{appointmentId}")
	public Map<String, Object> apply(HttpServletRequest request,@PathVariable Long appointmentId) {
		
		logger.debug("appointmentId:"+appointmentId);
		Map<String, Object> map = null;
		try {
			long userId = CookiesUtil.getInstance().getUserId(request);
			logger.debug("userId:"+userId);
			
			myAppointmentService.save(userId, appointmentId, 0);
			map = MapResult.initMap(BaseResult.SUCCESS.getCode(),
					BaseResult.SUCCESS.getMsg());
		} catch (Exception e) {
			logger.error("用户签到系统出现异常", e);
			map = MapResult.initMap(BaseResult.SERVER_ERROR.getCode(),
					BaseResult.SERVER_ERROR.getMsg());
		}

		return map;

	}

}
