package com.github.flux.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.User;
import com.github.flux.plugin.mybatis.plugin.PageView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class UserServiceTest {

	@Resource
	private UserService userService;
	
	@Test
	public void addTest() throws Exception {
		for(int i = 100; i < 200; i++) {
			User user = new User();
			user.setAccount(20L + i);
			user.setFrozenAccount(0l);
			user.setLocked(0);
			user.setAddSum(10L + i);
			user.setSubSum(4L + i );
			user.setCreateTime(System.currentTimeMillis());
			user.setMobile("130" + i);
			user.setGender(1);
			user.setIndustry("元业");
			user.setLogo(null);
			user.setNickname("nn" + i);
			user.setSignature("st");
			user.setYear(2*i);
			// user.set
			userService.add(user);
		}
	}
	
	@Test
	public void addTest2() {
		User user = new User();
		user.setAccount(0l);
		user.setAddSum(10l);
		user.setSubSum(10l);
		user.setCreateTime(System.currentTimeMillis());
		user.setMobile("120");
		user.setGender(1);
		user.setIndustry("元业");
		user.setLogo(null);
		user.setNickname("mm");
		user.setSignature("mm");
		user.setYear(2015);
		// user.set
		userService.add(user);
		System.out.println(user);
		
	}
	
	@Test
	public void deleteTest() {
		userService.deleteById(1l);
		
	}
	
	@Test
	public void queryTest(){
		PageView pageView = new PageView(1, 10);
		User user = new User();
		user.setMobile("130");
		user.setNickname("nn");
		pageView = userService.query(pageView, user);
		System.out.println(pageView.getRowCount());
		System.out.println(pageView.getRecords().size());	
	}
	
	@Test
	public void queryAllTest() {
		User user = new User();
		user.setMobile("130");
		user.setNickname("nn");
		List<User> users = userService.queryAll(user);
		System.out.println(users.size());
	}
	
	@Test
	public void updateTest() {
		User user = new User();
		user.setUserid(2l);
		user.setNickname("n2");
		userService.update(user);
		
	}
}
