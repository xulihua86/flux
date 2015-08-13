package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.MyTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class MyTaskServiceTest {

	@Resource
	private MyTaskService myTaskService;
	
	@Test
	public void addTest() {
		MyTask m = new MyTask();
		m.setCreateTime(System.currentTimeMillis());
		m.setMyTaskId(1l);
		m.setReceiveTime(System.currentTimeMillis());
		m.setRewardNum(10l);
		m.setStatus(1);
		m.setTaskId(1l);
		m.setUserid(1l);
		myTaskService.add(m);
		
	}
}
