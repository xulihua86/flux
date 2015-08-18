package com.sinowel.netflow.common.model;

import java.io.Serializable;

public class JsTicket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4824556058156492170L;

	private int errcode;
	private int expires_in;
	private String ticket;
	private String errmsg;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
