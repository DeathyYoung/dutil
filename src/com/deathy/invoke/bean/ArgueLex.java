package com.deathy.invoke.bean;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class ArgueLex {

	private Object object;

	private Class<?> cls;

	public ArgueLex() {
	}

	public ArgueLex(Object object, Class<?> cls) {
		this.object = object;
		this.cls = cls;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the cls
	 */
	public Class<?> getCls() {
		return cls;
	}

	/**
	 * @param cls
	 *            the cls to set
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
}
