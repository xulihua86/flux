package com.github.flux.util.result;

import java.util.HashMap;
import java.util.Map;

public class MapResult {

	public static Map<String, Object> successMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", BaseResult.SUCCESS.getCode());
		map.put("msg", BaseResult.SUCCESS.getMsg());
		return map;
	}
	
	public static Map<String, Object> initMap(int code, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	
	public static Map<String, Object> failMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", BaseResult.FAILED.getCode());
		map.put("msg", BaseResult.FAILED.getMsg());
		return map;
	}
	
}
