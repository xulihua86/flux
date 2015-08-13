package com.github.flux.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Log;
import com.github.flux.mapper.LogMapper;
import com.github.flux.service.LogService;

@Transactional
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {
	
	@Autowired
	private LogMapper logMapper;

	@Override
	public BaseMapper<Log> getBaseMapper() {
		return logMapper;
	}

	

}
