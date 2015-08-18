package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class CardConsume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6725043794817200147L;
	private String code;
	private String card_id;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

}
