package com.github.flux.entity;

import java.io.Serializable;

public class Flower implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flower.flower_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    private Long flowerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flower.userid
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flower.friend_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    private Long friendId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flower.create_time
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flower.flower_id
     *
     * @return the value of flower.flower_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public Long getFlowerId() {
        return flowerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flower.flower_id
     *
     * @param flowerId the value for flower.flower_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public void setFlowerId(Long flowerId) {
        this.flowerId = flowerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flower.userid
     *
     * @return the value of flower.userid
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flower.userid
     *
     * @param userid the value for flower.userid
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flower.friend_id
     *
     * @return the value of flower.friend_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public Long getFriendId() {
        return friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flower.friend_id
     *
     * @param friendId the value for flower.friend_id
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flower.create_time
     *
     * @return the value of flower.create_time
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flower.create_time
     *
     * @param createTime the value for flower.create_time
     *
     * @mbggenerated Fri Aug 14 01:11:10 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}