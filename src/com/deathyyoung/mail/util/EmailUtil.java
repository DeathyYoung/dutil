package com.deathyyoung.mail.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import com.deathyyoung.mail.bean.MailInfo;
import com.deathyyoung.mail.util.SimpleMailSender;

/**
 * <p>
 * Need javax.mail.jar.
 * 
 * @author <a href="#" target="_blank">Deathy Young</a>
 *         (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 * @since Mar 2, 2015
 */
public class EmailUtil {

	/**
	 * @Fields email
	 */
	private String email;
	/**
	 * @Fields password
	 */
	private String password;
	/**
	 * @Fields smtp
	 */
	private String smtp;
	/**
	 * @Fields imap
	 */
	@SuppressWarnings("unused")
	private String imap;
	/**
	 * @Fields pop
	 */
	@SuppressWarnings("unused")
	private String pop;

	private static Map<String, String> smtpMap;
	private static Map<String, String> popMap;
	private static Map<String, String> imapMap;

	static {
		smtpMap = new LinkedHashMap<String, String>();
		popMap = new LinkedHashMap<String, String>();
		imapMap = new LinkedHashMap<String, String>();
		// ////////////////////////////////
		smtpMap.put("zju.edu.cn", "smtp.zju.edu.cn");
		popMap.put("zju.edu.cn", "pop.zju.edu.cn");
		imapMap.put("zju.edu.cn", "imap.zju.edu.cn");
		// ////////////////////////////////
		smtpMap.put("qq.com", "smtp.qq.com");
		popMap.put("qq.com", "pop.qq.com");
		imapMap.put("qq.com", "imap.qq.com");
	}

	public EmailUtil(String email, String password) {
		this.email = email;
		this.password = password;
		String emailServer = email.substring(email.lastIndexOf("@") + 1);
		if (smtpMap.get(emailServer) == null) {
			this.smtp = "smtp." + emailServer;
			this.pop = "pop." + emailServer;
			this.imap = "imap." + emailServer;
		} else {
			this.smtp = smtpMap.get(emailServer);
			this.pop = popMap.get(emailServer);
			this.imap = imapMap.get(emailServer);
		}
	}

	public void sentEmailwithStop() {
		sentEmail("deathyyoung@qq.com", "运行停止");
	}

	public void sentEmail(String toAddress, String content) {
		// 这个类主要是设置邮件
		MailInfo mailInfo = new MailInfo();
		mailInfo.setMailServerHost(smtp);
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(email);
		mailInfo.setPassword(password);// 您的邮箱密码
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(content);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);// 发送html格式
	}

	public void sentEmail(String toAddress, String subject, String content) {
		// 这个类主要是设置邮件
		MailInfo mailInfo = new MailInfo();
		mailInfo.setMailServerHost(smtp);
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(email);
		mailInfo.setPassword(password);// 您的邮箱密码
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);// 发送html格式
	}

	public void sentHTML(String toAddress, String subject, String content) {
		// 这个类主要是设置邮件
		MailInfo mailInfo = new MailInfo();
		mailInfo.setMailServerHost(smtp);
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(email);
		mailInfo.setPassword(password);// 您的邮箱密码
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
	}

	public void sentHTML(String toAddress, String subject, String content,
			File... files) {
		// 这个类主要是设置邮件
		MailInfo mailInfo = new MailInfo();
		mailInfo.setMailServerHost(smtp);
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(email);
		mailInfo.setPassword(password);// 您的邮箱密码
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		MailSender.sendHtmlMail(mailInfo, files);// 发送html格式
	}

}
