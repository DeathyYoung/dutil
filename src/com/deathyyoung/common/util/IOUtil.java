package com.deathyyoung.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * IO工具类
 * 
 * @author <a href="https://github.com/DeathyYoung" target="_blank">Deathy
 *         Young</a>
 *         (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class IOUtil {

	/**
	 * <p>
	 * byte数组转换为InputStream
	 *
	 * @param bytes
	 *            byte数组
	 * @return InputStream
	 */
	public static final InputStream byteToInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * <p>
	 * InputStream转换为byte数组
	 *
	 * @param inStream
	 *            InputStream对象
	 * @return byte数组
	 * @throws IOException
	 */
	public static final byte[] inputStreamToByte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}
