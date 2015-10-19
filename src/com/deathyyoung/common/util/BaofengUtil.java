package com.deathyyoung.common.util;

import java.io.File;

public class BaofengUtil {

	/**
	 * <p>
	 * Remove "_baofeng" tag.
	 *
	 * @param path
	 */
	public static void removeTag(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				removeTag(files[i].getAbsolutePath());
			}
		} else if (file.isFile()) {
			String fileName = file.getAbsolutePath();
			String newname = fileName.split("(_baofeng)?.mp4")[0] + ".mp4";
			File newfile = new File(newname);
			file.renameTo(newfile);
		}
	}
}
