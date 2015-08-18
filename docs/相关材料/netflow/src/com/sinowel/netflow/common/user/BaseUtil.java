package com.sinowel.netflow.common.user;

import com.sinowel.netflow.common.AcstkenAndJstkenUtil;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.JedisUtil;
import com.sinowel.netflow.common.JsonUtil;


public class BaseUtil {

	
	/**
	 * 
	 *  获取微信用户列表
	 *  @return
	 *  @author linyaoyao
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年5月28日 上午10:25:20
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static WechatUserResult getUserList(String openid) {

		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=OPENID";
		url = url.replace("OPENID", openid);
//		url = url.replace("ACCESS_TOKEN", JedisUtil.getToken());
		url = url.replace("ACCESS_TOKEN", AcstkenAndJstkenUtil.getInstance().getAccessToken());
		System.out.println("getUserList url is =============================="+url);
		String userInfo = HttpUtil.httpsRequest(url, "GET", null);
		WechatUserResult userList = JsonUtil.getInstance().toBean(userInfo,
				WechatUserResult.class);
		System.out.println("userList size is ============================="+userList.getCount());
		return userList;
	}
}
