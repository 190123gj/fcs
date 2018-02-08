package com.bornsoft.utils.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 日期帮助类
 * @author xiaohui@yiji.com
 * @date 2015-9-8 下午4:52:59 
 * @version V1.0
 */
public abstract class DateUtils {

	/** 存放不同的日期模板格式的SimpleDateFormat的Map */
	private static Map<String, ThreadLocal<SimpleDateFormat>> map = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static Calendar calendar = Calendar.getInstance();
	/**
	 * 返回一个ThreadLocal的simpleDateFormat,每个线程只会new一次simpleDateFormat
	 * @param pattern
	 * @return
	 */
	private static synchronized SimpleDateFormat getFormat(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = map.get(pattern);
		if (tl == null) {
			tl = new ThreadLocal<SimpleDateFormat>(){
				@Override
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			};
		}
		return tl.get();
	}

	/**
	 * yyyy-MM-dd
	 */
	public static final String PATTERN1 = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String PATTERN2 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyyMMdd
	 */
	public static final String PATTERN3 = "yyyyMMdd";

	/**
	 * yyMMddHHmmssSSS
	 */
	public static final String PATTERN4 = "yyMMddHHmmssSSS";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String PATTERN5 = "yyyyMMddHHmmss";

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String PATTERN6 = "yyyyMMddHHmmssSSS";

	/**
	 * yyyy-MM
	 */
	public static final String PATTERN7 = "yyyy-MM";
	
	/**
	 * HHmmss
	 */
	public static final String PATTERN8 = "HHmmss";

	/**
	 * 获取当前时间[格式：yyyy-MM-dd HH:mm:ss]
	 * @return
	 */
	public static String getCurrentDate() {
		return getFormat(PATTERN2).format(new Date());
	}

	/**
	 * 将日期转为yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date
	 * @return
	 */
	public static String toDefaultString(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(PATTERN2).format(date);
	}

	/**
	 * 将字符串按yyyy-MM-dd HH:mm:ss格式转为日期对象
	 * @param source
	 * @return
	 * @throws java.text.ParseException 
	 */
	public static Date toDefaultDate(String source) throws ParseException {
		if (source == null) {
			return null;
		}
		return getFormat(PATTERN2).parse(source);
	}

	/**
	 * 将日期按照指定的格式转为字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		return getFormat(pattern).format(date);
	}
	
	/**
	 * 将日期按照指定的格式转为字符串[默认当前日期]
	 * @param pattern
	 * @return
	 */
	public static String toString(String pattern) {
		return getFormat(pattern).format(new Date());
	}

	/**
	 * 将字符串按指定的格式转为日期对象
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String source, String pattern) throws ParseException {
		if (source == null) {
			return null;
		}
		return getFormat(pattern).parse(source);
	}

	/**
	 * 将字符串按指定的格式转为日期对象[如果有异常就默认为空]
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date toDate2(String source, String pattern) {
		if (source == null) {
			return null;
		}
		try {
			return getFormat(pattern).parse(source);
		} catch (Exception ex) {
			return null;
		}
	}


	/**
	 * 将日期转为yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param time
	 * @return
	 */
	public static String toDefaultString(long time) {
		Date date = new Date(time);
		return toDefaultString(date);
	}

	/**
	 * 将日期按照指定的格式转为字符串
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String toString(long time, String pattern) {
		Date date = new Date(time);
		return toString(date, pattern);
	}


	/**
	 * 获取本月第一天
	 * @return
	 */
	public static String getFastDate() {
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);			//设置为1号,当前日期既为本月第一天 
		return toString(c.getTime(), PATTERN1);
	}

	/**
	 * 获取本月最后一天
	 * @return
	 */
	public static String getLastDate() {
		Calendar ca = Calendar.getInstance();    
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return toString(ca.getTime(), PATTERN1);
	}

	/**
	 * 获取两个日期相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获取两个日期相差小时
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalHour(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (60 * 60 * 1000));
	}
	
	/**
	 * 获取两个日期相差分钟
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalMinute(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (60 * 1000));
	}

	/**
	 * 获取当前月的参数差月[格式： yyyy-MM-dd]
	 * @param date
	 * @param amount
	 * @return
	 */
	public static String getMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, amount);
		return toString(calendar.getTime(), PATTERN1);
	}

	/**
	 * 获取当前天的参数差天[格式： yyyy-MM-dd]
	 * @param date
	 * @param amount
	 * @return
	 */
	public static String getDay(Date date, int amount) {
		return getDay(date, amount, PATTERN1);
	}
	
	/**
	 * 获取当前天的参数差天[格式： yyyy-MM-dd HH:mm:ss]
	 * @param date
	 * @param amount
	 * @param pattern
	 * @return
	 */
	public static String getDay(Date date, int amount, String pattern) {
		return toString(addDay(date, amount), pattern);
	}
	
	/**
	 * 获取当前天的参数差分[格式： yyyy-MM-dd HH:mm:ss]
	 * @param date
	 * @param amount
	 * @param pattern
	 * @return
	 */
	public static String getMinute(Date date, int amount, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, amount);
		return toString(calendar.getTime(), pattern);
	}
	
	/**
	 * 获取某个日期的最后一刻
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getLastSecond(Date date){
		String dateStr = toString(date, PATTERN1);
		return dateStr + " 23:59:59";
	}
	
	/**
	 * 获取某个日期的起始时刻
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getFirstSecond(Date date){
		String dateStr = toString(date, PATTERN1);
		return dateStr + " 00:00:00";
	}
	
	/**
	 * 日期计算
	 * @param date
	 * @param peroid  可以传负数，表示前推
	 * @return
	 */
	public static Date addDay(Date date, int peroid) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, peroid);
		return calendar.getTime();
	}
	
	/**
	 * 日期计算 
	 * @param date
	 * @param fieldType  Calendar.DAY_OF_MONTH
	 * @param peroid     1
	 * @return
	 */
	public static Date add(Date date, int fieldType ,int peroid) {
		calendar.setTime(date);
		calendar.add(fieldType, peroid);
		return calendar.getTime();
	}
	

	/**
	 * 转换日期字符串格式
	 * @param dateStr
	 * @param fromPattern
	 * @param toPattern
	 * @return
	 * @throws ParseException 
	 */
	public static String transferDateFormat(String dateStr, String fromPattern, String toPattern) throws ParseException{
		return toString(toDate(dateStr, fromPattern), toPattern);
	}
}

