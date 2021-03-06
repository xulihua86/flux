package com.github.flux.entity;

import java.io.Serializable;

public class TraceLog implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trace_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long logId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trace_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trace_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String descn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trace_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trace_log.log_id
     *
     * @return the value of trace_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trace_log.log_id
     *
     * @param logId the value for trace_log.log_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trace_log.userid
     *
     * @return the value of trace_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trace_log.userid
     *
     * @param userid the value for trace_log.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trace_log.descn
     *
     * @return the value of trace_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getDescn() {
        return descn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trace_log.descn
     *
     * @param descn the value for trace_log.descn
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trace_log.create_time
     *
     * @return the value of trace_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trace_log.create_time
     *
     * @param createTime the value for trace_log.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}