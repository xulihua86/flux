package com.sinowel.netflow.card.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sinowel.netflow.card.model.ActionInfo;
import com.sinowel.netflow.card.model.CardConsume;
import com.sinowel.netflow.card.model.EncryptCode;
import com.sinowel.netflow.card.model.QRCard;
import com.sinowel.netflow.card.model.QRCardObject;
import com.sinowel.netflow.card.model.ResultEncryptCode;
import com.sinowel.netflow.card.model.TicketResult;
import com.sinowel.netflow.common.AcstkenAndJstkenUtil;
import com.sinowel.netflow.common.HttpUtil;
import com.sinowel.netflow.common.JsonUtil;

public class CardUtil {

	public static ResultConsume consumeCard(CardConsume card) throws Exception {

		String url = "https://api.weixin.qq.com/card/code/consume?access_token=TOKEN";
		String accessToken = AcstkenAndJstkenUtil.getInstance().getAccessToken();
		url = url.replace("TOKEN", accessToken);
		String resultStr = HttpUtil.httpsRequest(url, "POST", JsonUtil
				.getInstance().toJson(card));
		ResultConsume rc = JsonUtil.getInstance().toBean(resultStr,
				ResultConsume.class);
		return rc;
	}

	public static ResultEncryptCode getEncryptCardCode(String encrypt_code)
			throws Exception {
		String url = "https://api.weixin.qq.com/card/code/decrypt?access_token=TOKEN";
		String token = AcstkenAndJstkenUtil.getInstance().getAccessToken();
		url = url.replace("TOKEN", token);

		EncryptCode code = new EncryptCode();
		encrypt_code = java.net.URLDecoder.decode(encrypt_code, "utf-8");
		code.setEncrypt_code(encrypt_code);
		String resultStr = HttpUtil.httpsRequest(url, "POST", JsonUtil
				.getInstance().toJson(code));

		ResultEncryptCode ec = JsonUtil.getInstance().toBean(resultStr,
				ResultEncryptCode.class);
		return ec;
	}
	
	/**
	 * 将encryptCode 转为code
	 * @param encryptCode
	 * @author chendawei
	 * @return
	 * @throws Exception
	 */
	public static String getCode(String encryptCode)
			throws Exception {
		
		String accessToken = AcstkenAndJstkenUtil.getInstance().getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/decrypt?access_token=@access_token".replaceAll("@access_token", accessToken);
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("encrypt_code", encryptCode);
		
		String resultStr = HttpUtil.httpsRequest(url, "POST", jsonData.toString());
		
		Map<String,Object> resultMap = (Map<String,Object>)JsonUtil.getInstance().toMap(resultStr);
		System.out.println("resultMap==========================="+resultMap);
		
		if(resultMap.containsKey("code")){
			return String.valueOf(resultMap.get("code"));
		}else{
			return "";
		}
	}
	
	/**
	 * 取得卡券详情
	 * @param list 内部结构为List<Map[cardId,cardCode]>
	 * @return List<Map[cardId,cardCode,cardImg]>
	 */
	public static List getCardDetail(List list){
		
		System.out.println("^^^^^^^^^^^^^^^^^ 取得卡券详情 getCardDetail begin ^^^^^^^^^^^^^^^^^");
		System.out.println("list："+list);
		if(list == null || list.size() == 0){
			return null;
		}
		
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		String accessToken = AcstkenAndJstkenUtil.getInstance().getAccessToken();
		String url = "https://api.weixin.qq.com/card/get?access_token=@access_token".replaceAll("@access_token", accessToken);
		
		try{
			for (int i = 0; i < list.size(); i++) {
				Map rowMap = (Map)list.get(i);
				String cardId = String.valueOf(rowMap.get("cardId"));
				String cardCode = String.valueOf(rowMap.get("cardCode"));
				
				System.out.println(">>>>>>>>>>>>>>cardId:"+cardId+"    cardCode:"+cardCode);
				
				
				JSONObject jsonData = new JSONObject();
				jsonData.put("card_id", cardId);
				
				String resultStr = HttpUtil.httpsRequest(url, "POST", jsonData.toString());
				System.out.println("^^^^^^^^^^^^^^^json："+resultStr);
				
				Map<String,Object> resultMap = (Map<String,Object>)JsonUtil.getInstance().toMap(resultStr);
				
				//成功
				if("0".equals(String.valueOf(resultMap.get("errcode")))){
					System.out.println("^^^^^^^^^^^resultMap："+resultMap);
					
					Map cardMap = (Map)resultMap.get("card");
					System.out.println("^^^^^^^^^^cardMap:"+cardMap);
					
					Map grouponMap = (Map)cardMap.get("general_coupon");
					System.out.println("^^^^^^^^^^grouponMap:"+grouponMap);
					
					Map baseInfo = (Map)grouponMap.get("base_info");
					System.out.println("^^^^^^^^^^baseInfo:"+baseInfo);
					
					
					
					String title = String.valueOf(baseInfo.get("title"));
					String brand_name = String.valueOf(baseInfo.get("brand_name"));
					String logo_url = String.valueOf(baseInfo.get("logo_url"));
					String qr_code = createQRCode(cardId,cardCode);//获取卡券的二维码
					
					Map<String,String> map = new HashMap<String,String>();
					map.put("cardId", cardId);
					map.put("cardCode", cardCode);
					map.put("logo_url", logo_url);
					map.put("qr_code", qr_code);
					map.put("title", title);
					map.put("brand_name", brand_name);
					
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^未领取卡券：getCardDetail 构造卡券详细");
					System.out.println(map);
					
					returnList.add(map);
				}
				
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("^^^^^^^^^^^^^^^^^ 取得卡券详情 getCardDetail end ^^^^^^^^^^^^^^^^^");
		return returnList;
	}

	/**
	 * 
	 * 获取卡券的二维码-码值
	 * 
	 * @author linyaoyao
	 * @version 1.0 </pre> Created on :2015年1月12日 下午3:52:54 LastModified:
	 *          History: </pre>
	 * @throws UnsupportedEncodingException
	 */
	public static String createQRCode(String cardId, String code)
			throws UnsupportedEncodingException {

		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

		QRCardObject object = new QRCardObject();
		QRCard card = new QRCard();
		ActionInfo ai = new ActionInfo();

		card.setCard_id(cardId);
		card.setCode(code);
		card.setIs_unique_code(false);

		ai.setCard(card);
		object.setAction_name("QR_CARD");
		object.setAction_info(ai);
		String accesstoken=AcstkenAndJstkenUtil.getInstance().getAccessToken();
//		String accesstoken="Z-GYE6X1pGxgLSgYAbKRj1C019eCoD3RVSM62qsX1t2P79zAM2XGdyZT5yQrSRC3_ghQQ11VtYyYK6hE_DIT5Y-WoGsQIY0RHP7El7bcHp4";
		url = url.replace("TOKEN",accesstoken);
		String resultCode = HttpUtil.httpsRequest(url, "POST", JsonUtil
				.getInstance().toJson(object));
		System.out.println(JsonUtil.getInstance().toJson(object));
		System.out.println(resultCode);

		TicketResult rs = JsonUtil.getInstance().toBean(resultCode,
				TicketResult.class);

		String result = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
				+ java.net.URLEncoder.encode(rs.getTicket(), "UTF-8");
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		
	}

}
