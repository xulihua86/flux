package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Message;
import com.github.flux.plugin.mybatis.plugin.PageView;

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
		System.out.println(m);
	}
	
	@Test
	public void batchAddTest() {
		for(int i = 0; i < 100; i++) {
			Message m = new Message();
			m.setCreateTime(System.currentTimeMillis());
			m.setNum(new Long(i));
			m.setStatus(i%2);
			m.setTemplate("t" + i);
			m.setType(1);
			m.setUserid(1l);
			m.setTagValue(null);
			messageService.add(m);
		}
	} 
	
	@Test
	public void queryTest() {
		Message m = new Message();
		m.setUserid(1l);
		PageView pageView = new PageView(1, 10);
		pageView = messageService.query(pageView, m);
		System.out.println(pageView);
	}
	
	// 106
	@Test
	public void receiveTest() {
		boolean b = messageService.receive(2l, 106l);
		System.out.println(b);
	}
	
}
