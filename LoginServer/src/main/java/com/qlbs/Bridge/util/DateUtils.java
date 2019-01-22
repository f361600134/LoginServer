package com.qlbs.Bridge.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final int MS = 1000; // 毫秒:1秒
	public static final int TOTAL_SEC_PER_MINUTE = 60; // 秒:一分钟
	public static final int TOTAL_SEC_PER_HOUR = 60 * TOTAL_SEC_PER_MINUTE;// 秒:一小时
	public static final int TOTAL_SEC_PER_DAY = 24 * TOTAL_SEC_PER_HOUR;// 秒:一天
	public static final long TOTAL_MS_PER_DAY = 24L * TOTAL_SEC_PER_HOUR * MS;// 毫秒:一天
	public static final long TOTAL_MS_PER_WEEK = 7L * 24 * TOTAL_SEC_PER_HOUR * MS;// 毫秒:一周

	public static String FORMAT_TIME = "HH:mm:ss";
	public static String FORMAT_DATE = "yyyyMMdd";
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_DATE_HYPHEN = "yyyy-MM-dd";

	/**
	 * 格式化指定日期/时间
	 * 
	 * @param date
	 * @param format
	 *            指定格式
	 * @return
	 * @return String
	 * @date 2018年9月19日上午4:49:45
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat f = new SimpleDateFormat(format);

		return f.format(date);
	}

	/**
	 * 格式化时间戳
	 * 
	 * @param timeVal
	 * @return
	 * @return String
	 * @date 2018年9月19日上午4:49:25
	 */
	public static String formatTime(long timeVal) {
		if (timeVal < 0) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(FORMAT_FULL);
		return f.format(new Date(timeVal));
	}

	/**
	 * 格式化期日期时间
	 * 
	 * @param date
	 * @return
	 * @return String
	 * @date 2018年9月19日上午4:49:08
	 */
	public static String formatTime(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(FORMAT_FULL);
		return f.format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return
	 * @return String
	 * @date 2018年9月19日上午4:48:46
	 */
	public static String formatHourTime(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(FORMAT_TIME);
		return f.format(date);
	}

	/**
	 * 获取到今天是一个月中的第几天
	 * 
	 * @return
	 * @return int
	 * @date 2018年9月19日上午12:18:38
	 */
	public static int getDay(long millis) {
		Calendar cal = Calendar.getInstance();
		if (millis != 0) {
			cal.setTimeInMillis(millis);
		}
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算某个时间增加的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 * @return Date
	 * @date 2018年9月19日上午4:47:07
	 */
	public static Date addDay(Date date, int day) {
		long time = date.getTime();
		long newTime = time + (TOTAL_MS_PER_DAY * day);
		return new Date(newTime);
	}
}
