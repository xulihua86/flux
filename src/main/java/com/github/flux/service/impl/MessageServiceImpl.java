package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Message;
import com.github.flux.mapper.MessageMapper;
import com.github.flux.service.MessageService;

@Transactional
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message>implements MessageService {

	@Resource
	private MessageMapper messageMapper;
	
	@Override
	public BaseMapper<Message> getBaseMapper() {
		return messageMapper;
	}

	

}
