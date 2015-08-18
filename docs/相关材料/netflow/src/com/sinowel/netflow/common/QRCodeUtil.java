package com.sinowel.netflow.common;

 
public class QRCodeUtil {

//	public static String dd(){
//		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
//
//		String cardId = "pUZWKsyjiH3inxeE1TsmTv8S3_VE";
//		QRCardObject object = new QRCardObject();
//		QRCard card = new QRCard();
//		ActionInfo ai = new ActionInfo();
//
//		card.setCard_id(cardId);
//		card.setCode(MD5.Md5(String.format("%020d", System.nanoTime()))
//				.toUpperCase());
//		card.setIs_unique_code(false);
//
//		ai.setCard(card);
//		object.setAction_name("QR_CARD");
//		object.setAction_info(ai);
//
//		url = url.replace("TOKEN", JedisUtil.getToken());
//		String resultCode = CardUtil.httpRequest(url, "POST",
//				JsonUtil.toJson(object));
//
//		System.out.println(resultCode);
//
//		TicketResult rs = JsonUtil.toBean(resultCode, TicketResult.class);
//		
//		
//		String result = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
//				+ java.net.URLEncoder.encode(rs.getTicket(), "UTF-8");
//		System.out.println(result);
//		return result;
//	}
}
