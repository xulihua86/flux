package com.github.flux.base;

public class FluxHelp {

	public static String getUserKey(Long userid){
		return "user:" + userid;
	}
	
	public static String gerTokenKey(Long userid) {
		return "userToken:" + userid;
	}
	
	public static String getValidCodeKey(String mobile, String uuid) {
		return "valid:" + mobile + ":" + uuid;
	}
	
}
