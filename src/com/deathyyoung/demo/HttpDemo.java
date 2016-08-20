package com.deathyyoung.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.deathyyoung.common.util.HttpUtil;

/**
 * <p> TODO
 * 
 * @author <a href="#" target="_blank">Deathy Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class HttpDemo {

	/**
	 * <p> TODO
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://10.15.62.62:8082/ExtraPhrase/NGram";
		String text = "美国科学家已经成功研制出“死亡计算器”,只需输入生日、性别、身高体重等信息,就能预测出你的死亡时间,有胆就来看看您的“死期”是什么时候, 死亡计算器在线预测";
		Map<String, String> map  =  new LinkedHashMap<String, String>();
		map.put("text", text);
		System.out.println(HttpUtil.get(url, map));
	}

}
