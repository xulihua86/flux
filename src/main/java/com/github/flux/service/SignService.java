package com.github.flux.service;

import java.util.List;
import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.Sign;

public interface SignService extends BaseService<Sign> {
	
	/**
	 * 保存用户签到信息
	 * @param userId
	 */
	public Map<String,Object> save(long userId);
	
	/**
	 * 获取用户当月所有签到信息
	 * @param userId
	 * @return
	 */
	public List<Sign> get(long userId);
	
	public long getCount(long userId);
}
