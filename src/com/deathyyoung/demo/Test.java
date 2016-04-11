package com.deathyyoung.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.deathyyoung.common.util.FileUtil;

public class Test {

	public static void main(String[] args) {
		Date date = new Date();
		String datePattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		String time = sdf.format(date);
		System.out.println(time);

		System.out.println(FileUtil.getCharset("F:\\茫然\\SqlUtils.java"));
		System.out.println(FileUtil.getString("F:\\茫然\\SqlUtils.java"));
	}

}
