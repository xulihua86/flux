package com.sinowel.netflow.card.model;

import java.io.Serializable;

public class ActionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 505649405629223123L;
	
	private QRCard card;

	public QRCard getCard() {
		return card;
	}

	public void setCard(QRCard card) {
		this.card = card;
	}
	
	


}
