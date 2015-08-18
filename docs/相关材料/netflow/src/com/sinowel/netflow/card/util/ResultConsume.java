package com.sinowel.netflow.card.util;

import java.io.Serializable;

import com.sinowel.netflow.card.model.CardObject;

public class ResultConsume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235691982297445695L;

	private int errcode;
	private String errmsg;
	private CardObject card;
	private String openid;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public CardObject getCard() {
		return card;
	}

	public void setCard(CardObject card) {
		this.card = card;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
