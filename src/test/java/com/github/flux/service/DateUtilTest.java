package com.github.flux.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.github.flux.util.DateUtil;

public class DateUtilTest {
	@Test
	public void DateTest(){
		System.out.println(DateUtil.getIntFromDate(new Date(), "yyyyMMdd"));
	}
}
