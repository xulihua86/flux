package com.github.flux.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.github.flux.util.DateUtil;

public class DateUtilTest {
	@Test
	public void DateTest(){
		System.out.println(DateUtil.str2Date("2015-08-21 09:54:55").getTime());
		System.out.println(DateUtil.date2Str(new Date()));
	}
}
