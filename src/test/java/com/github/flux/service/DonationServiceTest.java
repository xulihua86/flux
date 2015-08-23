package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Donation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class DonationServiceTest {

	@Resource
	private DonationService donationService;
	
	@Test
	public void queryByMsgTest() {
		Donation d = donationService.queryByMsg(1);
		System.out.println(d);
	}
	
	@Test
	public void doingTest() {
		long fromuserid = 1;
		long touserid = 2;
		long num = 10;
		boolean b = donationService.doing(fromuserid, touserid, num);
		System.out.println(b);
		
	}
	
}
