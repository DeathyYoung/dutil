package com.deathy.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
* <p> C3P0工具类
* 
*/ 
public class C3P0Util {
	/**
	 * <p>
	 * 数据源
	 */
	private DataSource ds;
	/**
	 * <p>
	 * 线程
	 */
	private ThreadLocal<Connection> tl;

	public C3P0Util() {
		tl = new ThreadLocal<Connection>();
	}

	/**
	 * <p>
	 * 获得数据源
	 *
	 * @return 数据源
	 */
	public DataSource getDataSource() {
		return ds;
	}

	/**
	 * <p>
	 * 打开数据源
	 *
	 * @return 是否打开成功
	 */
	public boolean openDataSource() {
		try {
			ds = new ComboPooledDataSource();// 直接使用即可，不用显示的配置，其会自动识别配置文件
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <p>
	 * 打开数据源
	 * 
	 * @param alias
	 *              数据库配置项的名字
	 *
	 * @return 是否打开成功
	 */
	public boolean openDataSource(String alias) {
		try {
			ds = new ComboPooledDataSource(alias);// 直接使用即可，不用显示的配置，其会自动识别配置文件
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <p>
	 * 获取数据库连接
	 *
	 * @return 数据库连接
	 * @throws SQLException
	 *               获取数据库连接异常
	 */
	public Connection getConnection() throws SQLException {
		try {
			// 得到当前线程上绑定的连接
			Connection conn = tl.get();
			if (conn == null) { // 代表线程上没有绑定连接
				conn = ds.getConnection();
				tl.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * 开始事务
	 */
	public void startTransaction() {
		try {
			// 得到当前线程上绑定连接开启事务
			Connection conn = tl.get();
			if (conn == null) { // 代表线程上没有绑定连接
				conn = ds.getConnection();
				tl.set(conn);
			}
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * 提交事务
	 */
	public void commitTransaction() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * 关闭连接
	 */
	public void closeConnection() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			tl.remove(); // 千万注意，解除当前线程上绑定的链接（从threadlocal容器中移除对应当前线程的链接）
		}
	}

}