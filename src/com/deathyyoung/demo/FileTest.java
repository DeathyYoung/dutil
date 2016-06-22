package com.deathyyoung.demo;

import com.deathyyoung.common.util.FileUtil;

public class FileTest {

	public static void main(String[] args) {

		String path = "F:\\account.txt";

		FileUtil fu = new FileUtil();
		fu.openFile(path);
		String str = null;
		while ((str = fu.nextLine()) != null) {
			System.out.println(str);
		}
		fu.closeFile();
	}

}
