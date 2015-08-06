package com.github.flux.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.flux.entity.Log;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-application.xml")
public class LogMapperTest {

	@Resource
	private LogMapper logMapper;
	
	@Test
	public void save() throws Exception {
		Log log = new Log();
		log.setUsername("un");
		log.setModule("d");
		log.setAction("de");
		log.setActionTime("21");
		log.setUserIP("12");
		log.setOperTime(new Date());
		logMapper.add(log);
	}
	
	
}
