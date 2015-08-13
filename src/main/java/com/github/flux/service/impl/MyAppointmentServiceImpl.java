package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.MyAppointment;
import com.github.flux.mapper.MyAppointmentMapper;
import com.github.flux.service.MyAppointmentService;

@Transactional
@Service("myAppointmentService")
public class MyAppointmentServiceImpl extends BaseServiceImpl<MyAppointment>implements MyAppointmentService {

	@Resource
	private MyAppointmentMapper myAppointmentMapper;
	
	@Override
	public BaseMapper<MyAppointment> getBaseMapper() {
		return myAppointmentMapper;
	}


}
