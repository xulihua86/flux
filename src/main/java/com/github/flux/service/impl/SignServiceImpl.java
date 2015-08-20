package com.github.flux.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.entity.Sign;
import com.github.flux.mapper.SignMapper;
import com.github.flux.service.SignService;
import com.github.flux.util.DateUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;


@Transactional
@Service("signService")
public class SignServiceImpl extends BaseServiceImpl<Sign>implements SignService {

	@Resource
	private SignMapper signMapper;
	
	@Override
	public BaseMapper<Sign> getBaseMapper() {
		return signMapper;
	}

	@Override
	public Map<String,Object> save(long userId){
		Sign sign = this.getSignInstance(userId);
		long count = signMapper.getCount(sign);
		if(count > 0){
			return MapResult.initMap(BaseResult.REPEAT_OPERATE.getCode(),
					BaseResult.REPEAT_OPERATE.getMsg());
		}
		
		signMapper.add(sign);
		return MapResult.initMap(BaseResult.SUCCESS.getCode(),
				BaseResult.SUCCESS.getMsg());
	}

	@Override
	public List<Sign> get(long userId) {
		List<Sign> list = signMapper.get(userId);
		return list;
	}

	@Override
	public long getCount(long userId) {
		long count = signMapper.getCount(this.getSignInstance(userId));
		return count;
	}
	
	
	private Sign getSignInstance(long userId){
		Sign sign = new Sign();
		Date date = new Date();
		sign.setCreateTime(System.currentTimeMillis());
		sign.setUserId(userId);
		sign.setYyyymm(DateUtil.getIntFromCurrent(date, "yyyyMM"));
		sign.setYyyymmdd(DateUtil.getIntFromCurrent(date, "yyyyMMdd"));
		return sign;
	}
	
	

}
