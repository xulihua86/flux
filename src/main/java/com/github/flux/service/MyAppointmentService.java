package com.github.flux.service;

import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.MyAppointment;

public interface MyAppointmentService extends BaseService<MyAppointment> {
	
	/**
	 * 保存我的约会信息
	 * @param userId
	 * @param AppointmentId
	 * @return
	 */
	public Map<String,Object> save(Long userId, Long appointmentId, Integer type);
	
	
	public long getCount(Long userId, Long appointmentId, Integer type);
	
	/**的
	 * 删除该约会
	 * @param appointmentId
	 * @return
	 */
	public Map<String,Object> delByAppointmentId(Long appointmentId);

}
