package com.deathyyoung.demo;

import com.deathyyoung.jdbc.proxool.DecUtil;

/**
 * <p> TODO
 * 
 * @author <a href="#" target="_blank">Deathy Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class ProxoolEncryptAndDecrypt {

	/**
	 * <p> TODO
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String username = "qiushangers";
		String password = "QSers2015";
		DecUtil du = new DecUtil();
		System.out.println(du.encrypt(username));
		System.out.println(du.encrypt(password));
	}

}
