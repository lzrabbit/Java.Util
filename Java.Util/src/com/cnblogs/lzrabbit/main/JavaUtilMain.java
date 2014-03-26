package com.cnblogs.lzrabbit.main;

import java.lang.reflect.ParameterizedType;

import com.cnblogs.lzrabbit.util.HttpUtil;

public class JavaUtilMain {

	public static void main(String[] args) throws Exception {
		String html = HttpUtil.sendGet("http://www.cnblogs.com/");
		System.out.println(html);
	}

//	public <T> Void getMyTT() {
//		//Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
//		System.out.println();
//	}
}
