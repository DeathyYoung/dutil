package com.deathy.office.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class WordUtil {

	public static String getText(String wordPath) {
		return getText(new File(wordPath));
	}

	public static String getText(File wordFile) {
		try {
			InputStream is = new FileInputStream(wordFile);
			WordExtractor extractor = new WordExtractor(is);
			return extractor.getText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
