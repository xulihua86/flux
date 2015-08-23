package com.github.flux.service;

import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.AppointmentMessage;

public interface AppointmentMessageService extends BaseService<AppointmentMessage> {
	
	public Map<String,Object> save(Long userId, Long appointmentId, String message);
 
}
