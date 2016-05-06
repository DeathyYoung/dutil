package com.deathyyoung.common.util;

/**
 * <p>
 * ExceptionUtil
 * 
 * @author <a href="#" target="_blank">Deathy Young</a> (<a
 *         href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class ExceptionUtil {

	/**
	 * @Title: getInfo
	 * @param e
	 * @return String
	 */
	public static String getInfo(Exception e) {
		StringBuffer sb = new StringBuffer();

		sb.append(e.fillInStackTrace());
		sb.append('\n');
		sb.append("cause: ");
		sb.append(e.getCause());
		sb.append('\n');
		for (int i = 0; i < e.getStackTrace().length; i++) {
			sb.append(e.getStackTrace()[i]);
			sb.append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}
}
