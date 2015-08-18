package com.github.flux.entity;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.mobile
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.account
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long account;

    private Long frozenAccount;
    private Integer locked;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.add_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long addSum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.sub_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long subSum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.logo
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String logo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nickname
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String nickname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.industry
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String industry;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Integer gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.year
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Integer year;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.signature
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String signature;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userid
     *
     * @return the value of user.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userid
     *
     * @param userid the value for user.userid
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.mobile
     *
     * @return the value of user.mobile
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.mobile
     *
     * @param mobile the value for user.mobile
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.account
     *
     * @return the value of user.account
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.account
     *
     * @param account the value for user.account
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setAccount(Long account) {
        this.account = account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.add_sum
     *
     * @return the value of user.add_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getAddSum() {
        return addSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.add_sum
     *
     * @param addSum the value for user.add_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setAddSum(Long addSum) {
        this.addSum = addSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.sub_sum
     *
     * @return the value of user.sub_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getSubSum() {
        return subSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.sub_sum
     *
     * @param subSum the value for user.sub_sum
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setSubSum(Long subSum) {
        this.subSum = subSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.logo
     *
     * @return the value of user.logo
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getLogo() {
        return logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.logo
     *
     * @param logo the value for user.logo
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nickname
     *
     * @return the value of user.nickname
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nickname
     *
     * @param nickname the value for user.nickname
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.industry
     *
     * @return the value of user.industry
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.industry
     *
     * @param industry the value for user.industry
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gender
     *
     * @param gender the value for user.gender
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.year
     *
     * @return the value of user.year
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Integer getYear() {
        return year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.year
     *
     * @param year the value for user.year
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.signature
     *
     * @return the value of user.signature
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getSignature() {
        return signature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.signature
     *
     * @param signature the value for user.signature
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_time
     *
     * @return the value of user.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_time
     *
     * @param createTime the value for user.create_time
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

	public Long getFrozenAccount() {
		return frozenAccount;
	}

	public void setFrozenAccount(Long frozenAccount) {
		this.frozenAccount = frozenAccount;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	private String token;
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private long expireTime;

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", mobile=" + mobile + ", account=" + account + ", frozenAccount="
				+ frozenAccount + ", locked=" + locked + ", addSum=" + addSum + ", subSum=" + subSum + ", logo=" + logo
				+ ", nickname=" + nickname + ", industry=" + industry + ", gender=" + gender + ", year=" + year
				+ ", signature=" + signature + ", createTime=" + createTime + ", token=" + token + "]";
	}

	

	
}