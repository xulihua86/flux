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
import com.github.flux.entity.User;
import com.github.flux.mapper.DonationMapper;
import com.github.flux.service.AccountLogService;
import com.github.flux.service.DonationService;
import com.github.flux.service.MessageService;
import com.github.flux.service.UserService;

@Transactional
@Service("donationService")
public class DonationServiceImpl extends BaseServiceImpl<Donation> implements DonationService {

	private static final Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);

	@Resource
	private DonationMapper donationMapper;

	@Override
	public BaseMapper<Donation> getBaseMapper() {
		return donationMapper;
	}
	
	@Resource
	private UserService userService;
	@Resource
	private AccountLogService accountLogService;
	@Resource
	private MessageService messageService;
	
	
	/**
	 * from 转赠 num 流量币给 to
	 * @param fromuserid
	 * @param touserid
	 * @param num
	 * @return
	 */
	public boolean doing(long fromuserid, long touserid, long num){
		
		if(fromuserid == touserid) {
			logger.error("自己不能赠送给自己！");
			return false;
		}
		
		User from = userService.getByIdWithCache(fromuserid);
		if(from == null) {
			logger.error("fromuserid is null  {}", fromuserid);
			return false;
		}
		
		User to = userService.getByIdWithCache(touserid);
		if(to == null) {
			logger.error("touser id null {}", touserid);
			return false;
		}
		
		if(from.getAccount() < num) {
			logger.error("帐号流量币不够, {}, num:{}", from, num);
			return false;
		}
		
		// 增加to 消息
		Message message = new Message();
		message.setCreateTime(System.currentTimeMillis());
		message.setNum(num);
		message.setStatus(Message.unReceived_status);
		message.setType(Category.friendGive.getIndex());  // 朋友赠送
		message.setUserid(touserid);
		messageService.add(message);
		
		// 赠送记录
		Donation d = new Donation();
		d.setCreateTime(System.currentTimeMillis());
		d.setFromuserid(fromuserid);
		d.setNum(new Long(num));
		d.setTouserid(touserid);
		d.setMsgId(message.getMsgId());
		donationMapper.add(d);
		
		// 扣减from 帐户流量
		from.setAccount(from.getAccount() - num);
		from.setSubSum(new Long(num));
		userService.update(from);
		
		// 扣减明细
		AccountLog aclog = new AccountLog();
		aclog.setAddorsub(AccountLog.Sub);
		aclog.setCreateTime(System.currentTimeMillis());
		aclog.setDescn("转赠朋友");
		aclog.setNum(num);
		aclog.setUserid(fromuserid);
		accountLogService.add(aclog);
		
		logger.info("转赠成成 from:{}, to:{}, num:{}", fromuserid, touserid, num);
		return true;
	}
	
	
	public Donation queryByMsg(long msgId){
		return donationMapper.queryByMsg(msgId);
	}
	

}
