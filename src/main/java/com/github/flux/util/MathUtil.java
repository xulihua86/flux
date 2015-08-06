package com.github.flux.util;

import java.math.BigDecimal;

public class MathUtil {

	/**
	 * 
	 * @param r  原始数
	 * @param scale  保留小数点位数
	 * @return  四舍五入
	 */
	public static Double formate(Double r, int scale) {
		if(r == null) return null;
		if(scale < 0) return r;
		BigDecimal   b   =   new   BigDecimal(r);  
		return b.setScale(scale,  BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	
	
	public static void main(String[] args) {
		Double d = MathUtil.formate(null, 10);
		System.out.println(d);
	}
}
