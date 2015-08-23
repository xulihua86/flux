package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.Category;
import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.AccountLog;
import com.github.flux.entity.Donation;
import com.github.flux.entity.Message;
import com.github.flux.entity.TraceLog;
import com.github.flux.entity.User;
import com.github.flux.mapper.MessageMapper;
import com.github.flux.service.AccountLogService;
import com.github.flux.service.DonationService;
import com.github.flux.service.MessageService;
import com.github.flux.service.TraceLogService;
import com.github.flux.service.UserService;

@Transactional
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message>implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Resource
	private MessageMapper messageMapper;
	@Resource
	private AccountLogService accountLogService;
	@Resource
	private UserService userService;
	@Resource
	private DonationService donationService;
	@Resource
	private TraceLogService traceLogService;
	
	@Override
	public BaseMapper<Message> getBaseMapper() {
		return messageMapper;
	}

	
	/**
	 * 处理消息流量币
	 */
	public boolean receive(long userid, long msgId){
		// 查找消息
		Message message = messageMapper.getById(msgId);
		if(message == null) {  // 消息不存在
			logger.error("消息不存在 {}", msgId);
			return false; 
		}
		if(!message.getUserid().equals(userid)) {
			logger.error("消息不是该用户的 {} userid:{}", message, userid);
			return false;
		}
		
		if(message.getStatus() == Message.Received_Status) return false;
		
		// 变更用户帐户明细
		AccountLog log = new AccountLog();
		log.setAddorsub(AccountLog.Add);
		log.setCreateTime(System.currentTimeMillis());
		log.setDescn(message.getTemplate());
		log.setNum(message.getNum());
		log.setUserid(userid);
		accountLogService.add(log);
		
		// 更新用户帐户
		User user = userService.getById(userid);
		user.setAccount(user.getAccount() + message.getNum());
		user.setAddSum(user.getAddSum() + message.getNum());
		userService.update(user);
		
		// 更新消息
		message.setStatus(Message.Received_Status);
		messageMapper.update(message);
		
		// 发送跟踪消息 
		if(message.getType() == Category.friendGive.getIndex()) {
			Donation d = donationService.queryByMsg(msgId);
			long touserid = d.getFromuserid();  // 赠送人
			TraceLog t = new TraceLog();
			t.setUserid(touserid);
			t.setCreateTime(System.currentTimeMillis());
			t.setDescn("您转赠的" + message.getNum() + "流量币已被成功领取");
			traceLogService.add(t);
		}
		
		logger.info("更新成功！{}", user);
		return true;
	}

	
}
