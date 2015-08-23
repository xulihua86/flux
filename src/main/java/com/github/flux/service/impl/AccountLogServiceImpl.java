package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AccountLog;
import com.github.flux.mapper.AccountLogMapper;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.AccountLogService;

@Transactional
@Service("accountLogService")
public class AccountLogServiceImpl extends BaseServiceImpl<AccountLog>implements AccountLogService {

	@Resource
	private AccountLogMapper accountLogMapper;

	@Override
	public BaseMapper<AccountLog> getBaseMapper() {
		return accountLogMapper;
	}

	// 用户的帐单
	public PageView bill(int pageno, int pagesize, long userid){
		if(userid == 0) return null;
		if(pageno < 1) pageno = 1;
		if(pagesize < 1) pagesize = 10;
		
		PageView pageView = new PageView(pageno, pagesize);
		AccountLog log = new AccountLog();
		log.setUserid(userid);
		pageView = this.query(pageView, log);
		return pageView;
	}
	
}
