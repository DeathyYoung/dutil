package com.deathyyoung.demo;

import com.deathyyoung.common.util.ExceptionUtil;

public class Test {

	public static void d() {
		int a = 1;
		int b = 0;
		System.out.println(a / b);
	}

	public static void main(String[] args) throws Exception {
		try {
			d();
		} catch (java.lang.ArithmeticException e) {
			System.out.println(ExceptionUtil.getInfo(e));
			System.out.println("===================================");
			e.printStackTrace();
		}
	}

	

}
