package com.deathyyoung.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.deathyyoung.common.util.HttpUtil;
import com.deathyyoung.common.util.MathUtil;

public class MathDemo {

	public static void main(String[] args) {
		String url = "http://10.15.82.35:8081/word2vec/GetVector";
		Map<String, String> map = new LinkedHashMap<String, String>();

		String[] words = { "基坑", "基底" };
		words[0] = "类型";
		words[1] = "引用";
		map.put("word", words[0]);
		String vecStr1 = HttpUtil.get(url, map);
		map.put("word", words[1]);
		String vecStr2 = HttpUtil.get(url, map);

		String[] vecArray1 = vecStr1.split(",");
		String[] vecArray2 = vecStr2.split(",");

		double[] vec1 = new double[vecArray1.length];

		for (int i = 0; i < vecArray1.length; i++) {
			vec1[i] = Double.parseDouble(vecArray1[i]);
		}
		double[] vec2 = new double[vecArray2.length];
		for (int i = 0; i < vecArray2.length; i++) {
			vec2[i] = Double.parseDouble(vecArray2[i]);
		}
		double rst = MathUtil.cosDistance(vec1, vec2);
		System.out.println(rst);
	}


}
