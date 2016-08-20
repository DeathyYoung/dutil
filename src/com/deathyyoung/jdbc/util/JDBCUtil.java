package com.deathyyoung.jdbc.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.deathyyoung.common.util.SecurityUtil;
import com.deathyyoung.jdbc.factory.JDBCFactory;

/**
 * <p>
 * JDBC工具类
 * 
 * @author <a href="#" target="_blank">Deathy Young</a> (
 *         <a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class JDBCUtil {

	public enum POOL {
		NONE, C3P0, PROXOOL, DBCP
	};

	/**
	 * <p>
	 * 连接池类型
	 *
	 */
	private static POOL pool;
	/**
	 * <p>
	 * 数据库url
	 */
	private static String url;
	/**
	 * <p>
	 * 用户名
	 *
	 */
	private static String user;
	/**
	 * <p>
	 * 密码
	 *
	 */
	private static String password;
	/**
	 * <p>
	 * 数据库驱动路径
	 */
	private static String driverPath;
	/**
	 * <p>
	 * DBCP 初始连接池连接个数
	 *
	 */
	private static int initialSize;
	/**
	 * <p>
	 * 最大激活连接数
	 */
	private static int maxActive;
	/**
	 * <p>
	 * 最大闲置连接数
	 */
	private static int maxIdle;
	/**
	 * <p>
	 * 获得连接的最大等待毫秒数
	 */
	private static int maxWait;

	/** 指定超时时间，单位：秒。超过该时间后如果链接满了，会销毁超时的空闲链接 */
	private static int overtime;
	/**
	 * <p>
	 * 数据库配置项的名字
	 *
	 */
	private String alias;
	/**
	 * <p>
	 * 数据库连接
	 *
	 */
	private Connection conn;
	/** 重试次数 */
	private int retryCount;
	/**
	 * <p>
	 * C3P0工具类
	 *
	 */
	private C3P0Util cu;

	/** 重连次数 */
	private static final int RETRY_COUNT = 10;

	/** 重连等待时间，单位毫秒 */
	private static final int WAITING_RECONNECT_TIME = 10000;
	private static String[] freeAlias;

	static {
		Properties pro = new Properties();
		try {
			pro.load(JDBCUtil.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			System.out.println("未找到配置文件！！！");
		}
		pool = POOL.valueOf(pro.getProperty("pool"));

		{// @deprecated
			String overtimeStr = pro.getProperty("overtime");
			if (overtimeStr != null && overtimeStr.trim().length() > 0) {
				overtimeStr = overtimeStr.trim();
				if (overtimeStr.matches("[0-9]+")) {
					overtime = Integer.parseInt(overtimeStr);
				}
			}

			String freeAliasStr = pro.getProperty("freeAlias");
			if (freeAliasStr != null && freeAliasStr.trim().length() > 0) {
				freeAlias = pro.getProperty("freeAlias").split("[,，]");
			}
		}

		switch (pool) {
		case NONE:
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			driverPath = pro.getProperty("driverPath");
			break;
		case C3P0:
			break;
		case PROXOOL:
			File conFileSrc = new File("src/proxool.xml");
			File conFileBin = new File("bin/proxool.xml");
			try {
				if (conFileSrc.exists()) {
					JAXPConfigurator.configure("src/proxool.xml", false);
				} else if (conFileBin.exists()) {
					JAXPConfigurator.configure("bin/proxool.xml", false);
				}
			} catch (ProxoolException e) {
				e.printStackTrace();
			}
			break;
		case DBCP:
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			driverPath = pro.getProperty("driverPath");
			initialSize = Integer.parseInt(pro.getProperty("initialSize"));
			maxActive = Integer.parseInt(pro.getProperty("maxActive"));
			maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
			maxWait = Integer.parseInt(pro.getProperty("maxWait"));
		default:
		}
	}

	public JDBCUtil(String alias) {
		this.alias = alias;

		switch (pool) {
		case NONE:
			break;
		case C3P0:
			System.out.println("使用C3P0连接池");
			break;
		case PROXOOL:
			System.out.println("使用PROXOOL连接池");
			break;
		case DBCP:
			System.out.println("使用DBCP连接池");
		default:
		}

		open();
	}

	/**
	 * <p>
	 * 重新连接
	 *
	 * @return 连接
	 */
	public Connection reOpen() {
		System.out.println("re open [" + retryCount + "]");
		retryCount--;
		if (retryCount <= 0) {
			try {
				retryCount = RETRY_COUNT;
				System.out.println("Connection full: waiting...");
				// free();
				Thread.sleep(WAITING_RECONNECT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			switch (pool) {
			case NONE:
				Class.forName(driverPath);
				conn = DriverManager.getConnection(url, user, password);
				break;
			case C3P0:
				cu = new C3P0Util();
				cu.openDataSource();
				conn = cu.getDataSource().getConnection();
				break;
			case PROXOOL:
				conn = DriverManager.getConnection("proxool." + alias);
				break;
			case DBCP:
				conn = DBCPUtil.initDS(url, user, password, driverPath, initialSize, maxActive, maxIdle, maxWait)
						.getConnection();
				break;
			default:
			}
			// } catch (MySQLNonTransientConnectionException e) {
			// reOpen();
		} catch (SQLException e) {
			reOpen();
			// String sqlState = e.getSQLState();
			// if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
			// reOpen();
			// } else {
			// retryCount = 0;
			// e.printStackTrace();
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * <p>
	 * 打开连接
	 *
	 * @return 连接
	 */
	public Connection open() {
		retryCount = RETRY_COUNT;
		try {
			if (conn == null || conn.isClosed()) {
				return reOpen();
			}
		} catch (SQLException e) {
			return reOpen();
		}
		return conn;
	}

	/**
	 * <p>
	 * 关闭连接
	 *
	 */
	private void close() {
		try {
			// switch (pool) {
			// case NONE:
			// conn.close();
			// break;
			// // 连接池管理是否关闭连接
			// case C3P0:
			// case PROXOOL:
			// break;
			// case DBCP:
			// break;
			// default:
			// }
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 判断连接是否关闭
	 *
	 * @return 连接是否关闭
	 */
	public boolean isConnClosed() {
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <p>
	 * 获取字段名称
	 *
	 * @param sql
	 *            预处理sql语句
	 * @param objs
	 *            预处理sql参数值
	 * @return 字段名称
	 */
	public String[] getColumnNames(String sql, Object... objs) {
		String[] columnNames = null;
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				columnNames = new String[columnCount];
				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = rsmd.getColumnName(i + 1);
				}
				rs.close();
				pstmt.close();

				retryCount = 0;
			} catch (SQLException e) {
				String sqlState = e.getSQLState();
				e.printStackTrace();
				if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
					retryCount--;
				} else {
					retryCount = 0;
				}
			} finally {
				close();
			}
		} while (retryCount > 0);
		return columnNames;
	}

	/**
	 * <p>
	 * 获取字段类型
	 *
	 * @param sql
	 *            预处理sql语句
	 * @param objs
	 *            预处理sql参数值
	 * @return 字段类型
	 */
	public String[] getColumnTypes(String sql, Object... objs) {
		String[] columnTypes = null;
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				columnTypes = new String[columnCount];
				for (int i = 0; i < columnTypes.length; i++) {
					columnTypes[i] = rsmd.getColumnTypeName(i + 1);
				}
				rs.close();
				pstmt.close();
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return columnTypes;
	}

	/**
	 * <p>
	 * 获取预处理语句对象
	 *
	 * @param sql
	 *            语句
	 * @return 预处理语句对象
	 */
	public PreparedStatement prepareStatement(String sql) {
		PreparedStatement pstmt = null;
		open();
		do {
			try {
				pstmt = conn.prepareStatement(sql);
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			}
		} while (retryCount > 0);
		return pstmt;
	}

	/**
	 * <p>
	 * 从数据库提取文件
	 *
	 * @param sql
	 * 
	 * 
	 * @param filePath
	 * @param blobColumnName
	 * @param objs
	 * @return 是否提取成功
	 */
	public boolean loadFromDatabase(String sql, String filePath, String blobColumnName, Object... objs) {
		FileOutputStream output = null;
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					InputStream input = rs.getBinaryStream(blobColumnName);
					if (File.separator.equals("\\")) {
						filePath = filePath.replace("/", "\\\\");
					} else {
						filePath = filePath.replace('\\', File.separatorChar);
					}
					File target = new File(filePath);
					if (target.exists()) {
						continue;
					}
					output = new FileOutputStream(target);
					byte[] cache = new byte[512];
					int len = 0;
					while ((len = input.read(cache)) > 0) {
						output.write(cache, 0, len);
					}
				}
				if (output != null) {
					output.close();
				}
				rs.close();
				pstmt.close();
				retryCount = 0;
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				reOpen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (retryCount > 0);
		return false;
	}

	/**
	 * <p>
	 * 返回LinkedList<String[]>搜索分页结果
	 *
	 * @param sql
	 * @param page
	 * @param perCount
	 * @param obj
	 * @return LinkedList<String[]>搜索分页结果
	 */
	public LinkedList<String[]> executeQueryPagingToString(String sql, String page, int perCount, Object... obj) {
		return executeQueryPagingToString(sql, Integer.parseInt(page), perCount, obj);
	}

	/**
	 * <p>
	 * 返回LinkedList<String[]>搜索分页结果
	 *
	 * @param sql
	 * @param page
	 * @param perCount
	 * @param obj
	 * @return LinkedList<String[]>搜索分页结果
	 */
	public LinkedList<String[]> executeQueryPagingToString(String sql, int page, int perCount, Object... obj) {
		LinkedList<String[]> aList = executeQueryToStrings(sql, obj);
		aList = new LinkedList<String[]>();
		sql += " limit ?, ?";
		Object[] objs = new Object[obj.length + 2];
		int tempCount;
		for (tempCount = 0; tempCount < obj.length; tempCount++) {
			objs[tempCount] = obj[tempCount];
		}
		int start = (page - 1) * perCount;
		objs[tempCount] = start;
		objs[tempCount + 1] = start + perCount;

		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				String[] columnNames = new String[columnCount];
				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = rsmd.getColumnName(i + 1);
				}

				while (rs.next()) {
					String[] temp = new String[columnCount];
					for (int i = 0; i < columnCount; i++) {
						temp[i] = rs.getString(columnNames[i]);
					}
					aList.add(temp);
				}

				rs.close();
				pstmt.close();
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return aList;
	}

	/**
	 * <p>
	 * 直接执行sql并获得搜索结果
	 *
	 * @param sql
	 *            搜索sql语句
	 * @return 搜索结果
	 */
	public LinkedList<String[]> search(String sql) {
		return executeQueryToStrings(sql);
	}

	/**
	 * <p>
	 * 执行一条预处理语句
	 *
	 * @param sql
	 *            预处理语句
	 * @param objs
	 *            参数值
	 * @return 影响的行数
	 */
	public int execute(String sql, Object... objs) {
		int line = -1;
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof File) {
						File file = (File) objs[i];
						pstmt.setBinaryStream(i + 1, new DataInputStream(new FileInputStream(file)),
								(int) file.length());
						continue;
					} else if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				line = pstmt.executeUpdate();
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return line;
	}

	/**
	 * <p>
	 * 预处理语句进行查询
	 *
	 * @param sql
	 *            预处理语句
	 * @param objs
	 *            参数值
	 * @return 查询结果
	 */
	public LinkedList<Map<String, Object>> executeQuery(String sql, Object... objs) {
		LinkedList<Map<String, Object>> aList = new LinkedList<Map<String, Object>>();
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				String[] columnNames = new String[columnCount];
				String[] columnTypes = new String[columnCount];
				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = rsmd.getColumnName(i + 1);
					columnTypes[i] = rsmd.getColumnTypeName(i + 1);
				}

				while (rs.next()) {// 遍历结果集
					Map<String, Object> rsMap = new HashMap<String, Object>();
					for (int i = 0; i < columnCount; i++) {
						String key = columnNames[i];
						Object value = null;
						if (columnTypes[i].contains("INT")) {
							value = rs.getInt(columnNames[i]);
						} else if (columnTypes[i].equals("DATE")) {
							value = rs.getDate(columnNames[i]);
						} else if (columnTypes[i].equals("TIME")) {
							value = rs.getTime(columnNames[i]);
						} else if (columnTypes[i].equals("TIMESTAMP")) {
							value = rs.getTimestamp(columnNames[i]);
						} else if (columnTypes[i].equals("BLOB")) {
							value = rs.getBinaryStream(columnNames[i]);
						} else {
							value = rs.getString(columnNames[i]);
						}
						rsMap.put(key, value);
					}
					aList.add(rsMap);
				}

				rs.close();
				rs = null;
				pstmt.close();
				pstmt = null;
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return aList;
	}

	/**
	 * <p>
	 * 销毁超时的链接，使用指定的alias=freeSleepConn
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void free() {
		for (String alias : freeAlias) {
			JDBCUtil juFree = JDBCFactory.getJDBCUtil(alias);
			juFree.freeSleepConn();
		}
	}

	/**
	 * <p>
	 * 销毁超时的链接
	 * 
	 * @deprecated
	 *
	 * @return 是否成功
	 */
	private boolean freeSleepConn() {
		open();
		do {
			try {
				System.out.println("free idle connection");
				PreparedStatement pstmt = conn.prepareStatement("show processlist");
				ResultSet rs = pstmt.executeQuery();

				LinkedList<Object> ids = new LinkedList<Object>();
				while (rs.next()) {// 遍历结果集
					int id = rs.getInt("Id");
					String command = rs.getString("Command");
					int time = rs.getInt("Time");
					if (command.equals("Sleep") && time >= overtime) {
						ids.add(id);
					}
				}

				rs.close();
				rs = null;
				pstmt.close();
				pstmt = null;
				retryCount = 0;
				executeBatch("kill ?", 1, ids);
			} catch (SQLException e) {
				reOpen();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return true;
	}

	/**
	 * <p>
	 * 执行预处理语句并根据参数进行查询
	 *
	 * @param sql
	 *            预处理语句
	 * @param objs
	 *            参数值
	 * @return 查询结果
	 */
	public LinkedList<String[]> executeQueryToStrings(String sql, Object... objs) {
		LinkedList<String[]> aList = new LinkedList<String[]>();
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				String[] columnNames = new String[columnCount];
				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = rsmd.getColumnName(i + 1);
				}
				while (rs.next()) {
					String[] temp = new String[columnCount];
					for (int i = 0; i < columnCount; i++) {
						temp[i] = rs.getString(columnNames[i]);
					}
					aList.add(temp);
				}
				rs.close();
				pstmt.close();
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return aList;
	}

	/**
	 * <p>
	 * 执行预处理语句
	 *
	 * @param sql
	 *            预处理语句
	 * @param objs
	 *            参数值
	 * @return 影响的行数
	 */
	public int executeUpdate(String sql, Object... objs) {
		int line = -1;
		open();
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < objs.length; i++) {
					if (objs[i] != null && objs[i] instanceof File) {
						File file = (File) objs[i];
						pstmt.setBinaryStream(i + 1, new DataInputStream(new FileInputStream(file)),
								(int) file.length());
						continue;
					} else if (objs[i] != null && objs[i] instanceof java.lang.String) {
						objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
					}
					pstmt.setObject(i + 1, objs[i]);
				}
				line = pstmt.executeUpdate();
				pstmt.close();
				retryCount = 0;
			} catch (SQLException e) {
				reOpen();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} while (retryCount > 0);
		return line;
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * 批量执行同一条预处理语句
	 *
	 * @param sql
	 *            预处理语句
	 * @param valueNumber
	 *            每条预处理语句的参数数量
	 * @param values
	 *            参数值
	 * @return 影响的行数
	 */
	public synchronized int[] executeBatch(String sql, int valueNumber, LinkedList<Object> values) {
		int[] lines = null;
		open();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		do {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < values.size(); i = i + valueNumber) {
					for (int j = 0; j < valueNumber; j++) {
						if (values.get(i + j) != null && values.get(i + j) instanceof java.lang.String) {
							values.set(i + j, SecurityUtil.sqlValueFilter(values.get(i + j).toString()));
						}
						pstmt.setObject(j + 1, values.get(i + j));
					}
					pstmt.addBatch();
				}
				lines = pstmt.executeBatch();
				conn.commit();
				pstmt.close();
				retryCount = 0;
			} catch (SQLException e) {
				String sqlState = e.getSQLState();
				if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
					reOpen();
				} else {
					retryCount = 0;
					try {
						conn.rollback();
					} catch (SQLException e1) {
					}
					e.printStackTrace();
				}
			} finally {
				close();
			}
		} while (retryCount > 0);
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * <p>
	 * 判断是否有链接
	 *
	 */
	public boolean hasConn() {
		return conn != null;
	}

}