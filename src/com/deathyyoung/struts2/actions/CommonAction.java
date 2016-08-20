package com.deathyyoung.struts2.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;

import com.deathyyoung.common.bean.PageBean;
import com.deathyyoung.hibernate.dao.DbDao;
import com.deathyyoung.hibernate.factory.DbDaoFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
* <p> 依赖org.hibernate.Session
* 
* @author <a href="#" target="_blank">Deathy Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
*/ 
public class CommonAction extends ActionSupport implements ServletRequestAware {

	/** serialVersionUID */
	private static final long serialVersionUID = 3500838959774845740L;
	/** REDIRECT_SUCCESS */
	protected static final String REDIRECT_SUCCESS = "redirect_success";
	/** DELETE_SUCCESS */
	protected static final String DELETE_SUCCESS = "delete_success";
	/** ADD_SUCCESS */
	protected static final String ADD_SUCCESS = "add_success";
	/** CHAIN_SUCCESS */
	protected static final String CHAIN_SUCCESS = "chain_success";
	/** CHAIN_SUCCESS */
	protected static final String EDIT_SUCCESS = "edit_success";
	/** REMOVED */
	protected static final String REMOVED = "removed";
	/** DbDao对象 */
	protected DbDao db = DbDaoFactory.getDbDaoBean();
	/** Hibernate Session 对象 */
	protected Session hsession;
	/** 公共转向源 */
	protected PageBean pageBean;
	/** 所有数量 */
	protected int allMax;
	/** 查询页 */
	protected int to_page;
	/** 总页数 */
	protected int maxPageNum;
	/** 公共转向源 */
	protected String fromURL;
	/** HQL */
	protected String hql;
	/** 公共转向标 */
	protected String toURL;
	/** AJAX信息 */
	protected String msg;
	/** 关注情况 */
	protected String listen;

	public void setServletRequest(HttpServletRequest request) {
		// 通过IOC机制注入Request对象
		hsession = (Session) request.getAttribute("hsession");
		db.setHsession(hsession);
	}

	/**
	 * @return the db
	 */
	public DbDao getDb() {
		return db;
	}

	/**
	 * @param db
	 *            the db to set
	 */
	public void setDb(DbDao db) {
		this.db = db;
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

	/**
	 * @return the pageBean
	 */
	public PageBean getPageBean() {
		return pageBean;
	}

	/**
	 * @param pageBean
	 *            the pageBean to set
	 */
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	/**
	 * @return the fromURL
	 */
	public String getFromURL() {
		return fromURL;
	}

	/**
	 * @param fromURL
	 *            the fromURL to set
	 */
	public void setFromURL(String fromURL) {
		this.fromURL = fromURL;
	}

	/**
	 * @return the toURL
	 */
	public String getToURL() {
		return toURL;
	}

	/**
	 * @param toURL
	 *            the toURL to set
	 */
	public void setToURL(String toURL) {
		this.toURL = toURL;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the listen
	 */
	public String getListen() {
		return listen;
	}

	/**
	 * @param listen
	 *            the listen to set
	 */
	public void setListen(String listen) {
		this.listen = listen;
	}

	/**
	 * @return the allMax
	 */
	public int getAllMax() {
		return allMax;
	}

	/**
	 * @param allMax
	 *            the allMax to set
	 */
	public void setAllMax(int allMax) {
		this.allMax = allMax;
	}

	/**
	 * @return the to_page
	 */
	public int getTo_page() {
		return to_page;
	}

	/**
	 * @param to_page
	 *            the to_page to set
	 */
	public void setTo_page(int to_page) {
		this.to_page = to_page;
	}

	/**
	 * @return the maxPageNum
	 */
	public int getMaxPageNum() {
		return maxPageNum;
	}

	/**
	 * @param maxPageNum
	 *            the maxPageNum to set
	 */
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}
}
