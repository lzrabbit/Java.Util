package cn.lzrabbit.util;


public class TimeSpan {
	private int days;
	private int hours;
	private int minutes;
	private long seconds;
	private long milliseconds;

	private double totalDays;
	private double totalHours;
	private double totalMinutes;
	private double totalSeconds;
	private double totalMilliseconds;

	public TimeSpan(long millisecond) {
		this.totalMilliseconds = millisecond;
		this.totalSeconds = millisecond / 1000.0;
		this.totalMinutes = millisecond / (60 * 1000.0);
		this.totalHours = millisecond / (60 * 60 * 1000.0);
		this.totalDays = millisecond / (24 * 60 * 60 * 1000.0);

		this.milliseconds = millisecond % 1000;
		this.seconds = millisecond % (60 * 1000) / 1000;
		this.minutes = (int) millisecond % (60 * 60 * 1000) / (60 * 1000);
		this.hours = (int) millisecond % (24 * 60 * 60 * 1000) / (60 * 60 * 1000);
		this.days = (int) millisecond / (24 * 60 * 60 * 1000);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		String str = String.format("%s天  %s小时  %s分钟  %s秒  %s毫秒", this.totalDays, this.totalHours, this.totalMinutes, this.totalSeconds, this.totalMilliseconds);
		str += String.format("\r\n%s天 %s小时 %s分钟 %s秒 %s毫秒", this.days, this.hours, this.minutes, this.seconds, this.milliseconds);
		return str;
	}

	public int getDays() {
		return days;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public long getSeconds() {
		return seconds;
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public double getTotalDays() {
		return totalDays;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public double getTotalMinutes() {
		return totalMinutes;
	}

	public double getTotalSeconds() {
		return totalSeconds;
	}

	public double getTotalMilliseconds() {
		return totalMilliseconds;
	}
}
