package com.github.flux.entity;

import java.io.Serializable;

public class Task implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.task_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.code
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.reward_num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long rewardNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String descn;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.task_id
     *
     * @return the value of task.task_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.task_id
     *
     * @param taskId the value for task.task_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.name
     *
     * @return the value of task.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.name
     *
     * @param name the value for task.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.code
     *
     * @return the value of task.code
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.code
     *
     * @param code the value for task.code
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.reward_num
     *
     * @return the value of task.reward_num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getRewardNum() {
        return rewardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.reward_num
     *
     * @param rewardNum the value for task.reward_num
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setRewardNum(Long rewardNum) {
        this.rewardNum = rewardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.descn
     *
     * @return the value of task.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getDescn() {
        return descn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.descn
     *
     * @param descn the value for task.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setDescn(String descn) {
        this.descn = descn;
    }
}