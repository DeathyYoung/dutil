package com.deathy.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

/**
 * <p>
 * 1、利用MD5加密算法对密码进行加密
 * <p>
 * 2、利用MD5检查输入密码是否与数据库匹配
 * <p>
 * 3、生成指定长度的随机密码
 * 
 * @author Laijun Lin
 */

public class RMD5 {

	/**
	 * 利用MD5进行加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String EncoderByMd5(String str) {
		// 确定计算方法
		MessageDigest md5;
		String newstr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * 判断密码是否正确
	 * 
	 * @param newpasswd
	 *            输入的尚未加密的密码
	 * @param oldpasswd
	 *            数据库中已加密的密码
	 * @return true/false
	 */
	public static boolean checkpassword(String newpasswd, String oldpasswd) {
		if (EncoderByMd5(newpasswd).equals(oldpasswd)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
				'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static void main(String[] args) {
		System.out.println(EncoderByMd5("220102198012154011"));
	}
}