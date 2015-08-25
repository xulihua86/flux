package com.github.flux.service;

import java.util.List;
import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.AppointmentType;

/**
 * 约会类型模版
 * @author zhang
 *
 */
public interface AppointmentTypeService extends BaseService<AppointmentType> {
	
	/**
	 * 增加活动类型
	 * @param name
	 * @param template
	 * @return
	 */
	public Map<String,Object> save(String name, String template);
	
	
	public List<AppointmentType> queryAll();

}
