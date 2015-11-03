package com.deathyyoung.demo;

import java.util.List;

import com.deathyyoung.ftp.util.FTPUtil;

public class FTPdemo {

	public static void main(String[] args) {
		FTPUtil ftpUtil = new FTPUtil();
		if (ftpUtil.login("127.0.0.1", "ftpuser", "ftpuser")) {
			List<String> list = ftpUtil.getNames(true);
			for (String string : list) {
				System.out.println(string);
			}
			if (ftpUtil.download("20100714.rar", "d:/ftp")) {
				System.out.println("下载成功！");
			}
		}
	}
}
