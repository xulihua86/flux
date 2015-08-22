package com.github.flux.util;

public class Main {

	public static void main(String[] args) {
		String s = "userId||||lastLoginTime|appVer";
		String ss[] = s.split("[|]");
		System.out.println(ss.length);
	}

}
