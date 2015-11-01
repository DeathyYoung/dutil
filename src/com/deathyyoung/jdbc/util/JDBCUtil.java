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

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.deathyyoung.common.util.SecurityUtil;
import com.deathyyoung.jdbc.bean.ColumnType;

/**
 * <p>
 * JDBC工具类
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
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
	private POOL pool;
	/**
	 * <p>
	 * 数据库url
	 */
	private String url;
	/**
	 * <p>
	 * 数据库配置项的名字
	 *
	 */
	private String alias;
	/**
	 * <p>
	 * 用户名
	 *
	 */
	private String user;
	/**
	 * <p>
	 * 密码
	 *
	 */
	private String password;
	/**
	 * <p>
	 * 数据库连接
	 *
	 */
	private Connection conn;
	/**
	 * <p>
	 * C3P0工具类
	 *
	 */
	private C3P0Util cu;
	/**
	 * <p>
	 * 数据库驱动路径
	 */
	private String driverPath;
	/**
	 * <p>
	 * DBCP 初始连接池连接个数
	 *
	 */
	private int initialSize;
	/**
	 * <p>
	 * 最大激活连接数
	 */
	private int maxActive;
	/**
	 * <p>
	 * 最大闲置连接数
	 */
	private int maxIdle;
	/**
	 * <p>
	 * 获得连接的最大等待毫秒数
	 */
	private int maxWait;

	/**
	 * <p>
	 * 预处理语句
	 *
	 */
	private PreparedStatement pstmt;
	/**
	 * <p>
	 * 查询结果集
	 */
	private ResultSet rs;
	/**
	 * <p>
	 * 影响的行数
	 *
	 */
	private int line;
	/**
	 * <p>
	 * 批处理影响的行数
	 *
	 */
	private int[] lines;

	public JDBCUtil(String alias) {
		pstmt = null;
		rs = null;
		line = 0;
		lines = null;
		Properties pro = new Properties();
		try {
			pro.load(JDBCUtil.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			System.out.println("未找到配置文件！！！");
		}
		pool = POOL.valueOf(pro.getProperty("pool"));
		this.alias = alias;
		try {
			switch (pool) {
			case NONE:
				System.out.println("不使用连接池");
				driverPath = pro.getProperty("driverPath");
				url = pro.getProperty("url");
				user = pro.getProperty("user");
				password = pro.getProperty("password");
				Class.forName(driverPath);
				conn = DriverManager.getConnection(url, user, password);
				break;
			case C3P0:
				cu = new C3P0Util();
				System.out.println("使用C3P0连接池");
				cu.openDataSource();
				conn = cu.getDataSource().getConnection();
				break;
			case PROXOOL:
				System.out.println("使用PROXOOL连接池");
				File conFile = new File("src/proxool.xml");
				if (conFile.exists()) {
					JAXPConfigurator.configure("src/proxool.xml", false);
				}
				conn = DriverManager.getConnection("proxool." + alias);
				;
				break;
			case DBCP:
				System.out.println("使用DBCP连接池");
				driverPath = pro.getProperty("driverPath");
				url = pro.getProperty("url");
				user = pro.getProperty("user");
				password = pro.getProperty("password");
				initialSize = Integer.parseInt(pro.getProperty("initialSize"));
				maxActive = Integer.parseInt(pro.getProperty("maxActive"));
				maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
				maxWait = Integer.parseInt(pro.getProperty("maxWait"));
				conn = DBCPUtil.initDS(url, user, password, driverPath,
						initialSize, maxActive, maxIdle, maxWait)
						.getConnection();
			default:
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 打开连接
	 *
	 * @return 连接
	 */
	public Connection open() {
		try {
			if (conn == null || conn.isClosed()) {
				switch (pool) {
				case NONE:
					Class.forName(driverPath);
					conn = DriverManager.getConnection(url, user, password);
					break;
				case C3P0:
					conn = cu.getDataSource().getConnection();
					break;
				case PROXOOL:
					conn = DriverManager.getConnection("proxool." + alias);
					break;
				case DBCP:
					conn = DBCPUtil.initDS(url, user, password, driverPath,
							initialSize, maxActive, maxIdle, maxWait)
							.getConnection();
					break;
				default:
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("获得类出错！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("获得连接出错！");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * <p>
	 * 关闭连接
	 *
	 */
	private void close() {
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
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			columnNames = new String[columnCount];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rsmd.getColumnName(i + 1);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
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
	public ColumnType[] getColumnTypes(String sql, Object... objs) {
		ColumnType[] columnTypes = null;
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			columnTypes = new ColumnType[columnCount];
			for (int i = 0; i < columnTypes.length; i++) {
				columnTypes[i] = ColumnType.valueOf(rsmd
						.getColumnTypeName(i + 1));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
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
		open();
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	/**
	 * <p>
	 * 设置预处理语句的int型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setInt(int i, int var) {
		try {
			pstmt.setInt(i, var);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 设置预处理语句的int型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setIntByString(int i, String var) {
		setInt(i, Integer.parseInt(var));
	}

	/**
	 * <p>
	 * 设置预处理语句的long型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setLong(int i, long var) {
		try {
			pstmt.setLong(i, var);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 设置预处理语句的String型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setString(int i, String var) {
		try {
			pstmt.setString(i, var);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 设置预处理语句的long型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setLongByString(int i, String var) {
		setLong(i, Long.parseLong(var));
	}

	/**
	 * <p>
	 * 设置预处理语句的File型参数值
	 *
	 * @param i
	 *            第i个参数
	 * @param var
	 *            参数值
	 */
	public void setFile(int i, File file) {
		try {
			pstmt.setBinaryStream(i, new DataInputStream(new FileInputStream(
					file)), (int) file.length());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 从数据库提取文件
	 *
	 * @param sql
	 *            　
	 * @param filePath
	 * @param blobColumnName
	 * @param objs
	 * @return　是否提取成功
	 */
	public boolean loadFromDatabase(String sql, String filePath,
			String blobColumnName, Object... objs) {
		FileOutputStream output = null;
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
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
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <p>
	 * 返回LinkedList<String[]>搜索结果
	 *
	 * @return　LinkedList<String[]>搜索结果
	 */
	public LinkedList<String[]> executeQuery() {
		LinkedList<String[]> aList = new LinkedList<String[]>();
		try {
			rs = pstmt.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return aList;
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
	public LinkedList<String[]> executeQueryPagingToString(String sql,
			String page, int perCount, Object... obj) {
		return executeQueryPagingToString(sql, Integer.parseInt(page),
				perCount, obj);
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
	public LinkedList<String[]> executeQueryPagingToString(String sql,
			int page, int perCount, Object... obj) {
		LinkedList<String[]> aList = executeQueryToStrings(sql, obj);

		String[] infos = new String[4];
		infos[0] = page + "";// 当前页
		infos[1] = ((aList.size() - 1) / perCount + 1) + "";// 总页数
		infos[2] = perCount + "";// 每页信息条数
		infos[3] = aList.size() + "";// 总信息条数

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
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
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
		prepareStatement(sql);
		return executeQuery();
	}

	/**
	 * <p>
	 * 执行一条预处理语句，需要事先配置好预处理语句
	 *
	 * @return 影响的行数
	 */
	public int executeUpdate() {
		line = -1;
		open();
		try {
			line = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return line;
	}

	/**
	 * <p>
	 * 执行一条语句，需要事先配置好预处理语句
	 *
	 * @return 是否成功
	 */
	public boolean execute() {
		boolean flag = false;
		open();
		try {
			flag = pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
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
		line = -1;
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof File) {
					File file = (File) objs[i];
					pstmt.setBinaryStream(i + 1, new DataInputStream(
							new FileInputStream(file)), (int) file.length());
					continue;
				} else if (objs[i] != null
						&& objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			line = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	public LinkedList<Map<String, Object>> executeQuery(String sql,
			Object... objs) {
		LinkedList<Map<String, Object>> aList = new LinkedList<Map<String, Object>>();
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] columnNames = new String[columnCount];
			ColumnType[] columnTypes = new ColumnType[columnCount];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rsmd.getColumnName(i + 1);
				columnTypes[i] = ColumnType.valueOf(rsmd
						.getColumnTypeName(i + 1));
			}

			while (rs.next()) {// 遍历结果集
				Map<String, Object> rsMap = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String key = columnNames[i];
					Object value = null;
					switch (columnTypes[i]) {
					case INTEGER:
					case TINYINT:
					case BIGINT:
						value = rs.getInt(columnNames[i]);
						break;
					case DATE:
						value = rs.getDate(columnNames[i]);
						break;
					case TIME:
						value = rs.getTime(columnNames[i]);
						break;
					case TIMESTAMP:
						value = rs.getTimestamp(columnNames[i]);
						break;
					case BLOB:
						value = rs.getBinaryStream(columnNames[i]);
						break;
					case TEXT:
					case CLOB:
					case CHAR:
					case VARCHAR:
					default:
						value = rs.getString(columnNames[i]);

					}
					rsMap.put(key, value);
				}
				aList.add(rsMap);
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return aList;
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
	public LinkedList<String[]> executeQueryToStrings(String sql,
			Object... objs) {
		LinkedList<String[]> aList = new LinkedList<String[]>();
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			rs = pstmt.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
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
		line = -1;
		open();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null && objs[i] instanceof File) {
					File file = (File) objs[i];
					pstmt.setBinaryStream(i + 1, new DataInputStream(
							new FileInputStream(file)), (int) file.length());
					continue;
				} else if (objs[i] != null
						&& objs[i] instanceof java.lang.String) {
					objs[i] = SecurityUtil.sqlValueFilter(objs[i].toString());
				}
				pstmt.setObject(i + 1, objs[i]);
			}
			line = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * <p>
	 * 批量提交，装入缓冲区
	 *
	 * @param sql
	 *            语句
	 */
	public void addBatch(String sql) {
		try {
			pstmt.addBatch(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 执行容器中的所有语句
	 *
	 * @return 影响的行数
	 */
	public int[] executeBatch() {
		lines = null;
		open();
		try {
			lines = pstmt.executeBatch();
			commit();
			pstmt.close();
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		} finally {
			close();
		}
		return lines;
	}

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
	public int[] executeBatch(String sql, int valueNumber,
			LinkedList<Object> values) {
		lines = null;
		try {
			open();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.size(); i = i + valueNumber) {
				for (int j = 0; j < valueNumber; j++) {
					if (values.get(i + j) != null
							&& values.get(i + j) instanceof java.lang.String) {
						values.set(i + j, SecurityUtil.sqlValueFilter(values
								.get(i + j).toString()));
					}
					pstmt.setObject(j + 1, values.get(i + j));
				}
				pstmt.addBatch();
			}
			lines = pstmt.executeBatch();
			commit();
			pstmt.close();
		} catch (SQLException e) {
			rollback();
			e.printStackTrace();
		} finally {
			close();
		}
		return lines;
	}

	/**
	 * <p>
	 * 获取最近一次执行影响的行数
	 *
	 * @return 最近一次执行影响的行数
	 */
	public int getLine() {
		return line;
	}

	/**
	 * <p>
	 * 获取最近一次批量执行影响的行数
	 *
	 * @return 最近一次批量执行影响的行数
	 */
	public int[] getLines() {
		return lines;
	}

	/**
	 * <p>
	 * 连接提交
	 *
	 */
	private void commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 回滚
	 * */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}