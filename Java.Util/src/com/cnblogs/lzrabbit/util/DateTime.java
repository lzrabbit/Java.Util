package com.cnblogs.lzrabbit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

	private java.util.Date instance;

	private DateTime() {
		this.instance = new java.util.Date();
	}

	private DateTime(Date date) {
		this.instance = date;
	}

	public static DateTime parse(Date date) {
		return new DateTime(date);
	}

	public static DateTime parse(String source) throws Exception {
		String pattern = "yyyy-MM-dd";
		if (source.contains(":")) {
			pattern += " HH:mm:ss";
		}
		return parse(source, pattern);
	}

	public static DateTime parse(String source, String pattern) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		DateTime dt = new DateTime();
		dt.instance = format.parse(source);
		return dt;
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static DateTime now() {
		return new DateTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		return calendar.getTime();
	}

	public static TimeSpan diff(Date date1, Date date2) {
		long time = 0;
		if (date1 != null && date2 != null) time = date1.getTime() - date2.getTime();
		return new TimeSpan(time);
	}

	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(this.instance);
	}

	public String toString(String pattern) {
		return format(this.instance, pattern);
	}

	public java.sql.Date sqlDate() {
		return new java.sql.Date(this.instance.getTime());
	}

	public java.sql.Timestamp sqlDateTime() {
		return new java.sql.Timestamp(this.instance.getTime());
	}

	public java.sql.Time sqlTime() {
		return new java.sql.Time(this.instance.getTime());
	}

	public java.util.Date date() {
		return this.sqlDate();
	}

	public long timeStamp() {
		return this.instance.getTime();
	}

	public DateTime addDays(int days) {
		this.instance = addDays(this.instance, days);
		return this;
	}

}
