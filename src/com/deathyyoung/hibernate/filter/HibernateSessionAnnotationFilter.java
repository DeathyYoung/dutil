package com.deathyyoung.hibernate.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.deathyyoung.hibernate.dao.DbDao;
import com.deathyyoung.hibernate.factory.DbDaoFactory;
import com.deathyyoung.hibernate.factory.HibernateSessionAnnotationFactory;

public class HibernateSessionAnnotationFilter implements Filter {

	/** db操作对象 */
	private DbDao db = DbDaoFactory.getDbDaoBean();

	/*
	 * @see Session过滤器
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		Session hsession = HibernateSessionAnnotationFactory.getSession();
		db.setHsession(hsession);
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.setAttribute("hsession", hsession);
		chain.doFilter(arg0, arg1);
		hsession.close();
	}

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
