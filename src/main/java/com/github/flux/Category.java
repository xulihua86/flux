package com.github.flux;

public enum Category {

	// 0 签到赠送 1 手机绑定 2 企业活动赠送 3 用户赠送 4流量币充值
	signIn("签到送流量", 0), mobileBind("手机绑定", 1), ent("企业活动赠送", 2), friendGive("用户赠送", 3), full("流量币充值", 4);

	// 成员变量
	private String descn;
	private int index;

	// 构造方法
	private Category(String descn, int index) {
		this.descn = descn;
		this.index = index;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public static Category index(final int indx) {
		for (Category c : Category.values()){
			if(c.getIndex() == indx) {
				return c;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
	    for (Category c : Category.values()) {
	      System.out.println(c + " : " + c.getDescn() + " : " + c.getIndex());
	      System.out.println(c + "_count");
	    }

	    // System.out.println(Category.friendGiven.getDescn());
	    
	    System.out.println(Category.index(2).getDescn());
	    
	  }
	
	
	
}
