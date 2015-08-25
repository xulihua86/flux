package com.github.flux.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Appointment;
import com.github.flux.mapper.AppointmentMapper;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.AppointmentService;
import com.github.flux.service.MyAppointmentService;
import com.github.flux.util.DateUtil;
import com.github.flux.util.result.MapResult;

@Transactional
@Service("appointmentService")
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment>
		implements AppointmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Resource
	private AppointmentMapper appointmentMapper;
	@Resource
	private MyAppointmentService myAppointmentService;

	@Override
	public BaseMapper<Appointment> getBaseMapper() {
		return appointmentMapper;
	}

	private Appointment getAppointmentInstance(Long userId, Long typeId,
			String logo, String name, Long targetNum, Integer face,
			String beginTime, String endTime, Integer pushFriend,
			String standard, String declaration, Long fluxNum, String rule) {
		Appointment app = new Appointment();
		app.setBeginTime(DateUtil.str2Date(beginTime, "yyyy-MM-dd HH:mm:ss").getTime());
		app.setCreateTime(System.currentTimeMillis());
		app.setDeclaration(declaration);
		app.setEndTime(DateUtil.str2Date(endTime, "yyyy-MM-dd HH:mm:ss").getTime());
		app.setEnrollNum(0l);
		app.setEnrollUserids("");
		app.setFluxNum(fluxNum);
		app.setLogo(logo);
		app.setName(name);
		app.setOnlyFriend(1);
		app.setPushFriend(pushFriend);
		app.setStandard(standard);
		app.setStatus(-1);
		app.setTargetNum(targetNum);
		app.setTypeId(typeId);
		app.setUserid(userId);
		app.setViewNum(0l);
		app.setRule(rule);
		app.setTarget(face);
		return app;
	}

	@Override
	public Map<String, Object> save(Long userId, Long typeId, String logo,
			String name, Long targetNum, Integer face, String beginTime,
			String endTime, Integer pushFriend, String standard,
			String declaration, Long fluxNum, String rule) {
		Appointment app = this.getAppointmentInstance(userId, typeId, logo, name, targetNum, face, beginTime, endTime, pushFriend, standard, declaration, fluxNum, rule);
		appointmentMapper.add(app);
		return MapResult.successMap();
	}

	@Override
	public Appointment get(Long appointmentId) {
		Appointment app = appointmentMapper.getById(appointmentId);
		app.setViewNum(app.getViewNum()+1);
		appointmentMapper.update(app);//更新浏览数
        return app;
	}

	@Override
	public Map<String, Object> del(Long userId, Long appointmentId) {
		//更改为删除状态
		Appointment app = appointmentMapper.getById(appointmentId);
		if(app == null || app.getUserid() == null){
			logger.debug("删除约会对象不存在");
			return MapResult.failMap();
		}
		
		app.setStatus(-2);
		appointmentMapper.update(app);//修改状态
		//TODO  删除参与MyAppointment信息
		myAppointmentService.delByAppointmentId(appointmentId);
		return MapResult.successMap();
	}

	@Override
	public Map<String, Object> update(Long appointmentId, Long userId, Long typeId, String logo, String name,
			Long targetNum, Integer face, String beginTime, String endTime, Integer pushFriend, String standard,
			String declaration, Long fluxNum, String rule) {
		Appointment app = this.getAppointmentInstance(userId, typeId, logo, name, targetNum, face, beginTime, endTime, pushFriend, standard, declaration, fluxNum, rule);
		app.setAppointmentId(appointmentId);
		appointmentMapper.update(app);
		return MapResult.successMap();
	}

	@Override
	public Map<String, Object> getJoin(String sort, Integer pageNo,
			Integer pageSize) {
		if(pageNo == null || pageNo < 1){
			pageNo=1;
		}
		if(pageSize ==null || pageSize < 1){
			pageSize=20;
		}
		
		PageView pv = new PageView(pageNo, pageSize);
		
		return null;
	}

	@Override
	public Map<String, Object> apply(Long userId, Long appointmentId) {
		
		Appointment app = appointmentMapper.getById(appointmentId);
		if(app == null || app.getUserid() == userId || app.getStatus() != 0){
			logger.debug("约会对象不存在或该约会不正常");
			return MapResult.failMap();
		}
		
		Map<String, Object> map = myAppointmentService.save(userId, appointmentId, 0);
		if(map == null || !map.get("code").equals(0)){
			return MapResult.failMap();
		}
		
		app.setEnrollNum(app.getEnrollNum()+1);
		String enrollUserids = app.getEnrollUserids().equals("") ? ""+userId : app.getEnrollUserids()+","+userId;
		app.setEnrollUserids(enrollUserids);
		appointmentMapper.update(app);//报名人数+1,增加报名用户
		
		
		return MapResult.successMap();
	}

	@Override
	public Map<String, Object> follow(Long userId, Long appointmentId) {
		Appointment app = appointmentMapper.getById(appointmentId);
		if(app == null || app.getUserid() == userId || app.getStatus() != 0){
			logger.debug("约会对象不存在或该约会不正常");
			return MapResult.failMap();
		}
		
		Map<String, Object> map = myAppointmentService.save(userId, appointmentId, 1);
		return map;
	}

	@Override
	public PageView queryPage(PageView pageView, Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paging", pageView);
		map.putAll(param);
		List<Appointment> list = appointmentMapper.queryPage(map);
		pageView.setRecords(list);
		return pageView;
	}
}
