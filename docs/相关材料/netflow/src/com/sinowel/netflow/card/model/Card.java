package com.sinowel.netflow.card.model;

import java.io.Serializable;

/**
 *  卡Bean
 *  @author langkai
 *  @version 1.0
 *  </pre>
 *  Created on :上午11:28:58
 *  LastModified:
 *  History:
 *  </pre>
 */
public class Card implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 卡id. */
    private String id;

    /** 微信卡券id. */
    private String cardId;
    
    /** 卡名. */
    private String cardName;
    
    /** 流量大小M. */
    private String cardUnit;
    
    /** 运营商1移动2联通3电信. */
    private String cardOperator;
    
    /** 卡描述. */
    private String cardDescription;
    
    /** 卡价格. */
    private String cardPrice;

    /** 流量充值平台套餐ID(全功能卡运营商以逗号分隔(移动,联通,电信)). */
    private String menuId;
    
    /** 卡删除标识 0:删除 1:未删除. */
    private String delFlag;
    
    /** 创建时间. */
    private String createTime;
    
    /** 创建人. */
    private String createUser;
    
    /** 修改时间. */
    private String modifyTime;
    
    /** 修改人. */
    private String modifyUser;

    /** 卡Code. */
    private String cardCode;
    
    /**
     * 取得 卡id
     * @return 卡id
     */
    public String getCardId () {
        return cardId;
    }
    
    /**
     * 设置 卡id
     * @param cardId 卡id
     */
    public void setCardId (String cardId) {
        this.cardId = cardId;
    }
    
    /**
     * 取得 卡名
     * @return 卡名
     */
    public String getCardName () {
        return cardName;
    }
    
    /**
     * 设置 卡名
     * @param cardName 卡名
     */
    public void setCardName (String cardName) {
        this.cardName = cardName;
    }
    
    /**
     * 取得 流量大小M
     * @return 流量大小M
     */
    public String getCardUnit () {
        return cardUnit;
    }
    
    /**
     * 设置 流量大小M
     * @param cardUnit 流量大小M
     */
    public void setCardUnit (String cardUnit) {
        this.cardUnit = cardUnit;
    }
    
    /**
     * 取得 运营商1移动2联通3电信
     * @return 运营商1移动2联通3电信
     */
    public String getCardOperator () {
        return cardOperator;
    }
    
    /**
     * 设置 运营商1移动2联通3电信
     * @param cardOperator 运营商1移动2联通3电信
     */
    public void setCardOperator (String cardOperator) {
        this.cardOperator = cardOperator;
    }
    
    /**
     * 取得 卡描述
     * @return 卡描述
     */
    public String getCardDescription () {
        return cardDescription;
    }
    
    /**
     * 设置 卡描述
     * @param cardDescription 卡描述
     */
    public void setCardDescription (String cardDescription) {
        this.cardDescription = cardDescription;
    }
    
    /**
     * 取得 卡价格
     * @return 卡价格
     */
    public String getCardPrice () {
        return cardPrice;
    }
    
    /**
     * 设置 卡价格
     * @param cardPrice 卡价格
     */
    public void setCardPrice (String cardPrice) {
        this.cardPrice = cardPrice;
    }
    
    /**
     * 取得 卡删除标识 0:删除 1:未删除
     * @return 卡删除标识 0:删除 1:未删除
     */
    public String getDelFlag () {
        return delFlag;
    }
    
    /**
     * 设置 卡删除标识 0:删除 1:未删除
     * @param delFlag 卡删除标识 0:删除 1:未删除
     */
    public void setDelFlag (String delFlag) {
        this.delFlag = delFlag;
    }
    
    /**
     * 取得 创建时间
     * @return 创建时间
     */
    public String getCreateTime () {
        return createTime;
    }
    
    /**
     * 设置 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime (String createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 取得 创建人
     * @return 创建人
     */
    public String getCreateUser () {
        return createUser;
    }
    
    /**
     * 设置 创建人
     * @param createUser 创建人
     */
    public void setCreateUser (String createUser) {
        this.createUser = createUser;
    }
    
    /**
     * 取得 修改时间
     * @return 修改时间
     */
    public String getModifyTime () {
        return modifyTime;
    }
    
    /**
     * 设置 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime (String modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    /**
     * 取得 修改人
     * @return 修改人
     */
    public String getModifyUser () {
        return modifyUser;
    }
    
    /**
     * 设置 修改人
     * @param modifyUser 修改人
     */
    public void setModifyUser (String modifyUser) {
        this.modifyUser = modifyUser;
    }

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

}
