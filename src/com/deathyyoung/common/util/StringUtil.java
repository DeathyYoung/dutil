package com.deathyyoung.common.util;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author <a href="#" target="_blank">Deathy Young</a>
 *         (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 * @since Jun 16, 2015
 */
public class StringUtil {

	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";
	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";
	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";
	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";
	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";
	/** 中文超大字符集 */
	public static final String GBK = "GBK";

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
			if (chars[i] == '.' && i + 2 < chars.length && RegexUtil.matches(chars[i + 1], "\\W")) {
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

	/**
	 * <p>
	 * change a string to UTF8 encode
	 *
	 * @param str
	 *            the string
	 * @return the utf-8 string
	 */
	public static String toUTF8(String str) {
		String encode = getEncoding(str);
		try {
			return new String(str.getBytes(encode), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * <p>
	 * get a string's encode
	 *
	 * @param str
	 *            the string
	 * @return the encode
	 */
	public static String getEncoding(String str) {
		String[] encodes = { "UTF-8", "GBK", "US-ASCII", "ISO-8859-1", "UTF-16BE", "UTF-16LE", "UTF-16", "GB2312",
				"GB18030" };
		for (String encode : encodes) {
			try {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					String s = encode;
					return s;
				}
			} catch (UnsupportedEncodingException exception) {
			}
		}
		return "";
	}

	/**
	 * <p>
	 * byte to hex
	 *
	 * @param b
	 *            bytes
	 * @return hex
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * whether the string is numeric
	 *
	 * @param str
	 *            the string
	 * @return result
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String bytesToString(byte[] bytes, String encode) {
		String str = "";
		try {
			str = new String(bytes, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String bytesToString(byte[] bytes) {
		return bytesToString(bytes, getEncoding(bytes));
	}

	public static String getEncoding(byte[] bytes) {
		String code = "";
		if (bytes[0] == -1 && bytes[1] == -2) {
			code = "UTF-16";
		} else if (bytes[0] == -2 && bytes[1] == -1) {
			code = "UTF-16";
		} else if (bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65) {
			code = "UTF-8";
		} else if ("Unicode".equals(code)) {
			code = "UTF-16";
		}
		if (code.length() == 0) {
			int index = 0;
			for (; index + 3 < bytes.length; index++) {
				if (bytes[index] < 0) {
					break;
				}
			}
			byte[] charBytes = new byte[] { bytes[index], bytes[index + 1], bytes[index + 2] };

			if ((charBytes[0] & 0xF0) == 0xE0 && (charBytes[1] & 0xC0) == 0x80 && (charBytes[2] & 0xC0) == 0x80) {// 无BOM的UTF-8
				code = "UTF-8";
			} else {
				code = "GBK";
			}
		}
		return code;
	}
}
