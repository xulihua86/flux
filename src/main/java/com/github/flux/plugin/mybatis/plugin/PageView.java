package com.github.flux.plugin.mybatis.plugin;

import java.util.List;

/**
 * //分页封装函数
 * 
 * @param <T>
 */
public class PageView {
	/**
	 * 分页数据
	 */
	private List records;

	/**
	 * 总页数 这个数是计算出来的
	 * 
	 */
	private long pageCount;

	/**
	 * 每页显示几条记录
	 */
	private int pageSize = 10;

	/**
	 * 默认 当前页 为第一页 这个数是计算出来的
	 */
	private int pageNow = 1;

	/**
	 * 总记录数
	 */
	private long rowCount;

	/**
	 * 从第几条记录开始
	 */
	private int startPage;

	/**
	 * 规定显示5个页码
	 */
	private int pagecode = 10;

	public PageView() {
	}

	/**
	 * 要获得记录的开始索引　即　开始页码
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.pageNow - 1) * this.pageSize;
	}

	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	/**
	 * 使用构造函数，，强制必需输入 每页显示数量　和　当前页
	 * 
	 * @param pageSize
	 *            　　每页显示数量
	 * @param pageNow
	 *            　当前页
	 */
	public PageView(int pageNow, int pageSize) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
	}

	/**
	 * 使用构造函数，，强制必需输入 当前页
	 * 
	 * @param pageNow
	 *            　当前页
	 */
	public PageView(int pageNow) {
		this.pageNow = pageNow;
		startPage = (this.pageNow - 1) * this.pageSize;
	}

	/**
	 * 查询结果方法 把　记录数　结果集合　放入到　PageView对象
	 * 
	 * @param rowCount
	 *            总记录数
	 * @param records
	 *            结果集合
	 */

	public void setQueryResult(long rowCount, List records) {
		setRowCount(rowCount);
		setRecords(records);
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
		setPageCount(this.rowCount % this.pageSize == 0 ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);
	}

	/**
	public PageView end() {
		// 1, 总页码
		// pageCount = ((int)this.totalCount + pageSize - 1) / pageSize;
		long pageCount = this.getPageCount();
		// 2, startPageIndex（显示的页码列表的开始索引）与endPageIndex（显示的页码列表的结束索引）
		// a, 总页码不大于10页
		if (pageCount <= 10) {
			startPageIndex = 1;
			endPageIndex = pageCount;
		}
		// b, 总码大于10页
		else {
			// 在中间，显示前面4个，后面5个
			startPageIndex = pageNo - 4;
			endPageIndex = pageNo + 5;

			// 前面不足4个时，显示前10个页码
			if (startPageIndex < 1) {
				startPageIndex = 1;
				endPageIndex = 10;
			}
			// 后面不足5个时，显示后10个页码
			else if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				startPageIndex = pageCount - 10 + 1;
			}
		}
		return this;

	}
	**/
	
	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public long getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowCount() {
		return rowCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

}
