package com.sinowel.netflow.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 微信卡券api_ticket
 * @author chendawei
 *
 */
public class WxCardApiTicket {

	private String ticket;
	private long createTime;
	private long expiresIn;
	
	public static WxCardApiTicket INSTANCE = new WxCardApiTicket();
	
	private WxCardApiTicket(){}
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	/**
	 * 是否过期
	 * @return true:过期;false:未过期
	 */
	public boolean isExpires(){
		
		if(StringUtils.isEmpty(ticket)){
			return true;
		}
		
		if((System.currentTimeMillis() - createTime)/1000 >= expiresIn){
			return true;
		}else{
			return false;
		}
		
	}
	
	
}//end class
