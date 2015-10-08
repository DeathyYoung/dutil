package com.deathy.hibernate.factory;

import com.deathy.hibernate.dao.DbDao;
import com.deathy.hibernate.dao.bean.DbDaoBean;

/**
 * <p>
 * 获取数据访问对象的工厂
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class DbDaoFactory {
	/**
	 * <p>
	 * 生成数据访问对象
	 *
	 * @return 数据访问对象
	 */
	public static DbDao getDbDaoBean() {
		try {
			return new DbDaoBean();
		} catch (Exception e) {
			return null;
		}
	}
}
