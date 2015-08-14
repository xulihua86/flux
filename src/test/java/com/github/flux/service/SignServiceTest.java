package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Sign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class SignServiceTest {

	@Resource
	private SignService signService;
	
	@Test
	public void addTest() {
		Sign m = new Sign();
		m.setCreateTime(System.currentTimeMillis());
		m.setUserId(1l);
		m.setYyyymm(201412);
		m.setYyyymmdd(20141201);
		signService.add(m);
		
	}
}
