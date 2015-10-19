package com.deathyyoung.invoke.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.deathyyoung.invoke.bean.ArgueLex;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class InvokeUtil {

	private static Class<?> type;

	/**
	 * <p>
	 * get the "set" method name
	 *
	 * @param fieldName
	 * @return the "set" method name
	 */
	public static String getSetName(String fieldName) {
		return "set" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
	}

	/**
	 * <p>
	 * get the "get" method name
	 *
	 * @param fieldName
	 *            fieldName
	 * @param cla
	 *            the class
	 * @return the "get" method name
	 */
	public static String getGetName(String fieldName, Class<?> cla) {
		if (cla != boolean.class && cla != Boolean.class) {
			return "get" + Character.toUpperCase(fieldName.charAt(0))
					+ fieldName.substring(1);
		}
		return "is" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
	}

	/**
	 * <p>
	 * Method 类的 invoke(Object obj, Object args[])方法接收的参数必须为对象
	 * <p>
	 * 如果参数为基本类型数据，必须转换为相应的包装类型的对象。invoke() 方法的返回值总是对象
	 * <p>
	 * 如果实际被调用的方法的返回类型是基本类型数据，那么 invoke() 方法会把它转换为相应的包装类型的对象，再将其返回
	 *
	 * @param classType
	 *            classType
	 * @param methodName
	 *            methodName
	 * @param als
	 *            ArgueLexes
	 * @return the object
	 */
	public static Object showInvoke(Class<?> classType, String methodName,
			ArgueLex... als) {
		try {
			Object instance = classType.newInstance();
			Object[] objs = new Object[als.length];
			Class<?>[] classes = new Class[als.length];
			for (int i = 0; i < als.length; i++) {
				objs[i] = als[i].getObject();
				classes[i] = als[i].getCls();
			}
			Method addMethod = classType.getMethod(methodName, classes);
			Object result = addMethod.invoke(instance, objs);
			return result;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 将父类所有的属性COPY到子类中。 类定义中child一定要extends father；
	 * <p>
	 * 而且child和father一定为严格javabean写法，属性为deleteDate，方法为getDeleteDate
	 *
	 * @param father
	 *            父类对象
	 * @param child
	 *            子类对象
	 */
	public static void fatherToChild(Object father, Object child) {
		if (!(child.getClass().getSuperclass() == father.getClass())) {
			System.err.println("child不是father的子类");
		}
		Field ff[] = father.getClass().getDeclaredFields();
		ArgueLex al = new ArgueLex();
		for (int i = 0; i < ff.length; i++) {
			Field f = ff[i];// 取出每一个属性，如deleteDate
			setType(f.getType());
			try {
				if (f.getName().equals("serialVersionUID"))
					continue;
				Object obj = showInvoke(father.getClass(),
						getGetName(f.getName(), f.getType()));
				System.out.println(obj + ":::"
						+ getGetName(f.getName(), father.getClass()));
				al.setObject(obj);
				al.setCls(obj.getClass());
				showInvoke(child.getClass(), getSetName(f.getName()), al);

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the type
	 */
	public static Class<?> getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public static void setType(Class<?> type) {
		InvokeUtil.type = type;
	}
}
