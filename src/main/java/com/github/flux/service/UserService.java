package com.github.flux.service;

import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.User;

public interface UserService extends BaseService<User> {

	public Map<String, Object> login(String mobile, String uuid, String code);
	
	// 更新cache及db
	public void updateWithCache(User user);
	
	// from cache if null from db and set cache
	public User getByIdWithCache(Long userId);
}
