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
import java.util.Map.Entry;

/**
 * @ClassName: HttpUtil
 * @author Deathy Young (deathyyoung_gmail_com)
 * @date Feb 5, 2015 9:12:45 PM
 *
 */
public class HttpUtil {

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

}