package com.github.flux.service;

import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.Appointment;

public interface AppointmentService extends BaseService<Appointment> {
	
	public Map<String,Object> save(Long userId,Long typeId,String logo,String name,Long targetNum,Integer face,String beginTime,String endTime,Integer pushFriend,String standard,String declaration,Long fluxNum,String rule);
	
	public Map<String,Object> update(Long appointmentId,Long userId,Long typeId,String logo,String name,Long targetNum,Integer face,String beginTime,String endTime,Integer pushFriend,String standard,String declaration,Long fluxNum,String rule);
	/**
	 * 获取一条约会信息
	 * @param AppointmentId   约会id
	 * @return
	 */
	public Appointment get(Long appointmentId);
	
	public Map<String,Object> del(Long userId, Long appointmentId);
	
	/**
	 * 分页获取我参加的
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> getJoin(String sort, Integer pageNo, Integer pageSize);

}
