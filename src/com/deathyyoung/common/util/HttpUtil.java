package com.deathyyoung.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.deathyyoung.jdbc.util.JDBCUtil;

/**
 * @ClassName: HttpUtil
 * @author Deathy Young (deathyyoung_gmail_com)
 * @date Feb 5, 2015 9:12:45 PM
 *
 */
public class HttpUtil {

	private static Properties pro;

	static {
		pro = new Properties();
		try {
			pro.load(JDBCUtil.class.getResourceAsStream("/mime.properties"));
		} catch (IOException e) {
			System.out.println("未找到配置文件！！！");
		}
	}

	/**
	 * <p>
	 * get mime type
	 *
	 * @param extension
	 *            file extension
	 * @return mime type
	 */
	public static String getMIMEType(String extension) {
		extension = extension.toLowerCase();
		String mime = pro.getProperty(extension);
		return mime == null ? pro.getProperty("default") : mime;
	}

	/**
	 * <p>
	 * get http content-type
	 *
	 * @param extension
	 *            file extension
	 * @return http content-type
	 */
	public static String getHTTPContentType(String extension) {
		return getMIMEType(extension);
	}

	/**
	 * <p>
	 * get method
	 *
	 * @param url
	 */
	public static String get(String url) {
		return get(url, null);
	}

	/**
	 * <p>
	 * get method
	 *
	 * @param url
	 * @param map
	 */
	public static String get(String url, Map<String, String> map) {
		String result = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			if (map != null && map.size() > 0) {
				sb.append('?');
				Iterator<Entry<String, String>> iter = map.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					sb.append(entry.getKey());
					sb.append('=');
					sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
					sb.append('&');
				}
				sb.setLength(sb.length() - 1);
			}
			URL getUrl = new URL(sb.toString());
			sb.setLength(0);
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
				sb.append('\n');
			}
			reader.close();
			connection.disconnect();
			result = sb.toString();
			sb.setLength(0);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Description: post method
	 * @param url
	 * @return String
	 */
	public static String post(String url) {
		return post(url, null);
	}

	/**
	 * @Description: post method
	 * @param url
	 * @param map
	 * @return String
	 */
	public static String post(String url, Map<String, String> map) {
		String result = "";
		try {
			URL postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			StringBuffer sb = new StringBuffer();
			if (map != null && map.size() > 0) {
				sb.append('?');
				Iterator<Entry<String, String>> iter = map.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					sb.append(entry.getKey());
					sb.append('=');
					sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
					sb.append('&');
				}
				sb.setLength(sb.length() - 1);
			}
			out.writeBytes(sb.toString());
			sb.setLength(0);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			reader.close();
			connection.disconnect();
			result = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 传入http请求的UserAgent 根据它判断是手机还是电脑发送过来的请求
	 * 
	 * @param userAgent
	 *            http请求的UserAgent
	 * @return 如果是手机返回true，否则返回false
	 */
	public static boolean choose(String userAgent) {
		if (userAgent.indexOf("Noki") > -1 || // Nokia phones and emulators
				userAgent.indexOf("Eric") > -1 || // Ericsson WAP phones and
													// emulators
				userAgent.indexOf("WapI") > -1 || // Ericsson WapIDE 2.0
				userAgent.indexOf("MC21") > -1 || // Ericsson MC218
				userAgent.indexOf("AUR") > -1 || // Ericsson R320
				userAgent.indexOf("R380") > -1 || // Ericsson R380
				userAgent.indexOf("UP.B") > -1 || // UP.Browser
				userAgent.indexOf("WinW") > -1 || // WinWAP browser
				userAgent.indexOf("UPG1") > -1 || // UP.SDK 4.0
				userAgent.indexOf("upsi") > -1 || // another kind of UP.Browser
				userAgent.indexOf("QWAP") > -1 || // unknown QWAPPER browser
				userAgent.indexOf("Jigs") > -1 || // unknown JigSaw browser
				userAgent.indexOf("Java") > -1 || // unknown Java based browser
				userAgent.indexOf("Alca") > -1 || // unknown Alcatel-BE3 browser
													// (UP based)
				userAgent.indexOf("MITS") > -1 || // unknown Mitsubishi browser
				userAgent.indexOf("MOT-") > -1 || // unknown browser (UP based)
				userAgent.indexOf("My S") > -1 || // unknown Ericsson devkit
													// browser
				userAgent.indexOf("WAPJ") > -1 || // Virtual WAPJAG
													// www.wapjag.de
				userAgent.indexOf("fetc") > -1 || // fetchpage.cgi Perl script
													// from www.wapcab.de
				userAgent.indexOf("ALAV") > -1 || // yet another unknown UP
													// based browser
				userAgent.indexOf("Wapa") > -1 || // another unknown browser
													// (Web based "Wapalyzer")
				userAgent.indexOf("Oper") > -1) {
			return true;
		} else {
			return false;
		}
	}
}
