package com.deathyyoung.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.stream.ImageInputStream;

/**
 * @author <a href="#" target="_blank">Deathy Young</a> (<a
 *         href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Feb 24, 2015
 */
public class FileUtil {

	/** for Path Manipulation */
	public static final String pathReg = "[a-zA-Z0-9-_:/\\\\]+";

	public static enum CHARSET {
		UTF8, GBK, UTF16
	}

	/**
	 * <p>
	 * Wheter the <code>path</code> is valided.
	 * 
	 * @param path
	 *            the path
	 * @return <code>true</code> if and only if the path is valided;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean validPath(String path) {
		// return validPath(path);
		return !path.contains("..");
	}

	/**
	 * <p>
	 * Add the <code>content</code> to the target file.
	 * 
	 * @param content
	 * @param path
	 * @return <code>true</code> if and only if the content is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String content, String path) {
		if (!validPath(path))
			return false;
		File targetFile = new File(path);
		return addToFile(content, targetFile);
	}

	/**
	 * <p>
	 * Add the <code>content</code> to the target file.
	 * 
	 * @param content
	 * @param path
	 * @return <code>true</code> if and only if the content is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFileln(String content, String path) {
		if (!validPath(path))
			return false;
		File targetFile = new File(path);
		return addToFileln(content, targetFile);
	}

	/**
	 * <p>
	 * <p>
	 * Add a string, which is a substring of the <code>contents</code>, begins
	 * at the specified <code>beginIndex</code> and extends to the character at
	 * index <code>endIndex - 1</code>, to target file.
	 * 
	 * @param contents
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @param path
	 * @return <code>true</code> if and only if the specified substring is added
	 *         to the file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String[] contents, int beginIndex,
			int endIndex, String path) {
		if (!validPath(path))
			return false;
		File targetFile = new File(path);
		return addToFile(contents, beginIndex, endIndex, targetFile);
	}

	/**
	 * <p>
	 * Add the <code>contents</code> to the target file.
	 * 
	 * @param contents
	 * @param path
	 * @return <code>true</code> if and only if the contents is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String[] contents, String path) {
		if (!validPath(path))
			return false;
		File targetFile = new File(path);
		return addToFile(contents, targetFile);
	}

	/**
	 * <p>
	 * Add a string, which is a substring of the <code>contents</code>, begins
	 * at the specified <code>beginIndex</code> and extends to the character at
	 * index <code>endIndex - 1</code>, to target file.
	 * 
	 * @param contents
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @param path
	 * @return <code>true</code> if and only if the specified substring is added
	 *         to the file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFileln(String[] contents, int beginIndex,
			int endIndex, String path) {
		if (!validPath(path))
			return false;
		File targetFile = new File(path);
		return addToFileln(contents, beginIndex, endIndex, targetFile);
	}

	/**
	 * <p>
	 * Add a string, which is a substring of the <code>contents</code>, begins
	 * at the specified <code>beginIndex</code> and extends to the character at
	 * index <code>endIndex - 1</code>, to target file.
	 * 
	 * @param contents
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @param file
	 * @return <code>true</code> if and only if the specified substring is added
	 *         to the file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String[] contents, int beginIndex,
			int endIndex, File file) {
		FileWriter fw = null;
		try {
			beginIndex = beginIndex < 0 ? 0 : beginIndex;
			endIndex = endIndex > contents.length ? contents.length : endIndex;
			fw = new FileWriter(file, true);
			for (int i = beginIndex; i < endIndex; i++) {
				fw.write(contents[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			safeClose(fw);
		}
		return true;
	}

	/**
	 * <p>
	 * Add the <code>contents</code> to the target file.
	 * 
	 * @param contents
	 * @param file
	 * @return <code>true</code> if and only if the contents is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String[] contents, File file) {
		return addToFile(contents, 0, contents.length, file);
	}

	/**
	 * <p>
	 * Add the <code>content</code> to the target file.
	 * 
	 * @param content
	 * @param file
	 * @return <code>true</code> if and only if the content is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFile(String content, File file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
			fw.write(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			safeClose(fw);
		}
		return true;
	}

	/**
	 * <p>
	 * Add the <code>content</code> to the target file.
	 * 
	 * @param content
	 * @param file
	 * @return <code>true</code> if and only if the content is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFileln(String content, File file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
			fw.write(content);
			fw.write("\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			safeClose(fw);
		}
		return true;
	}

	/**
	 * <p>
	 * Add the <code>contents</code> to the target file.
	 * 
	 * @param contents
	 * @param file
	 * @return <code>true</code> if and only if the contents is added to the
	 *         file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFileln(String[] contents, File file) {
		return addToFileln(contents, 0, contents.length, file);
	}

	/**
	 * <p>
	 * Add a string, which is a substring of the <code>contents</code>, begins
	 * at the specified <code>beginIndex</code> and extends to the character at
	 * index <code>endIndex - 1</code>, to target file.
	 * 
	 * @param contents
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @param file
	 * @return <code>true</code> if and only if the specified substring is added
	 *         to the file;<br>
	 *         <code>false</code> otherwise
	 */
	public static boolean addToFileln(String[] contents, int beginIndex,
			int endIndex, File file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
			for (int i = beginIndex; i < endIndex; i++) {
				fw.write(contents[i]);
				fw.write("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			safeClose(fw);
		}
		return true;
	}

	/**
	 * <p>
	 * Get a new string from the file.
	 * 
	 * @param file
	 * @return the content of the file
	 */
	public static String getString(File file) {
		return getString(file, 0, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Get a new string from the file.
	 * 
	 * @param path
	 * @return the content of the file
	 */
	public static String getString(String path) {
		return getString(path, 0, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Get a new string which is a substring begins with the character at the
	 * specified <code>beginIndex</code> and extends to the end of the content
	 * of the file.
	 * 
	 * @param path
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @return the specified substring
	 */
	public static String getString(String path, int beginIndex) {
		return getString(path, beginIndex, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Get a new string which is a substring begins with the character at the
	 * specified <code>beginIndex</code> and extends to the character at index
	 * <code>endIndex - 1</code> of the content of the file.
	 * 
	 * @param path
	 *            the text path
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @return the specified substring
	 */
	public static String getString(String path, int beginIndex, int endIndex) {
		if (!validPath(path))
			return null;
		File textFile = new File(path);
		return getString(textFile, beginIndex, endIndex);
	}

	/**
	 * <p>
	 * Get a new string which is a substring begins with the character at the
	 * specified <code>beginIndex</code> and extends to the character at index
	 * <code>endIndex - 1</code> of the content of the file.
	 * 
	 * @param textFile
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @return the specified substring
	 */
	public static String getString(File textFile, int beginIndex, int endIndex) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			String charset = getCharset(textFile);
			reader = new InputStreamReader(new FileInputStream(textFile),
					charset);
			br = new BufferedReader(reader);
			br.skip(beginIndex);
			int charSize = 1024;
			char[] cs = new char[charSize];
			int length = -1;
			int i = 0;
			while ((length = br.read(cs)) > 0) {
				for (int j = 0; j < length && i < endIndex - beginIndex; i++, j++) {
					sb.append(cs[j]);
				}
			}
			return new String(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			safeClose(br);
			safeClose(reader);
		}
		return null;
	}

	/**
	 * <p>
	 * Create a new file. If the file exists, it will be deleted first.
	 * 
	 * 
	 * @param path
	 * @return <code>true</code> if and only if the file is created; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean createNewFileForce(String path) {
		return createNewFileForce(path, 0);
	}

	/**
	 * <p>
	 * Create a new file. If the file exists, it will be deleted first.
	 * 
	 * @param file
	 * @return <code>true</code> if and only if the file is created; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean createNewFileForce(File file) {
		return createNewFileForce(file, 0);
	}

	/**
	 * <p>
	 * Create a new <code>fileLength</code>-byte file. If the file exists, it
	 * will be deleted first.
	 * 
	 * @param path
	 * @param fileLength
	 * @return <code>true</code> if and only if the file is created; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean createNewFileForce(String path, int fileLength) {
		if (!validPath(path))
			return false;
		return createNewFileForce(new File(path), fileLength);
	}

	/**
	 * <p>
	 * Create a new <code>fileLength</code>-KB file. If the file exists, it will
	 * be deleted first.
	 * 
	 * @param file
	 * @param fileLength
	 * @return <code>true</code> if and only if the file is created; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean createNewFileForce(File file, int fileLength) {
		if (!mkdir(file.getAbsolutePath().substring(0,
				file.getAbsolutePath().lastIndexOf(File.separatorChar)))) {
			return false;
		}
		if (file.exists()) {
			file.delete();
		}
		FileWriter fw = null;
		FileOutputStream fos = null;
		try {
			fw = new FileWriter(file, true);
			fos = new FileOutputStream(file);
			for (int i = 0; i < fileLength / 4096; i++) {
				byte[] buffer = new byte[4096 * 1024];
				fos.write(buffer);
			}
			fos.write(new byte[fileLength % 4096 * 1024]);
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(fos);
			safeClose(fw);
		}
		return false;
	}

	/**
	 * <p>
	 * Delete the directory.
	 * 
	 * @param dirPath
	 * @return <code>true</code> if and only if the directory is deleted; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean deleteDirectory(String dirPath) {
		if (!validPath(dirPath))
			return false;
		boolean flag = true;
		try {
			flag = deleteAllFile(dirPath);
			if (flag) {
				String filePath = dirPath;
				filePath = filePath.toString();
				File myFilePath = new File(filePath);
				flag = myFilePath.delete();
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * <p>
	 * Delete all files under the <code>path</code>.
	 * 
	 * @param path
	 * @return <code>true</code> if and only if all files are deleted; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean deleteAllFile(String path) {
		if (!validPath(path))
			return false;
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		boolean flag = true;
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			String subPath;
			switch (path.charAt(path.length() - 1)) {
			case '/':
			case '\\':
				subPath = path + tempList[i];
				break;
			default:
				subPath = path + File.separator + tempList[i];
			}
			temp = new File(subPath);
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				flag = deleteDirectory(subPath);
			}
		}
		return flag;
	}

	/**
	 * <p>
	 * Check the zise of the <code>file</code>.
	 * 
	 * @param file
	 * @return <code>true</code> if and only if the size is not bigger than 2
	 *         MB; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean checkMaxSize(File file) {
		return checkMaxSize(file, 2048);
	}

	/**
	 * <p>
	 * Check the zise of the <code>file</code>.
	 * 
	 * @param file
	 * @param maxSize
	 * @return <code>true</code> if and only if the size is not bigger than
	 *         <code>maxSize</code> KB; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean checkMaxSize(File file, int maxSize) {
		boolean re = false;
		if (file == null) {
			return re;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			long fileSize = fis.available();
			if (fileSize < maxSize << 10) {
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			re = false;
		} finally {
			safeClose(fis);
		}
		return re;
	}

	/**
	 * <p>
	 * Tests whether the picture <code>type</code> is jpeg, png or gif.
	 * 
	 * @param type
	 * @return <code>true</code> if and only if the <code>type</code> is jpeg,
	 *         png or gif; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean checkContentType(String type) {
		Set<String> types = new HashSet<String>();
		types.add("image/jpeg");
		types.add("image/png");
		types.add("image/gif");
		types.add("image/bmp");
		return checkContentType(type, types);
	}

	/**
	 * <p>
	 * Tests whether the <code>type</code> is in the <code>typeSet</code>.
	 * 
	 * @param type
	 * @param typeSet
	 * @return <code>true</code> if and only if the <code>type</code> is in the
	 *         <code>typeSet</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean checkContentType(String type, Set<String> typeSet) {
		if (typeSet == null || typeSet.size() == 0) {
			return checkContentType(type);
		}
		return typeSet.contains(type);
	}

	/**
	 * <p>
	 * Make a directory.
	 * 
	 * @param path
	 * @return <code>true</code> if and only if the directory is made; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean mkdir(String path) {
		if (!validPath(path))
			return false;
		if (path == null || path.trim().length() == 0 || path.endsWith(":")) {
			return true;
		}
		if (path.endsWith("/") || path.endsWith("\\")) {
			path = path.substring(0, path.length() - 1);
		}
		if (File.separator.equals("\\")) {
			path = path.replace('/', File.separatorChar);
		} else {
			path = path.replace('\\', File.separatorChar);
		}
		int last = path.lastIndexOf(File.separatorChar);
		if (last <= 0) {
			return true;
		}
		boolean flag = mkdir(path.substring(0, last));
		File file = new File(path);
		if (path.length() != 0 && flag && !file.isDirectory()) {
			flag = file.mkdir();
		}
		return flag;
	}

	/**
	 * <p>
	 * Make a directory.
	 * 
	 * @param dir
	 * @return <code>true</code> if and only if the directory is made; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean mkdir(File dir) {
		return mkdir(dir.getAbsoluteFile());
	}

	/**
	 * <p>
	 * Tests whether the file or directory denoted by this <code>path</code>
	 * exists.
	 * 
	 * @param path
	 * @return <code>true</code> if and only if the file or directory denoted by
	 *         this <code>path</code> exists; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean exists(String path) {
		if (!validPath(path))
			return false;
		File file = new File(path);
		return file.exists();
	}

	/**
	 * <p>
	 * Copy a directory.
	 * 
	 * @param srcPath
	 * @param destPath
	 * @return <code>true</code> if and only if the directory <code>src</code>
	 *         is copied as the directory <code>src</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean copyDirectiory(String srcPath, String destPath) {
		if (!validPath(srcPath))
			return false;
		if (!validPath(destPath))
			return false;
		File srcDir = new File(srcPath);
		File destDir = new File(destPath);
		mkdir(srcPath);
		mkdir(destPath);
		File[] file = srcDir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				copyFile(file[i], new File(destDir.getAbsoluteFile()
						+ File.separator + file[i].getName()));
			} else if (file[i].isDirectory()) {
				String srcSubDir = srcDir.getAbsoluteFile() + File.separator
						+ file[i].getName();
				String destSubDir = destDir.getAbsoluteFile() + File.separator
						+ file[i].getName();
				if (!copyDirectiory(srcSubDir, destSubDir))
					return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Copy a file or a directory.
	 * 
	 * @param srcPath
	 * @param destPath
	 * @return <code>true</code> if and only if the file (or directory)
	 *         <code>srcPath</code> is copied as the file (or directory)
	 *         <code>srcPath</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean copy(String srcPath, String destPath) {
		if (!validPath(srcPath))
			return false;
		if (!validPath(destPath))
			return false;
		return copy(new File(srcPath), new File(destPath));
	}

	/**
	 * <p>
	 * Copy a file or a directory.
	 * 
	 * @param src
	 * @param dest
	 * @return <code>true</code> if and only if the file (or directory)
	 *         <code>src</code> is copied as the file (or directory)
	 *         <code>src</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean copy(File src, File dest) {
		if (src.isFile()) {
			return copyFile(src, dest);
		} else if (src.isDirectory()) {
			return copyDirectiory(src, dest);
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * Copy a directory.
	 * 
	 * @param src
	 * @param dest
	 * @return <code>true</code> if and only if the directory <code>src</code>
	 *         is copied as the directory <code>src</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean copyDirectiory(File src, File dest) {
		mkdir(src);
		mkdir(dest);
		File[] file = src.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				copyFile(file[i], new File(dest.getAbsoluteFile()
						+ File.separator + file[i].getName()));
			} else if (file[i].isDirectory()) {
				String srcSubDir = src.getAbsoluteFile() + File.separator
						+ file[i].getName();
				String destSubDir = dest.getAbsoluteFile() + File.separator
						+ file[i].getName();
				if (!copyDirectiory(srcSubDir, destSubDir))
					return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Copy a file.
	 * 
	 * @param src
	 * @param dest
	 * @return <code>true</code> if and only if the file <code>src</code> is
	 *         copied as the the <code>src</code>; <br>
	 *         <code>false</code> otherwise
	 */
	public static boolean copyFile(File src, File dest) {
		FileInputStream input = null;
		BufferedInputStream inBuff = null;
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			input = new FileInputStream(src);
			inBuff = new BufferedInputStream(input);
			output = new FileOutputStream(dest);
			outBuff = new BufferedOutputStream(output);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			safeClose(inBuff);
			safeClose(outBuff);
			safeClose(output);
			safeClose(input);
		}
	}

	/**
	 * Skip <code>linesNum</code> lines.
	 * 
	 * @param br
	 * @param linesNum
	 * @return <code>true</code> if and only if the BufferedReader
	 *         <code>br</code> has skipped <code>linesNum</code> lines; <br>
	 *         <code>false</code> otherwise
	 */
	private static boolean skipLine(BufferedReader br, int linesNum) {
		while (linesNum-- > 0) {
			try {
				br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Get the first line as a new String from the file.
	 * 
	 * @param path
	 * @return the first line
	 */
	public static String getLine(String path) {
		if (!validPath(path))
			return null;
		return getLine(new File(path));
	}

	/**
	 * <p>
	 * Get the first line as a new String from the file.
	 * 
	 * @param in
	 * @return the first line
	 */
	public static String getLine(File in) {
		return getLine(in, 0);
	}

	/**
	 * <p>
	 * Get the <code>lineNum</code>-th line as a new String from the
	 * BufferedReader.
	 * 
	 * @param br
	 * @param lineNum
	 * @return the <code>lineNum</code>-th line
	 */
	private static String getLine(BufferedReader br, int lineNum) {
		if (skipLine(br, lineNum)) {
			try {
				return br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Get the <code>lineNum</code>-th line as a new String from the file.
	 * 
	 * @param in
	 * @param lineNum
	 * @return the <code>lineNum</code>-th line
	 */
	public static String getLine(File in, int lineNum) {
		String str = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			String charset = getCharset(in);
			reader = new InputStreamReader(new FileInputStream(in), charset);
			br = new BufferedReader(reader);
			str = getLine(br, lineNum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(br);
			safeClose(reader);
		}
		return str;
	}

	/**
	 * <p>
	 * Get all lines as a new String array from the BufferedReader.
	 * 
	 * @param br
	 * @return the array
	 */
	private static String[] getLines(BufferedReader br) {
		List<String> strList = new LinkedList<String>();
		String temp;
		try {
			while ((temp = br.readLine()) != null) {
				strList.add(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] strs = strList.toArray(new String[] {});
		return strs;
	}

	/**
	 * <p>
	 * Get all lines as a new String array from the file.
	 * 
	 * @param in
	 * @return the array
	 */
	public static String[] getLines(File in) {
		String[] strs = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			String charset = getCharset(in);
			reader = new InputStreamReader(new FileInputStream(in), charset);
			br = new BufferedReader(reader);
			strs = getLines(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(br);
			safeClose(reader);
		}
		return strs;
	}

	/**
	 * <p>
	 * Get all lines as a new String array from the file.
	 * 
	 * @param path
	 * @return the array
	 */
	public static String[] getLines(String path) {
		if (!validPath(path))
			return null;
		return getLines(new File(path));
	}

	/**
	 * <p>
	 * Get the first <code>topN</code> lines as a new String array from the
	 * BufferedReader.
	 * 
	 * @param br
	 * @param topN
	 * @return the array
	 */
	private static String[] getLines(BufferedReader br, int topN) {
		List<String> strList = new LinkedList<String>();
		for (int i = 0; i < topN; i++) {
			try {
				String temp = br.readLine();
				if (temp == null)
					break;
				strList.add(temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String[] strs = strList.toArray(new String[] {});
		return strs;
	}

	/**
	 * Get the first <code>topN</code> lines as a new String array from the
	 * file.
	 * 
	 * @param in
	 * @param topN
	 * @return the array
	 */
	public static String[] getLines(File in, int topN) {
		String[] str = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			String charset = getCharset(in);
			reader = new InputStreamReader(new FileInputStream(in), charset);
			br = new BufferedReader(reader);
			str = getLines(br, topN);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(br);
			safeClose(reader);
		}
		return str;
	}

	/**
	 * Get the first <code>topN</code> lines as a new String array from the
	 * file.
	 * 
	 * @param path
	 * @param topN
	 * @return the array
	 */
	public static String[] getLines(String path, int topN) {
		if (!validPath(path))
			return null;
		return getLines(new File(path), topN);
	}

	/**
	 * <p>
	 * Get a new String array, which begins at the specified
	 * <code>beginIndex</code> line and extends to the line at index
	 * <code>endIndex - 1</code>, from the BufferedReader.
	 * 
	 * @param br
	 * @param beginIndex
	 *            the beginning line index, inclusive
	 * @param endIndex
	 *            the ending line index, exclusive
	 * @return the specified array
	 */
	private static String[] getLines(BufferedReader br, int beginIndex,
			int endIndex) {
		String[] strs = new String[endIndex - beginIndex];
		skipLine(br, beginIndex - 1);
		for (int i = 0; i < endIndex - beginIndex; i++) {
			try {
				strs[i] = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strs;
	}

	/**
	 * 
	 * <p>
	 * Get a new String array, which begins at the specified
	 * <code>beginIndex</code> line and extends to the line at index
	 * <code>endIndex - 1</code>, from the file.
	 * 
	 * @param in
	 * @param beginIndex
	 *            the beginning line index, inclusive
	 * @param endIndex
	 *            the ending line index, exclusive
	 * @return the specified array
	 */
	public static String[] getLines(File in, int beginIndex, int endIndex) {
		String[] str = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			String charset = getCharset(in);
			reader = new InputStreamReader(new FileInputStream(in), charset);
			br = new BufferedReader(reader);
			str = getLines(br, beginIndex, endIndex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(br);
			safeClose(reader);
		}
		return str;
	}

	/**
	 * 
	 * <p>
	 * Get a new String array, which begins at the specified
	 * <code>beginIndex</code> line and extends to the line at index
	 * <code>endIndex - 1</code>, from the file.
	 * 
	 * @param in
	 * @param beginIndex
	 *            the beginning line index, inclusive
	 * @param endIndex
	 *            the ending line index, exclusive
	 * @return the specified array
	 */
	public static String[] getLines(String path, int beginIndex, int endIndex) {
		if (!validPath(path))
			return null;
		return getLines(new File(path), beginIndex, endIndex);
	}

	/**
	 * <p>
	 * Get the number of lines of the content.
	 * 
	 * @param path
	 * @return the number of lines
	 */
	public static int getLinesNum(String path) {
		if (!validPath(path))
			return -1;
		return getLinesNum(new File(path));
	}

	/**
	 * <p>
	 * Get the number of lines of the content.
	 * 
	 * @param in
	 * @return the number of lines
	 */
	public static int getLinesNum(File in) {
		int num = -1;
		InputStreamReader reader = null;
		try {
			String charset = getCharset(in);
			reader = new InputStreamReader(new FileInputStream(in), charset);
			num = getLinesNum(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(reader);
		}
		return num;
	}

	/**
	 * <p>
	 * Get the number of lines of the content.
	 * 
	 * @param reader
	 * @return the number of lines
	 */
	private static int getLinesNum(InputStreamReader reader) {
		int num = -1;
		BufferedReader br = null;
		br = new BufferedReader(reader);
		num = getLinesNum(br);
		safeClose(br);
		return num;
	}

	/**
	 * <p>
	 * Get the number of lines of the content.
	 * 
	 * @param br
	 * @return the number of lines
	 */
	private static int getLinesNum(BufferedReader br) {
		int num = 0;
		try {
			while ((br.readLine()) != null) {
				num++;
			}
		} catch (IOException e) {
			num = -1;
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * <p>
	 * get the file's charset
	 *
	 * @param path
	 *            the text file path
	 * @return charset
	 */
	public static String getCharset(String path) {
		return getCharset(new File(path));
	}

	/**
	 * <p>
	 * get the file's charset
	 *
	 * @param file
	 *            the text file
	 * @return charset
	 */
	public static String getCharset(File file) {
		String code = "";
		if (file == null || !file.exists())
			return code;
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] head = new byte[3];
			try {
				in.read(head);
				code = "";
				if (head[0] == -1 && head[1] == -2) {
					code = "UTF-16";
				} else if (head[0] == -2 && head[1] == -1) {
					code = "UTF-16";
				} else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
					code = "UTF-8";
				} else if ("Unicode".equals(code)) {
					code = "UTF-16";
				}
				if (code.length() == 0) {
					int index = 0;
					for (; index < head.length; index++) {
						if (head[index] < 0) {
							break;
						}
					}
					byte[] charBytes = new byte[] { 0, 0, 0 };
					switch (index) {
					case 0:
						charBytes[0] = head[0];
						charBytes[1] = head[1];
						charBytes[2] = head[2];
						break;
					case 1:
						charBytes[0] = head[1];
						charBytes[1] = head[2];
						charBytes[2] = (byte) in.read();
						break;
					case 2:
						charBytes[0] = head[2];
						charBytes[1] = (byte) in.read();
						charBytes[2] = (byte) in.read();
						break;
					default:
						while ((charBytes[0] = (byte) in.read()) >= 0)
							;
						charBytes[1] = (byte) in.read();
						charBytes[2] = (byte) in.read();
						break;
					}
					if ((charBytes[0] & 0xF0) == 0xE0
							&& (charBytes[1] & 0xC0) == 0x80
							&& (charBytes[2] & 0xC0) == 0x80) {// 无BOM的UTF-8
						code = "UTF-8";
					} else {
						code = "GBK";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * safely close
	 *
	 * @param stream
	 *            the stream
	 */
	public static void safeClose(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * safely close
	 *
	 * @param stream
	 *            the ImageInputStream
	 */
	public static void safeClose(ImageInputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * <p>
	 * safely close
	 * 
	 *
	 * @param stream
	 *            the OutputStream
	 */
	public static void safeClose(OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * <p>
	 * safely close
	 * 
	 *
	 * @param br
	 *            the BufferedReader
	 */
	private static void safeClose(BufferedReader br) {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * <p>
	 * safely close
	 * 
	 *
	 * @param isr
	 *            the InputStreamReader
	 */
	private static void safeClose(InputStreamReader isr) {
		if (isr != null) {
			try {
				isr.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * <p>
	 * safely close
	 * 
	 *
	 * @param fw
	 *            the FileWriter
	 */
	private static void safeClose(FileWriter fw) {
		if (fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * 更改文件或文件夹下的字符编码，默认更改为UTF-8
	 *
	 * @param path
	 *            路径
	 */
	public static void changeEncode(String path) {
		changeEncode(path, CHARSET.UTF8);
	}

	/**
	 * 更改指定后缀文件或文件夹下指定后缀文件的字符编码，默认更改为UTF-8
	 *
	 * @param path
	 *            路径
	 * @param charset
	 *            字符编码
	 * @param suffixes
	 *            指定后缀
	 */
	private static void changeEncode(String path, CHARSET charset,
			String... suffixes) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile()) {
				changeEncodeFile(file, charset, suffixes);
			} else {
				changeEncodeDir(file, charset, suffixes);
			}
		}
	}

	private static void changeEncodeFile(File file, CHARSET charset) {
		String[] lines = getLines(file);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
			sb.append('\n');
		}
		createNewFileForce(file);
		// String code = Charset.forName(charset.name()).toString();
		FileUtil.addToFile(sb.toString(), file);
	}

	private static void changeEncodeFile(File file, CHARSET charset,
			String... suffixes) {
		boolean flag = false;
		if (suffixes.length == 0) {
			flag = true;
		} else {
			String suf = file.getName();
			int index = suf.lastIndexOf('.');
			index = index >= 0 ? index : 0;
			suf = suf.substring(index);

			for (String suffix : suffixes) {
				if (suffix.endsWith(suf)) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			changeEncodeFile(file, charset);
		}
	}

	private static void changeEncodeDir(File file, CHARSET charset,
			String... suffixes) {
		File[] files = file.listFiles();
		Set<String> suffixSet = new LinkedHashSet<String>();
		for (String suffix : suffixes) {
			suffixSet.add(suffix);
		}
		for (File f : files) {
			if (f.isDirectory()) {
				changeEncodeDir(f, charset, suffixes);
			} else if (f.isFile()) {
				boolean flag = false;
				if (suffixSet.size() == 0) {
					flag = true;
				} else {
					String suf = f.getName();
					int index = suf.lastIndexOf('.');
					index = index >= 0 ? index : 0;
					suf = suf.substring(index);
					flag = suffixSet.contains(suf);
				}
				if (flag) {
					changeEncodeFile(f, charset);
				}
			}
		}

	}
}