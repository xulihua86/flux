package com.github.flux.service;

import com.github.flux.base.BaseService;
import com.github.flux.entity.Donation;

public interface DonationService extends BaseService<Donation> {

	/**
	 * from 转赠 num 流量币给 to
	 * @param fromuserid
	 * @param touserid
	 * @param num
	 * @return
	 */
	public boolean doing(long fromuserid, long touserid, long num);
	
	public Donation queryByMsg(long msgId);
	
}
