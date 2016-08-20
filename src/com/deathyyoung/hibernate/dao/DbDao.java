package com.deathyyoung.hibernate.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.deathyyoung.common.bean.PageBean;

/**
 * <p>
 * 数据库操作接口
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public interface DbDao {

	/**
	 * <p>
	 * 数据查询(根据主键)
	 *
	 * @param cla
	 *            持久态对象
	 * @param id
	 *            主键
	 * @return Object类型， 持久态对象
	 */
	public abstract Object getObject(Class<?> cla, int id);

	/**
	 * <p>
	 * 数据查询(根据主键)
	 *
	 * @param cla
	 *            持久态对象
	 * @param id
	 *            主键
	 * @return Object类型， 持久态对象
	 */
	public abstract Object getObject(Class<?> cla, String id);

	/**
	 * <p>
	 * 数据查询(根据语句)
	 *
	 * @param hql
	 *            HQL语句
	 * @param objs
	 *            预处理语句参数数组
	 * @return PageBean类型， 查询结果分页数据
	 */
	public abstract PageBean getListByPage(String hql, Object... objs);

	/**
	 * <p>
	 * 数据查询(根据语句)
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objs
	 *            预处理语句参数数组
	 * @return List类型查询结果数据表
	 */
	public List<Object> getList(String hql, Object... objs);

	/**
	 * 
	 * 数据查询(根据语句)
	 * 
	 * @param hql
	 *            HQL语句
	 * 
	 */
	/**
	 * <p>
	 * 数据查询(根据语句)
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objs
	 *            预处理语句参数数组
	 * @return Object类型， 查询结果单个数据
	 */
	public abstract Object uniqueResult(String hql, Object... objs);

	/**
	 * <p>
	 * 数据查询(根据语句)
	 * 
	 * @param hql
	 *            HQL语句
	 * @return Iterator类型， 数据查询结果迭代器
	 */
	public abstract Iterator<Object> getIterator(String hql);

	/**
	 * 
	 * 数据入库
	 * 
	 * @param object
	 *            对象
	 * @return boolean类型，是否成功
	 */
	public abstract boolean save(Object object);

	/**
	 * 
	 * 数据入库
	 * 
	 * @param object
	 *            对象
	 * @return boolean类型，是否成功
	 * 
	 */
	public abstract boolean delete(Object object);

	/**
	 * 
	 * 数据更新
	 * 
	 * @param object
	 *            对象
	 * @return boolean类型，是否成功
	 * 
	 */
	public abstract boolean update(Object object);

	/**
	 * 
	 * 数据入库或更新
	 * 
	 * @param object
	 *            对象
	 * @return boolean类型，是否成功
	 * 
	 */
	public abstract boolean saveOrUpdate(Object object);

	/**
	 * 
	 * Hibernate Session 对象传入
	 * 
	 * @param hsession
	 *            Hibernate Session对象
	 * 
	 */
	public abstract void setHsession(Session hsession);

	/**
	 * <p>
	 * Hibernate Session 对象传出
	 *
	 * @return Hibernate Session 对象
	 */
	public abstract Session getHsession();

	/**
	 * <p>
	 * 查询得到的分页结果
	 *
	 * @param hql
	 *            HQL语句
	 * @param maxResults
	 *            每页最大数据条数
	 * @param firstResult
	 *            起始记录位置
	 * @param maxNum
	 *            总记录数
	 * @param objs
	 *            预处理语句参数数组
	 * @return 分页结果
	 */
	public abstract PageBean pageList(String hql, int maxResults,
			int firstResult, int maxNum, Object... objs);

	/**
	 * <p>
	 * 查询分页结果的最大页数
	 *
	 * @param hql
	 *            HQL语句
	 * @param objs
	 *            预处理语句参数数组
	 * @return 分页结果的最大页数
	 */
	public abstract int pageMaxList(String hql, Object... objs);

	public abstract int executeBySql(String sql, Object... objs);

	public abstract List<Object[]> queryBySql(String sql, Object... objs);
}
