package com.apk.builder.model;

import com.apk.builder.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Library {
    
    private String mLibraryName;
    private File mPath;
    
    private Pattern mPackagePattern = Pattern.compile("(package\\=\".*\")");
    
    public Library(String path) {
        mPath = new File(path);
		mLibraryName = mPath.getName();
    }
	
	public Library(String path, String name) {
		mPath = new File(path);
		mLibraryName = (name != null && !name.isEmpty()) ? name : mPath.getName();
	}
	
    public static List<Library> fromFile(File file){
        
		List<Library> libraries = new ArrayList<>();
		
		if (!file.exists()) {
			return libraries;
		}
		
		File[] childs = file.listFiles();
		if (childs == null) {
			return libraries;
		}
		
		for (File child : childs) {
			if (new File(child, "classes.jar").exists()) {
				libraries.add(new Library(child.getAbsolutePath()));
			}
		}
		return libraries;
    }
	
	/**
	 * Builds a list of Library objects from a local_libs.json file.
	 *
	 * Expected shape (array of objects):
	 * [
	 *   { "lib_name": "appcompat", "lib_path": "/storage/emulated/0/.androIDE/libs/appcompat" },
	 *   { "lib_name": "material",  "lib_path": "/storage/emulated/0/.androIDE/libs/material" }
	 * ]
	 *
	 * lib_path points into the shared /storage/emulated/0/.androIDE/libs/
	 * store, not into the project itself. Entries whose lib_path no longer
	 * exists on disk are silently skipped rather than crashing the build.
	 */
	public static List<Library> fromLibsJson(File jsonFile) {
		List<Library> libraries = new ArrayList<>();

		if (jsonFile == null || !jsonFile.exists()) {
			return libraries;
		}

		String json = FileUtil.readFile(jsonFile.getAbsolutePath());
		if (json == null || json.trim().isEmpty()) {
			return libraries;
		}

		try {
			org.json.JSONArray arr = new org.json.JSONArray(json);
			for (int i = 0; i < arr.length(); i++) {
				org.json.JSONObject entry = arr.getJSONObject(i);
				String libName = entry.optString("lib_name", null);
				String libPath = entry.optString("lib_path", null);

				if (libPath == null || libPath.isEmpty()) {
					continue;
				}

				File libDir = new File(libPath);
				if (!libDir.exists()) {
					continue;
				}

				libraries.add(new Library(libPath, libName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return libraries;
	}
	
	public File getPath() {
		return mPath;
	}
	
	public String getName() {
		return mLibraryName;
	}
	
	public File getResourcesFile() {
	    return new File(mPath, "res");
	}
	
	public File getClassJarFile() {
	    return new File(mPath, "classes.jar");
	}
	
	public List<File> getDexFiles() {
	    List<File> files = new ArrayList<>();
	    File[] fileArr = mPath.listFiles();
	    if (fileArr == null) {
	        return files;
	    }
	    
	    for (File file : fileArr) {
	        if (file.getName().endsWith(".dex")) {
	            files.add(file);
	        }
	    }
	    return files;
	}
	
	public boolean requiresResourceFile() {
		return new File(mPath, "res").exists();
	}
	
	public String getPackageName() {
		String manifest = FileUtil.readFile(mPath + "/AndroidManifest.xml");
		Matcher matcher = mPackagePattern.matcher(manifest);
		
		if (matcher.find()) {
			return matcher.group(1).substring(9, matcher.group(1).length() -1);
	    }
		
		return null;
	}
}
