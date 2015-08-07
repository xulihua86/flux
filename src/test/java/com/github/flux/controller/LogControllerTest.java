package com.github.flux.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.github.flux.util.ThreeDes;

@ContextConfiguration(locations = { "classpath*:spring-application.xml", "classpath*:spring-servlet.xml" })
public class LogControllerTest extends AbstractJUnit4SpringContextTests {

	protected Model model = new ExtendedModelMap();
	protected MockHttpServletRequest req = new MockHttpServletRequest();
	protected MockHttpServletResponse res = new MockHttpServletResponse();

	@Resource
	private LogRestController logRestController;

	@Test
	public void saveTest() {
		String authorization = auth(60000006100L, "a7f9618e-eecf-40ce-8128-decaf4b882fb");
		req.addHeader("Authorization", authorization);
		req.setRequestURI("/rest/log/save");
		
		Map<String, Object> map = logRestController.save(req, "hujh", "mod", "action");
		System.out.println(map);
	}

	// userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
	private String auth(long userid, String token) {
		String deviceType = "APH";
		long deviceId = 1;
		// String token = "847d2aa2-774e-4410-8b92-274c979d326f";
		long lastLoginTime = System.currentTimeMillis();
		String appVer = "1.0";
		String appId = "gzq";
		System.out.println(lastLoginTime);
		String tmp = userid + "|" + deviceType + "|" + deviceId + "|" + token + "|" + lastLoginTime + "|" + appVer + "|"
				+ appId;
		return ThreeDes.encrypt(tmp);
	}

}
