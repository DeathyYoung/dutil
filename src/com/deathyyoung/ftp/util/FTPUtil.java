package com.deathyyoung.ftp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>
 * need commons-net-3.3.jar
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class FTPUtil {
	private FTPClient client = null;
	private String remoteDir = "/";

	public boolean login(String host, String user, String pwd) {
		return login(host, user, pwd, "/");
	}

	/**
	 * 登录
	 */
	public boolean login(String host, String user, String pwd, String remoteDir) {
		client = new FTPClient();
		try {
			client.connect(host);
			client.setControlEncoding("UTF-8");
			int reply = client.getReplyCode();
			client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			client.setConnectTimeout(0);
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				System.err.println("FTP server refused connection.");
			}
			this.remoteDir = remoteDir;
			return client.login(user, pwd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/***
	 * 得到文件名
	 */
	public List<String> getNames(boolean deep) {
		List<String> names = new ArrayList<String>();
		if (client != null) {
			names = checkDirectory(names, client, deep);
		}
		return names;
	}

	public List<FTPFile> getFTPFiles() throws IOException {
		List<FTPFile> list = new ArrayList<FTPFile>();
		if (client != null) {
			FTPFile[] files;
			files = client.listFiles(this.remoteDir);
			for (int i = 0; i < files.length; i++) {
				FTPFile file = files[i];
				String name = file.getName();
				if (name.equals(".") || name.equals("..")) {
					continue;
				} else if (file.isFile()) {
					list.add(file);
				}
			}
		}
		return list;
	}

	private List<String> checkDirectory(List<String> names, FTPClient client,
			boolean deep) {
		try {
			System.out.println("---" + client.printWorkingDirectory());
			FTPFile[] files = client.listFiles(client.printWorkingDirectory());
			for (int i = 0; i < files.length; i++) {
				FTPFile file = files[i];
				String name = file.getName();
				if (name.equals(".") || name.equals("..")) {
					continue;
				}
				if (deep && file.isDirectory()) {
					System.out.println(client.printWorkingDirectory());
					if (client.changeWorkingDirectory(client
							.printWorkingDirectory() + "/" + name)) {
						names = checkDirectory(names, client, deep);
					}
					client.changeToParentDirectory();
				} else if (file.isFile()) {
					String d = client.printWorkingDirectory();
					d = (d.equals("/") ? "" : d);
					name = d + "/" + name;
					names.add(name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}

	/***
	 * 下载数据到指定的目录中
	 * 
	 * @param filename
	 *            ftp端文件名称
	 * @param local
	 *            客户端保存路劲
	 * @return 是否成功
	 */
	public boolean download(String filename, String local) {
		boolean f = false;
		try {
			File file = new File(local
					+ (filename.startsWith("/") ? filename : "/" + filename));
			File file2 = file.getParentFile();
			if (!file2.exists()) {
				file2.mkdirs();
			}
			file2 = null;
			System.out.println(file.getParent());
			OutputStream out = new FileOutputStream(file);
			f = client.retrieveFile(this.remoteDir + "/" + filename, out);
			out.close();
			System.out.println(client.getReplyCode());
			client.logout();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return f;
	}
}
