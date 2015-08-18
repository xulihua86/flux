package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class QRCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6107685439695469451L;

	private String card_id;
	private String code;
	private String openid;
	private String expire_seconds;
	private boolean is_unique_code;
	private int outer_id;
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getExpire_seconds() {
		return expire_seconds;
	}
	public void setExpire_seconds(String expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	public boolean isIs_unique_code() {
		return is_unique_code;
	}
	public void setIs_unique_code(boolean is_unique_code) {
		this.is_unique_code = is_unique_code;
	}
	public int getOuter_id() {
		return outer_id;
	}
	public void setOuter_id(int outer_id) {
		this.outer_id = outer_id;
	}

}
