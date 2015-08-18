package com.sinowel.community.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinowel.flowplatform.domain.InterfaceAuthentication.UserEntity;
import com.sinowel.iucp.core.ddd.domain.AbstractEntity;
import com.sinowel.iucp.exception.DbException;

/**
 * 公社用户实体类
 */
public class CommunityUserEntity extends AbstractEntity {
	
	private static final long serialVersionUID = -8213319976598852154L;
	/* 用户ID */
	private Integer userId;
	/* 用户微信ID */
	private String openId;
	/* 昵称 */
	private String nickName;
	/* 用户类型 */
	private int userType;
	/* 是否锁定 */
	private int isLocked;
	/* 是否绑定过手机 */
	private int isBindMobile;
	/* 是否绑定了手机 */
	private int isBindingMobile;
	/* 绑定手机号 */
	private String mobileNo;
	/* 创建时间 */
	private String createTime;
	/* 修改时间 */
	private String modifyTime;
	/* 性别 */
	private String gender;
	/* 城市 */
	private String city;
	/* 省份 */
	private String province;
	/* 国家地区 */
	private String area;
	/* 语言 */
	private String language;
	/* 头像url */
	private String headimgUrl;
	/* 关注时间 */
	private String subscribeTime;
	/* 备注 */
	private String remark;
	/* 微信公众号ID */
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public int getIsBindMobile() {
		return isBindMobile;
	}

	public void setIsBindMobile(int isBindMobile) {
		this.isBindMobile = isBindMobile;
	}

	public int getIsBindingMobile() {
		return isBindingMobile;
	}

	public void setIsBindingMobile(int isBindingMobile) {
		this.isBindingMobile = isBindingMobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgUrl() {
		return headimgUrl;
	}

	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public boolean existed() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean notExisted() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existed(String propertyName, Object propertyValue)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CommunityUserEntity other = (CommunityUserEntity) obj;
		if (openId == null) {
			if (other.openId != null) {
				return false;
			}
		} else if (!openId.equals(other.openId)) {
			return false;
		}
		return true;
	}

	@Override
	public Serializable getId() {
		return userId;
	}
	
	/*
	 * 判断是否存在微信ID
	 */
	public static boolean IsExistUserByOpenId(String openId) throws DbException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openId", openId);
		CommunityUserEntity entity = null;
		try {
			entity = getEntityRepository().get(CommunityUserEntity.class, openId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return entity!=null;
	}

	/*
	 * 判断是否用户锁定
	 */
	public static boolean checkUserIsLock(String openId) throws DbException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openId", openId);
		int num = 0;
		try {
			num = getEntityRepository().getSingleResult("checkUserIsLock",param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return 1==num;
	}

	public static boolean checkIsRestore(String orderId) throws DbException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		int num = 0;
		try {
			num = getEntityRepository().getSingleResult("checkIsRestore", param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return 1==num;
	}	
	
	public static int changeIslocked(List<CommunityUserEntity> list) throws Exception {
		// TODO Auto-generated method stub
		String[] arr = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			arr[i] = list.get(i).getOpenId(); 
		}
		return getEntityRepository().executeUpdateReturnRowCount("changeIslocked", arr);
	}

	public static int changeUserUnlock() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		return getEntityRepository().executeUpdateReturnRowCount("changeUserUnlock", param);
	}
}
