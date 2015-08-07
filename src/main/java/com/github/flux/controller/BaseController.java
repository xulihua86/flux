package com.github.flux.controller;

import com.github.flux.plugin.mybatis.plugin.PageView;


public class BaseController {
	PageView pageView = null;
	
	public PageView getPageView(int pageNow, int pagesize) {
		
		if(pageNow <= 0) {
			pageNow = 1;
		}
		
		if(pagesize <= 0) {
			pagesize = 10;
		}
		pageView = new PageView(pageNow, pagesize);
		return pageView;
	}
	
	/**
	 * @ModelAttribute
	 * 这个注解作用.每执行controllor前都会先执行这个方法
	 */
	/*@ModelAttribute
	public void init(HttpServletRequest request){
		String path = Common.BACKGROUND_PATH;
		Object ep = request.getSession().getAttribute("basePath");
		if(ep!=null){
			if(!path.endsWith(ep.toString())){
				Common.BACKGROUND_PATH = "/WEB-INF/jsp/background"+ep;
			}
		}
		
	}*/
}