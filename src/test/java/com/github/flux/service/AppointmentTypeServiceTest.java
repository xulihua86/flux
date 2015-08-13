package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.AppointmentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class AppointmentTypeServiceTest {

	@Resource
	private AppointmentTypeService appointmentTypeService;
	
	@Test
	public void addTest(){
		AppointmentType type = new AppointmentType();
		type.setName("n");
		type.setTemplate("t");
		appointmentTypeService.add(type);
		System.out.println(type);
	}
	
}
