package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.flux.base.FluxHelp;
import com.github.flux.cache.RedisCache;
import com.github.flux.intercepter.AccountResourceIntf;

@Transactional
@Service("userAuthService")
public class UserAuthServiceImpl implements AccountResourceIntf {

	@Resource
	private RedisCache redisCache; 
	
	@Override
	public String findToken(long userId, String deviceType, String deviceId) {
		
		return null;
	}

	@Override
	public String getUserToken(long userId, String deviceType, String deviceId) {
		
		return null;
	}

	
	@Override
	// cookieValue:  userId|deviceType|deviceId|token|lastLoginTime|appVer
	public boolean isCookieValid(String cookieValue) {
		if(cookieValue == null) return false;
		String[] cookies = cookieValue.split("[|]");
		if(cookies.length != 6) return false;
		
		String token = cookies[3];
		long userid = Long.parseLong(cookies[0]);
		String tokenKey = FluxHelp.gerTokenKey(userid);
		String value = redisCache.getString(tokenKey);
		if(StringUtils.isEmpty(value)) {
			return false;
		}
		if(value.equals(token)) {
			return true;
		}
		return false;
	}

}
