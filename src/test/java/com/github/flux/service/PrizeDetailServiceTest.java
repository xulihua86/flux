package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.PrizeDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class PrizeDetailServiceTest {

	@Resource
	private PrizeDetailService prizeDetailService;
	
	@Test
	public void addTest() {
		PrizeDetail m = new PrizeDetail();
		m.setCreateTime(System.currentTimeMillis());
		m.setDetailId(1l);
		m.setFlowNum(1l);
		m.setIsReceive(1);
		m.setOurId("12");
		m.setReceiveTime(System.currentTimeMillis());
		m.setUserid(1l);
		prizeDetailService.add(m);
	}
	
}
