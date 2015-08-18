package com.sinowel.netflow.common;

import java.io.Serializable;

public class Tds implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2228331921921123641L;

	private String orderNo;
	private String code;
	private String msg;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
