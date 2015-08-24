package com.github.flux.service;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class AppointmentMessageTest {
	
	@Resource
	private AppointmentMessageService appointmentMessageService;
	
	@Test
	public void saveTest(){
		Long userId = 0l;
		Long appointmentId = 1l;
		String message = "hello";
		Map<String,Object> map = appointmentMessageService.save(userId, appointmentId, message);
		System.out.println(map.toString());
	}
	


}
