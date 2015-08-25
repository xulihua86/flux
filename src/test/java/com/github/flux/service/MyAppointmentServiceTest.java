package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.MyAppointment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class MyAppointmentServiceTest {

	@Resource
	private MyAppointmentService myAppointmentService;
	
	@Test
	public void addTest() {
		MyAppointment m = new MyAppointment();
		m.setCreateTime(System.currentTimeMillis());
		m.setAppointmentId(5l);
		m.setCreateTime(System.currentTimeMillis());
		// m.setMyId(myId);
		m.setType(0);
		m.setUserid(1l);
		myAppointmentService.add(m);
		
	}
}
