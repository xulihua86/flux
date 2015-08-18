package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class ResultEncryptCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3204127577319020264L;

	private String errmsg;
	private int errcode;
	private String code;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
