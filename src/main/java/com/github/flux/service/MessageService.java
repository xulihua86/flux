package com.github.flux.service;

import com.github.flux.base.BaseService;
import com.github.flux.entity.Message;

public interface MessageService extends BaseService<Message> {
	
	/**
	 * 领取消息里的流量币
	 * @param userid  消息接受者
	 * @param msgId  消息id
	 * @return true|false
	 */
	public boolean receive(long userid, long msgId);
	
}
