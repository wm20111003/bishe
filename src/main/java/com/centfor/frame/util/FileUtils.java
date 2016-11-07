package com.centfor.frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<File> getPathAllFileExt(String path, String ext) {
		List<File> list = new ArrayList<File>();

		getPathAllFileExt(list, path, ext);

		return list;

	}

	public static List<File> getPathAllFileExt(List<File> list, String path,
			String ext) {

		File dir = new File(path);
		if (dir.isDirectory() == false)
			return list;

		File[] files = dir.listFiles();

		if (files == null || files.length < 1)
			return list;
		for (File f : files) {
			if (f.isDirectory()) {
				String dirPath = f.getAbsolutePath();
				getPathAllFileExt(list, dirPath, ext);
			} else {
				if (f.getName().endsWith(ext)) {

					list.add(f);
				}
			}
		}

		return list;
	}



    
    
}


