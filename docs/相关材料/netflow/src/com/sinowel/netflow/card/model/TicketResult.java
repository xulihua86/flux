package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class TicketResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7774248291823013119L;

	private String ticket;
	private String url;
	private long expire_seconds;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getExpire_seconds() {
		return expire_seconds;
	}

	public void setExpire_seconds(long expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

}
