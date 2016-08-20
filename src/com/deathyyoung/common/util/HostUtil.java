package com.deathyyoung.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class HostUtil {

	private static Properties hostPro;

	static {
		hostPro = new Properties();
		try {
			InputStream in = HttpUtil.class
					.getResourceAsStream("/hosts.properties");
			hostPro.load(in);
		} catch (IOException e) {
			System.out.println("未找到host的配置文件！！！");
		}
	}

	/**
	 * <p>
	 * 根据别名获得ip地址
	 *
	 * @param alias
	 * @return ip地址
	 */
	public static String getIP(String alias) {
		return hostPro.getProperty(alias);
	}
}
