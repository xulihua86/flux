package com.sinowel.framework;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.common.OpenIdManager;

/**
 * 
 * @author chendawei
 *
 */
public class BaseHandlerInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//取得openId
		String openId = null; //request.getParameter("openId");
		if(StringUtils.isEmpty(openId) && request.getSession().getAttribute("openId") != null){
			openId = String.valueOf(request.getSession().getAttribute("openId"));
		}
		
		if(StringUtils.isEmpty(openId)){
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			
			if(StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(state)){
				openId = OpenIdManager.getOpenId(code).getOpenid();
			}
		}
		
		//排除页面内嵌套的“赚三网流量”
		if(handler instanceof HandlerMethod && !"link".equals(((HandlerMethod)handler).getMethod().getName())){
			//判断openId请求是否过于频繁
			if(!checkAccessTime(request,openId)){ //如果访问过于频繁
				//强制跳转
				request.getSession().getServletContext().getRequestDispatcher("/WEB-INF/page/accessBusy.jsp").forward(request, response);
				
				return false;
			}
		}
		
		//如果是BaseController的子类，进行初始化设置
		if(handler instanceof HandlerMethod){
			Object bean = ((HandlerMethod)handler).getBean();
			if(bean instanceof BaseController){
				
				((BaseController)bean).init(request, response,request.getSession());
				((BaseController)bean).setOpenId(openId); //设置openId
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	/**
	 * 检查当前openId访问是否过于频繁
	 * @return true:能过;false:不通过
	 */
	private final int finalAccessCount = 15; //一分钟访问次数限制，暂定15次
	private boolean checkAccessTime(HttpServletRequest request,String openId){
		if(StringUtils.isEmpty(openId)){
			return true;
		}
		
		Map<String,Object> map = null;
		ServletContext context = request.getSession().getServletContext();//全局上下文
		
		if(context.getAttribute(openId) == null){
			map = new HashMap<String,Object>();
			map.put("accessTime", new Date());//访问时间
			map.put("accessCount", 1);//访问次数
			
			context.setAttribute(openId, map);
			return true;
		}else{
			map = (Map<String,Object>)context.getAttribute(openId);
			Date accessTime = (Date)map.get("accessTime");
			int accessCount = (int)map.get("accessCount");
			accessCount = accessCount + 1;//访问次数加1
			Long accessLongTime = new Date().getTime() - accessTime.getTime(); //毫秒
			
			if(accessLongTime <= 1000*60 && accessCount > finalAccessCount){ //一分钟之内，访问次数大于10
				
				return false;
			}else{
				
				if(accessLongTime > 1000*60){ //超过一分钟，重置
					map = new HashMap<String,Object>();
					map.put("accessTime", new Date());//访问时间
					map.put("accessCount", 1);//访问次数
				}else{
					map.put("accessCount", accessCount);
				}
				
				context.setAttribute(openId, map);
				
				return true;
			}
		}
		
		
	}//end checkAccessTime
	
}//end class
