package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AppointmentType;
import com.github.flux.mapper.AppointmentTypeMapper;
import com.github.flux.service.AppointmentTypeService;


@Transactional
@Service("appointmentTypeService")
public class AppointmentTypeServiceImpl extends BaseServiceImpl<AppointmentType>implements AppointmentTypeService {

	@Resource
	private AppointmentTypeMapper appointmentTypeMapper;
	
	@Override
	public BaseMapper<AppointmentType> getBaseMapper() {
		return appointmentTypeMapper;
	}



}
