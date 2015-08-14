package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.PrizePool;
import com.github.flux.mapper.PrizePoolMapper;
import com.github.flux.service.PrizePoolService;



@Transactional
@Service("prizePoolService")
public class PrizePoolServiceImpl extends BaseServiceImpl<PrizePool>implements PrizePoolService {

	@Resource
	private PrizePoolMapper prizePoolMapper;
	
	@Override
	public BaseMapper<PrizePool> getBaseMapper() {
		return prizePoolMapper;
	}

	
}
