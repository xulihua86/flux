package com.github.flux.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.ShopGoods;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class ShopGoodsServiceTest {

	@Resource
	private ShopGoodsService shopGoodsService;
	
	@Test
	public void addTest() {
		ShopGoods m = new ShopGoods();
		m.setDescn("de");
		m.setLogo("s");
		m.setName("n");
		m.setPrice(1l);
		m.setSold(2l);
		m.setStock(2l);
		m.setType(1);
		shopGoodsService.add(m);
		
	}
	
}
