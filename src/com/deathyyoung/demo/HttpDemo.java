package com.deathyyoung.demo;

import com.deathyyoung.common.util.HttpUtil;

/**
 * <p>
 * TODO
 * 
 * @author <a href="#" target="_blank">Deathy Young</a>
 *         (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class HttpDemo {

	/**
	 * <p>
	 * TODO
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String url = "http://www.zju.edu.cn/";
		String rst = HttpUtil.get(url);
		System.out.println(rst);
	}

}
