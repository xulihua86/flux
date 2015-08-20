package com.github.flux.mapper;

import java.util.List;

import com.github.flux.base.BaseMapper;
import com.github.flux.entity.Sign;

public interface SignMapper extends BaseMapper<Sign> {
	
	public long getCount(Sign sign);
	
	public List<Sign> get(long userId);

}
