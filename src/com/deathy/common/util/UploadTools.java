package com.deathy.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * <p>
 * 上传工具类，需要tomcat环境
 * 
 */
public class UploadTools implements Serializable {

	private static final long serialVersionUID = -2014006661393236746L;

	/** 获取上传Map */
	public Map<String, FileItem> getUploadMap(HttpServletRequest request) {
		Map<String, FileItem> fileMap = new HashMap<String, FileItem>();

		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(dfif);

		try {
			List<FileItem> fileList = sfu
					.parseRequest(new ServletRequestContext(request));
			for (int i = 0; i < fileList.size(); i++) {
				FileItem item = fileList.get(i);
				String name = item.getFieldName();
				fileMap.put(name, item);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		return fileMap;
	}

	public static void uploadFile(File file, String fileName, String root) {
		try {
			InputStream is = new FileInputStream(file);// 输入流
			// 上传文件路径到tomcat中
			File destfile = new File(root, fileName);
			OutputStream os = new FileOutputStream(destfile);// 输出流
			byte[] buffer = new byte[400];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			System.out.println("对不起文件不存在！");
			e.printStackTrace();
		}

	}

	public static void uploadFile(InputStream is, String fileName, String root) {
		try {
			// 上传文件路径到tomcat中
			File destfile = new File(root, fileName);
			if (destfile.exists()) {
				destfile.delete();
			}
			destfile.createNewFile();
			OutputStream os = new FileOutputStream(destfile);// 输出流
			byte[] buffer = new byte[400];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			System.out.println("对不起文件不存在！");
			e.printStackTrace();
		}
	}

}
