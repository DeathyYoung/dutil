package com.deathy.hibernate.util;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * <p>
 * HibernateUtil
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class HibernateUtil {

	/**
	 * <p>
	 * 根据注解生成数据库表
	 */
	public static void createDatabaseByAnnotation() {
		Configuration config = null;
		Transaction tx = null;
		config = new AnnotationConfiguration().configure();
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		tx = session.beginTransaction();
		tx.commit();
	}

	/**
	 * <p>
	 * 根据xml配置文件生成数据库表
	 *
	 * @param xmlPath
	 *            xml配置文件路径
	 */
	public static void createDatabaseByAnnotation(String xmlPath) {
		Configuration config = null;
		Transaction tx = null;
		config = new Configuration().configure(new File(xmlPath));
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		tx = session.beginTransaction();
		tx.commit();
	}

	/**
	 * <p>
	 * 根据xml配置文件生成数据库表
	 *
	 * @param xmlFile
	 *            xml配置文件
	 */
	public static void createDatabaseByXML(File xmlFile) {
		Configuration config = null;
		Transaction tx = null;
		config = new Configuration().configure(xmlFile);
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		tx = session.beginTransaction();
		tx.commit();
	}

}
