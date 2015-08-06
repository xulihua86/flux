package com.github.flux.intercepter;

public interface AccountResourceIntf {
	public String findToken(long userId, String deviceType, String deviceId);

	public String getUserToken(long userId, String deviceType, String deviceId);

	public boolean isCookieValid(String cookieValue);
}
