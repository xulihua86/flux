package com.github.flux.entity;

import java.io.Serializable;

/**
 * 约会留言
 * 
 * @author zhang
 *
 */
public class AppointmentMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008934972940579874L;

	/**
	 * 留言id
	 */
	private Long messageId;

	/**
	 * 约会id
	 */
	private Long appointmentId;

	/**
	 * 留言内容
	 */
	private String content;
	
	/**
	 * 留言时间
	 */
	private Long createTime;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	

}
