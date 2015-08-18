package com.sinowel.netflow.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;


public class Signature {
	
	private static Signature instance = null;

	private Signature() {
	}

	public static synchronized Signature getInstance() {
		if (instance == null)
			instance = new Signature();
		return instance;
	}
	
	/**
	 * 
	 *  微信sdk cofing 初始化
	 *  @param url
	 *  @return
	 *  @author linyaoyao
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年4月2日 下午3:45:50
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public  Map<String, String> sign(String url) {
		Map<String, String> ret = new HashMap<String, String>();

		String jsapi_ticket = JedisUtil.getJsTicket();
		if(jsapi_ticket == null){
			System.out.println("==========  获取js_ticket 失败,蛋疼 啊    ===========");
		}
		String timestamp = create_timestamp();
		String nonce_str = create_nonce_str(timestamp);
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("url", url);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", PropertiesUtil.getProperty("wechat.appId"));
		
		System.out.println("============ weixin SDK signature  ===============");
		System.out.println("page_url:"+url);
		System.out.println("jsapi_ticket:"+jsapi_ticket);
		System.out.println("nonceStr:"+nonce_str);
		System.out.println("timestamp:"+timestamp);
		System.out.println("signature:"+signature);
		System.out.println("============  weixin SDK signature end  ===============");
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str(String timestamp) {
		String loginpasswd = null;
		loginpasswd = MD5.Md5(timestamp);
		return loginpasswd;
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	

	
	/**
	 * 微信卡券 signature 
	 * @param code 卡券code
	 * @param cardId 卡券id
	 * @param openId 
	 * @param secret 公众帐号密钥
	 * @return
	 */
	public Map<String,String> getWXCardSign(String code,String cardId,String openId ,String secret){
		Map<String,String> data = new HashMap<String,String>();
		
		WxCardSign sign = new WxCardSign();
		String timestamp = create_timestamp();
		
		sign.AddData(code);//卡券code
		sign.AddData(timestamp);//时间戳
		sign.AddData(secret);//公众帐号密钥
		sign.AddData(cardId);//卡券id
		String signature = sign.GetSignature();//生成的 sdj config js signature
		
		data.put("code", code);
		data.put("timestamp", timestamp);
		data.put("cardId", cardId);
		data.put("signature", signature);
		
		System.out.println("============  cardExt   =======================");
		System.out.println("code:"+code);
		System.out.println("timestamp:"+timestamp);
		System.out.println("secret:"+secret);
		System.out.println("card_id:"+cardId);
		System.out.println("signature:"+signature);
		System.out.println("============  cardExt end   =======================");
		
		return data;
	}
	

}
