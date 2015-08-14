package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.PrizeOut;
import com.github.flux.mapper.PrizeOutMapper;
import com.github.flux.service.PrizeOutService;

@Transactional
@Service("prizeOutService")
public class PrizeOutServiceImpl extends BaseServiceImpl<PrizeOut>implements PrizeOutService {

	@Resource
	private PrizeOutMapper prizeOutMapper;
	
	@Override
	public BaseMapper<PrizeOut> getBaseMapper() {
		return prizeOutMapper;
	}

	
	

}
