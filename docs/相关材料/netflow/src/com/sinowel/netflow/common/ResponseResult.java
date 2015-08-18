package com.sinowel.netflow.common;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


public class ResponseResult implements Serializable {
	private static Logger logger = Logger.getLogger(ResponseResult.class);
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String message;
	
	public ResponseResult () {
	}
	
	/**
	 * 会自动 输出返回值日志
	 * @param code
	 * @param message
	 */
	public ResponseResult (String code, String message) {
		this.code = code;
		this.message = message;
		logger.debug("=================== " + JSONObject.fromObject(this).toString());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
