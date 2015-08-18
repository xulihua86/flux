
package com.sinowel.community.controller;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinowel.community.common.Constants;
import com.sinowel.community.domain.AwardRuleEntity;
import com.sinowel.community.domain.CoinToFlowProductOrderEntity;
import com.sinowel.community.domain.CommunityReceiveCodeEntity2;
import com.sinowel.community.domain.CommunityUserBillDetailEntity;
import com.sinowel.community.domain.CommunityUserEntity;
import com.sinowel.community.domain.CommunityUserFriendEntity;
import com.sinowel.community.domain.FlowGiftEntity;
import com.sinowel.community.domain.FlowRechargeRecordEntity;
import com.sinowel.community.domain.ProductPriceEntity;
import com.sinowel.community.domain.UsedReceiveCodeEntity;
import com.sinowel.community.domain.UserAccountEntity;
import com.sinowel.community.domain.UserCheckInEntity;
import com.sinowel.community.domain.UserMessageEntity;
import com.sinowel.community.domain.UserProductItemEntity;
import com.sinowel.community.dto.CardCheckDto;
import com.sinowel.community.dto.CommunityCheckInDto;
import com.sinowel.community.dto.FlowRechargeRecordDto;
import com.sinowel.community.dto.ReceiveCodeCheckDto;
import com.sinowel.community.dto.RechargeFlowPackageGearDto;
import com.sinowel.community.dto.WxCardDto;
import com.sinowel.community.service.ICommunityMangerService;
import com.sinowel.community.service.IMessageGeneratorService;
import com.sinowel.community.service.IWXMangerService;
import com.sinowel.community.service.userCheckIn.IUserCheckInService;
import com.sinowel.demo.domain.ResponseMessage;
import com.sinowel.flowplatform.controller.FlowPlatFormController;
import com.sinowel.flowplatform.domain.receiveCode.FullFlowPackEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductDefinitionEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductItemEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeRechargeRecordEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUnusedEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUsedEntity;
import com.sinowel.flowplatform.domain.receiveCode.SingleFlowPackEntity;
import com.sinowel.flowplatform.domain.receiveCode.WxAndFlowPackProductEntity;
import com.sinowel.flowplatform.domain.receiveCode.WxFlowProductEntity;
import com.sinowel.flowplatform.domain.rechargeManager.BusinessSystemFlowRelationEntity;
import com.sinowel.flowplatform.domain.rechargeManager.BusinessUserRechargeTaskEntity;
import com.sinowel.flowplatform.domain.rechargeManager.SystemFlowPackageEntity;
import com.sinowel.flowplatform.domain.rechargeManager.TaskRechargeDetailEntity;
import com.sinowel.flowplatform.log.LogUtil;
import com.sinowel.flowplatform.service.receiveCode.IReceiveCodeService;
import com.sinowel.flowplatform.service.rechargeManager.IRechargeManagerService;
import com.sinowel.iucp.commons.utils.CheckTools;
import com.sinowel.iucp.commons.utils.DateTools;
import com.sinowel.iucp.commons.utils.JsonUtils;
import com.sinowel.iucp.exception.SystemException;


/**
 * 飞流公社统一使用的Controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private ICommunityMangerService service;
	@Autowired
	private IUserCheckInService userCheckInService;
	@Autowired
	private IMessageGeneratorService messageCheckInEverydayServiceImpl;
	@Autowired
	private IMessageGeneratorService messageCheckInCumulateServiceImpl;
	@Autowired
	private IRechargeManagerService rechargeManagerService;
	@Autowired
	private IReceiveCodeService receiveCodeService;
	@Autowired
	private IWXMangerService wxMangerService;
	@Autowired
	private LogUtil logUtil;	
	
	private static String CHECK_IN_EVERYDAY_AWARD;
	private static String CMCC = "cmcc";// 移动
	private static String CUCC = "cucc";// 联通
	private static String CTCC = "ctcc";// 电信
	private static String CURRENT_MONTH = "currentmonth";
	private static String NEXT_MONTH = "nextmonth";
	
	private static String COMMUNITY_USER_ID;
	private static String MOBILE_REPEAT_ERROR = "221001";
	private static String MOBILE_NO_FLOW_ID_ERROR = "221003";
	private static String MOBILE_ERROR = "221002";
	private static String SYSTEM_ERROR = "E99999";
	private static String PARAMETER_ERROR = "输入参数不正确";
	private static String QUANTITY_MAX = "100000000";
	private static int ERROR_COIN_AMOUNT = -9;
	
	static {
		Properties prop = new Properties();
		Properties prop1 = new Properties();
		InputStream in = FlowPlatFormController.class.getResourceAsStream("/init.properties");
		InputStream in1 = FlowPlatFormController.class.getResourceAsStream("/config.properties");
		try {
			prop.load(in);
			CHECK_IN_EVERYDAY_AWARD = prop.getProperty("CHECK_IN_EVERYDAY_AWARD").trim();
			prop1.load(in1);
			COMMUNITY_USER_ID=prop1.getProperty("communityUserID").trim();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/getUserStatus")
	@ResponseBody
	public Map<String, Object> getUserStatus(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String openId = request.getParameter("openId");
		boolean isLock = false;
		try {
			
			if(openId == null || openId.isEmpty()){
				resultMap.put("code",ResponseMessage.PARAM_ERROR );//参数错误
				return resultMap;
			}
			// 判断用户是否被锁定
			if(openId != null){
			    isLock = service.checkUserIsLock(openId);
			}
			
			// 用户被锁定则返回错误消息
			if(!isLock){
				resultMap.put("code",ResponseMessage.RECHARGE_ERROR);//用户已被锁定
				return resultMap;
			}
			
			resultMap = service.getFlowAccountsByOpenId(openId);
			
			if(!resultMap.keySet().contains("openId")){
				resultMap.clear();
				resultMap.put("code",ResponseMessage.PARAM_ERROR);
				return resultMap;
			}
			Map<String, Object> tempMap = service.getAwardRuleDetailById(Constants.FlowAccounts.AWARD_RULE_DETAIL_ID);
			resultMap.putAll(tempMap);
			resultMap.put("code",ResponseMessage.OK);
        } catch (Exception e) {
	        e.printStackTrace();
        }
		return resultMap;
	}
	
	
	
}








