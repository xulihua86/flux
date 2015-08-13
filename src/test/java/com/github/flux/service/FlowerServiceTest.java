package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Flower;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class FlowerServiceTest {
	
	@Resource
	private FlowerService flowerService;
	
	@Test
	public void addTest(){
		Flower f = new Flower();
		f.setCreateTime(System.currentTimeMillis());
		f.setFriendId(1l);
		f.setUserid(2l);
		flowerService.add(f);
		System.out.println(f);
	}
}
