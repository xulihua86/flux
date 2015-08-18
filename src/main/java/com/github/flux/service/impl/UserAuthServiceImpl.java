package com.github.flux.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.intercepter.AccountResourceIntf;

@Transactional
@Service("userAuthService")
public class UserAuthServiceImpl implements AccountResourceIntf {

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
		
		return true;
	}

}
