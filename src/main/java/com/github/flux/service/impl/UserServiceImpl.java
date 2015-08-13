package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.User;
import com.github.flux.mapper.UserMapper;
import com.github.flux.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	public BaseMapper<User> getBaseMapper() {
		return userMapper;
	}
	
	
	

	
	
}
