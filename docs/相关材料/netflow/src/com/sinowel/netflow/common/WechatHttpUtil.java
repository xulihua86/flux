package com.sinowel.netflow.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 
 * 微信服务接口工具类
 * 
 * @author linyaoyao
 * @version 1.0 </pre> Created on :下午3:11:50 LastModified: History: </pre>
 */
public class WechatHttpUtil {
	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 *            与腾讯开发者模式接口配置信息中的Token要一致，不懂去看 //
	 *            http://mp.weixin.qq.com/wiki/index.php?title=接入指南
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {

		String[] arr = new String[] { ConfigManager.getValue("token"),
				timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	
	/**
	 * 取得微信卡券api_ticket
	 * @author chendawei 
	 * @return
	 */
	private static String getWxCardApiTicket(){
		
		if(WxCardApiTicket.INSTANCE.isExpires()){
			String accessToken = AcstkenAndJstkenUtil.getInstance().getAccessToken();
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=@access_token&type=wx_card".replaceAll("@access_token", accessToken);
			
			String resultStr = HttpUtil.httpsRequest(url, "GET", "");
			
			Map<String,Object> resultMap = JsonUtil.getInstance().toBean(resultStr, Map.class);
			if(resultMap != null){
				String ticket = String.valueOf(resultMap.get("ticket"));
				Long expiresIn = Long.valueOf(resultMap.get("expires_in").toString());
				
				WxCardApiTicket.INSTANCE.setTicket(ticket);
				WxCardApiTicket.INSTANCE.setExpiresIn(expiresIn);
				WxCardApiTicket.INSTANCE.setCreateTime(System.currentTimeMillis());
				return ticket;
			}else{
				return null;
			}
		}else{
			return WxCardApiTicket.INSTANCE.getTicket();
		}
	}//end getWxCardApiTicket
	

	/**
	 * @param app_id
	 * @param times_tamp
	 * @param nonce_str
	 * @author chendawei
	 * @return
	 */
	public static String getCardSign(String app_id,String times_tamp,String nonce_str){
//		将 api_ticket（特别说明：api_ticket 相较 appsecret 安全性更高，同时兼容老版本文档中使用的 appsecret 作为签名凭证。）、
//		app_id、
//		location_id、
//		times_tamp、
//		nonce_str、
//		card_id、
//		card_type的value值进行字符串的字典序排序。 
		
		String cardApiTicket = WechatHttpUtil.getWxCardApiTicket();
		
		String[] arr = new String[] { cardApiTicket,app_id,times_tamp,nonce_str };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String returnStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			returnStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return returnStr;
	}//end getCardSign
	
	
	/**
	 * 创建卡券
	 * @author chendawei
	 * @return 
	 * {
		"errcode":0,
		"errmsg":"ok",
		"card_id":"p1Pj9jr90_SQRaVqYI239Ka1erkI"
		}
	 */
	public static String createCard(){
		
		String accessToken = AcstkenAndJstkenUtil.getInstance().getAccessToken();
		String url = "https://api.weixin.qq.com/card/create?access_token=@access_token".replaceAll("@access_token", accessToken);
		
		StringBuilder sbData = new StringBuilder();
		sbData.append("{ \"card\": {");
		sbData.append("\"card_type\": \"GROUPON\",");
		sbData.append("\"groupon\": {");
		sbData.append("\"base_info\": {");
		sbData.append("\"logo_url\":");
		sbData.append("\"http://www.supadmin.cn/uploads/allimg/120216/1_120216214725_1.jpg\",");
		sbData.append("\"brand_name\":\"海底捞\",");
		sbData.append("\"code_type\":\"CODE_TYPE_TEXT\",");
		sbData.append("\"title\": \"132 元双人火锅套餐\",");
		sbData.append("\"sub_title\": \"\",");
		sbData.append("\"color\": \"Color010\",");
		sbData.append("\"notice\": \"使用时向服务员出示此券\",");
		sbData.append("\"service_phone\": \"020-88888888\",");
		sbData.append("\"description\": \"不可与其他优惠同享\n 如需团购券发票，请在消费时向商户提出\n 店内均可");
		sbData.append("使用，仅限堂食\n 餐前不可打包，餐后未吃完，可打包\n 本团购券不限人数，建议2 人使用，超过建议人");
		sbData.append("数须另收酱料费5 元/位\n 本单谢绝自带酒水饮料\",");
		sbData.append("\"date_info\": {");
		sbData.append("\"type\": 1,");
		sbData.append("\"begin_timestamp\": 1397577600 ,");
		sbData.append("\"end_timestamp\": 1422724261");
		sbData.append("},");
		sbData.append("\"sku\": {");
		sbData.append("\"quantity\": 50000000");
		sbData.append("},");
		sbData.append("\"get_limit\": 3,");
		sbData.append("\"use_custom_code\": false,");
		sbData.append("\"bind_openid\": false,");
		sbData.append("\"can_share\": true,");
		sbData.append("\"can_give_friend\": true,");
		sbData.append("\"location_id_list\" : [123, 12321, 345345],");
		sbData.append("\"custom_url_name\": \"立即使用\",");
		sbData.append("\"custom_url\": \"http://www.qq.com\",");
		sbData.append("\"custom_url_sub_title\": \"6 个汉字tips\",");
		sbData.append("\"promotion_url_name\": \"更多优惠\",");
		sbData.append("\"promotion_url\": \"http://www.qq.com\",");
		sbData.append("\"source\": \"大众点评\"");
		sbData.append("},");
		sbData.append("\"deal_detail\": \"以下锅底2 选1（有菌王锅、麻辣锅、大骨锅、番茄锅、清补凉锅、酸菜鱼锅可");
		sbData.append("选）：\n 大锅1 份12 元\n 小锅2 份16 元\n 以下菜品2 选1\n 特级肥牛1 份30 元\n 洞庭鮰鱼卷1 份");
		sbData.append("20 元\n 其他\n 鲜菇猪肉滑1 份18 元\n 金针菇1 份16 元\n 黑木耳1 份9 元\n 娃娃菜1 份8 元\n 冬");
		sbData.append("瓜1 份6 元\n 火锅面2 个6 元\n 欢乐畅饮2 位12 元\n 自助酱料2 位10 元\"}");
		sbData.append("}");
		sbData.append("}");
		
		String resultStr = HttpUtil.httpsRequest(url, "POST", sbData.toString());
		return resultStr;
	}
	
}//end class
