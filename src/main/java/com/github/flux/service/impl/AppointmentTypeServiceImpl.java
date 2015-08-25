package com.github.flux.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AppointmentType;
import com.github.flux.mapper.AppointmentTypeMapper;
import com.github.flux.service.AppointmentTypeService;

import com.github.flux.util.result.MapResult;


@Transactional
@Service("appointmentTypeService")
public class AppointmentTypeServiceImpl extends BaseServiceImpl<AppointmentType>implements AppointmentTypeService {

	@Resource
	private AppointmentTypeMapper appointmentTypeMapper;
	
	@Override
	public BaseMapper<AppointmentType> getBaseMapper() {
		return appointmentTypeMapper;
	}

	@Override
	public Map<String, Object> save(String name, String template) {
		AppointmentType app = this.getAppointmentTypeInstance(name, template);
		appointmentTypeMapper.add(app);
		return MapResult.successMap();
	}

	
	private AppointmentType getAppointmentTypeInstance(String name, String template){
		AppointmentType app = new AppointmentType();
		app.setName(name);
		app.setTemplate(template);
		return app;
	}

	@Override
	public List<AppointmentType> queryAll() {
		List<AppointmentType> list = appointmentTypeMapper.queryAll(null);
		return list;
	}


}
