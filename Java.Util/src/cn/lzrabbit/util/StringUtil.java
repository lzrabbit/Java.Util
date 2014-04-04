package cn.lzrabbit.util;

import java.util.Collection;

public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.equals("");
	}

	public static boolean isNullOrWhitespace(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static <T> String join(String separator, Collection<T> collection) {
		return join(separator, collection.toArray());
	}

	public static <T> String join(String separator, Object[] collection) {
		if (collection == null) return null;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < collection.length; i++) {
			if (i == 0) sb.append(collection[i]);
			else sb.append(separator + collection[i]);
		}
		return sb.toString();
	}
}
