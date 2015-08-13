package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Flower;
import com.github.flux.mapper.FlowerMapper;
import com.github.flux.service.FlowerService;

@Transactional
@Service("flowerService")
public class FlowerServiceImpl extends BaseServiceImpl<Flower>implements FlowerService {

	@Resource
	private FlowerMapper flowerMapper;
	
	@Override
	public BaseMapper<Flower> getBaseMapper() {
		return flowerMapper;
	}

	

}
