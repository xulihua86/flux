package com.github.flux.base;

import java.util.List;

public interface Base<T> {
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<T> queryAll(T t);
	public void deleteById(Long id);
	public void update(T t);
	public T getById(Long id);
	public void add(T t);
}
