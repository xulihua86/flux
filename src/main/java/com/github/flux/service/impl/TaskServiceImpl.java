package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Task;
import com.github.flux.mapper.TaskMapper;
import com.github.flux.service.TaskService;

@Transactional
@Service("taskService")
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {

	@Resource
	private TaskMapper taskMapper;
	
	@Override
	public BaseMapper<Task> getBaseMapper() {
		return taskMapper;
	}

	

}
