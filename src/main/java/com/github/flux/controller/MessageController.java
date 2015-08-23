package com.github.flux.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flux.entity.Message;
import com.github.flux.plugin.mybatis.plugin.PageView;
import com.github.flux.service.MessageService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.StringUtils;
import com.github.flux.util.result.MapResult;

@Controller
@RequestMapping("/rest/message")
public class MessageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Resource
	private MessageService messageService;
	
	/**
	 * 我的消息
	 * @param request
	 * @param pageNow 开始页
	 * @param pageSize 每页
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/my")
	public Map<String, Object> myMessage(HttpServletRequest request,
			@RequestParam(value = "pageNow", required = false) String pageNow,
			@RequestParam(value = "pageSize", required = false) String pageSize
			){
		long myuserid = CookiesUtil.getInstance().getUserId(request);
		// long myuserid = 1l;
		
		int pageNow_int = 1;
		int pagesize_int = 10;

		if (!StringUtils.isEmpty(pageNow)) {
			pageNow_int = StringUtils.parseInt(pageNow);
			if (pageNow_int == 0) {
				pageNow_int = 1;
			}
		}
		if (StringUtils.isNotEmpty(pageSize)) {
			pagesize_int = StringUtils.parseInt(pageSize);
			if (pagesize_int == 0) {
				pagesize_int = 10;
			}
		}
		try{
			PageView pageView = new PageView(pageNow_int, pagesize_int);
			Message message = new Message();
			message.setUserid(myuserid);
			pageView = messageService.query(pageView, message);
			Map<String, Object> returnmap = MapResult.successMap();
			returnmap.put("data", pageView);
			return returnmap;
		}catch(Exception ex) {
			logger.error("", ex);
			return MapResult.failMap();
		}
	}
	
	
	
	
	
}
