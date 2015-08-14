package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.ShopGoods;
import com.github.flux.mapper.ShopGoodsMapper;
import com.github.flux.service.ShopGoodsService;


@Transactional
@Service("shopGoodsService")
public class ShopGoodsServiceImpl extends BaseServiceImpl<ShopGoods> implements ShopGoodsService {

	@Resource
	private ShopGoodsMapper shopGoodsMapper;
	
	@Override
	public BaseMapper<ShopGoods> getBaseMapper() {
		return shopGoodsMapper;
	}

	

}
