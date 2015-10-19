package com.deathyyoung.web.util;

/**
 * XSS
 *
 */
public class XSSUtil {
	/**
	 * <p>
	 * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击，请通过将敏感字符转换为其对 应的字符实体来清理HTML。
	 * <p>
	 * 这些是HTML 敏感字符：< > " ' % ; ) ( & +
	 * <p>
	 * 以下示例通过将敏感字符转换为其对应的字符实体，来过滤指定字符串
	 * <p>
	 * Example to filter sensitive data to prevent cross-site scripting
	 * */
	public static String scriptingFilter(String value) {

		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case '%':
				result.append("&#37;");
				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return new String(result);
	}

}
