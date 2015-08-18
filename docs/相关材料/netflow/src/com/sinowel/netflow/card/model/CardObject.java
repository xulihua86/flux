package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class CardObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1329915470775838814L;

	private String card_id;

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

}
