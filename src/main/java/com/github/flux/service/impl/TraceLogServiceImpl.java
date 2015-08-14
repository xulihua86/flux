package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.TraceLog;
import com.github.flux.mapper.TraceLogMapper;
import com.github.flux.service.TraceLogService;

@Transactional
@Service("traceLogService")
public class TraceLogServiceImpl extends BaseServiceImpl<TraceLog> implements TraceLogService {

	@Resource
	private TraceLogMapper traceLogMapper;
	
	@Override
	public BaseMapper<TraceLog> getBaseMapper() {
		return traceLogMapper;
	}

	
}
