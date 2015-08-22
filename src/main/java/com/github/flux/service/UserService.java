package com.github.flux.service;

import java.util.List;
import java.util.Map;

import com.github.flux.base.BaseService;
import com.github.flux.entity.User;

public interface UserService extends BaseService<User> {

	public Map<String, Object> login(String mobile, String uuid, String code);
	
	// 更新cache及db
	// public void updateWithCache(User user);
	
	// from cache if null from db and set cache
	public User getByIdWithCache(Long userId);
	
	// 我关注userid
	public boolean follow(long myuserid, long userid);
	
	/**
	 * 我的关注列表
	 * @param userid 
	 * @param followTime  关注时间
	 * @param count  关注时间以前的多少个，
	 * @return
	 */
	public List<User> followList(long userid, long followTime, int count);
	
	/**
	 * 判断 myuserid 是否关注过 userid
	 * @param myuserid
	 * @param userid
	 * @return
	 */
	public boolean isFollow(long myuserid, long userid);
	
	
}
