package com.sinowel.netflow.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sinowel.framework.controller.BaseController;
import com.sinowel.netflow.card.model.CardConsume;
import com.sinowel.netflow.card.util.CardUtil;
import com.sinowel.netflow.card.util.ResultConsume;
import com.sinowel.netflow.common.ConfigManager;
import com.sinowel.netflow.common.JsonUtil;
import com.sinowel.netflow.common.PropertiesUtil;
import com.sinowel.netflow.common.SignatureForNetFlow;
import com.sinowel.netflow.common.WechatHttpUtil;

/**
 * 流量帐户
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	
	//我的流量账户
	private String urlMyAccount = interfaceIP+"/sfip/community/getUserStatus";
	
	//流量好友查询
	private String urlFlowFriends = interfaceIP+"/sfip/community/getFlowFriendList";
	
	//好友收赠记录查询
	private String urlFriendDetail = interfaceIP+"/sfip/community/getFlowGiftRecords";
	
	//消息查询
	private String urlQueryMessage = interfaceIP+"/sfip/community/getMessages";
	
	//流量账单
	private String urlTrafficBill = interfaceIP+"/sfip/community/getUserAccountRecord";
	
	//卡券兑换流量币
	private String urlRechargeTraffic = interfaceIP+"/sfip/community/cardCodeRechargeToFlowAccount";
	
	//获取未领取卡券列表
	private String urlSelectUnUsedCardCode = interfaceIP+"/sfip/community/selectUnUsedCardCode"; //后台接口对应：崔莹
	
	private String length = "10"; //每页10条

	/**
	 * 跳转到流量帐户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public ModelAndView toList() throws Exception {
		
		session.setAttribute("openId", openId);
		
		ModelAndView modelView = new ModelAndView("user/toList");
		
		resultMap = this.getDataFromURL(urlMyAccount+"?openId="+openId);
		
		modelView.addObject("data", resultMap);

		return modelView;
	}
	
	/**
	 * 跳转到流量好友
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toFlowFriends", method = RequestMethod.GET)
	public ModelAndView toFlowFriends() throws Exception {
		String start = request.getParameter("start");  //第几页
		if(StringUtils.isEmpty(start)){
			start = "0";
		}
		ModelAndView modelView = new ModelAndView();
		
		String url = urlFlowFriends+"?openId=@openId&start=@start&length=@length"
		        .replaceAll("@openId", openId)
				.replaceAll("@start", start)
				.replaceAll("@length", length);
		resultMap = this.getDataFromURL(url);
		
		if(resultMap == null || resultMap.size() == 0){
			modelView.setViewName("user/toFlowFriendsNo");
		}else{
			modelView.setViewName("user/toFlowFriends");
		}
		
		modelView.addObject("data", resultMap);
		modelView.addObject("openId", openId);
		modelView.addObject("start", start);
		
		return modelView;
	}
	
	/**
	 * 流量好友加载更多
	 */
	@RequestMapping(value = "/loadFriendsMore", method = RequestMethod.POST)
	public ModelAndView loadFriendsMore(){
		String start = request.getParameter("start");  //第几页
		if(StringUtils.isEmpty(start)){
			start = "0";
		}
		
		start = String.valueOf(Integer.valueOf(start)*Integer.valueOf(length));
		String url = urlFlowFriends+"?openId=@openId&start=@start&length=@length"
		        .replaceAll("@openId", openId)
				.replaceAll("@start", start)
				.replaceAll("@length", length);
		resultMap = this.getDataFromURL(url);
		
		this.writeJson(JsonUtil.getInstance().toJson(resultMap));
		
		return null;
	}
	
	
	/**
	 * 获取好友详情
	 */
	@RequestMapping(value = "/getFriendDetail", method = RequestMethod.POST)
	public ModelAndView getFriendDetail(){
		
		String friendOpenId=request.getParameter("friendOpenId");
		
		String url = urlFriendDetail+"?openId=@openId&friendOpenId=@friendOpenId"
				.replaceAll("@openId", openId)
				.replaceAll("@friendOpenId", friendOpenId);
		resultMap = this.getDataFromURL(url);
		
		
		String jsonStr = JsonUtil.getInstance().toJson(resultMap);
		writeJson(jsonStr);
		
		return null;
		
	}
	
	/**
	 * 跳转到微信卡券页面,该页面有[已领取]、[未领取]两个按钮
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toWechatTicket", method = RequestMethod.GET)
	public ModelAndView toWechatTicket() throws Exception {
		ModelAndView modelView = new ModelAndView("user/toWechatTicket");
		
		return modelView;
	}
	
	/**
	 * 已领取卡券
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toHaveReceive", method = RequestMethod.GET)
	public ModelAndView toHaveReceive() throws Exception {
		ModelAndView modelView = new ModelAndView("user/toHaveReceive");
		
		//获取jssdk的signature
		String requestURL = request.getRequestURL().toString();
		String params = request.getQueryString();
		if(!"".equals(params)&&params!=null){
			requestURL = requestURL+"?"+params;
		}
		Map<String,String> signdata = SignatureForNetFlow.getInstance().sign(requestURL);
		resultMap.putAll(signdata);
		resultMap.put("openId", openId);
		
		
		//cardSign
		String appId = ConfigManager.getValue("wechat.appId");
		String times_tamp = signdata.get("timestamp");
		String nonce_str = signdata.get("nonceStr");
		String cardSign = WechatHttpUtil.getCardSign(appId,times_tamp,nonce_str);
		resultMap.put("cardSign", cardSign);
		
		
		modelView.addObject("data", resultMap);
		
		return modelView;
	}
	
	/**
	 * 未领取卡券
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUnhaveReceive", method = RequestMethod.GET)
	public ModelAndView toUnhaveReceive() throws Exception {
		ModelAndView modelView = new ModelAndView("user/toUnhaveReceive");
		
		String appId = PropertiesUtil.getProperty("wechat.appId");
		
		String url = urlSelectUnUsedCardCode+"?openId=@openId&appId=@appId".replaceAll("@openId", openId).replaceAll("@appId", appId);
		//调用后台接口获取未领取卡券列表
		resultMap = this.getDataFromURL(url);
		
		//获取卡券详情
		List unhaveReceiveCardList = (List)resultMap.get("resultList");
		System.out.println("toUnhaveReceive is ^^^^^^^^^^^^^^^^^^^^^^^^ ");
		System.out.println(unhaveReceiveCardList);
		if(unhaveReceiveCardList != null && unhaveReceiveCardList.size() > 0){
			List cardDetailList = CardUtil.getCardDetail(unhaveReceiveCardList);
			if(cardDetailList != null && cardDetailList.size() > 0){
				resultMap.put("cardDetailList", cardDetailList);
			}else{
				resultMap.put("operateFlag", "-1");
				resultMap.put("operateMessage", "调用公众号接口失败，未能取得卡券详情。");
			}
		}else if(!"000000".equals(String.valueOf(resultMap.get("code")))){
			resultMap.put("operateFlag", "-1");
			resultMap.put("operateMessage", "调用数据接口失败，不能取得未领卡券列表。错误码为："+String.valueOf(resultMap.get("code")));
		}
		
		modelView.addObject("data", resultMap);
		return modelView;
	}
	
	
	/**
	 * 跳转到消息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toMessage", method = RequestMethod.GET)
	public ModelAndView toMessage() throws Exception {
		
		String start = request.getParameter("start");
		if(StringUtils.isEmpty(start)){
			start = "0"; //从0开始
		}
		
		ModelAndView modelView = new ModelAndView();
		
		String url = urlQueryMessage+"?openId=@openId&start=@start&length=@length"
				        .replaceAll("@openId", openId)
						.replaceAll("@start", start)
						.replaceAll("@length", length);
		
		resultMap = this.getDataFromURL(url);
		
		//^^^^^^^^^^^^^^^^^^^^^^^^^ 测试用 begin
//		List result = new ArrayList();
//		for (int i = 0; i < 10; i++) {
//			Map rowMap = new HashMap();
//			rowMap.put("createTime", "2015-7-"+i);
//			rowMap.put("content", "妈妈说这个字段好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长好长");
//			
//			result.add(rowMap);
//		}
//		resultMap.put("result", result);
		//^^^^^^^^^^^^^^^^^^^^^^^^^ 测试用 end
		
		modelView.addObject("data", resultMap);
		modelView.addObject("openId", openId);
		modelView.addObject("start", start);
		
		if(resultMap == null || resultMap.size() == 0){ //没有数据
			modelView.setViewName("user/toMessageNo");
		}else{
			modelView.setViewName("user/toMessage");
		}
		
		return modelView;
	}
	
	/**
	 * 加载更多消息
	 */
	@RequestMapping(value = "/loadMessageMore", method = RequestMethod.POST)
	public ModelAndView loadMessageMore(){
		String start = request.getParameter("start");
		
		if(StringUtils.isEmpty(start)){
			start = "0";
		}
		
		start = String.valueOf(Integer.valueOf(start)*Integer.valueOf(length));
				
		
		String url = urlQueryMessage+"?openId=@openId&start=@start&length=@length"
				.replaceAll("@openId", openId)
				.replaceAll("@start", start)
				.replaceAll("@length", length);
		resultMap = this.getDataFromURL(url);
		
		this.writeJson(JsonUtil.getInstance().toJson(resultMap));
		
		return null;
	}
	
	/**
	 * 跳转到常见问题
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toQuestion", method = RequestMethod.GET)
	public ModelAndView toQuestion() throws Exception {
		
		ModelAndView modelView = new ModelAndView("user/toQuestion");
		
		
		return modelView;
	}
	
	/**
	 * 跳转到流量账单
	 * @return
	 */
	@RequestMapping(value = "/toTrafficBill", method = RequestMethod.GET)
	public ModelAndView toTrafficBill(){
		
		ModelAndView modelView = new ModelAndView();
		
		String start = request.getParameter("start");
		if(StringUtils.isEmpty(start)){
			start = "0";
		}
		
		String url = this.urlTrafficBill+"?openId=@openId&start=@start&length=@length"
								.replaceAll("@openId", openId)
								.replaceAll("@start", start)
								.replaceAll("@length", length);
		resultMap = getDataFromURL(url);
		
		if(resultMap != null && resultMap.size() > 0){
			modelView.setViewName("user/toTrafficBill");
		}else{
			modelView.setViewName("user/toTrafficBillNo");
		}
		
		modelView.addObject("data",resultMap);
		modelView.addObject("openId",openId);
		modelView.addObject("start",start);
		return modelView;
	}
	
	/**
	 * 加载更多流量账单
	 * @return
	 */
	@RequestMapping(value = "/loadTrafficBillMore", method = RequestMethod.POST)
	public ModelAndView loadTrafficBillMore(){
		
		String start = request.getParameter("start");
		if(StringUtils.isEmpty(start)){
			start = "0";
		}
		
		start = String.valueOf(Integer.valueOf(start)*Integer.valueOf(length));
		
		String url = this.urlTrafficBill+"?openId=@openId&start=@start&length=@length"
								.replaceAll("@openId", openId)
								.replaceAll("@start", start)
								.replaceAll("@length", length);
		resultMap = getDataFromURL(url);
		
		this.writeJson(JsonUtil.getInstance().toJson(resultMap));  //返回数据
		
		return null;
	}
	
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public ModelAndView getCode(){
		
		String encryptCode = request.getParameter("encryptCode");
		JSONObject jsonObj = new JSONObject();
		if(StringUtils.isEmpty(encryptCode)){
			jsonObj.put("operateFlag", "0");
			jsonObj.put("operateMessage", "encrypt_code为空，无法获取卡券对应code");
		}else{
			try{
				String code = CardUtil.getCode(encryptCode);
				
				jsonObj.put("cardCode", code);
				jsonObj.put("operateFlag", "1");
				jsonObj.put("operateMessage", "转换成功");
			}catch(Exception ex){
				ex.printStackTrace();
				jsonObj.put("operateFlag", "0");
				jsonObj.put("operateMessage", "encryptCode转换code失败，原因为："+ex.getMessage());
			}
		}
		
		this.writeJson(jsonObj.toString());
		
		return null;
	}
	
	/**
	 * 卡券兑换流量币
	 * @param card_id
	 * @param card_code
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/exeExchageTraffic", method = RequestMethod.POST)
	public ModelAndView exeExchageTraffic(String card_id,String card_code){
		
		
		ModelAndView modelView = new ModelAndView("user/toExchangeTrafficResult");
		
		String url = urlRechargeTraffic+"?openId=@openId&cardCode=@cardCode" //&cardId=@cardId
						.replaceAll("@openId", openId)
						.replaceAll("@cardCode", card_code);
						//.replaceAll("@cardId", card_id)
						
		resultMap = this.getDataFromURL(url);
		
		String code = String.valueOf(resultMap.get("code"));
		if("000000".equals(code)){
			CardConsume card = new CardConsume();
			card.setCard_id(card_id);
			card.setCode(card_code);
			ResultConsume resultConsume;
			try {
				resultConsume = CardUtil.consumeCard(card);
				if ("40099".equals(String.valueOf(resultConsume.getErrcode()))) {
					resultMap.put("operateFlag", "0");
					resultMap.put("operateMessage", "微信核销卡券失败,原因："+resultConsume.getErrmsg());
				}else{
					resultMap.put("operateFlag", "1");
					resultMap.put("operateMessage", "卡券兑换流量币成功");
				}
			} catch (Exception e) {
				resultMap.put("operateFlag", "0");
				resultMap.put("operateMessage", "微信核销卡券发生异常，原因："+e.getMessage());
				e.printStackTrace();
			}
			
		}else{
			resultMap.put("operateFlag", "0");
			resultMap.put("operateMessage", "卡券兑换流量币接口失败,原因："+resultMap.get("errorMsg"));
		}
		
		resultMap.put("openId", openId);
		modelView.addObject("data", resultMap);
		
		return modelView;
	}
	
	@RequestMapping(value = "/toQRCard", method = RequestMethod.GET)
	public ModelAndView toQRCard(String card_id,String card_code){
		
		Map<String, String> data = new HashMap<String, String>();
		
		
		//签名
		String requestURL = request.getRequestURL().toString();
		String params = request.getQueryString();
		if(!"".equals(params)&&params!=null){
			requestURL = requestURL+"?"+params;
		}
		Map<String,String> signdata = SignatureForNetFlow.getInstance().sign(requestURL);
		data.putAll(signdata);
		System.out.println("data=============="+data);
		System.out.println("signdata=============="+signdata);
		
		try {
			data.put("qrcodeUrl", CardUtil.createQRCode(card_id, card_code));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return new ModelAndView("card/cardGetcard", data);
	}
	
	
	
}//end class
