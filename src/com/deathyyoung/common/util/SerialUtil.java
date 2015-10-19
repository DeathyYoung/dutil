package com.deathyyoung.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>
 * TODO
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Apr 18, 2015
 */
public class SerialUtil {

	/**
	 * <p>
	 * 将Java对象保存到文件
	 *
	 * @param obj
	 *            Java对象
	 * @param filePath
	 *            保存Java对象的文件路径
	 */
	public static void writeObjectToFile(Object obj, String filePath) {
		writeObjectToFile(obj, new File(filePath));
	}

	/**
	 * <p>
	 * 将Java对象保存到文件
	 *
	 * @param obj
	 *            Java对象
	 * @param file
	 *            保存Java对象的文件
	 */
	public static void writeObjectToFile(Object obj, File file) {
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(obj);
			objOut.flush();
			objOut.close();
			System.out.println("write object success!");
		} catch (IOException e) {
			System.out.println("write object failed");
			e.printStackTrace();
		}
		System.gc();
	}

	/**
	 * <p>
	 * 从文件中读取Java对象
	 *
	 * @param filePath
	 *            保存Java对象的文件路径
	 * @return Java对象
	 */
	public static Object readObjectFromFile(String filePath) {
		return readObjectFromFile(new File(filePath));
	}

	/**
	 * <p>
	 * 从文件中读取Java对象
	 *
	 * @param file
	 *            保存Java对象的文件
	 * @return Java对象
	 */
	public static Object readObjectFromFile(File file) {
		if (!file.exists()) {
			return null;
		}
		Object temp = null;
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
			objIn.close();
			System.out.println("read object success!");
		} catch (IOException e) {
			System.out.println("read object failed");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.gc();
		return temp;
	}

}
