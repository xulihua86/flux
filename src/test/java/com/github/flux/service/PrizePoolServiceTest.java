package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.PrizePool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class PrizePoolServiceTest {

	@Resource
	private PrizePoolService prizePoolService;
	
	@Test
	public void addTest() {
		PrizePool m = new PrizePool();
		m.setConsumed(1l);
		m.setDailyMax(10l);
		m.setEveryMax(2l);
		m.setSumNum(20l);
		
		prizePoolService.add(m);
	}
	
	
}
