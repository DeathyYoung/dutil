package com.deathyyoung.demo;

import com.deathyyoung.common.util.SecurityUtil;

public class TestSecurity {
	public static void main(String[] args) {
		String str = "easyleaf";
		str = SecurityUtil.sha((SecurityUtil.md5_32(str)));
		System.out.println(str);
	}
}
