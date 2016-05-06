package com.deathyyoung.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Regex Util
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Jun 23, 2015
 */
public class RegexUtil {

	/** The pattern for a float number. */
	public static final String floatPat = "^\\d+(\\.\\d+)?$";
	/** The pattern for a percent number. */
	public static final String percentPat = "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?\\%$";
	/** The pattern for a single Chinese word. */
	public static final String singleChinesePat = "[\\u4E00-\\u9FA5]";
	/** The pattern for Chinese. */
	public static final String ChinesePat = "^[\\u4E00-\\u9FA5]+$";
	/** The pattern for a sentence. */
	public static final String sentPat = "。|；|;|!|！|(……)";

	/**
	 * <p>
	 * Check whether the <code>content</code> is a float number.
	 *
	 * @param content
	 *            The string to be matched
	 * @return <code>true</code> if, and only if, the <code>content</code> is a
	 *         float number.
	 */
	public static boolean matchFloat(String content) {
		return matches(content, floatPat);
	}

	/**
	 * <p>
	 * Check whether the <code>content</code> is a percent number.
	 *
	 * @param content
	 *            The string to be matched
	 * @return <code>true</code> if, and only if, the <code>content</code> is a
	 *         percent number.
	 */
	public static boolean matchesPercent(String content) {
		return matches(content, percentPat);
	}

	/**
	 * <p>
	 * Check whether the <code>content</code> is Chinese.
	 *
	 * @param content
	 *            The string to be matched
	 * @return <code>true</code> if, and only if, the <code>content</code> is
	 *         Chinese.
	 */
	public static boolean matchesChinese(String content) {
		return matches(content, ChinesePat);
	}

	/**
	 * <p>
	 * Check whether the <code>content</code> matches the <code>pat</code>.
	 * 
	 * @param content
	 *            The string to be matched
	 * @param pat
	 *            The expression to be compiled
	 * @return <code>true</code> if, and only if, the <code>content</code>
	 *         matches the <code>pat</code>.
	 */
	public static boolean matches(String content, String pat) {
		return Pattern.compile(pat).matcher(content).matches();
	}

	/**
	 * <p>
	 * Check whether the <code>content</code> matches the <code>pat</code>.
	 * 
	 * @param content
	 *            The character to be matched
	 * @param pat
	 *            The expression to be compiled
	 * @return <code>true</code> if, and only if, the <code>content</code>
	 *         matches the <code>pat</code>.
	 */
	public static boolean matches(char content, String pat) {
		return Pattern.compile(pat).matcher(content + "").matches();
	}

	/**
	 * <p>
	 * Check whether there exists a subsequence of the <code>content</code>
	 * matches the <code>pat</code>.
	 * 
	 * @param content
	 *            The character to be matched
	 * @param pat
	 *            The expression to be compiled
	 * @return <code>true</code> if, and only if, a subsequence of the
	 *         <code>content</code> matches the <code>pat</code>.
	 */
	public static boolean find(String content, String pat) {
		Matcher matcher = Pattern.compile(pat).matcher(content);
		return matcher.find();
	}

	/**
	 * @param content
	 * @param pat
	 * @return List<String> extra a string list
	 */
	public static List<String> extraList(String content, String pat) {
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find())
			ls.add(matcher.group(0));
		return ls;
	}

	/**
	 * @param content
	 * @param pat
	 * @return Set<String> extra a string Set
	 */
	public static Set<String> extraSet(String content, String pat) {
		Set<String> ls = new TreeSet<String>();
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find())
			ls.add(matcher.group());
		return ls;
	}

	/**
	 * <p>
	 * 正则计数
	 *
	 * @param content
	 * @param pat
	 * @return
	 * @since Apr 15, 2015
	 */
	public static int count(String content, String pat) {
		int i = 0;
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find())
			i++;
		return i;
	}

	/**
	 * 利用正则切分句子
	 * 
	 * @param str
	 * @return String[]
	 */
	public static List<String> cutStr(String str) {
		return cutStr(str, sentPat);
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
	 * <p>
	 * Set里的String用splitWord分割做成一个字符串
	 *
	 * @param set
	 * @param splitWord
	 * @return 这个字符串
	 * @since May 28, 2015
	 */
	public static String join(Set<String> set, String splitWord) {
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = set.iterator();
		if (iter.hasNext()) {
			sb.append(iter.next());
		}
		while (iter.hasNext()) {
			sb.append(splitWord);
			sb.append(iter.next());
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * String数组用splitWord分割做成一个字符串
	 *
	 * @param words
	 * @param splitWord
	 * @return 这个字符串
	 * @since May 28, 2015
	 */
	public static String join(String[] words, String splitWord) {
		StringBuffer sb = new StringBuffer();

		if (words.length > 0) {
			sb.append(words[0]);
		}
		for (int i = 1; i < words.length; i++) {
			sb.append(splitWord);
			sb.append(words[i]);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * String列表用splitWord分割做成一个字符串
	 *
	 * @param words
	 * @param splitWord
	 * @return 这个字符串
	 * @since May 28, 2015
	 */
	public static String join(List<String> words, String splitWord) {
		StringBuffer sb = new StringBuffer();

		if (words.size() > 0) {
			sb.append(words.get(0));
		}
		for (int i = 1; i < words.size(); i++) {
			sb.append(splitWord);
			sb.append(words.get(i));
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 获得可以匹配正则表达式regex的正则表达式
	 *
	 * @param regex
	 *            待匹配正则表达式
	 * @return 正则表达式的正则表达式
	 */
	public static String regexToString(String regex) {
		if (regex == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < regex.length(); i++) {
			char c = regex.charAt(i);
			switch (c) {
			case '\\':
			case '*':
			case '+':
			case '.':
			case '[':
			case ']':
			case '|':
			case '^':
			case '$':
			case '?':
			case '{':
			case '}':
			case ',':
			case '(':
			case ')':
			case '!':
			case '<':
				sb.append('\\');
				sb.append(c);
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
