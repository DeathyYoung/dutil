/** 
 * Title: SecurityUtil.java
 * <P>Copyright: Copyright (c) 2013 
 * @date 2013-6-22 
 * @version 1.0 
 */
package com.deathyyoung.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * <P>
 * 在DES中，使用了一个 56 位的密钥以及附加的8位奇偶校验位，产生最大64 位的分组大小。
 * <P>
 * 加密过程中，将加密的文本块分成两半。
 * <P>
 * 使用子密钥对其中一半应用循环功能，然后将输出与另一半进行“异或”运算；接着交换这两半。
 * <P>
 * 循环往复。SecurityUtil 使用16个循环，但最后一个循环不交换。
 */
public class SecurityUtil {
	// KeyGenerator提供对称密钥生成器的功能，支持各种算法
	private static KeyGenerator keygen;
	// KeyPairGenerator 类用于生成公钥和私钥对
	private static KeyPairGenerator keyPairGen;
	/* RSA公钥 */
	private static RSAPublicKey publicKey;
	/* RSA私钥 */
	private static RSAPrivateKey privateKey;
	// SecretKey负责保存对称密钥
	private static SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private static Cipher c;

	/**
	 * Description: Constructor
	 */
	public SecurityUtil() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 默认实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("DES");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象，指定其支持DES算法
			c = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Title: createEncryptor
	 * <P>
	 * Description: 对字符串str加密
	 * 
	 * @param str
	 *            目标字符串
	 * @return 加密后的字节数组
	 */
	public byte[] createEncryptor(String str) {
		try {
			if (c.getAlgorithm().equals("RSA")) {
				// 根据公钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
				c.init(Cipher.ENCRYPT_MODE, publicKey);
			} else {
				// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
				c.init(Cipher.ENCRYPT_MODE, deskey);
			}
			byte[] src = str.getBytes();
			// 加密，结果保存进cipherByte
			return c.doFinal(src);
		} catch (java.security.InvalidKeyException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Title: createDecryptor
	 * <P>
	 * Description: 对字节数组buff解密
	 * 
	 * @param buff
	 *            加密字节数组buff
	 * @return 解密后的字符串
	 */
	public String createDecryptor(byte[] buff) {
		try {
			if (c.getAlgorithm().equals("RSA")) {
				// 根据公钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
				c.init(Cipher.DECRYPT_MODE, privateKey);
			} else {
				// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
				c.init(Cipher.DECRYPT_MODE, deskey);
			}
			// 得到明文，存入cipherByte字符数组
			return new String(c.doFinal(buff));
		} catch (java.security.InvalidKeyException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Title: selectDES
	 * <P>
	 * Description: 选择使用DES加密算法
	 */
	public void selectDES() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("DES");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象，指定其支持DES算法
			c = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Title: select3DES
	 * <P>
	 * Description: 选择使用3DES加密算法
	 */
	public void select3DES() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("DESede");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象，指定其支持DES算法
			c = Cipher.getInstance("DESede");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Title: selectAES
	 * <P>
	 * Description: 选择使用AES加密算法
	 */
	public void selectAES() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("AES");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象，指定其支持DES算法
			c = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Title: selectRSA
	 * <P>
	 * Description: 选择使用RSA加密算法
	 */
	public void selectRSA() {
		try {
			// 基于RSA算法生成对象
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器,密钥大小为1024位
			keyPairGen.initialize(1024);
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 得到私钥
			privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥
			publicKey = (RSAPublicKey) keyPair.getPublic();
			// Cipher负责完成加密或解密工作，基于RSA
			c = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: md5_16
	 * <P>
	 * Description: 16位MD5的加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 密文
	 */
	public static String md5_16(String plainText) {
		return md5_32(plainText).substring(8, 24);
	}

	/**
	 * Title: md5_32
	 * <P>
	 * Description: 32位MD5的加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 密文
	 */
	public static String md5_32(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte resultBytes[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < resultBytes.length; offset++) {
				i = resultBytes[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Title: sha
	 * <P>
	 * Description: 32位SHA的加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 密文
	 */
	public static String sha(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(plainText.getBytes());
			byte resultBytes[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < resultBytes.length; offset++) {
				i = resultBytes[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击，请通过将敏感字符转换为其对 应的字符实体来清理HTML。
	 * <p>
	 * 这些是HTML 敏感字符：< > " ' % ; ) ( & +
	 * <p>
	 * 以下示例通过将敏感字符转换为其对应的字符实体，来过滤指定字符串
	 * <p>
	 * Example to filter sensitive data to prevent cross-site scripting
	 * */
	public static String xssFilter(String value) {

		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case '%':
				result.append("&#37;");
				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return new String(result);
	}

	/**
	 * <p>
	 * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击，请通过将敏感字符转换为其对应的字符实体来清理
	 * <p>
	 * 这些是SQL 敏感字符：< > " ' ; ) ( & +
	 * <p>
	 * 以下示例通过将敏感字符转换为其对应的字符实体，来过滤指定字符串
	 * <p>
	 * Example to filter sensitive data to prevent cross-site scripting
	 * */
	public static String sqlValueFilter(String value) {

		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return new String(result);
	}

	/**
	 * <p>
	 * 清除转义字符
	 *
	 * @param value
	 *            字符串
	 * @return 清除后的转义字符
	 */
	public static String valueUnfilter(String value) {
		if (value == null) {
			return null;
		}
		value = value.replaceAll("&gt;", ">");
		value = value.replaceAll("&lt;", "<");
		value = value.replaceAll("&quot;", "\"");
		value = value.replaceAll("&#37;", "%");
		value = value.replaceAll("&#39;", "\'");
		value = value.replaceAll("&#40;", "(");
		value = value.replaceAll("&#41;", ")");
		value = value.replaceAll("&#43;", "+");
		value = value.replaceAll("&#59;", ";");

		value = value.replaceAll("&amp;", "&");
		return value;
	}
}
