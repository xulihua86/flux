package com.github.flux.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.PayLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class PayLogServiceTest {

	@Resource
	private PayLogService payLogService;
	
	@Test
	public void addTest() {
		PayLog m = new PayLog();
		m.setCreateTime(System.currentTimeMillis());
//		m.setLogId(logId);
		m.setOrderId(1l);
		m.setPrice(1l);
		m.setStatus(1);
		m.setTranNo(UUID.randomUUID().toString());
		payLogService.add(m);
		
	}
}
