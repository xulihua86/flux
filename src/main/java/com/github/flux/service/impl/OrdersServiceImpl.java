package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Orders;
import com.github.flux.mapper.OrdersMapper;
import com.github.flux.service.OrdersService;

@Transactional
@Service("ordersService")
public class OrdersServiceImpl extends BaseServiceImpl<Orders>implements OrdersService {

	@Resource
	private OrdersMapper ordersMapper;
	
	@Override
	public BaseMapper<Orders> getBaseMapper() {
		return ordersMapper;
	}

	

}
