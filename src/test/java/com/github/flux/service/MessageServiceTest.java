package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class MessageServiceTest {

	@Resource
	private MessageService messageService;
	
	@Test
	public void addTest() {
		Message m = new Message();
		m.setCreateTime(System.currentTimeMillis());
		m.setNum(10L);
		m.setStatus(1);
		m.setTemplate("t");
		m.setType(2);
		m.setUserid(1l);
		messageService.add(m);
		
	}
	
	
}
