package com.github.flux.mapper;

import com.github.flux.base.BaseMapper;
import com.github.flux.entity.Donation;

public interface DonationMapper extends BaseMapper<Donation> {

	public Donation queryByMsg(Long msgId);
}
