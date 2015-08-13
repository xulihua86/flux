package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AccountLog;
import com.github.flux.mapper.AccountLogMapper;
import com.github.flux.service.AccountLogService;

@Transactional
@Service("accountLogService")
public class AccountLogServiceImpl extends BaseServiceImpl<AccountLog> implements AccountLogService {

	@Resource
	private AccountLogMapper accountLogMapper;
	
	@Override
	public BaseMapper<AccountLog> getBaseMapper() {
		return accountLogMapper;
	}

	
}
