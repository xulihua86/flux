package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.MyTask;
import com.github.flux.mapper.MyTaskMapper;
import com.github.flux.service.MyTaskService;

@Transactional
@Service("myTaskService")
public class MyTaskServiceImpl extends BaseServiceImpl<MyTask>implements MyTaskService {

	@Resource
	private MyTaskMapper myTaskMapper;
	
	@Override
	public BaseMapper<MyTask> getBaseMapper() {
		return myTaskMapper;
	}

	

}
