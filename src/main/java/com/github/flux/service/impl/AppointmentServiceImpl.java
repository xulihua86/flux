package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Appointment;
import com.github.flux.mapper.AppointmentMapper;
import com.github.flux.service.AppointmentService;

@Transactional
@Service("appointmentService")
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment> implements AppointmentService {

	@Resource
	private AppointmentMapper appointmentMapper;
	
	@Override
	public BaseMapper<Appointment> getBaseMapper() {
		return appointmentMapper;
	}

	

}
