package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.AccountLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class AccountLogServiceTest {
	
	@Resource
	private AccountLogService accountLogService;
	
	@Test
	public void addTest() {
		AccountLog log = new AccountLog();
		log.setAddorsub(1);
		log.setCreateTime(System.currentTimeMillis());
		log.setDescn("dw");
		log.setNum(10L);
		log.setUserid(10L);
		accountLogService.add(log);
		System.out.println(log);
	}
	
	
}
