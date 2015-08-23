package com.github.flux.entity;

import java.io.Serializable;

public class AccountLog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Integer Add = 1;  // 出库
	public static Integer Sub = 0;  // 入库
	
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long logId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.addorsub
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Integer addorsub;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String descn;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.log_id
     *
     * @return the value of account_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.log_id
     *
     * @param logId the value for account_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.userid
     *
     * @return the value of account_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.userid
     *
     * @param userid the value for account_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.addorsub
     *
     * @return the value of account_log.addorsub
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Integer getAddorsub() {
        return addorsub;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.addorsub
     *
     * @param addorsub the value for account_log.addorsub
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setAddorsub(Integer addorsub) {
        this.addorsub = addorsub;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.num
     *
     * @return the value of account_log.num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.num
     *
     * @param num the value for account_log.num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setNum(Long num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.create_time
     *
     * @return the value of account_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.create_time
     *
     * @param createTime the value for account_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_log.descn
     *
     * @return the value of account_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getDescn() {
        return descn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_log.descn
     *
     * @param descn the value for account_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    
	@Override
	public String toString() {
		return "AccountLog [logId=" + logId + ", userid=" + userid + ", addorsub=" + addorsub + ", num=" + num
				+ ", createTime=" + createTime + ", descn=" + descn + "]";
	}
    
    
}