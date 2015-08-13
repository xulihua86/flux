package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Appointment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class AppointmentServiceTest {
	
	@Resource
	private AppointmentService appointmentService;
	
	@Test
	public void addTest() {
		Appointment a = new Appointment();
		a.setBeginTime(System.currentTimeMillis());
		a.setEndTime(System.currentTimeMillis() + 200000);
		a.setCreateTime(System.currentTimeMillis());
		a.setUserid(1l);
		a.setTypeId(1l);
		a.setName("a");
		a.setLogo("logo");
		a.setTargetNum(100l);
		a.setStandard("sds");
		a.setDeclaration("deca");
		a.setFluxNum(10l);
		a.setOnlyFriend(1);
		a.setPushFriend(1);
		a.setEnrollNum(2l);
		a.setViewNum(20L);
		a.setStatus(2);
		a.setEnrollUserids("1,3,4");
		appointmentService.add(a);
		
	}
	
	
}
