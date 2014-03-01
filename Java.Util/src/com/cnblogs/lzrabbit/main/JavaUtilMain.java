package com.cnblogs.lzrabbit.main;

import com.cnblogs.lzrabbit.util.HttpUtil;

public class JavaUtilMain {
	
	public static void main(String[] args) throws Exception {
		String html= HttpUtil.sendGet("http://www.cnblogs.com/");
		System.out.println(html);
	}
}
