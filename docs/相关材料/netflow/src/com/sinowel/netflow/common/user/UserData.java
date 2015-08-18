package com.sinowel.netflow.common.user;

import java.io.Serializable;

public class UserData implements Serializable {

	private static final long serialVersionUID = 6535518361564277006L;

	/**
	 * 
	 */
	private String[] openid;

	 

	public String[] getOpenid() {
		return openid;
	}

	public void setOpenid(String[] openid) {
		this.openid = openid;
	}

}
