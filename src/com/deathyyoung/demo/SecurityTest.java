package com.deathyyoung.demo;

import com.deathyyoung.common.util.SecurityUtil;

public class SecurityTest {
public static void main(String[] args) {
	System.out.println(SecurityUtil.sha(SecurityUtil.md5_32("deathy")));
}
}
