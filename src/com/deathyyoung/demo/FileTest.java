package com.deathyyoung.demo;

import com.deathyyoung.common.util.FileUtil;

public class FileTest {

	public static void main(String[] args) {

		String path = "F:\\account.txt";

		
		System.out.println(FileUtil.getLines(path)[0].length());
		
		for(String str : FileUtil.getLines(path)){
			System.out.println(str);
		}
	}

}
