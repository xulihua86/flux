package com.github.flux.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Appointment;
import com.github.flux.plugin.mybatis.plugin.PageView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class AppointmentServiceTest {
	
	@Resource
	private AppointmentService appointmentService;
	
	@Test
	@Ignore
	public void addTest() {
		Appointment a = new Appointment();
		a.setBeginTime(System.currentTimeMillis());
		a.setEndTime(System.currentTimeMillis() + 200000);
		a.setCreateTime(System.currentTimeMillis());
		a.setUserid(1l);
		a.setTypeId(1l);
		a.setName("a");
		a.setLogo("logo");
		a.setTargetNum(100l);
		a.setStandard("sds");
		a.setDeclaration("deca");
		a.setFluxNum(10l);
		a.setOnlyFriend(1);
		a.setPushFriend(1);
		a.setEnrollNum(2l);
		a.setViewNum(20L);
		a.setStatus(2);
		a.setEnrollUserids("1,3,4");
		appointmentService.add(a);
		
	}
	
	
	@Test
	//@Ignore
	public void add2Test() {
        Long userId = 0l;
        Long typeId = 1l;
        String logo = "baidu.com.jpg";
        String name = "test01";
        Long targetNum = 12l;
        Integer face =1;
        String beginTime = "2015-08-31 12:12:12";
        String endTime = "2015-09-31 12:12:12";
        Integer pushFriend = 0;
        String standard = "标准qq";
        String declaration = "约会宣言";
        Long fluxNum = 10000l;
        String rule = "公平游戏，先到先得";
		appointmentService.save(userId, typeId, logo, name, targetNum, face, beginTime, endTime, pushFriend, standard, declaration, fluxNum, rule);
		
	}
	
	
	@Test
	@Ignore
	public void updateTest() {
		Long appointmentId = 1l;
		
        Long userId = 0l;
        Long typeId = 1l;
        String logo = "baidu.com.png";
        String name = "test01";
        Long targetNum = 12l;
        Integer face =1;
        String beginTime = "2015-08-31 12:12:12";
        String endTime = "2015-09-31 12:12:12";
        Integer pushFriend = 0;
        String standard = "标准qq";
        String declaration = "约会宣言";
        Long fluxNum = 10000l;
        String rule = "公平游戏，先到先得";
		appointmentService.update(appointmentId, userId, typeId, logo, name, targetNum, face, beginTime, endTime, pushFriend, standard, declaration, fluxNum, rule);
		
	}
	
	@Test
	public void getByIdTest(){
		Appointment app = appointmentService.get(1l);
		System.out.println(app.toString());
	}
	
	
	@Test
	public void delTest(){
		Long userId = 0l;
		Long appointmentId = 5l;
		Map<String, Object> map = appointmentService.del(userId, appointmentId);
		System.out.println(map.toString());
	}
	
	
	@Test
	public void appyTest(){
		Long userId = 2l;
		Long appointmentId = 1l;
		Map<String,Object> map = appointmentService.apply(userId, appointmentId);
		System.out.println(map.toString());
	}
	
	@Test
	public void followTest(){
		Long userId = 1l;
		Long appointmentId = 1l;
		Map<String,Object> map = appointmentService.follow(userId, appointmentId);
		System.out.println(map.toString());
	}
	
	@Test
	public void queryPageTest(){
		PageView view = new PageView(2, 2);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", "1");
		map.put("sort", "apply");
		view = appointmentService.queryPage(view, map);
		System.out.println(view.toString());
	}

}
