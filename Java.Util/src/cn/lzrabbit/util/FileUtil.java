package cn.lzrabbit.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Properties;

import org.omg.CORBA.portable.OutputStream;

public class FileUtil {
	public static String read(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void save(String file, String content) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String read(InputStream in, String charset) {
		try {
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取classpath根目录
	 * 
	 * @return
	 */
	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		if (path.startsWith("file:/")) return path.substring(6);
		else return path;
	}

	public static Properties loadPropertyFile(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();
			return prop;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void savePropertyFile(Properties prop, String file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			prop.store(fos, null);
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取应用程序根目录
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		String classpath = getClassPath();
		int len;
		if (classpath.contains("/WEB-INF/")) {
			len = "/WEB-INF/classes/".length() + 1;
		} else {
			len = "/classes/".length() + 1;
		}
		int index = classpath.lastIndexOf('/', classpath.length() - len);
		return classpath.substring(0, index + 1);
	}

	public static String getWebInfPath() {
		String classpath = getClassPath();
		// classpath="D:/SVN/EL_NBAPI_V02.00.00/Java/GHotel/WebRoot/WEB-INF/classes/";

		if (classpath.contains("/WEB-INF/")) {
			int index = classpath.lastIndexOf('/', classpath.length() - 2);
			classpath = classpath.substring(0, index + 1);
		} else {
			int index = classpath.lastIndexOf("/target/classes/") + 1;
			classpath = classpath.substring(0, index) + "WebRoot/WEB-INF/";
		}

		try {
			classpath = URLDecoder.decode(classpath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
		return classpath;
	}

	public static String getDir(String path, String pattern) {
		File baseDir = new File(path);
		if (!baseDir.exists() || !baseDir.isDirectory()) return null;
		for (File dir : baseDir.listFiles()) {
			if (dir.getName().equalsIgnoreCase(pattern)) { return dir.getAbsolutePath(); }
		}

		for (File dir : baseDir.listFiles()) {
			if (dir.isDirectory()) return getDir(dir.getAbsolutePath(), pattern);
		}
		return null;
	}
}
