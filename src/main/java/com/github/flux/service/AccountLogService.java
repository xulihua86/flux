package com.github.flux.service;

import com.github.flux.base.BaseService;
import com.github.flux.entity.AccountLog;
import com.github.flux.plugin.mybatis.plugin.PageView;

public interface AccountLogService extends BaseService<AccountLog> {
	
	// 用户的帐单
	public PageView bill(int pageno, int pagesize,long userid);
	
	
}
