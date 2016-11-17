package com.deathyyoung.common.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

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
			InputStream in = HttpUtil.class.getResourceAsStream("mime.properties");
			pro.load(in);
		} catch (IOException e) {
			System.out.println("未找到mime的配置文件！！！");
		}
	}

	/**
	 * <p>
	 * 获取资源文件的媒体类型
	 *
	 * @param extension
	 *            文件后缀
	 * @return 媒体类型
	 */
	public static String getMIMEType(String extension) {
		extension = extension.toLowerCase();
		String mime = pro.getProperty(extension);
		return mime == null ? pro.getProperty("default") : mime;
	}

	/**
	 * <p>
	 * 获取HTTP的内容类型
	 *
	 * @param extension
	 *            文件后缀
	 * @return 内容类型
	 */
	public static String getHTTPContentType(String extension) {
		return getMIMEType(extension);
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @return String结果
	 */
	public static String get(String url) {
		return get(url, null, null, null, "UTF-8");
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String get(String url, String sendEncode) {
		return get(url, null, null, null, sendEncode);
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @return String结果
	 */
	public static String get(String url, String proxyIP, Integer proxyPort) {
		return get(url, null, proxyIP, proxyPort, "UTF-8");
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String get(String url, String proxyIP, Integer proxyPort, String sendEncode) {
		return get(url, null, proxyIP, proxyPort, sendEncode);
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @return String结果
	 */
	public static String get(String url, Map<String, String> values) {
		return get(url, values, null, null, "UTF-8");
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String get(String url, Map<String, String> values, String sendEncode) {
		return get(url, values, null, null, sendEncode);
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @return String结果
	 */
	public static String get(String url, Map<String, String> values, String proxyIP, Integer proxyPort) {
		return get(url, values, proxyIP, proxyPort, "UTF-8");
	}

	/**
	 * 
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String get(String url, Map<String, String> values, String proxyIP, Integer proxyPort,
			String sendEncode) {
		String result = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(url);
			if (values != null && values.size() > 0) {
				sb.append('?');
				Iterator<Entry<String, String>> iter = values.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					sb.append(entry.getKey());
					sb.append('=');
					sb.append(URLEncoder.encode(entry.getValue(), sendEncode));
					sb.append('&');
				}
				sb.setLength(sb.length() - 1);
			}
			URL getUrl = new URL(sb.toString());

			HttpURLConnection connection = null;

			if (proxyIP != null && proxyPort != null) {
				InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(proxyIP), proxyPort);
				Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress); // 代理
				connection = (HttpURLConnection) getUrl.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) getUrl.openConnection();
			}

			connection.connect();

			InputStream is = connection.getInputStream();

			if (connection.getContentEncoding() != null && connection.getContentEncoding().contains("gzip")) {// 判断是否gzip压缩
				GZIPInputStream gzis = new GZIPInputStream(is);// InputStream转换为GZIPInputStream
				byte[] bytes = IOUtil.inputStreamToByte(gzis);
				result = StringUtil.bytesToString(bytes);
			} else {
				byte[] bytes = IOUtil.inputStreamToByte(is);
				result = StringUtil.bytesToString(bytes);
			}

			connection.disconnect();
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
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @return String结果
	 */
	public static String post(String url) {
		return post(url, null, null, null, "UTF-8");
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String post(String url, String sendEncode) {
		return post(url, null, null, null, sendEncode);
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @return String结果
	 */
	public static String post(String url, String proxyIP, Integer proxyPort) {
		return post(url, null, proxyIP, proxyPort, "UTF-8");
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String post(String url, String proxyIP, Integer proxyPort, String sendEncode) {
		return post(url, null, proxyIP, proxyPort, sendEncode);
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @return String结果
	 */
	public static String post(String url, Map<String, String> values) {
		return post(url, values, null, null, "UTF-8");
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String post(String url, Map<String, String> values, String sendEncode) {
		return post(url, values, null, null, sendEncode);
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @return String结果
	 */
	public static String post(String url, Map<String, String> values, String proxyIP, Integer proxyPort) {
		return post(url, values, proxyIP, proxyPort, "UTF-8");
	}

	/**
	 * <p>
	 * GET方法访问URL，返回String结果
	 *
	 * @param url
	 *            访问地址
	 * @param values
	 *            传递参数
	 * @param proxyIP
	 *            代理IP
	 * @param proxyPort
	 *            代理端口
	 * @param sendEncode
	 *            发送数据时用的编码
	 * @return String结果
	 */
	public static String post(String url, Map<String, String> values, String proxyIP, Integer proxyPort,
			String sendEncode) {
		String result = "";
		try {
			URL postUrl = new URL(url);

			HttpURLConnection connection = null;

			if (proxyIP != null && proxyPort != null) {
				InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(proxyIP), proxyPort);
				Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress); // 代理
				connection = (HttpURLConnection) postUrl.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) postUrl.openConnection();
			}

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			StringBuffer sb = new StringBuffer();
			if (values != null && values.size() > 0) {
				sb.append('?');
				Iterator<Entry<String, String>> iter = values.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					sb.append(entry.getKey());
					sb.append('=');
					sb.append(URLEncoder.encode(entry.getValue(), sendEncode));
					sb.append('&');
				}
				sb.setLength(sb.length() - 1);
			}
			out.writeBytes(sb.toString());
			sb.setLength(0);
			out.flush();
			out.close();

			connection.connect();

			InputStream is = connection.getInputStream();

			if (connection.getContentEncoding() != null && connection.getContentEncoding().contains("gzip")) {// 判断是否gzip压缩
				GZIPInputStream gzis = new GZIPInputStream(is);// InputStream转换为GZIPInputStream
				byte[] bytes = IOUtil.inputStreamToByte(gzis);
				result = StringUtil.bytesToString(bytes);
			} else {
				byte[] bytes = IOUtil.inputStreamToByte(is);
				result = StringUtil.bytesToString(bytes);
			}

			connection.disconnect();
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
	 * @deprecated
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
