package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.TraceLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class TraceLogServiceTest {

	@Resource
	private TraceLogService traceLogService;
	
	@Test
	public void addTest() {
		TraceLog m = new TraceLog();
		m.setCreateTime(System.currentTimeMillis());
		m.setDescn("cn");
		m.setUserid(1l);
		traceLogService.add(m);
		
	}
	
}
