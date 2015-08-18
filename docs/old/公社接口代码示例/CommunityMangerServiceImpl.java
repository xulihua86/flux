package com.sinowel.community.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinowel.community.common.Constants;
import com.sinowel.community.common.Util;
import com.sinowel.community.domain.CoinToFlowProductOrderEntity;
import com.sinowel.community.domain.CommunityItemCoinExchangeEntity;
import com.sinowel.community.domain.CommunityOrderEntity;
import com.sinowel.community.domain.CommunityReceiveCodeEntity;
import com.sinowel.community.domain.CommunityReceiveCodeEntity2;
import com.sinowel.community.domain.CommunityRechargeFlowUserEntity;
import com.sinowel.community.domain.CommunityUserBillDetailEntity;
import com.sinowel.community.domain.CommunityUserEntity;
import com.sinowel.community.domain.CommunityUserFriendEntity;
import com.sinowel.community.domain.FlowGiftEntity;
import com.sinowel.community.domain.FlowProductToCoinOrderEntity;
import com.sinowel.community.domain.FlowRechargeRecordEntity;
import com.sinowel.community.domain.ProductPriceEntity;
import com.sinowel.community.domain.UsedReceiveCodeEntity;
import com.sinowel.community.domain.UserAccountEntity;
import com.sinowel.community.domain.UserAccountRecordEntity;
import com.sinowel.community.domain.UserCheckInEntity;
import com.sinowel.community.domain.UserFriendEntity;
import com.sinowel.community.domain.UserLockRecordEntity;
import com.sinowel.community.domain.UserMessageEntity;
import com.sinowel.community.domain.UserProductItemEntity;
import com.sinowel.community.domain.WxCardBlackListEntity;
import com.sinowel.community.dto.RechargeFlowPackageGearDto;
import com.sinowel.community.dto.ResultMapDto;
import com.sinowel.community.dto.WxCardDto;
import com.sinowel.community.service.ICommunityMangerService;
import com.sinowel.community.service.IMessageGeneratorService;
import com.sinowel.community.service.IMessageService;
import com.sinowel.flowplatform.domain.receiveCode.ActivityAwardEntity;
import com.sinowel.flowplatform.domain.receiveCode.ActivityEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductDefinitionEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductItemEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductTypeEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeActivityBatchEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeActivityEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeFullRecordEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeRechargeRecordEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUnusedEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUsedEntity;
import com.sinowel.flowplatform.domain.receiveCode.SingleFlowPackEntity;
import com.sinowel.flowplatform.service.rechargeManager.IRechargeManagerService;
import com.sinowel.iucp.core.ddd.query.QueryChannel;
import com.sinowel.iucp.exception.DbException;
import com.sinowel.iucp.exception.SystemException;

@Service
@Transactional
public class CommunityMangerServiceImpl implements ICommunityMangerService {
	@Autowired
	private QueryChannel queryChannel;
	@Autowired
	IRechargeManagerService rechargeManagerService;
	@Autowired
	private IMessageService messageServiceImpl;
	
	private static final Logger logger = Logger.getLogger(CommunityMangerServiceImpl.class);

	private static String smsJdbcUrl;
	private static String smsUserName;
	private static String smsPassword;
	private static String expireTime;
	private static String cardCodeClassNames;

	static {
		Properties prop = new Properties();
		InputStream in = CommunityMangerServiceImpl.class
				.getResourceAsStream("/config.properties");
		try {
			prop.load(in);
			smsJdbcUrl = prop.getProperty("SMSJdbcUrl").trim();
			smsUserName = prop.getProperty("SMSUserName").trim();
			smsPassword = prop.getProperty("SMSPassword").trim();
			expireTime = prop.getProperty("expireTime").trim();
			cardCodeClassNames = prop.getProperty("cardCodeClassNames").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getFlowAccountsByOpenId(String openId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = queryChannel.queryMapResult(
				"getFlowPackageByUserId", new Object[] { openId });
		if (list != null && list.size() > 0) {
			resultMap = list.get(0);
		}
		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public boolean isExistUserByOpenId(String openId) throws SystemException {
		boolean b = false;
		try {
			b = CommunityUserEntity.IsExistUserByOpenId(openId);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public Map<String, Object> getAwardRuleDetailById(String awardRuleDetailId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = queryChannel.queryMapResult(
				"getAwardRuleDetailById", new Object[] { awardRuleDetailId });
		if (list != null && list.size() > 0) {
			resultMap = list.get(0);
		} else {
			resultMap.put("awardValue", 0);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getCommunityBillById(String openId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = queryChannel.queryMapResult(
				"getCommunityBillById", new Object[] { openId });
		if (list != null && list.size() > 0) {
			resultMap = list.get(0);
		}
		return resultMap;
	}

	@Override
	public List<CommunityUserBillDetailEntity> getUserBillListById(
			String openId, int start, int length) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("start", start);
		map.put("length", length);
		return queryChannel.findByNamedQuery("getUserBillListById", map);
	}

	@Override
	public List<CommunityUserFriendEntity> getUserFriendListById(String openId,
			int start, int length) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("start", start);
		map.put("length", length);
		return queryChannel.findByNamedQuery("getUserFriendListById", map);
	}

	@Override
	public List<CommunityReceiveCodeEntity2> getTransferRecordsById(
			String openId, String friendOpenId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("friendOpenId", friendOpenId);
		return queryChannel.findByNamedQuery("getTransferRecordsById", map);
	}

	@Override
	public List<UserMessageEntity> manageMessageRecordsById(String openId,
			int start, int length) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("start", start);
		map.put("length", length);

		List<UserMessageEntity> tempList = queryChannel.findByNamedQuery(
				"getMessageRecordsById", map);
		if (tempList != null && tempList.size() > 0) {
			List<Integer> paramsList = new ArrayList<Integer>();
			for (UserMessageEntity entity : tempList) {
				paramsList.add((Integer) entity.getId());
			}
			UserMessageEntity.updateIsViewedStatus(paramsList.toArray());
		}

		return tempList;
	}

	@Override
	public String getCommunityMobileVerifyCode(Map<String, Object> paramMap)
			throws Exception {
		String mobileNo = paramMap.get("mobileNo").toString();

		queryChannel.findByNamedQuery("getCommunityMobileVerifyCode", paramMap);
		Object smsContent = paramMap.get("smsContent");
		String rtnStr = paramMap.get("rtnStr").toString();

		if (smsContent != null && !smsContent.toString().isEmpty()) {
			String smsMessage = "【流量公社】验证码%s，您正在使用飞流流量公社，请填写验证码进行操作。";
			sendMessage(mobileNo, String.format(smsMessage, smsContent.toString()));
		}
		return rtnStr;
	}

	private boolean sendMessage(String telphone, String content) {
		Connection conn = null;
		PreparedStatement pst = null;
		Date now = new Date();
		String rcompleteDate = (new SimpleDateFormat("yyyy-MM-dd")).format(now);
		String sendDate = (new SimpleDateFormat("yyyyMMdd")).format(now);
		String sql = "INSERT INTO SM_Send_SM_List(ServiceID,SMContent,SendTarget,Priority,RCOMPLETETIMEBEGIN,RCompleteTimeEnd,RCompleteHourBegin,RCompleteHourEnd,RequestTime,RoadBy,SendTargetDesc,FeeValue,LinkID,Pad1,Pad2,Pad3,Pad4,Pad5) VALUES ('VerificationCode','"
				+ content
				+ "','"
				+ telphone
				+ "','0',to_date('"
				+ rcompleteDate
				+ "','yyyy-mm-dd'),to_date('"
				+ rcompleteDate
				+ "','yyyy-mm-dd'),'0000','1440',sysdate,'1','VerificationCode|"
				+ sendDate + "|1|||','0','','','','6','','')";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(smsJdbcUrl, smsUserName,
					smsPassword);
			pst = conn.prepareStatement(sql);
			pst.executeQuery();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public ReceiveCodeUnusedEntity checkReceiveCode(String receiveCode)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ReceiveCodeUnusedEntity
					.getReceiveCodeUnusedEntity(receiveCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public ReceiveCodeActivityEntity getReceiveCodeDetail(String awardId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ReceiveCodeActivityEntity
					.getReceiveCodeActivityByActivityId(awardId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public UsedReceiveCodeEntity getUsedReceiveCodeByCode(String receiveCode)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return UsedReceiveCodeEntity.getUsedReceiveCodeByCode(receiveCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public FlowGiftEntity getFlowGiftByCode(String receiveCode)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return FlowGiftEntity.getEntity(receiveCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public UserProductItemEntity getUserProductItemByWx(String productItemCode)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return UserProductItemEntity
					.getUserProductItemByWx(productItemCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public ProductItemEntity getProductItemByWx(String wxCode)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ProductItemEntity.getProductItemByWx(wxCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public ProductPriceEntity getProductPriceById(int productId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ProductPriceEntity.get(productId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public ProductDefinitionEntity getProductDefinitionById(int productId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ProductDefinitionEntity.get(productId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public ProductTypeEntity getProductTypeById(String productTypeId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return ProductTypeEntity.get(productTypeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public CommunityItemCoinExchangeEntity getItemCoinExchange(String packId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return CommunityItemCoinExchangeEntity.getItemCoinExchange(packId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public void newCreateCommunity(CommunityUserEntity userEntity,
			UserAccountEntity userAccountEntity,
			UserCheckInEntity userCheckInEntity) throws SystemException {

		try {
			// 添加公社用户
			userEntity.save();
			// 添加公社用户流量账户
			UserAccountEntity.getEntityRepository().add(userAccountEntity);
			// 添加用户签到
			UserCheckInEntity.getEntityRepository().add(userCheckInEntity);

		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}

	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public String selectFriendsService(String openId, int quantity)
			throws SystemException {
		try {
			// 随机得到领取码
			CommunityUserEntity user = CommunityUserEntity.getEntityRepository().
					get(CommunityUserEntity.class, openId);
			
			String receiveCode = Util.genCoinGiftCode((Integer) user.getId(),
					Timestamp.valueOf(user.getCreateTime()));

			// 添加流量赠送
			FlowGiftEntity flowGiftEntity = flowGiftEntitySetData(openId, null, quantity, receiveCode, null, 3,0);
			flowGiftEntity = FlowGiftEntity.save(flowGiftEntity);
			if(flowGiftEntity == null || flowGiftEntity.getFlowGiftId() <= 0) {
				throw new SystemException("保存失败！");
			}

			return receiveCode;
		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void turnGiveSend(String openId, String receiveCode)
			throws SystemException {

		try {
			// 根据领取码得到得到赠送流量币数量
			FlowGiftEntity flowGiftEntity = FlowGiftEntity
					.getEntity(receiveCode);
			if (flowGiftEntity == null) {
				throw new SystemException("数据库异常");
			}
			int quantity = flowGiftEntity.getCoinAmount();
			int flowGiftId = (Integer) flowGiftEntity.getId();

			
			// 更改公社用户流量账户
			UserAccountEntity userAccountEntity = UserAccountEntity.get(openId);
			int availableAmount = userAccountEntity.getAvailableAmount();
			if(availableAmount<quantity || availableAmount < 0){
				throw new SystemException("可用余额出现异常!");
			}
			userAccountEntity.setAvailableAmount(availableAmount - quantity);
			userAccountEntity.setFrozenAmount(userAccountEntity
					.getFrozenAmount() + quantity);
			userAccountEntity.setOutAmount(quantity
					+ userAccountEntity.getOutAmount());
			if (!(1 == UserAccountEntity.updateUserAccount(userAccountEntity.getAvailableAmount(),userAccountEntity.getFrozenAmount(),userAccountEntity.getOutAmount(),
					userAccountEntity.getInAmount(),quantity,openId)))
				throw new SystemException("更改公社用户流量账户失败!");

			// 更改流量赠送 status :3 （不可用） 改为 0（未领取）添加过期时间
			if (!(1 == FlowGiftEntity.updateStatusAndExpireTime(receiveCode, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date().getTime() + Long.parseLong(expireTime)))))
				throw new SystemException("更改订单记录失败!");
		
			// 添加用户账户操作记录
			addUserAccountRecordEntitySetData(openId, 1, "3",
					String.valueOf(flowGiftId), "赠送流量币", quantity).save();
			
			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_16),
					iMessageGeneratorServiceImpl(valueMap), valueMap);

		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void receiveCodeRechargeToFlowAccount(String openId, String code,
			int type, int quantity) throws SystemException {

		try {
			// 判断是企业还是公社 0:企业 1:公社
			if (type == 0) {
				// 根据领取码得到是否未使用
				if (checkCodeIsUsed(code)) {
					ReceiveCodeUnusedEntity unusedCodeEntity = ReceiveCodeUnusedEntity.getReceiveCodeUnusedEntity(code);
					int productId = unusedCodeEntity.getProductId();
					// 更改公社用户流量账户
					if (!(1 == UserAccountEntity
							.update(updateUserAccountEntitySetData(4, openId,
									quantity))))
						throw new SystemException("更改公社用户流量账户失败!");

					// 添加用户产品件
					String productItemCode = null;
					String expireTime = null;

					UserProductItemEntity userProductItemEntity = UserProductItemEntity
							.save(addUserProductItemEntitySetData(openId, 0,
									code, productId, productItemCode,
									expireTime, 1, 1, quantity));
					// 得到产品件ID
					int userProductItemId = (Integer) userProductItemEntity
							.getId();

					// 添加已领取领取码记录
					ResultMapDto resultMapDto = queryChannel.querySingleResult(
							"getAvailableTimeAndExpireTimeByCode",
							new Object[] { code });
					String availableTime = resultMapDto.getAvailableTime();
					String expireTime2 = resultMapDto.getExpireTime();
					UsedReceiveCodeEntity.getEntityRepository().add(addUsedReceiveCodeEntitySetData(code, openId, productId,
							quantity, availableTime, expireTime2, "now", 1,
							userProductItemId, 2, quantity, ""));

					// 核销领取码
					ReceiveCodeUnusedEntity unrcue = findUnusedInfo(code);
					String itemPrice = queryChannel.querySingleResult(
							"getItemPriceByProductId",
							new Object[] { productId });
					if (unrcue != null
							&& !changeCodeUnuseToUse(unrcue,2, code, openId, 0,
									itemPrice))
						throw new SystemException("核销失败!");
					
					ReceiveCodeUsedEntity rcue = new ReceiveCodeUsedEntity();
					BigDecimal bd = new BigDecimal(itemPrice);
				    rcue.setPayAmount(bd);
				    rcue.setReceiveCode(code);
				    rcue.setReceivedType(2);
				    rcue.setId(code);
    				ReceiveCodeUsedEntity.saveReceiveCodeUsedEntity(rcue);
					//活动表处理
					updateActivityReceiveCodeQty(code,itemPrice); 

					// 添加用户账户操作记录 
					addUserAccountRecordEntitySetData(openId, 0, "2",
							code, "企业活动领取码兑换流量币", quantity).save();

					// 添加公社用户消息
					Map<String, Object> valueMap = new HashMap<String, Object>();
					valueMap.put("name", "领取码");
					valueMap.put("quantity", quantity);
					messageServiceImpl.createMessage(openId, messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_4),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				} else if (checkIsExistInUsedReceiveCode(openId,code)) {// 在已领用领取码记录表查询是否存在并未使用
					ReceiveCodeUnusedEntity unusedCodeEntity = ReceiveCodeUnusedEntity.getReceiveCodeUnusedEntity(code);
					int productId = unusedCodeEntity.getProductId();
					// 更改用户产品件 status :已使用1
					
					// 根据领取码得到产品件id
					UserProductItemEntity userProductItemEntity = UserProductItemEntity.getEntityByCode(code);
					
					userProductItemEntity.setStatus(1);
					if (!(1 == UserProductItemEntity.updateUserProductItem(userProductItemEntity)))
						throw new SystemException("更改用户产品件失败!");

					// 更改已领用领取码记录：使用方式 1 状态：已使用  user_time
					String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					UsedReceiveCodeEntity usedReceiveCodeEntity = new UsedReceiveCodeEntity();
					//UsedReceiveCodeEntity usedReceiveCodeEntity = UsedReceiveCodeEntity.getUsedReceiveCodeByCode(code);
					usedReceiveCodeEntity.setCode(code);
					usedReceiveCodeEntity.setRechargeStatus(2);
					usedReceiveCodeEntity.setUsedTime(now);
					if (!(1 == UsedReceiveCodeEntity
							.updateUsedReceiveCode(usedReceiveCodeEntity)))
						throw new SystemException("更改已领用领取码记录失败!");

					// 更改领取码已用表（receive_code_used）received_type：2：存到流量账户
					// pay_amount：人民币最大成本 is_recharged 1已充值
					String receivedType = "2";
					// 通过productId得到最大成本
					String payAmount = queryChannel.querySingleResult(
							"getItemPriceByProductId",
							new Object[] { productId });
					String isRecharged = "1";
					if (!(1 == UsedReceiveCodeEntity.updateByCode(receivedType,
							payAmount, isRecharged, code)))
						throw new SystemException("更改领取码已用表失败!");
					
					// 更改公社用户流量账户
					if (!(1 == UserAccountEntity
							.update(updateUserAccountEntitySetData(4, openId,
									quantity))))
						throw new SystemException("更改公社用户流量账户失败!");

					ReceiveCodeUsedEntity rcue = new ReceiveCodeUsedEntity();
					BigDecimal bd = new BigDecimal(payAmount);
				    rcue.setPayAmount(bd);
				    rcue.setReceivedType(2);
				    rcue.setReceiveCode(code);
				    rcue.setId(code);
    				ReceiveCodeUsedEntity.saveReceiveCodeUsedEntity(rcue);
					//活动表处理 
					updateActivityReceiveCodeQty(code,payAmount); 
					// 添加用户账户操作记录 
					addUserAccountRecordEntitySetData(openId, 0, "2",
							code, "企业活动领取码兑换流量币", quantity).save();

					// 添加公社用户消息
					Map<String, Object> valueMap = new HashMap<String, Object>();
					valueMap.put("name", "领取码");
					valueMap.put("quantity", quantity);
					messageServiceImpl.createMessage(openId, messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_4),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				} else {
					throw new SystemException("企业活动领取码不能转存流量币！");
				}

			} else if (type == 1) {

				FlowGiftEntity flowGiftEntity = FlowGiftEntity.getEntity(code);

				// 得到赠送人openId
				String userOpenId = flowGiftEntity.getUserOpenId();

				// 根据领取码得到流量赠送Id
				int flowGiftId = (Integer) flowGiftEntity.getId();

				// 更改公社用户流量账户(赠送人)
				if (!(1 == UserAccountEntity
				.modifyAccountFrozenSubModifyAmount(userOpenId,quantity)))
				throw new SystemException("更改公社用户流量账户失败!");

				// 更改公社用户流量账户(领取人)
				if (!(1 == UserAccountEntity
				.modifyAccountAvailable(openId,quantity)))
				throw new SystemException("更改公社用户流量账户失败!");

				// 更改流量赠送 status :0（未领取） 改为 1（已领取）
				if (!(1 == FlowGiftEntity.updateFlowGift(updateFlowGiftEntitySetData(
						code, null, null, 1, "now"))))
					throw new SystemException("更改订单记录失败!");

				// 添加用户账户操作记录(领取人) 
				addUserAccountRecordEntitySetData(openId, 0, "3",
						String.valueOf(flowGiftId), "领取流量币", quantity).save();

				if (!userOpenId.equals(openId)) {
					// 判断赠送人与领取人是否有联系：有：更新表记录 没有：添加表记录
					if (checkOpenIdAndFriendOpenId(userOpenId, openId)) {
						// 更新流量好友(赠送人)
						if (!(1 == updateUserFriendEntity(userOpenId, openId,
								quantity, "赠送流量币")))
							throw new SystemException("更新好友表记录失败!");
					} else {
						// 添加流量好友(赠送人)
						UserFriendEntity.getEntityRepository().add(addUserFriendEntitySetData(userOpenId, openId,
								"赠送流量币" + quantity));
					}
					if (checkOpenIdAndFriendOpenId(openId, userOpenId)) {
						// 更新流量好友(领取人)
						if (!(1 == updateUserFriendEntity(openId, userOpenId,
								quantity, "收到流量币")))
							throw new SystemException("更新好友表记录失败!");
					} else {
						// 添加流量好友(领取人)
						UserFriendEntity.getEntityRepository().add(addUserFriendEntitySetData(openId, userOpenId,
								"领取流量币" + quantity));
					}
				}
				// 添加公社用户消息(赠送人)
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("quantity", quantity);

				messageServiceImpl.createMessage(userOpenId, messageServiceImpl
						.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_12),	
						iMessageGeneratorServiceImpl(valueMap), valueMap);

				// 添加公社用户消息(领取人)
				messageServiceImpl.createMessage(openId, messageServiceImpl
						.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_7),
						iMessageGeneratorServiceImpl(valueMap), valueMap);

			}

				
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/*
	 * 验证是否存在好友记录
	 */
	private boolean checkOpenIdAndFriendOpenId(String openId,
			String friendOpenId) throws SystemException {
		try {
			return UserFriendEntity.IsExistOpenIdAndFriendOpenId(openId,
					friendOpenId);
		} catch (DbException e) {
			throw new SystemException(e);
		}
	}

	

	private boolean checkCodeIsUsed(String code) throws SystemException {
		try {
			return UsedReceiveCodeEntity.checkCodeIsUsed(code);
		} catch (DbException e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public List<RechargeFlowPackageGearDto> getRechargeFlowPackageGearList(
			String openId, String operatorType) throws SystemException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("operatorType", operatorType);

			return queryChannel.findByNamedQuery(
					"getRechargeFlowPackageGearList", params);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public Map<String, Object> flowCurrencyRecharge(String openId,
			int effectType, String phone, int quantity, int productId)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 流量包信息集合
			RechargeFlowPackageGearDto rfpgDto = queryChannel
					.querySingleResult(
							"getRechargeFlowPackageGearDtoByproductId",
							new Object[] { productId });
			
			if (rfpgDto == null) {
				throw new SystemException("查询数据不存在！");
			}

			// 更改公社用户流量账户
			UserAccountEntity userAccountEntity =updateUserAccountEntitySetData(1, openId, quantity);
			if (!(1 == UserAccountEntity.updateUserAccount(userAccountEntity.getAvailableAmount(), userAccountEntity.getFrozenAmount()
					, userAccountEntity.getOutAmount(), userAccountEntity.getInAmount(), quantity, userAccountEntity.getOpenId())))
				throw new SystemException("更改公社用户流量账户失败!");

			// 添加流量币兑换流量产品订单
			String flowSize = rfpgDto.getFlowSize();
			String operatorType = rfpgDto.getOperatorType();
			String productSnapshot ="";
			if(Constants.CMCC.equals(operatorType)){
				productSnapshot = "移动流量包" + flowSize + "M";
			}else if(Constants.CUCC.equals(operatorType)){
				productSnapshot = "联通流量包" + flowSize + "M";
			}else if(Constants.CTCC.equals(operatorType)){
				productSnapshot = "电信流量包" + flowSize + "M";
			}else{
				throw new SystemException("运营商类型错误！ ");
			}
			CoinToFlowProductOrderEntity coinToFlowProductOrderEntity = CoinToFlowProductOrderEntity
					.save(addCoinToFlowProductOrderEntitySetData(openId,
							productId, 1, productSnapshot, quantity,
							1, 0, null, null));

			// 添加用户产品件
			String orderId = coinToFlowProductOrderEntity.getOrderId();
			String productItemCode = null;
			
			String expireTime = null;
			UserProductItemEntity userProductItemEntity = UserProductItemEntity
					.save(addUserProductItemEntitySetData(openId, 1, orderId,
							productId, productItemCode, expireTime, 0, 1,
							quantity));
			// 添加用户账户操作记录 
			addUserAccountRecordEntitySetData(openId, 1, "4", orderId,
					"流量币充值到手机", quantity).save();

			// 添加流量充值记录
			int userItemId = (Integer) userProductItemEntity.getId();
			String flowPackId = rfpgDto.getFlowPackId();
			FlowRechargeRecordEntity flowRechargeRecordEntity = FlowRechargeRecordEntity
					.save(addFlowRechargeRecordEntitySetData(openId,
							effectType, phone, operatorType,
							Integer.valueOf(flowPackId),
							Integer.valueOf(flowSize), productSnapshot, null, 1,
							null, userItemId, 2, String.valueOf(quantity)));

			// 返回订单Id与充值记录Id
			int rechargeId = (Integer) flowRechargeRecordEntity.getId();
			map.put("rechargeId", rechargeId);
			map.put("rfpgDto", rfpgDto);

			return map;

		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void restoreToFlowAccount(String openId, int quantity, String orderId,String rechargeId)
			throws SystemException {
		try {
			// 更改公社用户流量账户
			if (!(1 == UserAccountEntity.update(updateUserAccountEntitySetData(
					3, openId, quantity))))
				throw new SystemException("更改公社用户流量账户失败!");

			// 更改流量币兑换流量产品订单
			if (!(1 == CoinToFlowProductOrderEntity
					.updateStatusByOrderId(orderId,3)))
				throw new SystemException("更改流量币兑换流量产品订单失败!");

			// 根据订单得到产品件id
			int productItemId = UserProductItemEntity.getIdByOrderId(orderId);
			// 更改用户产品件
			if (!(1 == UserProductItemEntity.updateStatusById(productItemId,1)))
				throw new SystemException("更改用户产品件失败!");
			
			if (!(1 == FlowRechargeRecordEntity.updateFlowRechargeRecordStatus(rechargeId, "4")))
				throw new SystemException("更改流量充值记录失败!");
			
			// 添加用户账户操作记录 5：流量币充值到手机回退
			addUserAccountRecordEntitySetData(openId, 0, "5", null,
					"流量币充值到手机回退", quantity).save();

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_8),
					iMessageGeneratorServiceImpl(valueMap), valueMap);

		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void wxRestoreToFlowAccount(String openId, int userItemId, String wxCode,String rechargeId)
			throws SystemException {
		try {
			//得到用户产品件实体
			UserProductItemEntity userProductItemEntity = UserProductItemEntity.getUserProductItemEntityByWxCode(wxCode);
			
			int productId = userProductItemEntity.getProductId();
			//根据产品id得到买入价格
			int quantity = UserProductItemEntity.getQuantityByProductId(productId);
			
			// 更改用户产品件 status  改为已使用1 数量quantity
			if (!(1 == updateUserProductItemStatusBySourceData(wxCode,quantity)))
				throw new SystemException("更改用户产品件失败!");
			
			//得到源类型：如果是0 领取码，则 收入4  如果是1流量币购买，则为3
			int sourceType = userProductItemEntity.getSourceType();
			if(0==sourceType){
				// 更改公社用户流量账户
				if (!(1 == UserAccountEntity.update(updateUserAccountEntitySetData(
						4, openId, quantity))))
					throw new SystemException("更改公社用户流量账户失败!");
			}else if(1==sourceType){
				// 更改公社用户流量账户
				if (!(1 == UserAccountEntity.update(updateUserAccountEntitySetData(
						3, openId, quantity))))
					throw new SystemException("更改公社用户流量账户失败!");
			}else{
				throw new SystemException("类型错误!");
			}

			//更改充值记录状态
			if (!(1 == FlowRechargeRecordEntity.updateFlowRechargeRecordStatus(rechargeId, "4")))
				throw new SystemException("更改流量充值记录失败!");
			
			// 添加用户账户操作记录 
			addUserAccountRecordEntitySetData(openId, 0, "6", null,
					"卡券兑换回退到流量账户", quantity).save();
			
			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_8),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
			
		} catch (Exception e) {
			throw new SystemException(e);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void codeRestoreToFlowAccount(String openId, String receiveCode,
			String rechargeId) throws SystemException {
		try {
		    int productId = getProductIdByCode(receiveCode);
		    int quantity = getQuantityByProductId(productId);
			
			// 根据领取码得到产品件id
			
			int productItemId = UserProductItemEntity
					.getIdByOrderId(receiveCode);
			
			// 更改用户产品件
			if (!(1 == UserProductItemEntity.updateStatusById(productItemId,1)))
				throw new SystemException("更改用户产品件失败!");

			// 更改已领用领取码记录：使用方式 1 状态：已使用  user_time
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			UsedReceiveCodeEntity usedReceiveCodeEntity = new UsedReceiveCodeEntity();
			//UsedReceiveCodeEntity usedReceiveCodeEntity = UsedReceiveCodeEntity.getUsedReceiveCodeByCode(receiveCode);
			usedReceiveCodeEntity.setCode(receiveCode);
			usedReceiveCodeEntity.setRechargeStatus(2);
			usedReceiveCodeEntity.setUsedTime(now);
			if (!(1 == UsedReceiveCodeEntity
					.updateUsedReceiveCode(usedReceiveCodeEntity)))
				throw new SystemException("更改已领用领取码记录失败!");

			// 更改领取码已用表（receive_code_used）received_type：2：存到流量账户
			// pay_amount：人民币最大成本 is_recharged 1已充值
			String receivedType = "2";
			// 通过productId得到最大成本
			String payAmount = queryChannel.querySingleResult(
					"getItemPriceByProductId",
					new Object[] { productId });
			String isRecharged = "1";
			if (!(1 == UsedReceiveCodeEntity.updateByCode(receivedType,
					payAmount, isRecharged, receiveCode)))
				throw new SystemException("更改领取码已用表失败!");
			
			// 更改公社用户流量账户
			if (!(1 == UserAccountEntity
					.update(updateUserAccountEntitySetData(4, openId,
							quantity))))
				throw new SystemException("更改公社用户流量账户失败!");

			//活动表处理
			updateActivityReceiveCodeQty(receiveCode,payAmount); 
			// 添加用户账户操作记录
			addUserAccountRecordEntitySetData(openId, 0, "7",
					receiveCode, "领取码退存到流量帐户", quantity).save();
			
			// 更改流量充值记录 status 4失败退存到账户流量币
			if (!(1 == FlowRechargeRecordEntity.updateFlowRechargeRecordStatus(rechargeId, "4")))
				throw new SystemException("更改流量充值记录失败!");
			
			// 更改已领用领取码记录： 状态：3退存
			if (!(1 == ReceiveCodeRechargeRecordEntity.updateFlowRechargeRecordStatus(receiveCode,3)))
				throw new SystemException("更改领取码充值记录表失败!");

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_8),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		
	}
	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void cardCodeRechargeToFlowAccount(String openId, String cardCode)
			throws SystemException {
		try {
			//得到用户产品件实体
			UserProductItemEntity userProductItemEntity = UserProductItemEntity.getUserProductItemEntityByWxCode(cardCode);
			
			int productId = userProductItemEntity.getProductId();
			//根据产品id得到买入价格
			int quantity = UserProductItemEntity.getQuantityByProductId(productId);
			//add by dengyinging 2015-07-25 处理>50个流量币的非法数据
			if(quantity>50)
			{
				CommunityUserEntity entity=new CommunityUserEntity();
				entity.setOpenId(openId);
				List<CommunityUserEntity> list=new ArrayList<CommunityUserEntity>();
				list.add(entity);
				CommunityUserEntity.changeIslocked(list);
				//保存锁定用户记录
				UserLockRecordEntity ulre = new UserLockRecordEntity();
				ulre.setOpenId(openId);
				ulre.setOperationName(4);
				UserLockRecordEntity.saveUserLockRecord(ulre);
				
				updateCodeIsBigCode(cardCode,openId,productId);
				return;
			}
			//end by dengyingming
			// 更改用户产品件 status  改为已使用1 数量quantity
			if (!(1 == UserProductItemEntity.updateUserProductItemStatusAndUserTypeBySourceData(cardCode,quantity,1)))
				throw new SystemException("更改用户产品件失败!");
			
			// 更改公社用户流量账户
			if (!(1 == UserAccountEntity.update(updateUserAccountEntitySetData(
					4, openId, quantity))))
				throw new SystemException("更改公社用户流量账户失败!");
			
			//添加流量产品兑换流量币定单
			FlowProductToCoinOrderEntity entity = new FlowProductToCoinOrderEntity();
			String orderId = FlowProductToCoinOrderEntity.genId();
			entity.setOrderId(orderId);
			entity.setUserProductItemId(userProductItemEntity.getId());
			entity.setCreatorOpenId(openId);
			entity.setProductId(productId);
			entity.setProductItemCode(userProductItemEntity.getProductItemCode());
			entity.setCoinAmount(quantity);
			entity.setStatus(2);
			FlowProductToCoinOrderEntity.getEntityRepository().add(entity);

			// 添加用户账户操作记录
			addUserAccountRecordEntitySetData(openId, 0, "8", null,
					"卡券兑换成流量币", quantity).save();
			
			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("name", "卡券");
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_4),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
			
		} catch (Exception e) {
			throw new SystemException(e);
		}
		
	}
	public void expireCodeHander(){
		// 循环得到领取码不可用领取码
		List<String> receiveCodeList = null;
		try {
			receiveCodeList = getExpireReceiveCodeByExpireTime();
		} catch (SystemException e) {
			return;
		}	
		if (receiveCodeList != null && receiveCodeList.size() > 0) {
			for (int i = 0; i < receiveCodeList.size(); i++) {
				// 得到flow_gift表中id
				String code = receiveCodeList.get(i);
				
				try {
					//流程处理
					expireTask(code);
				} catch (SystemException e) {
					logger.info("流量币过期领取码【"+code+"】出现异常\n");
					continue;
				}
			}
		}	
	}
	
	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void expireTask(String code) throws SystemException {
		try {
			FlowGiftEntity flowGiftEntity = FlowGiftEntity.getEntity(code);
			String openId = flowGiftEntity.getUserOpenId();
			int quantity = flowGiftEntity.getCoinAmount();
			flowGiftEntity.setStatus(2);
			// 更改流量赠送 status :2 （已过期)
			if (!(1 == FlowGiftEntity.update(flowGiftEntity)))
				throw new SystemException("更改订单记录失败!");

			// 更改公社用户流量账户
			if (!(1 == UserAccountEntity.update(updateUserAccountEntitySetData(
					3, openId, quantity))))
				throw new SystemException("更改公社用户流量账户失败!");

			// 添加用户账户操作记录
			addUserAccountRecordEntitySetData(openId, 0, "3", null,
					"赠送流量币回退", quantity).save();

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_13),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void flowMoneytoFlowPackageSuccess(FlowRechargeRecordEntity frre)
			throws SystemException {
		try {
			String openId = frre.getOpenId();
			int quantity = 0;
			if (frre.getSourceData() != null) {
				quantity = Integer.parseInt(frre.getSourceData());
			}
			int productId = frre.getUserItemId();
			String recchargeMobileNo = frre.getRechargeMobileNo();

			// 通过产品件Id得到订单Id
			String orderId = queryChannel.querySingleResult(
					"getOrderIdByProductId", new Object[] { productId });
			// 更改公社用户流量账户
			if (!(1 == UserAccountEntity.modifyAccountFrozenSubModifyAmount(openId, quantity)))
				throw new SystemException("更改公社用户流量账户失败!");

			// 更改流量币兑换流量产品订单

			if (!(1 == CoinToFlowProductOrderEntity
					.updateStatusByOrderId(orderId,1)))
				throw new SystemException("更改流量币兑换流量产品订单失败!");

			// 更改用户产品件
			// 根据订单得到产品件id
			int productItemId = UserProductItemEntity.getIdByOrderId(orderId);
			if (!(1 == UserProductItemEntity.updateStatusById(productItemId,1)))
				throw new SystemException("更改用户产品件失败!");

			// 更改流量充值记录 status 2充值成功
			// 更改流量充值记录 添加taskId
			if (!(1 == FlowRechargeRecordEntity
					.updateFlowRechargeRecordStatusByRecharging(String.valueOf(frre.getId()),"2")))
				throw new SystemException("更改流量充值记录失败!");

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("recchargeMobileNo", recchargeMobileNo);

			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_9),
					iMessageGeneratorServiceImpl(valueMap), valueMap);

		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void flowMoneytoFlowPackageFail(FlowRechargeRecordEntity frre)
			throws SystemException {
		try {
			// 更改流量充值记录 status 3充值失败
			// 更改流量充值记录 
			if (!(1 == FlowRechargeRecordEntity
					.updateFlowRechargeRecordStatusByRechargingFail(String.valueOf(frre.getId()),"3",frre.getStatusRemark())))
				throw new SystemException("更改流量充值记录失败!");

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("recchargeMobileNo", frre.getRechargeMobileNo());

			messageServiceImpl.createMessage(frre.getOpenId(),
					messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_10),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private IMessageGeneratorService iMessageGeneratorServiceImpl(
			Map<String, Object> valueMap) {
		return new IMessageGeneratorService() {

			@Override
			public String process(String templateContent,
					Map<String, Object> valueMap) throws Exception {
				for (Entry<String, Object> entry : valueMap.entrySet()) {
					templateContent = templateContent.replace(
							"{" + entry.getKey() + "}", entry.getValue()
									.toString());
				}
				return templateContent;
			}

		};
	}
	private int updateUserProductItemStatusBySourceData(String wxCode,int quantity) throws Exception{
		
		return UserProductItemEntity.updateUserProductItemStatusBySourceData(wxCode,quantity);
	}
	/*
	 * 更新流量好友赋值
	 */
	private int updateUserFriendEntity(String openId, String sendId,
			int quantity, String msg) throws Exception {
		UserFriendEntity entity = new UserFriendEntity();

		entity.setOpenId(openId);
		entity.setLastContact(msg + quantity);
		entity.setFriendOpenId(sendId);
		return UserFriendEntity.update(entity);

	}

	/*
	 * 添加用户产品件赋值 
	 * sourceType:0:活动领取码领取，1:公社流量币购买流量产品
	 * sourceData:产品件来源为领取码领取，此字段对应领取码表中的领取码字段；如果产品件来源为流量币购买流量产品，此字段对应流量币兑换流量产品表中的订单ID字段；
	 * productId:外链product_price表中的product_id
	 * productItemCode:在流量产品平台中的产品件编码，根据此编码在流量产品平台中使用指定产品件目前流量平台产品件可以是活动领取码或者流量包产品间编号，微信卡券流量产品件编号等 
	 * status:0未使用，1:已使用 2使用中 3部分使用 4失效 此字段作为分区条件，加快未使用产品件的访问速度
	 * usageType:0:充值，1:兑换流量币（存到流量账户）,2:过期作废 包含多个流量产品时，暂定存放第一次的使用类型 
	 * coinAmount:在兑换成流量币后更新流量币金额
	 */
	private UserProductItemEntity addUserProductItemEntitySetData(
			String userOpenId, int sourceType, String sourceData,
			int productId, String productItemCode, String expireTime,
			int status, int usageType, int coinAmount) {
		UserProductItemEntity entity = new UserProductItemEntity();
		entity.setUserOpenId(userOpenId);
		entity.setSourceType(sourceType);
		entity.setSourceData(sourceData);
		entity.setProductId(productId);
		entity.setProductItemCode(productItemCode);
		entity.setExpireTime(expireTime);
		entity.setStatus(status);
		entity.setUsageType(usageType);
		entity.setCoinAmount(coinAmount);

		return entity;
	}

	/*
	 * 添加用户账户操作记录赋值 inOutFlag：0 入账 1 出帐 
	 * operationName：0 签到赠送 1 手机绑定 2 企业活动赠送 3用户赠送 4流量币充值5：流量币充值到手机回退 6：卡券兑换回退到流量账户7:领取码退存到流量帐户8:卡券兑换成流量币9:兑换微信卡券10:抽奖扣除流量币
	 * operationRecordId：不同的业务名称，对应于不同的业务记录： 领取码领取：领取码表的领取码字段
	 * 					   流量产品兑换：流量产品兑换流量币表的订单ID 兑换流量产品：流量币兑换流量产品表的订单ID 签到奖励：用户签到奖励日志表的ID
	 * 		   			   手机绑定奖励：用户手机绑定奖励日志的ID … 
	 * description：如：签到赠送流量币
	 */
	private UserAccountRecordEntity addUserAccountRecordEntitySetData(
			String openId, int inOutFlag, String operationName,
			String operationRecordId, String description, int amount) {
		UserAccountRecordEntity entity = new UserAccountRecordEntity();
		entity.setOpenId(openId);
		entity.setInOutFlag(inOutFlag);
		entity.setOperationName(operationName);
		entity.setOperationRecordId(operationRecordId);
		entity.setDescription(description);
		entity.setAmount(amount);

		return entity;
	}

	/*
	 * 添加流量币兑换流量产品订赋值 
	 * status:0充值中 1充值成功 2充值失败 3退存到账户
	 * expireTime:如果下单未付款，过期时间/如果发货未确认收货，过期时间/如果充值信息提交运营商未收到报告，过期时间等
	 */
	private CoinToFlowProductOrderEntity addCoinToFlowProductOrderEntitySetData(
			String creatorOpenId, int productId, int productQty,
			String productSnapshot, int coinAmount, int orderType, int status,
			String statusRemark, String expireTime) {
		CoinToFlowProductOrderEntity entity = new CoinToFlowProductOrderEntity();
		entity.setOrderId(CoinToFlowProductOrderEntity.genId());
		entity.setCreatorOpenId(creatorOpenId);
		entity.setProductId(productId);
		entity.setProductQty(productQty);
		entity.setProductSnapshot(productSnapshot);
		entity.setCoinAmount(coinAmount);
		entity.setOrderType(orderType);
		entity.setStatus(status);
		entity.setStatusRemark(statusRemark);
		entity.setExpireTime(expireTime);

		return entity;
	}

	/*
	 * 添加流量充值记录赋值
	 */
	private FlowRechargeRecordEntity addFlowRechargeRecordEntitySetData(
			String openId, int activeTypeId, String rechargeMobileNo,
			String operatorType, int flowPackId, int flowSize,
			String rechargeSnap, String taskId, int status,
			String statusRemark, int userItemId, int sourceType,
			String sourceData) {
		FlowRechargeRecordEntity entity = new FlowRechargeRecordEntity();
		entity.setOpenId(openId);
		entity.setActiveTypeId(activeTypeId);
		entity.setRechargeMobileNo(rechargeMobileNo);
		entity.setOperatorType(operatorType);
		entity.setFlowPackId(flowPackId);
		entity.setFlowSize(flowSize);
		entity.setRechargeSnap(rechargeSnap);
		entity.setTaskId(taskId);
		entity.setStatus(status);
		entity.setStatusRemark(statusRemark);
		entity.setUserItemId(userItemId);
		entity.setSourceType(sourceType);
		entity.setSourceData(sourceData);

		return entity;
	}

	/*
	 * 添加流量好友赋值
	 */
	private UserFriendEntity addUserFriendEntitySetData(String openId,
			String friendOpenId, String lastContact) {
		UserFriendEntity entity = new UserFriendEntity();
		entity.setOpenId(openId);
		entity.setFriendOpenId(friendOpenId);
		entity.setLastContact(lastContact);

		return entity;
	}

	/*
	 * 添加已领取领取码记录赋值(存到流量账户) 
	 * usageType:0:领取流量产品，1:存到流量账户
	 * productItemCode:对应于流量产品件表中的产品件编号字段 
	 * rechargeStatus:0未使用，1:部分使用,2：全部使用 3使用中 4过期失效 一个领取码领取的流量产品在使用中可以生成多个充值记录
	 * payAmount:领取流量产品的方式下，在充值时，已消费金额增加相应额度。存到流量账户的方式下，已消费金额设置为产品的最大成本
	 */
	private UsedReceiveCodeEntity addUsedReceiveCodeEntitySetData(String code,
			String userOpenId, int productId, int coinPrice,
			String availableTime, String expireTime, String usedTime,
			int usageType, int productItemCode, int rechargeStatus,
			double payAmount, String remark) {
		UsedReceiveCodeEntity entity = new UsedReceiveCodeEntity();
		entity.setCode(code);
		entity.setUserOpenId(userOpenId);
		entity.setProductId(productId);
		entity.setCoinPrice(coinPrice);
		entity.setAvailableTime(availableTime);
		entity.setExpireTime(expireTime);
		entity.setUsedTime(usedTime);
		entity.setUsageType(usageType);
		entity.setRechargeStatus(rechargeStatus);
		entity.setPayAmount(payAmount);
		entity.setRemark(remark);
		entity.setProductItemCode(productItemCode);

		return entity;
	}

	/*
	 * 流量赠送赋值
	 * 
	 * status 0:未领取,1:已领取，2:已过期 ，3：不可用 
	 * giftType 0通过领取码赠送 1公社内直接赠送
	 */

	private FlowGiftEntity flowGiftEntitySetData(String userOpenId,
			String friendOpenId, int coinAmount, String receiveCode,
			String expireTime, int status, int giftType) {

		FlowGiftEntity flowGiftEntity = new FlowGiftEntity();
		flowGiftEntity.setUserOpenId(userOpenId);
		flowGiftEntity.setFriendOpenId(friendOpenId);
		flowGiftEntity.setCoinAmount(coinAmount);
		flowGiftEntity.setReceiveCode(receiveCode);
		flowGiftEntity.setExpireTime(expireTime);
		flowGiftEntity.setStatus(status);
		flowGiftEntity.setGiftType(giftType);
		return flowGiftEntity;
	}

	/*
	 * 更改流量赠送赋值
	 */
	private FlowGiftEntity updateFlowGiftEntitySetData(String code,
			String friendOpenId, String expireTime, int status, String usedTime)
			throws Exception {
		FlowGiftEntity flowGiftEntity = new FlowGiftEntity();
		flowGiftEntity.setFriendOpenId(friendOpenId);
		if (expireTime != null && !"".equals(expireTime)) {
			Long time = Long.parseLong(expireTime);
			flowGiftEntity.setExpireTime(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date().getTime() + time));
		}
		flowGiftEntity.setStatus(status);
		flowGiftEntity.setUsedTime(usedTime);
		flowGiftEntity.setReceiveCode(code);
		return flowGiftEntity;
	}

	/*
	 * 更改公社用户流量账户赋值 type:
	 * 1:支出+冻结 (可用资金减少，冻结资金增加，总支出增加，总收入不变） 
	 * 2:支出+解冻(成功）(可用资金不变，冻结资金减少，总支出不变，总收入不变）
	 * 3:支出+解冻(回退）(可用资金增加，冻结资金减少，总支出不变，总收入增加）
	 * 4:收入(可用资金增加，冻结资金不变，总支出不变，总收入增加）
	 */
	private UserAccountEntity updateUserAccountEntitySetData(int type,
			String openId, int quantity) throws Exception {
		UserAccountEntity userAccountEntity = UserAccountEntity.get(openId);
		if (type == 1) {
			int availableAmount = userAccountEntity.getAvailableAmount();
			if(availableAmount<quantity || availableAmount < 0){
				throw new SystemException("可用余额出现异常!");
			}
			userAccountEntity.setAvailableAmount(userAccountEntity
					.getAvailableAmount() - quantity);
			userAccountEntity.setFrozenAmount(userAccountEntity
					.getFrozenAmount() + quantity);
			userAccountEntity.setOutAmount(quantity
					+ userAccountEntity.getOutAmount());
		} else if (type == 2) {
			int num = userAccountEntity.getFrozenAmount() - quantity;
			if (num < 0) {
				throw new SystemException("冻结金额出现异常!");
			}
			userAccountEntity.setFrozenAmount(num);
			
		} else if (type == 3) {
			userAccountEntity.setAvailableAmount(userAccountEntity
					.getAvailableAmount() + quantity);
			int num = userAccountEntity.getFrozenAmount() - quantity;
			if (num < 0) {
				throw new SystemException("冻结金额出现异常!");
			}
			userAccountEntity.setFrozenAmount(num);
			userAccountEntity.setInAmount(userAccountEntity.getInAmount()
					+ quantity);
		} else if (type == 4) {
			userAccountEntity.setAvailableAmount(userAccountEntity
					.getAvailableAmount() + quantity);
			userAccountEntity.setInAmount(userAccountEntity.getInAmount()
					+ quantity);
		}else {
			throw new SystemException("类型错误!");
		}
		return userAccountEntity;
	}

	@Override
	public boolean checkUserIsLock(String openId) throws SystemException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = CommunityUserEntity.checkUserIsLock(openId);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void saveCommunityOrderAndReceiveCode(CommunityOrderEntity coe,
			CommunityReceiveCodeEntity crce) throws SystemException {
		// TODO Auto-generated method stub
		try {
			CommunityOrderEntity.saveCommunityOrder(coe);
			CommunityReceiveCodeEntity.saveCommunityReceiveCode(crce);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public int saveUserProductItem(UserProductItemEntity upie)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			UserProductItemEntity up = UserProductItemEntity.save(upie);
			if (up != null && (Integer) up.getId() != 0) {
				return (Integer) up.getId();
			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public int updateByCode(UserProductItemEntity upie)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			int up = UserProductItemEntity.updateByCode(upie);
			return up;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	
	@Override
	public void saveUsedReceiveCode(UsedReceiveCodeEntity urce)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			//UsedReceiveCodeEntity.save(urce);
			UsedReceiveCodeEntity.getEntityRepository().add(urce);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public void saveReceiveCodeRechargeRecord(
			ReceiveCodeRechargeRecordEntity rcrre) throws SystemException {
		// TODO Auto-generated method stub
		try {
			ReceiveCodeRechargeRecordEntity
					.saveReceiveCodeRechargeRecord(rcrre);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public String bindTelephoneNumber(Map<String, Object> paramMap)
			throws Exception {
		String openId = paramMap.get("openId").toString();
		Map<String, Object> map = queryChannel.queryMapResult(
				"getFlowPackageByUserId", new Object[] { openId }).get(0);
		
		queryChannel.findByNamedQuery("bindTelephoneNumber", paramMap);
		String rtnStr = paramMap.get("rtnStr").toString();
		if(Integer.valueOf(rtnStr) == Constants.FlowAccounts.BIND_SUCCESS_STATUS 
				&& "Y".equals(map.get("isBoundMobile").toString())){
			Map<String, Object> awardMap = queryChannel.queryMapResult(
					"getAwardRuleDetailById", new Object[] { Constants.FlowAccounts.AWARD_RULE_DETAIL_ID }).get(0);
			// 添加消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", awardMap.get("awardValue"));

			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_15),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
		}
		return rtnStr;
	}

	@Override
	public String unbindTelephoneNumber(Map<String, Object> paramMap)
			throws Exception {
		queryChannel.findByNamedQuery("unbindTelephoneNumber", paramMap);
		return paramMap.get("rtnStr").toString();
	}

	@Override
	public ReceiveCodeUnusedEntity findUnusedInfo(String code)
			throws SystemException {
		ReceiveCodeUnusedEntity unrcue = null;
		try {
			unrcue = ReceiveCodeUnusedEntity.getReceiveCodeUnusedEntity(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unrcue;
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public boolean changeCodeUnuseToUse(ReceiveCodeUnusedEntity unrcue,int type,
			String code, String openId, int sysPackId, String itemPrice)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			ReceiveCodeUsedEntity rcue = new ReceiveCodeUsedEntity();
			ReceiveCodeUnusedEntity.deleteReceiveCodeUnusedEntity(code);
			if (unrcue != null) {

				rcue.setReceiveCode(code);
				rcue.setActivityId(unrcue.getActivityId());
				rcue.setAwardId(unrcue.getAwardId());
				rcue.setBatchId(unrcue.getBatchId());
				rcue.setProductId(unrcue.getProductId());
				rcue.setAvailableTime(unrcue.getAvailableTime());
				rcue.setExpireTime(unrcue.getExpireTime());
				rcue.setReceiverOpenId(openId);
				rcue.setIsRecharged(1);
				rcue.setReceivedType(type);
				// by liting 领取码购买流量产品核销领取码时存入产品价值
				if(type == 1){
					BigDecimal bd = new BigDecimal(itemPrice);
					rcue.setPayAmount(bd);
				}
				rcue.setSysFlowPackId(sysPackId);

				ReceiveCodeUsedEntity.saveReceiveCodeUsedEntity(rcue);
				//根据领取码更新全部领取码记录库表使用状态=1
				ReceiveCodeFullRecordEntity.modifyReceiveCodeFullRecordEntity(code);
				//根据活动id更新活动表 已领取礼品数 = 已领取礼品数 +1，已领取礼品最大成本 = 已领取礼品最大成本+产品最大成本
				ActivityEntity.modifyActivityEntity(unrcue.getActivityId(),
						itemPrice);
				//根据礼品id更新 已发放数量=已发放数量+1，已发放最大成本=已发放最大成本+产品最大成本
				ActivityAwardEntity.modifyAwardEntity(unrcue.getAwardId(),
						itemPrice);
				//更新 已发放数量=已发放数量+1，已发放最大成本=已发放最大成本+产品最大成本
				ReceiveCodeActivityBatchEntity.modifyBatchEntity(
						unrcue.getBatchId(), itemPrice);
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return true;
	}

	@Override
	public void saveCommunityRechargeFlow(CommunityRechargeFlowUserEntity crf)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			CommunityRechargeFlowUserEntity.saveCommunityRechargeFlow(crf);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkReceiveCodeIsExist(String receiveCode)
			throws SystemException {
		try {
			return CommunityReceiveCodeEntity.IsExistReceiveCode(receiveCode);
		} catch (DbException e) {
			throw new SystemException(e);
		}
	}

	@Override
	public boolean quantityIsEnough(String openId, String quantity)
			throws SystemException {
		try {
			UserAccountEntity userAccountEntity = UserAccountEntity.get(openId);
			int availableAmount = userAccountEntity.getAvailableAmount();
			return Integer.parseInt(quantity) <= availableAmount;
		} catch (DbException e) {
			throw new SystemException(e);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void SuccessSend(String taskId, int rechargeId)
			throws SystemException {
		try {

			FlowRechargeRecordEntity flowRechargeRecordEntity = new FlowRechargeRecordEntity();

			flowRechargeRecordEntity.setTaskId(taskId);
			flowRechargeRecordEntity.setId(rechargeId);
			// 更改流量充值记录 添加taskId
			if (!(1 == FlowRechargeRecordEntity
					.updateTaskId(taskId, rechargeId)))
				throw new SystemException("更改流量充值记录失败!");
		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	@Override
	public boolean checkreceiveCodeIsUsed(String code) throws SystemException {
		boolean flag = false;
		try {
			flag = CommunityReceiveCodeEntity.checkreceiveCodeIsUsed(code);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean checkReceiveCodeAndStatus(String receiveCode)
			throws SystemException {
		boolean flag = false;
		try {
			flag = FlowGiftEntity.checkReceiveCodeAndStatus(receiveCode);

		} catch (DbException e) {
			throw new SystemException(e);
		}
		return flag;
	}

	@Override
	public int saveReFlowRechargeRecord(FlowRechargeRecordEntity crfue)
			throws SystemException {

		try {
			int updateNum = FlowRechargeRecordEntity.updateFlowRechargeRecord(crfue);
			return updateNum;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}
	
	@Override
	public void saveFlowRechargeRecord(FlowRechargeRecordEntity crfue)
			throws SystemException {
		try {
			FlowRechargeRecordEntity.saveFlowRechargeRecord(crfue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkIsRestore(String orderId) throws SystemException {
		boolean b = false;
		try {
			b = CommunityUserEntity.checkIsRestore(orderId);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	/*
	 * 张萌涛
	 * */
	@Override
	public void saveCoinToFlowProductOrder(CoinToFlowProductOrderEntity cfpo)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			CoinToFlowProductOrderEntity.save(cfpo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}
	/*
	 * 张萌涛
	 * */
	@Override
	public void saveProductItem(ProductItemEntity pie ,CoinToFlowProductOrderEntity cfpo,UserProductItemEntity upie)
			throws SystemException{
		// TODO Auto-generated method stub
		try {
			ProductItemEntity.save(pie);
			if(cfpo!=null){
				saveCoinToFlowProductOrder(cfpo);
			}
			if(upie!=null){
				saveUserProductItem(upie);
			}
		} catch(DbException e){
			throw new SystemException(e);
		}
		
		
	}
	/*
	 * 张萌涛
	 * 充值成功后修改表
	 * */

	@Override
	public void coinsToweixinSuccess(int productId,String openId,String weixinCode,int quantity)
			throws SystemException {
		// TODO Auto-generated method stub
		//通过产品件Id得到订单Id
		try{
		String orderId = queryChannel.querySingleResult("getOrderIdByProductId",new Object[]{productId});
		//更改流量币兑换流量产品订单
		CoinToFlowProductOrderEntity coinToFlowProductOrderEntity = new CoinToFlowProductOrderEntity();
		coinToFlowProductOrderEntity.setOrderId(orderId);
		coinToFlowProductOrderEntity.setStatus(1);
		}catch(Exception e){
			e.printStackTrace();	
		}
		
	}

	@Override
	public List<String> getExpireReceiveCodeByExpireTime()
			throws SystemException {
		try {
			return FlowGiftEntity.getExpireReceiveCodeByExpireTime();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}

	}

	@Override
	public boolean checkFlowGiftCodeIsUsed(String openId,String code) throws SystemException {
		boolean b = false;
		try {
			b = FlowGiftEntity.checkFlowGiftCodeIsUsed(openId,code);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public boolean checkCompanyCodeIsUsed(String openId,String code) throws SystemException {
		boolean b = false;

		try {
			if (checkCodeIsUsed(code) || checkIsExistInUsedReceiveCode(openId,code))
				b = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public String getOrderIdByuserItemId(int userItemId) throws SystemException {
		
		try {
			return UserProductItemEntity.getOrderIdByuserItemId(userItemId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public FlowRechargeRecordEntity getFlowRechargeRecord(String rechargeId,String openId)
			throws SystemException {
		try {
			return FlowRechargeRecordEntity.getFlowRechargeRecord(rechargeId,openId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkIsWxCodeUsed(String wxCode) throws SystemException {
		boolean b = false;
		try {
			b = UserProductItemEntity.checkIsWxCodeUsed(wxCode);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public void saveReceiveCodeUsed(ReceiveCodeUsedEntity rcue,
			UsedReceiveCodeEntity urceResult) {
		try {
			UsedReceiveCodeEntity.updateUsedReceiveCodeStatus(urceResult);
			UsedReceiveCodeEntity.updateByCode(String.valueOf(rcue.getReceivedType()),String.valueOf(rcue.getPayAmount()),String.valueOf(rcue.getIsRecharged()),rcue.getReceiveCode());
			//ReceiveCodeUsedEntity.saveReceiveCodeUsedEntity(rcue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkIsWxCode(String wxCode) throws SystemException {
		
		boolean b = false;
		try {
			b = UserProductItemEntity.checkIsWxCode(wxCode);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public int getProductIdByCode(String receiveCode) throws SystemException {
		try {
			return UsedReceiveCodeEntity.getProductIdByCode(receiveCode);

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public int getQuantityByProductId(int productId) throws SystemException {
		try {
			return UsedReceiveCodeEntity.getQuantityByProductId(productId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public void updateActivityReceiveCodeQty(String receiveCode,String payAmount)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			ReceiveCodeUsedEntity rcue = ReceiveCodeUsedEntity.getReceiveCodeUsed(receiveCode);
			if(rcue != null){
				ActivityEntity.updateActivityUsedQty(rcue.getActivityId(),payAmount);
				ActivityAwardEntity.updateActivityAwardUsedQty(rcue.getAwardId(),payAmount);
				ReceiveCodeActivityBatchEntity.updateBatchUsedQty(rcue.getBatchId(),payAmount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getItemPriceByProductId(int productId) throws SystemException {
		// TODO Auto-generated method stub
		try {
			String itemPrice = queryChannel.querySingleResult(
					"getItemPriceByProductId",
					new Object[] { productId });
			return itemPrice;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 张萌涛
	 * 流量币兑换卡券，扣除并冻结流量币
	 * */
	@Override
	public void updatecoins(String openId,int quantity,String description,int productId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			// 更改公社用户流量账户
			UserAccountEntity userAccountEntity = UserAccountEntity.get(openId);
			userAccountEntity.setAvailableAmount(userAccountEntity
					.getAvailableAmount() - quantity);
			userAccountEntity.setUpdateFlag(1);
			userAccountEntity.setOutAmount(userAccountEntity.getOutAmount()
					+ quantity);

			if (!(1 == UserAccountEntity.update(userAccountEntity)))
				throw new SystemException("更改公社用户流量账户失败!");

			String orderId = queryChannel.querySingleResult(
					"getOrderIdByProductId", new Object[] { productId });
			System.out.println("orderId:" + orderId);
			// 添加用户账户操作记录
			addUserAccountRecordEntitySetData(openId, 1, "9",
					String.valueOf(orderId), "兑换微信卡券", quantity).save();			
			System.out.println("orderId:"+orderId);
			
			
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			valueMap.put("productDesc", description);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_14),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
			
			System.out.println("savemessage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SystemException("更改公社用户流量账户失败!");
		}
		
	}

	@Override
	public boolean checkIsExistInUsedReceiveCode(String openId,String receiveCode)
			throws SystemException {
		try {
			return UsedReceiveCodeEntity.checkIsExistInUsedReceiveCode(openId,receiveCode);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkIsCardCodeUnUsed(String cardCode)
			throws SystemException {
		boolean b = false;
		try {
			b = UserProductItemEntity.checkIsCardCodeUnUsed(cardCode);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public void exchangeWechatCardMessage(String openId, String description)
			throws SystemException {
		try {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("productDesc", description);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_17),
					iMessageGeneratorServiceImpl(valueMap), valueMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int findCountByOpenId(String openId) throws SystemException {
		try {
			return FlowRechargeRecordEntity.findCountByOpenId(openId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1000;
	}
	@Override
    public void modifyExpiredReceiveCode() {
		List<UsedReceiveCodeEntity> list = null;
		try {
			list = UsedReceiveCodeEntity.getExpiredReceiveCode();
			
			if(list != null && list.size() != 0){
				UsedReceiveCodeEntity.modifyExpiredReceiveCode(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
    }

	@Override
	public boolean getCheckCodeIsBlackByWxCode(String wxCode)
			throws SystemException {
		boolean flag = false;
		try {
			flag =  WxCardBlackListEntity.checkCodeIsBlack(wxCode);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void updateCodeIsBlack(String wxCode, String openId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			WxCardBlackListEntity.updateCodeIsBlack(wxCode,openId);
			int updateNum = UserProductItemEntity.updateUserProductItemStatusTimeOutBySourceData(wxCode);
			if(updateNum > 0){
				ProductItemEntity.deleteByCode(wxCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCodeIsBigCode(String wxCode, String openId, int productId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			ProductDefinitionEntity pde= ProductDefinitionEntity.get(productId);
			if("10".equals(pde.getTypeId())){
				WxCardBlackListEntity.addCodeIsBlack(wxCode, openId,pde.getParamValue1());
				int updateNum = UserProductItemEntity.updateUserProductItemStatusTimeOutBySourceData(wxCode);
				
				if(updateNum > 0){
					ProductItemEntity.deleteByCode(wxCode);
				}
			}else if("18".equals(pde.getTypeId())){
				WxCardBlackListEntity.addCodeIsBlack(wxCode, openId,pde.getParamValue9());
				int updateNum = UserProductItemEntity.updateUserProductItemStatusTimeOutBySourceData(wxCode);
				
				if(updateNum > 0){
					ProductItemEntity.deleteByCode(wxCode);
				}
			}else{
				throw new SystemException("微信卡券对应产品类型不正确!");
			}

		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	@Override
	public void saveFlowRechargeRecord2(FlowRechargeRecordEntity crfue) throws SystemException {
		// TODO Auto-generated method stub
		try {
			FlowRechargeRecordEntity.saveFlowRechargeRecord2(crfue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}

	@Override
	public boolean checkIsActivityCode(String code, String activityId)
			throws SystemException {
		// TODO Auto-generated method stub
		int num = 0;
		boolean flag = false;
		try {
			num = ReceiveCodeUnusedEntity.getReceiveCodeActivityCount(code, activityId)
			      + ReceiveCodeUsedEntity.getReceiveCodeActivityUseCount(code, activityId);
			if(num == 1){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public boolean checkOpenIdAndRechargeId(String openId, String rechargeId)
			throws SystemException {
		boolean b = false;
		try {
			b = FlowRechargeRecordEntity.checkOpenIdAndRechargeId(openId,rechargeId);
		} catch (DbException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		return b;
	}

	@Override
	public void setCheckUserAmount() throws SystemException {
		// TODO Auto-generated method stub
		try {
			int num = 0;
			List<CommunityUserEntity> list = new ArrayList<CommunityUserEntity>();
			List<UserAccountRecordEntity> userDetailList = UserAccountRecordEntity.getUserAccountList();
			List<String> lingdao = new ArrayList<String>();
			lingdao.add("oUZWKs0r7K8pVIwlfamrQSeJt39c");
			lingdao.add("oUZWKs83PmDuLJ0oMCLTQF4mot9o");
			lingdao.add("oUZWKs0lMD-t70yPwGh5-tReyphM");
			lingdao.add("oUZWKs7C5z3sFs5celNB08wn_-MM");
			lingdao.add("oUZWKs_p-mMi3ldZt7BC74gArZ88");
			for(int i = 0; i < userDetailList.size(); i++){
				CommunityUserEntity cue = new CommunityUserEntity();
				cue.setOpenId(userDetailList.get(i).getOpenId());
				list.add(cue);
				UserLockRecordEntity ulre = new UserLockRecordEntity();
				ulre.setOpenId(userDetailList.get(i).getOpenId());
				ulre.setOperationName(2);
				UserLockRecordEntity.saveUserLockRecord(ulre);
			}
			if(userDetailList != null && userDetailList.size() > 0){
				for(int i = 0; i < lingdao.size(); i++){
					Map<String, Object> valueMap = new HashMap<String, Object>();
					valueMap.put("userCount", userDetailList.size());
					messageServiceImpl.createMessage(lingdao.get(i), messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_20),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				}
			}
			
			List<UserAccountRecordEntity> userList = UserAccountRecordEntity.getUserAccountCheckInList();
			for(int i = 0; i < userList.size(); i++){
				CommunityUserEntity cue = new CommunityUserEntity();
				cue.setOpenId(userList.get(i).getOpenId());
				list.add(cue);
				UserLockRecordEntity ulre = new UserLockRecordEntity();
				ulre.setOpenId(userList.get(i).getOpenId());
				ulre.setOperationName(1);
				UserLockRecordEntity.saveUserLockRecord(ulre);
			}
			if(userList != null && userList.size() > 0){
				for(int i = 0; i < lingdao.size(); i++){
					Map<String, Object> valueMap = new HashMap<String, Object>();
					valueMap.put("userCount", userList.size());
					messageServiceImpl.createMessage(lingdao.get(i), messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_19),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				}
			}
			if(list != null && list.size() > 0){
				num = CommunityUserEntity.changeIslocked(list);
			}
			
			if(num > 0){
				for(int i = 0; i < list.size(); i++){
					Map<String, Object> valueMap = new HashMap<String, Object>();
					messageServiceImpl.createMessage(list.get(i).getOpenId(), messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_18),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public List<FlowRechargeRecordEntity> findRechargeFlowByStatus(int start, int num)
			throws SystemException {
		// TODO Auto-generated method stub
		List<FlowRechargeRecordEntity> list = null;
		try {
			list = FlowRechargeRecordEntity.findRechargeFlowByStatus(start, num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int findRechargeFlowCountByStatus()
			throws SystemException {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = FlowRechargeRecordEntity.findRechargeFlowCountByStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void updateRechargeResult(FlowRechargeRecordEntity frreResult, UsedReceiveCodeEntity urceResult,
			UserProductItemEntity upieResult, ReceiveCodeRechargeRecordEntity rcrreResult)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			FlowRechargeRecordEntity.executeUpdateStatus(frreResult);
			UsedReceiveCodeEntity.updateUsedReceiveCodeStatus(urceResult);
			//UserProductItemEntity.save(upieResult);
			UserProductItemEntity.updateStatusById(upieResult.getId(), upieResult.getStatus());
			ReceiveCodeRechargeRecordEntity.executeUpdateStatus(rcrreResult);
			
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("recchargeMobileNo", frreResult.getRechargeMobileNo());
			if(Constants.RECHARGE_FLOW_STATUS_SUCCESS == frreResult.getStatus()){
				messageServiceImpl.createMessage( frreResult.getOpenId(), messageServiceImpl.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_9),
						iMessageGeneratorServiceImpl(valueMap), valueMap);
			}else{
				messageServiceImpl.createMessage( frreResult.getOpenId(), messageServiceImpl.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_10),
						iMessageGeneratorServiceImpl(valueMap), valueMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void updateWXRechargeResult(FlowRechargeRecordEntity frreResult, 
			UserProductItemEntity upieResult)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			FlowRechargeRecordEntity.executeUpdateStatus(frreResult);
			//update by dengyingming 2015-07-30
			//UserProductItemEntity.save(upieResult);
			UserProductItemEntity.updateStatusById(upieResult.getId(), upieResult.getStatus());
			//end by dengyingming
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("recchargeMobileNo", frreResult.getRechargeMobileNo());
			if(Constants.RECHARGE_FLOW_STATUS_SUCCESS == frreResult.getStatus()){
				messageServiceImpl.createMessage( frreResult.getOpenId(), messageServiceImpl.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_9),
						iMessageGeneratorServiceImpl(valueMap), valueMap);
			}else{
				messageServiceImpl.createMessage( frreResult.getOpenId(), messageServiceImpl.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_10),
						iMessageGeneratorServiceImpl(valueMap), valueMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public void updateReceiveCodeUse(ReceiveCodeUsedEntity rcue)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			//ReceiveCodeUsedEntity.saveReceiveCodeUsedEntity(rcue);
			ReceiveCodeUsedEntity.updateReceiveCodeUsedEntity(rcue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	
	@Override
	public SingleFlowPackEntity getBySysFlowPackId(int sysFlowPackId)
			throws SystemException {
		// TODO Auto-generated method stub
		try {
			return SingleFlowPackEntity.getBySysFlowPackId(sysFlowPackId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkOpenIdByWxCard(String openId, String wxCard)
			throws SystemException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			String openIdCard = queryChannel.querySingleResult(
					"checkOpenIdByWxCard", new Object[] { wxCard });
			if(openId.equals(openIdCard)){
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
		return flag;
	}

	@Override
	public int updateOpenIdToFlowGift(FlowGiftEntity fge)
			throws SystemException {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = FlowGiftEntity.update(fge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
		return num;
	}

	@Override
	public int checkRechargeNumByWxCard(String openId) throws SystemException {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = FlowRechargeRecordEntity.selectFlowRechargeCount(openId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SystemException(e);
		}
		return num;
	}

	@Override
	public List<WxCardDto> selectUnUsedCardCode(String openId,String appId)
			throws SystemException {
		try {
			//String[] classNames = cardCodeClassNames.split(",");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("appId", appId);
			//params.put("classNames", classNames);
			
			return queryChannel.findByNamedQuery(
					"selectUnUsedCardCode", params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}

	@Override
	public boolean checkCodeIdisExist(String codeId) throws SystemException{
		
		long num = 0;
		try {
			num = queryChannel.queryResultSize("checkCodeIdisExist", new Object[]{codeId});
		} catch (DbException e) {
			throw new SystemException(e);
		}catch (Exception e) {
			throw new SystemException(e);
		}
		return num == 1;
	}

	@Override
	public boolean checkCoinAmountByOpenIdAndcodeId(String openId,String codeId) throws SystemException{
		long num = -1;
		try {
			num = queryChannel.queryResultSize("checkCoinAmountByOpenIdAndcodeId", new Object[]{openId,codeId});
		}catch (Exception e) {
			throw new SystemException(e);
		}
		return num >= 0;
	}

	@Override
	@Transactional(rollbackFor = { SystemException.class })
	public void deductCoinAmountforAward(String openId, String codeId) throws SystemException{
		try {
			//根据codeId得到quantity
			int quantity = queryChannel.querySingleResult("getQuantityByCodeId", new Object[]{codeId});
			
			UserAccountEntity userAccountEntity = UserAccountEntity.get(openId);
			int availableAmount = userAccountEntity.getAvailableAmount();
			if(availableAmount<quantity || availableAmount < 0){
				throw new SystemException("可用余额出现异常!");
			}
			userAccountEntity.setAvailableAmount(userAccountEntity
					.getAvailableAmount() - quantity);
			userAccountEntity.setOutAmount(quantity
					+ userAccountEntity.getOutAmount());
			if (!(1 == UserAccountEntity.updateUserAccount(userAccountEntity.getAvailableAmount(),userAccountEntity.getFrozenAmount(),userAccountEntity.getOutAmount(),
					userAccountEntity.getInAmount(),quantity,openId)))
				throw new SystemException("更改公社用户流量账户失败!");

			// 添加用户账户操作记录 
			addUserAccountRecordEntitySetData(openId, 1, "10",
					null, "抽奖扣除流量币", quantity).save();

			// 添加公社用户消息
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("quantity", quantity);
			messageServiceImpl.createMessage(openId, messageServiceImpl
					.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_21),
					iMessageGeneratorServiceImpl(valueMap), valueMap);

		} catch (Exception e) {
			throw new SystemException(e);
		}
		
	}

	@Override
	public void changeUserUnlock() throws SystemException {
		// TODO Auto-generated method stub
		try {
			int num = CommunityUserEntity.changeUserUnlock();
			List<String> lingdao = new ArrayList<String>();
			lingdao.add("oUZWKs0r7K8pVIwlfamrQSeJt39c");
			lingdao.add("oUZWKs83PmDuLJ0oMCLTQF4mot9o");
			lingdao.add("oUZWKs0lMD-t70yPwGh5-tReyphM");
			lingdao.add("oUZWKs7C5z3sFs5celNB08wn_-MM");
			lingdao.add("oUZWKs_p-mMi3ldZt7BC74gArZ88");
			if(num > 0){
				for(int i = 0; i < lingdao.size(); i++){
					Map<String, Object> valueMap = new HashMap<String, Object>();
					valueMap.put("userCount", num);
					messageServiceImpl.createMessage(lingdao.get(i), messageServiceImpl
							.getTemplateById(Constants.MESSAGE_TEMPLATE_ID_22),
							iMessageGeneratorServiceImpl(valueMap), valueMap);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
}
