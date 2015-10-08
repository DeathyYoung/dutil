package com.deathy.jdbc.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public final class DBCPUtil {
	/**
	 * 指定所有参数连接数据源
	 * 
	 * @param connectURI
	 *            数据库
	 * @param username
	 *            用户名
	 * @param pswd
	 *            密码
	 * @param driverClass
	 *            数据库连接驱动名
	 * @param initialSize
	 *            初始连接池连接个数
	 * @param maxActive
	 *            最大激活连接数
	 * @param maxIdle
	 *            最大闲置连接数
	 * @param maxWait
	 *            获得连接的最大等待毫秒数
	 * @return
	 */
	public static DataSource initDS(String connectURI, String username, String pswd,
			String driverClass, int initialSize, int maxActive, int maxIdle,
			int maxWait) {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(driverClass);
		bds.setUsername(username);
		bds.setPassword(pswd);
		bds.setUrl(connectURI);
		bds.setInitialSize(initialSize); // 初始的连接数；
		bds.setMaxActive(maxActive);
		bds.setMaxIdle(maxIdle);
		bds.setMaxWait(maxWait);
		return bds;
	}
}
