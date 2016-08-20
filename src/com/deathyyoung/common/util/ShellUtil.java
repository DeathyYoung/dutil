package com.deathyyoung.common.util;

import java.io.File;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:deathyyoung@qq.com" >deathyyoung@qq.com</a>)
 */
public class ShellUtil {

	public static String getSH(Class<?> clazz) {
		StringBuffer sb = new StringBuffer();
		sb.append("#!/bin/sh\n");
		sb.append("java ");
		sb.append(clazz.getCanonicalName());
		return sb.toString();
	}

	public static String getSH4Tomcat(Class<?> clazz, String webrootName) {
		String component = "./" + webrootName + "/WEB-INF/lib";
		String[] jarNames = new File(component).list();
		String libDirName = "../lib";

		StringBuffer sb = new StringBuffer();
		sb.append("#!/bin/sh\n");
		sb.append("java ");
		if (jarNames != null && jarNames.length > 0) {
			sb.append("-cp .");
			for (String name : jarNames) {
				sb.append(":");
				sb.append(libDirName);
				sb.append("/");
				sb.append(name);
			}
			sb.append(" ");
		}
		sb.append(clazz.getCanonicalName());
		String path = "./src/" + clazz.getCanonicalName() + ".sh";
		FileUtil.createNewFileForce(path);
		FileUtil.addToFile(sb.toString(), path);
		return sb.toString();
	}

	public static String getSH(Class<?> clazz, String libDirName, boolean isGIT) {
		String[] jarNames = new File(libDirName).list();

		if (isGIT) {
			libDirName = "../../" + libDirName;
		} else {
			libDirName = "../" + libDirName;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("#!/bin/sh\n");
		sb.append("java ");
		if (jarNames != null && jarNames.length > 0) {
			sb.append("-cp .");
			for (String name : jarNames) {
				sb.append(":");
				sb.append(libDirName);
				sb.append("/");
				sb.append(name);
			}
			sb.append(" ");
		}
		sb.append(clazz.getCanonicalName());
		String path = "./src/" + clazz.getCanonicalName() + ".sh";
		FileUtil.createNewFileForce(path);
		FileUtil.addToFile(sb.toString(), path);
		return sb.toString();
	}

}
