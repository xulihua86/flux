package com.github.flux.mapper;

import java.util.List;
import java.util.Map;

import com.github.flux.base.BaseMapper;
import com.github.flux.entity.Appointment;
import com.github.flux.plugin.mybatis.plugin.PageView;

public interface AppointmentMapper extends BaseMapper<Appointment> {

	public List<Appointment> queryPage(Map<String,Object> map);
}
