package com.sinowel.community.service;

import java.util.List;
import java.util.Map;

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
import com.sinowel.community.domain.FlowRechargeRecordEntity;
import com.sinowel.community.domain.ProductPriceEntity;
import com.sinowel.community.domain.UsedReceiveCodeEntity;
import com.sinowel.community.domain.UserAccountEntity;
import com.sinowel.community.domain.UserCheckInEntity;
import com.sinowel.community.domain.UserMessageEntity;
import com.sinowel.community.domain.UserProductItemEntity;
import com.sinowel.community.dto.RechargeFlowPackageGearDto;
import com.sinowel.community.dto.WxCardDto;
import com.sinowel.flowplatform.domain.receiveCode.ProductDefinitionEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductItemEntity;
import com.sinowel.flowplatform.domain.receiveCode.ProductTypeEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeActivityEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeRechargeRecordEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUnusedEntity;
import com.sinowel.flowplatform.domain.receiveCode.ReceiveCodeUsedEntity;
import com.sinowel.flowplatform.domain.receiveCode.SingleFlowPackEntity;
import com.sinowel.iucp.exception.SystemException;

public interface ICommunityMangerService {

	public Map<String, Object> getFlowAccountsByOpenId(String openId)
			throws Exception;

	public Map<String, Object> getAwardRuleDetailById(String awardRuleDetailId)
			throws Exception;

	public Map<String, Object> getCommunityBillById(String openId)
			throws Exception;

	public List<CommunityUserBillDetailEntity> getUserBillListById(
			String openId, int start, int length) throws Exception;

	public List<CommunityUserFriendEntity> getUserFriendListById(String openId,
			int start, int length) throws Exception;

	public List<CommunityReceiveCodeEntity2> getTransferRecordsById(
			String openId, String friendOpenId) throws Exception;

	public List<UserMessageEntity> manageMessageRecordsById(
			String openId, int start, int length) throws Exception;

	public String getCommunityMobileVerifyCode(Map<String, Object> paramMap) throws Exception;

	public String bindTelephoneNumber(Map<String, Object> paramMap) throws Exception;

	public String unbindTelephoneNumber(Map<String, Object> paramMap) throws Exception;

	/**
	 * 根据微信号判断是否属于公社用户
	 * 
	 * @param openId
	 *            微信号
	 * @return Booble(true,false)
	 * @author cy
	 */
	public boolean isExistUserByOpenId(String openId) throws SystemException;

	/**
	 * 创建用户信息
	 * 
	 * @param userEntity
	 *            公社用户集合
	 * @param userAccountEntity
	 *           公社用户流量账户集合
	 * @param userCheckInEntity
	 *            用户签到集合
	 *            
	 * @throws SystemException
	 * @author cy
	 */
	public void newCreateCommunity(CommunityUserEntity userEntity,
			UserAccountEntity userAccountEntity, UserCheckInEntity userCheckInEntity)
			throws SystemException;

	/**
	 * 创建用户信息
	 * 
	 * @param openId
	 *            微信号
	 * @param phone
	 *            手机号码
	 * @return List<RechargeFlowPackageGearDto> 充值档位列表
	 * @throws SystemException
	 * @author cy
	 */
	public List<RechargeFlowPackageGearDto> getRechargeFlowPackageGearList(
			String openId, String phone) throws SystemException;

	/**
	 * 转赠流量 (选择好友)发送接口
	 * 
	 * @param openId
	 *            微信号
	 * @param receiveCode
	 *            领取码
	 * 
	 * @throws SystemException
	 * @author cy 
	 */
	public void turnGiveSend(String openId, String receiveCode)
			throws SystemException;

	/**
	 * 流量币充值流量接口
	 * 
	 * @param openId
	 *            用户微信ID
	 * @param effectType
	 *            生效类型(当月或下月)
	 * @param phone
	 *            手机号码
	 * @param quantity
	 *            流量币数量
	 * @param flowId
	 *            系统流量ID
	 * @throws SystemException
	 * @author cy
	 */
	public Map<String, Object> flowCurrencyRecharge(String openId, int effectType,
			String phone, int quantity, int productId)
			throws SystemException;
	/**
	 * 领取码充值到流量账户接口
	 * 
	 * @param openId
	 *            微信ID
	 * @param code
	 *            领取码
	 * @param type
	 *            领取码类型：企业活动领取码0，公社领取码为1
	 * @param quantity
	 *            兑换流量币金额  
	 * @throws SystemException
	 * @author cy
	 */
	public void receiveCodeRechargeToFlowAccount(String openId, String code,
			int type,int quantity) throws SystemException;
	/**
	 * 得到不可用的领取码并创建订单
	 * 
	 * @param openId
	 *            用户微信ID
	 * @param quantity
	 *            赠送流量币数量		
	 * @throws SystemException
	 * @author cy
	 */
	public String selectFriendsService(String openId, int quantity) throws SystemException;
	/**
	 * 微信卡券存到流量账户接口
	 * 
	 * @param openId
	 *            微信ID
	 * @param cardCode
	 *            微信卡券Code
	 * @throws SystemException
	 * @author cy
	 */
	public void cardCodeRechargeToFlowAccount(String openId, String cardCode) throws SystemException;
	/**
	 * 验证此领取码是否是未使用的企业领取码
	 * 
	 * @param receiveCode
	 *            领取码
	 * @return ReceiveCodeUnusedEntity
	 * 			  未使用领取码         
	 * @throws SystemException
	 */
	public ReceiveCodeUnusedEntity checkReceiveCode(String receiveCode) throws SystemException;
	
	/**
	 * 验证此领取码是否在领取码记录表中
	 * 
	 * @param receiveCode
	 *            领取码
	 * @return CommunityReceiveCodeEntity 
	 * 			  领取码记录   
	 * @throws SystemException
	 */
	public UsedReceiveCodeEntity getUsedReceiveCodeByCode(String receiveCode) throws SystemException; 
	/**
	 * 验证此领取码是否在赠与流量表中
	 * 
	 * @param receiveCode
	 *            领取码
	 * @return FlowGiftEntity 
	 * 			  赠与流量   
	 * @throws SystemException
	 */
	public FlowGiftEntity getFlowGiftByCode(String receiveCode) throws SystemException;
	/**
	 * 根据流量产品编号查看流量产品
	 * 
	 * @param productId
	 *            流量产品编号
	 * @return ProductPriceEntity 
	 * 			流量产品价值   
	 * @throws SystemException
	 */
	public ProductPriceEntity getProductPriceById(int productId) throws SystemException;
	public ProductDefinitionEntity getProductDefinitionById(int productId) throws SystemException;
	/**
	 * 根据流量产品类型编号查看流量产品类型
	 * 
	 * @param productTypeId
	 *            流量产品类型编号
	 * @return ProductTypeEntity 
	 * 			流量产品类型  
	 * @throws SystemException
	 */
	public ProductTypeEntity getProductTypeById(String productTypeId) throws SystemException;
	public ReceiveCodeActivityEntity getReceiveCodeDetail(String awardId) throws SystemException; 
	public UserProductItemEntity getUserProductItemByWx(String productItemCode) throws SystemException;
	public ProductItemEntity getProductItemByWx(String wxCode) throws SystemException;
	/**
	 * 根据流量包号查看流量币
	 * 
	 * @param orderId
	 *            订单号
	 * @return CommunityOrderEntity 
	 * 			 订单记录记录   
	 * @throws SystemException
	 */
	public CommunityItemCoinExchangeEntity getItemCoinExchange(String packId) throws SystemException;
	
	/**
	 * 验证用户是否被锁定
	 * 
	 * @param openId
	 *            微信号
	 * @return isLock 
	 * 			 
	 * @throws SystemException
	 */
	public boolean checkUserIsLock(String packId) throws SystemException;
	
	/**
	 * 保存订单及领取码记录信息
	 * 
	 * @param CommunityOrderEntity
	 *            订单信息		
	 * @param CommunityReceiveCodeEntity
	 *            领取码记录信息	 
	 * @throws SystemException
	 */
	public void saveCommunityOrderAndReceiveCode(CommunityOrderEntity coe
			,CommunityReceiveCodeEntity crce) throws SystemException;
	
	/**
	 * 保存用户产品件信息
	 * 
	 * @param UserProductItemEntity
	 *            用户产品件信息		
	 * @throws SystemException
	 */
	public int saveUserProductItem(UserProductItemEntity upie) throws SystemException;
	/*
	 * 张萌涛
	 * */
	public void saveProductItem(ProductItemEntity pie,CoinToFlowProductOrderEntity cfpo,UserProductItemEntity upie) throws SystemException;
	/*
	 * 张萌涛
	 * */
	public void updatecoins(String openId,int quantity,String description,int productId) throws SystemException;
	/*
	 * 张萌涛
	 * */
	public void saveCoinToFlowProductOrder(CoinToFlowProductOrderEntity cfpo) throws SystemException;
	/*
	 * 张萌涛
	 * 微信卡券充值成功
	 * */
	public void coinsToweixinSuccess(int productId,String openId,String weixinCode,int quantity) throws SystemException;
	/**
	 * 保存已使用领取码信息
	 * 
	 * @param UsedReceiveCodeEntity
	 *            已使用领取码信息		
	 * @throws SystemException
	 */
	public void saveUsedReceiveCode(UsedReceiveCodeEntity urce) throws SystemException;
	/**
	 * 保存已使用领取码信息
	 * 
	 * @param UsedReceiveCodeEntity
	 *            已使用领取码信息		
	 * @throws SystemException
	 */
	public void saveReceiveCodeRechargeRecord(ReceiveCodeRechargeRecordEntity urce) throws SystemException;
	
	
	/**
	 * 查询领取码信息
	 * 
	 * @param CommunityOrderEntity
	 *            订单信息		
	 * @param CommunityReceiveCodeEntity
	 *            领取码记录信息	 
	 * @throws SystemException
	 */
	public ReceiveCodeUnusedEntity findUnusedInfo(String code) throws SystemException;
	
	/**
	 * 企业领取码核销
	 * 
	 * @param code
	 *           领取码		
	 * @return 成功失败 
	 * @throws SystemException
	 */
	public boolean changeCodeUnuseToUse(ReceiveCodeUnusedEntity unrcue, int type, String code, String openId, int sysPackId,
			 String itemPrice) throws SystemException;
	
	/**
	 * 保存流量记录
	 * 
	 * @param CommunityRechargeFlowUserEntity
	 *           流量记录表实体类		
	 * @throws SystemException
	 */
	public void saveCommunityRechargeFlow(CommunityRechargeFlowUserEntity crf) throws SystemException;
	/**
	 * 流量币充值手机成功
	 * 
	 * @param coe
	 *           订单ID		
	 * @throws SystemException
	 */
	public void flowMoneytoFlowPackageSuccess(FlowRechargeRecordEntity frre) throws SystemException;
	/**
	 * 流量币充值手机失败
	 * @param rechargeId 
	 * 
	 * @param orderId
	 *           订单ID		
	 * @throws SystemException
	 */
	public void flowMoneytoFlowPackageFail(FlowRechargeRecordEntity frre) throws SystemException;
	/**
	 * 检验领取码在库中是否存在
	 * @param receiveCode 
	 * 
	 * @return 成功失败 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkReceiveCodeIsExist(String receiveCode) throws SystemException;
	/**
	 * 检验微信ID钱包金额数量是否充足
	 * @param quantity 
	 * 
	 * @return 成功失败 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean quantityIsEnough(String openId,String quantity) throws SystemException;
	/**
	 * 发送到充值接口成功处理业务
	 * 
	 * @param rfpgDto 
	 * 
	 * @param orderId
	 * 
	 * @param rechargeId
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public void SuccessSend(String taskId,int rechargeId) throws SystemException;
	/**
	 * 检验企业领取码是否已使用
	 * 
	 * @param code 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkreceiveCodeIsUsed(String code) throws SystemException;
	/**
	 * 检验转赠领取码是否已使用
	 * 
	 * @param receiveCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkReceiveCodeAndStatus(String receiveCode) throws SystemException;
	/**
	 * 退存到流量帐户接口
	 * 
	 * @param openId
	 *            用户微信ID
	 * @param quantity
	 *            公社用户钱包中流量币数
	 *            
	 * @throws SystemException
	 * @author cy
	 */
	public void restoreToFlowAccount(String openId, int quantity,String orderId,String rechargeId) throws SystemException;
	/**
	 * 退存到流量帐户接口(卡券)
	 * 
	 * @param openId
	 *            用户微信ID
	 * @param wxId
	 *            卡券Id
	 * @param wxCode
	 * 			     卡券Code            
	 *            
	 * @throws SystemException
	 * @author cy
	 */
	public void wxRestoreToFlowAccount(String openId, int userItemId, String wxCode,String rechargeId) throws SystemException;
	/**
	 * 退存到流量帐户接口(企业领取码)
	 * 
	 * @param openId
	 *            用户微信ID
	 * @param receiveCode
	 *            领取码
	 * @param rechargeId
	 * 			     充值记录Id            
	 *            
	 * @throws SystemException
	 * @author cy
	 */
	public void codeRestoreToFlowAccount(String openId, String receiveCode,
			String rechargeId) throws SystemException;
	public int saveReFlowRechargeRecord(FlowRechargeRecordEntity crfue) throws SystemException;
	public void saveFlowRechargeRecord(FlowRechargeRecordEntity crfue) throws SystemException;
	/**
	 * 检验订单是否已退存
	 * 
	 * @param orderId
	 * 			订单Id 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkIsRestore(String orderId) throws SystemException;
	/**
	 * 得到过期领取码集合
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public List<String> getExpireReceiveCodeByExpireTime() throws SystemException;

	/**
	 * 检验流量赠送领取码是否可用并未过期
	 * 
	 * @param code 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkFlowGiftCodeIsUsed(String openId,String code) throws SystemException;
	/**
	 * 过期领取码处理任务
	 * 
	 * @param code 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public void expireTask(String code) throws SystemException;
	/**
	 * 检验企业领取码是否可用
	 * 
	 * @param openId
	 * 
	 *  @param code
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkCompanyCodeIsUsed(String openId,String code) throws SystemException;
	/**
	 * 根据用户产品件Id得到orderId
	 * 
	 * @param userItemId 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public String getOrderIdByuserItemId(int userItemId) throws SystemException;
	/**
	 * 根据充值记录Id得到充值记录集合
	 * 
	 * @param userItemId 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public FlowRechargeRecordEntity getFlowRechargeRecord(String rechargeId,String openId) throws SystemException;
	/**
	 * 检验卡券是否使用
	 * 
	 * @param wxCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkIsWxCodeUsed(String wxCode) throws SystemException;

	public void saveReceiveCodeUsed(ReceiveCodeUsedEntity rcue,
			UsedReceiveCodeEntity urceResult);
	/**
	 * 检验此流量产品是否为卡券
	 * 
	 * @param wxCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkIsWxCode(String wxCode) throws SystemException;
	/**
	 * 根据企业领取码得到产品Id
	 * 
	 * @param receiveCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public int getProductIdByCode(String receiveCode) throws SystemException;
	/**
	 * 根据产品Id得到兑换流量币金额
	 * 
	 * @param productId 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public int getQuantityByProductId(int productId) throws SystemException;

	
	/**
	 * 领取码使用成功后，更新领取码平台中的各表数据
	 * 
	 * @param receiveCode 
	 * 
	 * @throws SystemException
	 * @author lt
	 */
	public void updateActivityReceiveCodeQty(String receiveCode,String payAmount) throws SystemException;
	
	/**
	 * 根据产品ID得到产品最大价值
	 * 
	 * @param productId
	 * 
	 * @throws SystemException
	 * @author lt
	 */
	public String getItemPriceByProductId(int productId) throws SystemException;
	/**
	 * 检验企业领取码是否已使用
	 * 
	 * @param openId 
	 * 
	 * @param receiveCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkIsExistInUsedReceiveCode(String openId,String receiveCode) throws SystemException;
	/**
	 * 检验卡券码是否已使用
	 * 
	 * @param cardCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkIsCardCodeUnUsed(String cardCode) throws SystemException;
	/**
	 * 过期领取码处理方法
	 * 
	 * @param cardCode 
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public void expireCodeHander();
	
	/**
	 * 验证openId与充值记录Id是否为同一记录
	 * 
	 * @param openId
	 * @param rechargeId
	 * 
	 * @throws SystemException
	 * @author cy
	 */
	public boolean checkOpenIdAndRechargeId(String openId, String rechargeId) throws SystemException;
	
	/**
	 * 领取码兑换微信卡券存消息
	 * 
	 * @param openId
	 * @param description
	 * 
	 * @throws SystemException
	 * @author fh
	 */
	public void exchangeWechatCardMessage(String openId, String description)
			throws SystemException;
	
	public int findCountByOpenId(String openId) throws SystemException;
	
	/**
	 * 微信卡券验证是否是黑卡
	 * 
	 * @param openId
	 * @param description
	 * 
	 * @throws SystemException
	 * @author lt
	 */
	public boolean getCheckCodeIsBlackByWxCode(String wxCode) throws SystemException;
	public void updateCodeIsBigCode(String wxCode, String openId, int productId) throws SystemException;
	public int checkRechargeNumByWxCard(String openId) throws SystemException;
	/**
	 * 微信卡券验证是否是黑卡
	 * 
	 * @param openId
	 * @param description
	 * 
	 * @throws SystemException
	 * @author lt
	 */
	public void updateCodeIsBlack(String wxCode, String openId) throws SystemException;

	public void modifyExpiredReceiveCode();

	public int updateByCode(UserProductItemEntity upie)
			throws SystemException;

	public void saveFlowRechargeRecord2(FlowRechargeRecordEntity crfue) throws SystemException;

	public boolean checkIsActivityCode(String code, String activityId) throws SystemException;
	
	/**
	 * 用户账户监控操作
	 * @throws SystemException
	 * @author lt
	 */
	public void setCheckUserAmount() throws SystemException;
	
	/**
	 * 查询充值记录表中充值中状态的记录
	 * @param now 
	 * @return
	 * @throws SystemException 
	 */
	
	public List<FlowRechargeRecordEntity> findRechargeFlowByStatus(int start, int num) throws SystemException;
	public int findRechargeFlowCountByStatus() throws SystemException;
	/**
	 * 根据充值结果更新相关数据
	 * @param now 
	 * @return
	 * @throws SystemException 
	 */
	public void updateRechargeResult (FlowRechargeRecordEntity frreResult, UsedReceiveCodeEntity urceResult,
			UserProductItemEntity upieResult, ReceiveCodeRechargeRecordEntity rcrreResult)throws SystemException;
	/**
	 * 根据充值结果更新相关数据
	 * @param now 
	 * @return
	 * @throws SystemException 
	 */
	public void updateWXRechargeResult (FlowRechargeRecordEntity frreResult,
			UserProductItemEntity upieResult)throws SystemException;
	/**
	 * 更新领取码已使用表
	 * @return
	 * @throws SystemException 
	 */
	public void updateReceiveCodeUse(ReceiveCodeUsedEntity rcue) throws SystemException;
	
	/**
	 * 通过系统流量包ID查询最大成本
	 * @return
	 * @throws SystemException 
	 */
	public SingleFlowPackEntity getBySysFlowPackId(int sysFlowPackId) throws SystemException;
	
	/**
	 * 验证微信卡券是否属于当前用户
	 * @throws SystemException 
	 */
	public boolean checkOpenIdByWxCard(String openId, String wxCard) throws SystemException;
	
	public int updateOpenIdToFlowGift(FlowGiftEntity fge) throws SystemException;
	/**
	 * 查询用户所有未使用卡券
	 * @param openId
	 *            微信ID
	 * @throws SystemException
	 * @author cy 
	 */
	public List<WxCardDto> selectUnUsedCardCode(String openId,String appId) throws SystemException;
	/**
	 * 检验codeId是否存在
	 * @param codeId
	 *            编码ID
	 * @throws SystemException
	 * @author cy 
	 */
	public boolean checkCodeIdisExist(String codeId) throws SystemException;
	/**
	 * 检验用户帐户中的流量币是否足够
	 * @param openId
	 *            微信ID
	 *            
	 * @param codeId
	 *            编码ID
	 * @throws SystemException
	 * @author cy 
	 */
	public boolean checkCoinAmountByOpenIdAndcodeId(String openId,String codeId) throws SystemException;
	/**
	 * 用户抽奖扣流量币接口
	 * 
	 * @param openId
	 *            微信ID
	 * @param codeId
	 * 			  编码ID
	 * @throws SystemException
	 * @author cy
	 */
	public void deductCoinAmountforAward(String openId, String codeId) throws SystemException;
	/**
	 * 锁定用户七天后解锁接口
	 * @throws SystemException
	 * @author lt
	 */
	public void changeUserUnlock() throws SystemException;
}
