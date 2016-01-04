package com.deathyyoung.demo;

import com.deathyyoung.mail.util.EmailUtil;

public class EmailDemo {
	public static void main(String[] args) {
		EmailUtil eu = new EmailUtil("mapleyeh@qq.com", "Deathy@357");
		eu.sentEmail("84610328@qq.com", "asdf");
	}
}
