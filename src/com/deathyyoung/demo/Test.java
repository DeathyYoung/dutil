package com.deathyyoung.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.deathyyoung.common.util.HttpUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String url = "http://www.zju.edu.cn";
		String rst = HttpUtil.get(url, map);
		System.out.println(rst);
		System.out.println("rst len:" + rst.length());
	}

}
