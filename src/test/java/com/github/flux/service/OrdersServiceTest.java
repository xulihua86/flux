package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Orders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class OrdersServiceTest {

	@Resource
	private OrdersService ordersService;
	
	@Test
	public void addTest() {
		Orders m = new Orders();
		m.setCreateTime(System.currentTimeMillis());
		m.setGoodsId(1l);
		m.setNum(2);
		// m.setOrderId(orderId);
		m.setPayStatus(1);
		m.setPayTime(System.currentTimeMillis());
		m.setPrice(10l);
		m.setUserid(1l);;
		ordersService.add(m);
		
	}
}
