package com.deathyyoung.demo;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Exception {
		String root = "J:\\anime\\Seikon no Qwaser I\\";
		String[] names = new File(root).list();
		for(String n : names){
			if(n.contains("rmvb")){
				String nn = n.substring(0, n.lastIndexOf("."));
				System.out.println(nn);
				new File(root+n).renameTo(new File(root+nn));
			}
		}
	}

}
