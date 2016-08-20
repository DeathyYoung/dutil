package com.deathyyoung.hibernate.dao.bean;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deathyyoung.common.bean.PageBean;
import com.deathyyoung.hibernate.dao.DbDao;

/**
 * <p>
 * 数据库操作的实现类
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class DbDaoBean implements DbDao {

	/** session对象 */
	private Session hsession;

	public Object getObject(Class<?> cla, int id) {
		try {
			Object object = hsession.get(cla, id);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getObject(Class<?> cla, String id) {
		try {
			Object object = hsession.get(cla, id);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PageBean getListByPage(String hql, Object... objs) {
		PageBean pagebean = new PageBean();
		try {
			Query query = hsession.createQuery(hql);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			List<Object> list = query.list();
			pagebean.setList(list);
			return pagebean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList(String hql, Object... objs) {
		try {
			Query query = hsession.createQuery(hql);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Iterator<Object> getIterator(String hql) {
		try {
			Iterator<Object> iter = hsession.createQuery(hql).iterate();
			return iter;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean save(Object object) {
		Transaction transaction = hsession.beginTransaction();
		try {
			hsession.save(object);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	public boolean delete(Object object) {
		Transaction transaction = hsession.beginTransaction();
		try {
			hsession.delete(object);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	public boolean update(Object object) {
		Transaction transaction = hsession.beginTransaction();
		try {
			// hsession.update(object);
			hsession.update(hsession.merge(object));
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	public boolean saveOrUpdate(Object object) {
		Transaction transaction = hsession.beginTransaction();
		try {
			// hsession.saveOrUpdate(object);
			hsession.saveOrUpdate(hsession.merge(object));
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public PageBean pageList(String hql, int maxResults, int firstResult,
			int maxNum, Object... objs) {
		PageBean pagebean = new PageBean();
		try {
			Query query = hsession.createQuery(hql);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			// 开始封装
			List<Object> list = query.list();
			pagebean.setList(list);// 封装分页结构
			System.out.println(new Date() + "当前页记录数量" + list.size() + "条");
			pagebean.setStartIndex(firstResult);// 封装当前页的分页起始位置
			System.out.println(new Date() + "当前页的分页起始位置" + firstResult);
			pagebean.setTotalNum(maxNum);// 封装总记录数
			System.out.println(new Date() + "总记录数量" + maxNum + "条");
			pagebean.setMaxPage((maxNum + (maxResults - 1)) / maxResults);// 封装总页数
			System.out.println(new Date() + "共有" + (maxNum + (maxResults - 1))
					/ maxResults + "页记录");
			pagebean.setPage(firstResult / maxResults + 1);// 封装当前页
			System.out.println(new Date() + "当前是第"
					+ (firstResult / maxResults + 1) + "页");
			pagebean.setRows(maxResults);// 封装每页显示记录数
			System.out.println(new Date() + "每页显示" + maxResults + "条");
			return pagebean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int pageMaxList(String hql, Object... objs) {
		try {
			Query query = hsession.createQuery(hql);
			query.setFirstResult(0);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			return query.list().size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> queryBySql(String sql, Object... objs) {
		try {
			SQLQuery query = hsession.createSQLQuery(sql);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int executeBySql(String sql, Object... objs) {
		try {
			SQLQuery query = hsession.createSQLQuery(sql);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			Transaction tx = hsession.beginTransaction();
			int result = query.executeUpdate();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Object uniqueResult(String hql, Object... objs) {
		try {
			Query query = hsession.createQuery(hql);
			for (int i = 0; i < objs.length; i++) {
				query.setParameter(i, objs[i]);
			}
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the hsession
	 */
	public Session getHsession() {
		return hsession;
	}

	/**
	 * @param hsession
	 *            the hsession to set
	 */
	public void setHsession(Session hsession) {
		this.hsession = hsession;
	}

}
