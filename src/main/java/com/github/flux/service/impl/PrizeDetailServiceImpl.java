package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.PrizeDetail;
import com.github.flux.mapper.PrizeDetailMapper;
import com.github.flux.service.PrizeDetailService;

@Transactional
@Service("prizeDetailService")
public class PrizeDetailServiceImpl extends BaseServiceImpl<PrizeDetail>implements PrizeDetailService {

	@Resource
	private PrizeDetailMapper prizeDetailMapper;
	
	@Override
	public BaseMapper<PrizeDetail> getBaseMapper() {
		return prizeDetailMapper;
	}

	
	

}
