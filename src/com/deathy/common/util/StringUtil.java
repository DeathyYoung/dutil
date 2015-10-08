package com.deathy.common.util;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Jun 16, 2015
 */
public class StringUtil {

	/**
	 * <p>
	 * 切分句子到长度100以内
	 *
	 * @param str
	 * @return
	 * @since Jun 16, 2015
	 */
	public static List<String> cutStr(String str) {
		return cutStr(str, 100);
	}

	/**
	 * 切分句子到长度maxLength以内
	 * 
	 * @param str
	 * @return String[]
	 */
	public static List<String> cutStr(String str, int maxLength) {
		List<String> strlist = new LinkedList<String>();
		if (str.length() <= maxLength) {
			strlist.add(str);
		} else {
			List<String> templist = cutStr(str, "。|；|？|\\?|;|!|！|(……)");
			for (int i = 0; i < templist.size(); i++) {
				if (templist.get(i).length() <= maxLength) {
					strlist.add(templist.get(i));
				} else {
					strlist.addAll(splitWithDot(templist.get(i)));
				}
			}
		}
		return strlist;
	}

	/**
	 * 利用正则切分句子
	 * 
	 * @param str
	 * @param regex
	 * @return String[]
	 */
	public static List<String> cutStr(String str, String regex) {
		String[] temps = str.split(regex);
		List<String> strlist = new LinkedList<String>();
		int beginIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < temps.length; i++) {
			endIndex = beginIndex + temps[i].length() + 1;
			endIndex = endIndex < str.length() ? endIndex : str.length();
			strlist.add(str.substring(beginIndex, endIndex));
			beginIndex = endIndex;
		}
		return strlist;
	}

	/**
	 * splitWithDot
	 * 
	 * @param str
	 * @return List<String>
	 */
	private static List<String> splitWithDot(String str) {
		List<String> strlist = new LinkedList<String>();
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			sb.append(chars[i]);
			if (chars[i] == '.' && i + 2 < chars.length
					&& RegexUtil.matches(chars[i + 1], "\\W")) {
				strlist.add(sb.toString());
				sb.setLength(0);
			}
		}
		String temp = sb.toString().trim();
		if (temp.length() > 0) {
			strlist.add(temp);
		}
		return strlist;
	}

}
