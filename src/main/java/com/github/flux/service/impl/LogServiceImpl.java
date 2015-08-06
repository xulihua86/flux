package com.github.flux.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.flux.entity.Log;
import com.github.flux.mapper.LogMapper;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.LogService;

@Transactional
@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogMapper logMapper;

	public PageView query(PageView pageView, Log log) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paging", pageView);
		map.put("t", log);
		List<Log> list = logMapper.query(map);
		pageView.setRecords(list);
		return pageView;
	}

	public void add(Log log) throws Exception {
		logMapper.add(log);
	}

	public void delete(String id) throws Exception {
		logMapper.delete(id);
	}

	public Log getById(String id) {
		return logMapper.getById(id);
	}

	public void update(Log log) throws Exception {
		logMapper.update(log);
	}

	public List<Log> queryAll(Log log) {
		return logMapper.queryAll(log);
	}

}
