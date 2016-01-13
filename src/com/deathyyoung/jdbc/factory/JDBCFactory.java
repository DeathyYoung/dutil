package com.deathyyoung.jdbc.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.deathyyoung.jdbc.util.JDBCUtil;

/**
 * <p>
 * JDBC工厂类,每个数据库生产一个JDBC单例
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Apr 16, 2015
 */
public class JDBCFactory {

	/**
	 * <p>
	 * 数据库配置项与对应的JDBC工具类
	 *
	 */
	private static Map<String, JDBCUtil> jdbcMap;

	static {
		jdbcMap = new LinkedHashMap<String, JDBCUtil>();
	}

	/**
	 * <p>
	 * 获得 JDBCUtil
	 *
	 * @param alias
	 * @return
	 * @since Apr 16, 2015
	 */
	public static JDBCUtil getJDBCUtil(String alias) {
		if (jdbcMap == null) {
			jdbcMap = new LinkedHashMap<String, JDBCUtil>();
		}
		JDBCUtil ju = null;
		ju = jdbcMap.get(alias);
		try {
			if (ju == null || !ju.hasConn() || ju.isConnClosed()) {// 还需加强判断
				ju = new JDBCUtil(alias);
				jdbcMap.put(alias, ju);
				return ju;
			}
		} catch (Exception e) {
			return getJDBCUtilForce(alias);
		}
		return ju;
	}

	/**
	 * <p>
	 * 获得一个新的 JDBCUtil
	 *
	 * @param alias
	 * @return
	 * @since Apr 16, 2015
	 */
	public static JDBCUtil getJDBCUtilForce(String alias) {
		if (jdbcMap == null) {
			jdbcMap = new LinkedHashMap<String, JDBCUtil>();
		}
		JDBCUtil ju = null;
		ju = new JDBCUtil(alias);
		jdbcMap.put(alias, ju);
		return ju;
	}
}
