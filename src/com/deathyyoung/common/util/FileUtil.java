package com.deathyyoung.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.stream.ImageInputStream;

/**
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Feb 24, 2015
 */
public class FileUtil {

	/** for Path Manipulation */
	public static final String pathReg = "[a-zA-Z0-9-_:/\\\\]+";

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
		try {
			beginIndex = beginIndex < 0 ? 0 : beginIndex;
			endIndex = endIndex > contents.length ? contents.length : endIndex;
			FileWriter fw = new FileWriter(file, true);
			for (int i = beginIndex; i < endIndex; i++) {
				fw.write(contents[i]);
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(content);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(content);
			fw.write("\n");
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		try {
			FileWriter fw = new FileWriter(file, true);
			for (int i = beginIndex; i < endIndex; i++) {
				fw.write(contents[i]);
				fw.write("\n");
			}
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		try {
			@SuppressWarnings("resource")
			FileReader fr = new FileReader(textFile);
			fr.skip(beginIndex);
			int charSize = 1024;
			char[] cs = new char[charSize];
			int length = -1;
			int i = 0;
			while ((length = fr.read(cs)) > 0) {
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
		try {
			FileWriter fw = new FileWriter(file, true);
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(file);
			for (int i = 0; i < fileLength / 4096; i++) {
				byte[] buffer = new byte[4096 * 1024];
				fos.write(buffer);
			}
			fos.write(new byte[fileLength % 4096 * 1024]);
			fw.close();
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(file);
			long fileSize = fis.available();
			if (fileSize < maxSize << 10) {
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			re = false;
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
		FileInputStream input;
		try {
			input = new FileInputStream(src);
			BufferedInputStream inBuff = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(dest);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
	public static boolean skipLine(BufferedReader br, int linesNum) {
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
	public static String getLine(BufferedReader br, int lineNum) {
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
		FileReader reader = null;
		String str = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(in);
			br = new BufferedReader(reader);
			str = getLine(br, lineNum);
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static String[] getLines(BufferedReader br) {
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
		FileReader reader = null;
		String[] strs = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(in);
			br = new BufferedReader(reader);
			strs = getLines(br);
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static String[] getLines(BufferedReader br, int topN) {
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
		FileReader reader = null;
		String[] str = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(in);
			br = new BufferedReader(reader);
			str = getLines(br, topN);
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static String[] getLines(BufferedReader br, int beginIndex,
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
		FileReader reader = null;
		String[] str = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(in);
			br = new BufferedReader(reader);
			str = getLines(br, beginIndex, endIndex);
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		FileReader reader;
		int num = -1;
		try {
			reader = new FileReader(in);
			num = getLinesNum(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static int getLinesNum(FileReader reader) {
		int num = -1;
		try {
			BufferedReader br = new BufferedReader(reader);
			num = getLinesNum(br);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * <p>
	 * Get the number of lines of the content.
	 * 
	 * @param br
	 * @return the number of lines
	 */
	public static int getLinesNum(BufferedReader br) {
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

	public static String getCharset(String path) throws IOException {
		if (!validPath(path))
			return null;
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(fis);
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "UTF-16LE";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}
		bin.close();
		fis.close();
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
	 *            the imageInputStream
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
	 *            the stream
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
	 *            the bufferedReader
	 */
	public static void safeClose(BufferedReader br) {
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
	 *            the inputStreamReader
	 */
	public static void safeClose(InputStreamReader isr) {
		if (isr != null) {
			try {
				isr.close();
			} catch (IOException e) {
				return;
			}
		}
	}
}