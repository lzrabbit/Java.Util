package com.cnblogs.lzrabbit.main;

import com.cnblogs.lzrabbit.util.*;


/**
 * Hello world!
 *
 */
public class LzrabbitMain 
{
    public static void main( String[] args ) throws Exception
    {
    	String html= HttpUtil.sendGet("http://www.ask.com/");
    	System.out.println(html);
        System.out.println( "Hello World!" );
    }
}
