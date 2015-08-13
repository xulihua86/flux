package com.github.flux.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.flux.plugin.mybatis.plugin.PageView;

public abstract class BaseServiceImpl<T> implements BaseService<T>{

	public abstract BaseMapper<T> getBaseMapper();

	@Override
	public List<T> queryAll(T t) {
		return getBaseMapper().queryAll(t);
	}

	@Override
	public void deleteById(Long id) {
		getBaseMapper().deleteById(id);
	}

	@Override
	public void update(T t) {
		getBaseMapper().update(t);
	}

	@Override
	public T getById(Long id) {
		return getBaseMapper().getById(id);
	}

	@Override
	public void add(T t) {
		getBaseMapper().add(t);
	}

	@Override
	public PageView query(PageView pageView, T t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paging", pageView);
		map.put("t", t);
		List<T> list = getBaseMapper().query(map);
		pageView.setRecords(list);
		return pageView;
	}

}
