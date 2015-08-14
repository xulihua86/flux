package com.github.flux.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Sign;
import com.github.flux.mapper.SignMapper;
import com.github.flux.service.SignService;


@Transactional
@Service("signService")
public class SignServiceImpl extends BaseServiceImpl<Sign>implements SignService {

	@Resource
	private SignMapper signMapper;
	
	@Override
	public BaseMapper<Sign> getBaseMapper() {
		return signMapper;
	}

	
	

}
