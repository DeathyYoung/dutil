package com.deathyyoung.common.util;

/**
 * <p>
 * ExceptionUtil
 * 
 * @author <a href="#" target="_blank">Deathy Young</a>
 *         (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class ExceptionUtil {

	public static final String log = "exception.log";

	/**
	 * <p>
	 * 获取异常信息
	 *
	 * @param e
	 *            异常对象
	 * @return 异常信息
	 */
	public static String getInfo(Exception e) {
		StringBuffer sb = new StringBuffer();

		sb.append(e.fillInStackTrace());
		sb.append('\n');
		if (e.getCause() != null) {
			sb.append("cause: ");
			sb.append(e.getCause());
			sb.append('\n');
		}
		StackTraceElement[] stes = e.getStackTrace();
		for (int i = 2; i < stes.length; i++) {
			StackTraceElement ste = stes[i];
			sb.append("\tat ").append(ste).append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}

	public static void log(Exception e) {
		System.err.println(e.fillInStackTrace());
		FileUtil.addToFile(getInfo(e), log);
	}
}
