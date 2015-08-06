package com.github.flux.util;

public class PageHelpUtil {
//	private int totalCount;
//	private int pageSize;
//	private int pageNum;
//	private int targetPageNum;
//	private int currentPageNum;
	

	/**
	 * @说明 获取查询分页skip的数目  页数
	 * @param targetPageNum  页数从1开始计数
	 * @param totalCount     总行数
	 * @param pageSize       每页最大行数
	 * @return skipNum
	 */
	public static long getSkipNum(long targetPageNum, long totalCount,
			int pageSize) {
		if (totalCount <= 0)
			return 0;
		if (pageSize <= 0)
			return 0;
		if(targetPageNum<=1)
			return 0;
	    long  pageNum=getPageNum(totalCount,pageSize);
		if(pageNum<=0)
			return 0;

		
		long skipNum = (targetPageNum-1) * pageSize;
		return skipNum>totalCount?totalCount:skipNum;
	}
	
	/**
	 * @说明  获取查询分页的总页数
	 * @param totalCount     总行数
	 * @param pageSize       每页最大行数
	 * @return skipNum
	 */
	public static long getPageNum( long totalCount,
			int pageSize) {
		if (totalCount <= 0)
			return 0;
		if (pageSize <= 0)
			return 0;
		if(totalCount>0&pageSize>0&pageSize>totalCount){
			return 1;
		}
		return (totalCount/pageSize)+(totalCount%pageSize==0?0:1);
	}
}
