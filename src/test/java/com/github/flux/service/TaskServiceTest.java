package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class TaskServiceTest {

	@Resource
	private TaskService taskService;
	
	@Test
	public void addTest() {
		Task m = new Task();
		m.setCode(13l);
		m.setDescn("des");
		m.setName("name");
		m.setRewardNum(2l);
//		m.setTaskId();
		
		taskService.add(m);
		
	}
	
}
