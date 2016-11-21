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

import com.deathyyoung.common.util.ExceptionUtil;
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

	/** 指定超时时间，单位：秒。超过该时间后如果不能确定连接有效，则会认为连接失效 */
	@SuppressWarnings("unused")
	private static int validTimeout;
	/** 数据库配置项的名字 */
	private String alias;
	/** 数据库连接 */
	private Connection conn;
	/** 连接满 */
	private boolean connectionFull;
	/** 语句数量。如果数量为0则新建连接；如果数量 > 0，不关闭连接；如果减少至0，再释放连接 */
	private int statementNum;
	/** C3P0工具类 */
	private C3P0Util cu;
	/** 当前conn连接重连次数 */
	private int retryConnCount;
	/** 连接是否关闭 */
	private boolean connClose;
	/** sql执行尝试次数 */
	private static final int RETRY_COUNT = 3;
	/** conn连接重连次数 */
	private static final int RETRY_CONN_COUNT = 10;

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
			String validTimeoutStr = pro.getProperty("validTimeout");
			if (validTimeoutStr != null && validTimeoutStr.trim().length() > 0) {
				validTimeoutStr = validTimeoutStr.trim();
				if (validTimeoutStr.matches("[0-9]+")) {
					validTimeout = Integer.parseInt(validTimeoutStr);
				}
			}
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
				ExceptionUtil.log(e);
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
		connectionFull = false;
		statementNum = 0;

		this.alias = alias;

		switch (pool) {
		case NONE:
			break;
		case C3P0:
			break;
		case PROXOOL:
			break;
		case DBCP:
		default:
		}
	}

	/**
	 * <p>
	 * 重新连接
	 *
	 * @return 连接
	 */
	private synchronized Connection reOpen() {
		if (isConnValid()) {
			return conn;
		}

		do {
			if (connectionFull) {
				try {
					System.out.println("Connection full: waiting...");
					// free();
					Thread.sleep(WAITING_RECONNECT_TIME);
				} catch (InterruptedException e) {
					ExceptionUtil.log(e);
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
				retryConnCount = 0;// 成功
				connectionFull = false;
				return conn;
			} catch (SQLException e) {
				connectionFull = true;
				ExceptionUtil.log(e);
			} catch (ClassNotFoundException e) {
				ExceptionUtil.log(e);
			}
		} while (--retryConnCount >= 0);
		return conn;
	}

	/**
	 * <p>
	 * 打开连接
	 *
	 * @return 连接
	 */
	private Connection open() {
		statementNum++;
		System.out.println("statementNum:"+statementNum);
		if (!isConnValid()) {
			retryConnCount = RETRY_CONN_COUNT;
			conn = reOpen();
			if (isConnValid()) {
				connClose = true;
			}
		} else {
			connClose = true;
		}
		return conn;
	}

	/**
	 * <p>
	 * 关闭连接
	 *
	 */
	private void close() {
		statementNum--;
		try {
			if (statementNum <= 0 && isConnValid()) {
				conn.close();
				connClose = true;
			}
		} catch (SQLException e) {
			ExceptionUtil.log(e);
		}
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
		String[] columnNames = new String[0];
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				retryCount = 0;// 成功
			} catch (SQLException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {
				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		String[] columnTypes = new String[0];

		int retryCount = RETRY_COUNT;
		do {
			try {
				open();

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
				retryCount = 0;// 成功
			} catch (SQLException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败

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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
				pstmt = conn.prepareStatement(sql);
				retryCount = 0;
			} catch (SQLException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				retryCount = 0;// 成功
				return true;
			} catch (FileNotFoundException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} catch (SQLException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} catch (IOException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (retryCount > 0);
		return false;
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
	 * @return 是否成功
	 */
	public boolean execute(String sql, Object... objs) {
		boolean line = false;
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				line = pstmt.execute();
				retryCount = 0;// 成功
			} catch (SQLException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} catch (FileNotFoundException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} catch (Exception e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
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
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} catch (FileNotFoundException e) {
				if (retryCount == 1)
					ExceptionUtil.log(e);
			} finally {
				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败
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
		int retryCount = RETRY_COUNT;
		do {
			try {
				open();
				conn.setAutoCommit(false);
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
				if (retryCount == 1)
					ExceptionUtil.log(e);
				try {
					conn.rollback();
				} catch (SQLException e1) {
					ExceptionUtil.log(e1);
				}
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					if (retryCount == 1)
						ExceptionUtil.log(e);
				}

				close();
			}
		} while (--retryCount > 0);// retryCount=1时失败

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

	/**
	 * <p>
	 * TODO
	 *
	 * @return 判断连接是否可用
	 */
	public boolean isConnValid() {
		return hasConn() && !connClose;
	}

}