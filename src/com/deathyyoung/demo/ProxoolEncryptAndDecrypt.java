package com.deathyyoung.demo;

import com.deathyyoung.jdbc.proxool.DecUtil;

/**
 * <p> TODO
 * 
 * @author <a href="#" target="_blank">Deathy Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class ProxoolEncryptAndDecrypt {

	/**
	 * <p> TODO
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String username = "user";
		String password = "password";
		DecUtil du = new DecUtil();
		System.out.println(du.encrypt(username));
		System.out.println(du.encrypt(password));
	}

}
