package com.github.flux.util.aoptime;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.flux.util.StringUtils;

/**
 * aop service计算执行时间
 * 
 */
public class WorkTimeCenter implements MethodInterceptor {
	public static final Logger logger = LoggerFactory
			.getLogger(WorkTimeCenter.class);

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		String method = arg0.getMethod().toString();
		Object[] arguments = arg0.getArguments();
		StringBuffer paramValue = new StringBuffer("");
		if (arguments != null && arguments.length > 0)
			for (int i = 0; i < arguments.length; i++) {
				paramValue.append(arguments[i] + "##");
			}
		long s = System.currentTimeMillis();
		Object result = arg0.proceed();
		long e = System.currentTimeMillis();
		logger.info(StringUtils.contactString(method, " paramValue = "
				+ paramValue.toString(), " use time = ", (e - s) + "ms"));
		return result;

	}

}