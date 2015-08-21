package com.github.flux.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Appointment;
import com.github.flux.mapper.AppointmentMapper;
import com.github.flux.service.AppointmentService;
import com.github.flux.util.DateUtil;

@Transactional
@Service("appointmentService")
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment>
		implements AppointmentService {

	@Resource
	private AppointmentMapper appointmentMapper;

	@Override
	public BaseMapper<Appointment> getBaseMapper() {
		return appointmentMapper;
	}

	public Appointment getAppointmentInstance(Long userId, Long typeId,
			String logo, String name, Long targetNum, Integer face,
			String beginTime, String endTime, Integer pushFriend,
			String standard, String declaration, Long fluxNum, String rule) {
		Appointment app = new Appointment();
		app.setBeginTime(DateUtil.str2Date(beginTime, "yyyy-MM-dd HH:mm:ss").getTime());
		app.setCreateTime(System.currentTimeMillis());
		app.setDeclaration(declaration);
		app.setEndTime(DateUtil.str2Date(endTime, "yyyy-MM-dd HH:mm:ss").getTime());
		app.setEnrollNum(0l);
		app.setEnrollUserids("");
		app.setFluxNum(fluxNum);
		app.setLogo(logo);
		app.setName(name);
		//app.setOnlyFriend(1);
		app.setPushFriend(pushFriend);
		app.setStandard(standard);
		app.setStatus(-1);
		app.setTargetNum(targetNum);
		app.setTypeId(typeId);
		app.setUserid(userId);
		app.setViewNum(0l);
		return app;
	}

	@Override
	public Map<String, Object> save(Long userId, Long typeId, String logo,
			String name, Long targetNum, Integer face, String beginTime,
			String endTime, Integer pushFriend, String standard,
			String declaration, Long fluxNum, String rule) {

		return null;
	}

	@Override
	public Appointment get(Long AppointmentId) {
		Appointment app = new Appointment();
		app.setBeginTime(DateUtil.str2Date("2015-08-30 12:12:12", "yyyy-MM-dd HH:mm:ss").getTime());
		app.setCreateTime(System.currentTimeMillis());
		app.setDeclaration("haha");
		app.setEndTime(DateUtil.str2Date("2015-08-30 12:12:12", "yyyy-MM-dd HH:mm:ss").getTime());
		app.setEnrollNum(0l);
		app.setEnrollUserids("");
		app.setFluxNum(1000l);
		app.setLogo("baidu.com");
		app.setName("我们约会吧");
		//app.setOnlyFriend(1);
		app.setPushFriend(1);
		app.setStandard("qqqq");
		app.setStatus(-1);
		app.setTargetNum(10l);
		app.setTypeId(1222l);
		app.setUserid(0l);
		app.setViewNum(0l);
		return app;
	}
}
