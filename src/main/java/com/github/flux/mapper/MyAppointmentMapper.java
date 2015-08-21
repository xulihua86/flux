package com.github.flux.mapper;

import com.github.flux.base.BaseMapper;
import com.github.flux.entity.MyAppointment;

public interface MyAppointmentMapper extends BaseMapper<MyAppointment> {
	
	public long getCount(MyAppointment myAppointment);

}
