/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */
package com.deathyyoung.jdbc.proxool;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.logicalcobwebs.proxool.util.Decryptool;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author zhappy
 */
public class DecUtil implements Decryptool {

	/**
	 * <p>
	 * 解密
	 *
	 * @param 待解密的字符串
	 * @return 解密后的字符串
	 */
	@Override
	public String decrypt(String string) {
		BASE64Decoder decode = new BASE64Decoder();
		String s = null;
		try {
			byte[] b = decode.decodeBuffer(string);
			s = new String(b);
		} catch (IOException ex) {
			Logger.getLogger(DecUtil.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return s;
	}

	/**
	 * <p>
	 * 加密
	 *
	 * @param 待加密的字符串
	 * @return 加密后的字符串
	 */
	public String encrypt(String string) {
		BASE64Encoder encode = new BASE64Encoder();
		return encode.encode(string.getBytes());
	}

}