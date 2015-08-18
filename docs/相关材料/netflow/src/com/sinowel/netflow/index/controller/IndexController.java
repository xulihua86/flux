package com.sinowel.netflow.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	/**
	 * 首页跳转
	 * @return
	 */
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}

	/**
	 * 加载链接
	 * @return
	 */
	@RequestMapping(value="/link", method = RequestMethod.GET)
	public ModelAndView link(){
		ModelAndView view = new ModelAndView("link");
		return view;
	}

}
