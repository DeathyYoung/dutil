package com.deathyyoung.common.util;

/**
 * @ClassName: RamdomUtil
 * @Description: TODO
 * @author Deathy Young (deathyyoung_gmail_com)
 * @date 2014-12-6 上午9:25:03
 *
 */
public class RamdomUtil {

	/**
	 * @Title: getFileName
	 * @param header
	 * @param fileName
	 * @return String
	 */
	public static String getFileName(String header, String fileName) {
		if (fileName == null || fileName.equals("")) {
			return "default.jpg";
		}
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		return header + "_" + (int) (Math.random() * 100000) + suffix;
	}

	/**
	 * @Title: getFileName
	 * @param fileName
	 * @return String
	 */
	public static String getFileName(String fileName) {
		if (fileName == null || fileName.equals("")) {
			return "default.jpg";
		}
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		return TimeUtil.getTime("yyyyMMddHHmmss") + "_"
				+ (int) (Math.random() * 100000) + suffix;
	}

	//
}
