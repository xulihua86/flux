package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.cache.RedisCache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class RedisCacheTest {

	@Resource
	private RedisCache redisCache;
	
	@Test
	public void setObjectTest() {
		// @TODO 需要测试
	}
	
}
