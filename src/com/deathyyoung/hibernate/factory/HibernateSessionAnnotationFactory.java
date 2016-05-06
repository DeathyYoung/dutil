package com.deathyyoung.hibernate.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * Hibernate Session 工厂
 * 
 * @author <a href="#" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class HibernateSessionAnnotationFactory {

	/** Configuration负责配置并启动Hibernate */
	private static Configuration configuration;

	/** SessionFactory负责生成Session对象 */
	private static SessionFactory sessionFactory;

	/**
	 * 静态初始化，读取cfg配置文件
	 */
	static {
		configuration = new AnnotationConfiguration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	/**
	 * <p>
	 * 获得Session对象
	 *
	 * @return Session对象
	 */
	public static Session getSession() {
		try {
			return sessionFactory == null ? null : sessionFactory.openSession();
		} catch (Exception e) {
			return null;
		}
	}
}