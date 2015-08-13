package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.PayLog;
import com.github.flux.mapper.PayLogMapper;
import com.github.flux.service.PayLogService;

@Transactional
@Service("payLogService")
public class PayLogServiceImpl extends BaseServiceImpl<PayLog>implements PayLogService {

	@Resource
	private PayLogMapper payLogMapper;
	
	@Override
	public BaseMapper<PayLog> getBaseMapper() {
		return payLogMapper;
	}

	

}
