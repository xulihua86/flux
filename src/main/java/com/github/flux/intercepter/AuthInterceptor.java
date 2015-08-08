package com.github.flux.intercepter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.github.flux.util.result.BaseResult;

/**
 * 权限控制拦截器
 */

public class AuthInterceptor extends BaseAuthInterceptor {
	static {
		useUriFilterForNoCheck();
		// 不需要登录能访问的写在这里
		controllerUriFilter.add("/rest/test");
		controllerUriFilter.add("/web/getKaptchaImage");
		controllerUriFilter.add("/web/upload");

	}

	@Override
	protected void writeResponse(HttpServletResponse response) throws IOException {
		super.writeResponse(response, BaseResult.NO_LOGIN.getCode(), BaseResult.NO_LOGIN.getMsg());
	}

}
