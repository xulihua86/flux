package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.PrizeOut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class PrizeOutServicetest {


	@Resource
	private PrizeOutService prizeOutService;
	
	@Test
	public void addTest() {
		PrizeOut m = new PrizeOut();
		m.setOurId("uuu");
		m.setOutNum(1l);
		m.setOutTime(System.currentTimeMillis());
		m.setReturnNum(2L);
		m.setReturnTime(System.currentTimeMillis());
		m.setSumNum(2l);
		m.setUserid(1l);
		
		
		prizeOutService.add(m);
	}
	
}
