/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */
package org.logicalcobwebs.proxool.util;

/**
 * 
 * @author zhappy
 */
public interface Decryptool {

	/**
	 * 解密字符串
	 * 
	 * @param content
	 *            密文
	 * @return 明文
	 */
	public String decrypt(String content);

}