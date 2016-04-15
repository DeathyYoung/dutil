package com.deathyyoung.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.deathyyoung.common.util.FileUtil;

public class Test {

	private static String utfbom = "F:\\茫然\\UTF8_BOM.txt";
	private static String utf = "F:\\茫然\\UTF8.txt";
	private static String gbk = "F:\\茫然\\GBK.txt";

	public static void main(String[] args) throws Exception {
		Date date = new Date();
		String datePattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		String time = sdf.format(date);
		System.out.println(time);

		System.out.println(FileUtil.getCharset(utfbom));// true
		System.out.println(FileUtil.getString(utfbom));

		System.out.println(FileUtil.getCharset(utf));// true
		System.out.println(FileUtil.getString(utf));

		System.out.println(FileUtil.getCharset(gbk));// false
		System.out.println(FileUtil.getString(gbk));
		//
		System.out.println("///////////////////////////////////////////");
	}

	public static String getTxtEncode(String path) {
		String encode = "";
		File file = new File(path);
		try {
			FileInputStream in = new FileInputStream(file);
			encode = getTxtEncode(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encode;
	}

	public static String getTxtEncode(FileInputStream in) {
		byte[] head = new byte[3];
		String code = "";
		try {
			in.read(head);
			code = "";
			if (head[0] == -1 && head[1] == -2)
				code = "UTF-16";
			if (head[0] == -2 && head[1] == -1)
				code = "Unicode";
			// 带BOM
			if (head[0] == -17 && head[1] == -69 && head[2] == -65)
				code = "UTF-8";
			if ("Unicode".equals(code)) {
				code = "UTF-16";
			}
			if (code.length() == 0) {
				int index = 0;
				for (; index < head.length; index++) {
					if (head[index] < 0) {
						break;
					}
				}
				byte[] charBytes = new byte[] { 0, 0, 0 };

				switch (index) {
				case 0:
					charBytes[0] = head[0];
					charBytes[1] = head[1];
					charBytes[2] = head[2];
					break;
				case 1:
					charBytes[0] = head[1];
					charBytes[1] = head[2];
					charBytes[2] = (byte) in.read();
					break;
				case 2:
					charBytes[0] = head[2];
					charBytes[1] = (byte) in.read();
					charBytes[2] = (byte) in.read();
					break;
				default:
					while ((charBytes[0] = (byte) in.read()) >= 0)
						;
					charBytes[1] = (byte) in.read();
					charBytes[2] = (byte) in.read();
					break;
				}
				if ((charBytes[0] & 0xF0) == 0xE0
						&& (charBytes[1] & 0xC0) == 0x80
						&& (charBytes[2] & 0xC0) == 0x80) {
					code = "UTF-8";
				} else {
					code = "GBK";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

}
