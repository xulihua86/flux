package com.github.flux.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AppointmentMessage;
import com.github.flux.mapper.AppointmentMessageMapper;
import com.github.flux.service.AppointmentMessageService;

@Transactional
@Service("appointmentMessageService")
public class AppointmentMessageServiceImpl extends BaseServiceImpl<AppointmentMessage> implements AppointmentMessageService{
	
	@Resource
	private AppointmentMessageMapper appointmentMessageMapper;

	@Override
	public BaseMapper<AppointmentMessage> getBaseMapper() {
		return appointmentMessageMapper;
	}

	@Override
	public Map<String, Object> save(Long userId, Long appointmentId, String message) {
		AppointmentMessage appmess = this.getAppointmentMessageInstance(userId, appointmentId, message);
		appointmentMessageMapper.add(appmess);
		return null;
	}
	
	
	private AppointmentMessage getAppointmentMessageInstance(Long userId, Long appointmentId, String message){
		AppointmentMessage appmessage = new AppointmentMessage();
		appmessage.setAppointmentId(appointmentId);
		appmessage.setContent(message);
		appmessage.setCreateTime(System.currentTimeMillis());
		return appmessage;
	}
	

}
