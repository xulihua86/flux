package com.github.flux.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.MyAppointment;
import com.github.flux.mapper.MyAppointmentMapper;
import com.github.flux.service.MyAppointmentService;
import com.github.flux.util.result.MapResult;

@Transactional
@Service("myAppointmentService")
public class MyAppointmentServiceImpl extends BaseServiceImpl<MyAppointment>implements MyAppointmentService {

	@Resource
	private MyAppointmentMapper myAppointmentMapper;

	@Override
	public BaseMapper<MyAppointment> getBaseMapper() {
		return myAppointmentMapper;
	}

	@Override
	public Map<String, Object> save(Long userId, Long appointmentId, Integer type) {
		MyAppointment ma = this.getMyAppointmentInsance(userId, appointmentId, type);
		long count = myAppointmentMapper.getCount(ma);
		if(count > 0){
			return MapResult.failMap();
		}
		myAppointmentMapper.add(ma);
		Map<String, Object> map = MapResult.successMap();
		return map;
	}

	
	private MyAppointment getMyAppointmentInsance(Long userId, Long appointmentId, Integer type){
		MyAppointment ma = new MyAppointment();
		ma.setAppointmentId(appointmentId);
		ma.setCreateTime(System.currentTimeMillis());
		ma.setUserid(userId);
		ma.setType(type);
		return ma;
	}

	@Override
	public long getCount(Long userId, Long appointmentId, Integer type) {
		MyAppointment ma = this.getMyAppointmentInsance(userId, appointmentId, type);
		return 0;
	}

	@Override
	public Map<String, Object> delByAppointmentId(Long appointmentId) {
		// TODO 
		myAppointmentMapper.deleteByAppointmentId(appointmentId);//删除约会关系
		return null;
	}
	

}
