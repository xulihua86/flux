package com.sinowel.netflow.grab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/grab")
public class GrabController {

	@RequestMapping(value = "/initGrab", method = RequestMethod.GET)
	public ModelAndView index(String openid) throws Exception {
		return new ModelAndView("grab/grab", null);
	}
}
