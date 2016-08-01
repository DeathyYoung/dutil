package com.deathyyoung.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author <a href="#" target="_blank">Deathy Young</a> (<a
 *         href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 * @since Mar 2, 2015
 */
public class TimeUtil {

	private Date date;
	public static String datePattern = "yyyy-MM-dd";
	public static String normalPattern = "yyyy-MM-dd HH:mm:ss";
	public static String filePattern = "yyyy_MM_dd_HH_mm_ss_SSS";

	public TimeUtil() {
		date = new Date();
	}

	/**
	 * Title: getTime
	 * 
	 * @see 获取字符串类型时间
	 * @return 时间
	 */
	public static String getTime() {
		return getTime(normalPattern);
	}

	/**
	 * Title: getTimeForFileName
	 * 
	 * @see 为文件名获取字符串类型时间
	 * @return 时间
	 */
	public static String getTimeForFileName() {
		return getTime(filePattern);
	}

	/**
	 * Title: getTime
	 * 
	 * @see 获取字符串类型时间
	 * @param dateFormatString
	 *            时间格式
	 * @return 时间
	 */
	public static String getTime(String dateFormatString) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
		return sdf.format(date);
	}

	/**
	 * Title: secondAfter
	 * 
	 * @see 获取指定秒数之后的时间
	 * @param second
	 *            秒数
	 * @return 时间
	 */
	public static String secondAfter(long second) {
		return secondAfter(second, normalPattern);
	}

	/**
	 * Title: secondAfter
	 * 
	 * @see 获取指定秒数之后的时间
	 * @param second
	 *            秒数
	 * @param pattern
	 *            时间格式
	 * @return 时间
	 */
	public static String secondAfter(long second, String pattern) {
		Date date = new Date();
		date.setTime(date.getTime() + second * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(date);
	}

	public static String secondBefore(long second, String pattern) {
		return secondAfter(-second, pattern);
	}

	/**
	 * Title: minuteAfter
	 * 
	 * @see 获取指定分钟数之后的时间
	 * @param minute
	 *            分钟数
	 * @return 时间
	 */
	public static String minuteAfter(long minute) {
		return secondAfter(60 * minute, normalPattern);
	}

	/**
	 * Title: minuteAfter
	 * 
	 * @see 获取指定分钟数之后的时间
	 * @param minute
	 *            分钟数
	 * @param pattern
	 *            时间格式
	 * @return 时间
	 */
	public static String minuteAfter(long minute, String pattern) {
		return secondAfter(60 * minute, pattern);
	}

	public static String minuteBefore(long minute) {
		return minuteAfter(-minute, normalPattern);
	}

	public static String minuteBefore(long minute, String pattern) {
		return minuteAfter(-minute, pattern);
	}

	/**
	 * Title: hourAfter
	 * 
	 * @see 获取指定小时数之后的时间
	 * @param hour
	 *            小时数
	 * @param pattern
	 *            时间格式
	 * @return 时间
	 */
	public static String hourAfter(long hour, String pattern) {
		return minuteAfter(60 * hour, pattern);
	}

	public static String hourAfter(long hour) {
		return minuteAfter(60 * hour, normalPattern);
	}

	public static String hourBefore(long hour) {
		return hourAfter(-hour, normalPattern);
	}

	public static String hourBefore(long hour, String pattern) {
		return hourAfter(-hour, pattern);
	}

	public static String dayAfter(long day) {
		return hourAfter(24 * day, normalPattern);
	}

	public static String dayAfter(long day, String pattern) {
		return hourAfter(24 * day, pattern);
	}

	public static String dayBefore(long day) {
		return dayAfter(-day, normalPattern);
	}

	public static String dayBefore(long day, String pattern) {
		return dayAfter(-day, pattern);
	}

	public static String weekAfter(long week) {
		return weekAfter(7 * week, normalPattern);
	}

	public static String weekAfter(long week, String pattern) {
		return weekAfter(7 * week, pattern);
	}

	public static String weekBefore(long week) {
		return weekAfter(-week, normalPattern);
	}

	public static String weekBefore(long week, String pattern) {
		return weekAfter(-week, pattern);
	}

	/**
	 * Title: start
	 * <P>
	 * Description: 开始计时
	 */
	public void start() {
		date.setTime((new Date()).getTime());
	}

	/**
	 * Title: getCountdown
	 * <P>
	 * Description: 得到计数值
	 */
	public long getCountdown() {
		Date d = new Date();
		long time = d.getTime() - date.getTime();
		date = d;
		return time;
	}

	/**
	 * Title: getCountdown
	 * <P>
	 * Description: 得到计数时间
	 */
	public String showCountdown() {
		long ms = getCountdown();
		StringBuffer sb = new StringBuffer();
		long second = ms / 1000;
		ms = ms % 1000;
		long minite = second / 60;
		second = second % 60;
		sb.insert(0, "ms");
		sb.insert(0, ms);
		sb.insert(0, "s");
		sb.insert(0, second);
		if (minite > 0) {
			long h = minite / 60;
			minite = minite % 60;
			sb.insert(0, "m");
			sb.insert(0, minite);
			if (h > 0) {
				long d = h / 24;
				h = h % 24;
				sb.insert(0, "h");
				sb.insert(0, h);
				if (d > 0) {
					sb.insert(0, "d");
					sb.insert(0, d);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param start
	 * @param end
	 * @return long
	 */
	public static long getInterval(String start, String end) {
		return getInterval(start, end, normalPattern);
	}

	/**
	 * @param start
	 * @param end
	 * @return long
	 */
	public static long getIntervalBySecond(String start, String end) {
		return getInterval(start, end, normalPattern) / 1000;
	}

	/**
	 * @param start
	 * @param end
	 * @param dateFormatString
	 * @return long
	 */
	public static long getInterval(String start, String end,
			String dateFormatString) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
		try {
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			return endDate.getTime() - startDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param start
	 * @param end
	 * @param dateFormatString
	 * @return long
	 */
	public static long getIntervalBySecond(String start, String end,
			String dateFormatString) {
		return getInterval(start, end, dateFormatString) / 1000;
	}

	/**
	 * @param second
	 * @return String
	 */
	public static String getPassTimeByHms(int second) {
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		StringBuffer sb = new StringBuffer();
		sb.append(format2Digit(hour));
		sb.append(':');
		sb.append(format2Digit(minute));
		sb.append(':');
		sb.append(format2Digit(second));
		return sb.toString();
	}

	/**
	 * @param num
	 * @return String
	 */
	private static String format2Digit(int num) {
		if (num < 10) {
			return "0" + num;
		} else {
			return "" + num;
		}
	}

	/**
	 * @Title: getDate
	 * @param time
	 * @param pattern
	 * @return Date
	 */
	public static Date getDate(String time) {
		return getDate(time, datePattern);
	}

	/**
	 * @Title: getDate
	 * @param time
	 * @param pattern
	 * @return Date
	 */
	public static Date getDate(String time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: getRangeByDay
	 * @param startTime
	 * @param endTime
	 * @return int
	 */
	public static int getRangeByDay(String startTime, String endTime) {
		Date start = TimeUtil.getDate(startTime);
		Date end = TimeUtil.getDate(endTime);
		long ms = end.getTime() - start.getTime();
		return (int) (ms / 86400000) + 1;
	}

	/**
	 * @Title: getRangeByDay
	 * @param startTime
	 * @param endTime
	 * @return int
	 */
	public static int getRangeByDay(String startTime, String endTime,
			String pattern) {
		Date start = TimeUtil.getDate(startTime, pattern);
		Date end = TimeUtil.getDate(endTime, pattern);
		long ms = end.getTime() - start.getTime();
		return (int) (ms / 86400000) + 1;
	}

	/**
	 * 判断字符串是否是有效的日期， 格式 "yyyy/MM/dd,yyyy/MM/d,yyyy/M/dd,yyyy/M/d" "yyyyMMdd"
	 * 
	 * @param date
	 *            日期字符串
	 * @return 是则返回true，否则返回false
	 */
	public static boolean isValidDate(String date) {
		if ((date == null) || (date.length() < 8)) {
			return false;
		}

		try {
			boolean result = false;
			SimpleDateFormat formatter;
			char dateSpace = date.charAt(4);
			String format[];
			if ((dateSpace == '-') || (dateSpace == '/')) {
				format = new String[4];
				String strDateSpace = new String(new char[] { dateSpace });
				format[0] = "yyyy" + strDateSpace + "MM" + strDateSpace + "dd";
				format[1] = "yyyy" + strDateSpace + "MM" + strDateSpace + "d";
				format[2] = "yyyy" + strDateSpace + "M" + strDateSpace + "dd";
				format[3] = "yyyy" + strDateSpace + "M" + strDateSpace + "d";
			} else {
				format = new String[1];
				format[0] = "yyyyMMdd";
			}

			for (int i = 0; i < format.length; i++) {
				String aFormat = format[i];
				formatter = new SimpleDateFormat(aFormat);
				formatter.setLenient(false);
				String tmp = formatter.format(formatter.parse(date));
				if (date.equals(tmp)) {
					result = true;
					break;
				}
			}
			return result;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是有效的日期，格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 *            日期字符串
	 * @return 是则返回true，否则返回false
	 */
	public static boolean isValidTime(String date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			formatter.setLenient(false);
			formatter.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 转换字符串为日期，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static Date getDateTime(String date) throws ParseException {
		return getDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 自动识别日期格式，并转换
	 */
	public static Date getDateByAuto(String time) {
		SimpleDateFormat formatter = null;
		if (time.indexOf(":") > 0) {// 带有时间
			int tempPos = time.indexOf("AD");
			time = time.trim();
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
			if (tempPos > -1) {
				time = time.substring(0, tempPos) + "公元"
						+ time.substring(tempPos + "AD".length());// china
				formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
			} else if ((time.indexOf(" ") < 0) && time.length() > 8) {
				formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
			} else if ((time.indexOf("/") == 2) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			} else if ((time.indexOf("/") == 4) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			} else if ((time.indexOf("\\") == 2) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("dd\\MM\\yyyy HH:mm:ss");
			} else if ((time.indexOf("\\") == 4) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("yyyy\\MM\\dd HH:mm:ss");

			} else if ((time.indexOf("/") == 2) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("dd/MM/yyyy KK:mm:ss a");
			} else if ((time.indexOf("/") == 4) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd KK:mm:ss a");
			} else if ((time.indexOf("\\") == 2) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("dd\\MM\\yyyy KK:mm:ss a");
			} else if ((time.indexOf("\\") == 4) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("yyyy\\MM\\dd KK:mm:ss a");

			} else if ((time.indexOf("-") == 2) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			} else if ((time.indexOf("-") == 4) && (time.indexOf(" ") > -1)) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			} else if ((time.indexOf("-") == 2) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("dd-MM-yyyy KK:mm:ss a");
			} else if ((time.indexOf("-") == 4) && (time.indexOf("am") > -1)
					|| (time.indexOf("pm") > -1)) {
				formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
			}
		} else {
			if (time.indexOf("/") > 0) {
				int pos = time.indexOf("/");
				if (pos == 2) {
					formatter = new SimpleDateFormat("dd/MM/yyyy");
				} else if (pos == 4) {
					formatter = new SimpleDateFormat("yyyy/MM/dd");
				}
			} else if (time.indexOf("\\") > 0) {
				int pos = time.indexOf("\\");
				if (pos == 2) {
					formatter = new SimpleDateFormat("dd\\MM\\yyyy");
				} else if (pos == 4) {
					formatter = new SimpleDateFormat("yyyy\\MM\\dd");
				}
			} else if (time.indexOf("-") > 0) {
				int pos = time.indexOf("-");
				if (pos == 2) {
					formatter = new SimpleDateFormat("dd-MM-yyyy");
				} else if (pos == 4) {
					formatter = new SimpleDateFormat("yyyy-MM-dd");
				}
			} else if (time.trim().length() == 8) {
				try {
					formatter = new SimpleDateFormat("yyyyMMdd");
					formatter.parse(time);
				} catch (ParseException e) {
					formatter = new SimpleDateFormat("ddMMyyyy");
				}
			}
		}

		ParsePosition pos = new ParsePosition(0);
		java.util.Date ctime = formatter.parse(time, pos);

		return ctime;
	}

	/**
	 * 取得现在的时间，格式"HH:mm:ss"
	 */
	public static String getNowTimeText() {
		return getTimeText(new Date());
	}

	/**
	 * 取得现在的日期，格式"yyyy-MM-dd"
	 */
	public static String getNowDateText() {
		return getDateText(new Date());
	}

	/**
	 * 取得现在的日期，格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String getNowDateTimeText() {
		return getDateTimeText(new Date());
	}

	/**
	 * 将日期字符串按格式转换为日期
	 */
	public static String getTimeText(Date date) {
		return toString(date, "HH:mm:ss");
	}

	/**
	 * 将日期字符串按格式转换为日期
	 */
	public static String getDateText(Date date) {
		return toString(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期字符串按格式转换为日期
	 */
	public static String getDateTimeText(Date date) {
		return toString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期按格式转换为日期字符串
	 */
	public static String toString(Date date, String format) {
		if (date == null)
			return null;
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 取得日期的年份，格式"yyyy"
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取得日期的月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 取得日期的天数
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 在指定日期上增加天数
	 */
	public static Date addDay(Date date, int addCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, addCount);
		return calendar.getTime();
	}

	/**
	 * 在指定日期上增加周数
	 */
	public static Date addWeek(Date date, int addCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, addCount);
		return calendar.getTime();
	}

	/**
	 * 在指定日期上增加月数
	 */
	public static Date addMonth(Date date, int addCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, addCount);
		return calendar.getTime();
	}

	/**
	 * 拼接时间
	 */
	public static Date contactDate(Date startDate, Date time) {
		try {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
			DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format3.parse(format1.format(startDate) + " "
					+ format2.format(time));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 计算两个日期相隔的天数
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		return Math
				.abs((int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000)));
	}

	/**
	 * 计算两个日期相隔的月数(不足整月的算一个月)
	 */
	public static int getMonthsBetween(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		int months = calendarEndDate.get(Calendar.MONTH)
				- calendarStartDate.get(Calendar.MONTH)
				+ (calendarEndDate.get(Calendar.YEAR) - calendarStartDate
						.get(Calendar.YEAR)) * 12;

		if (getEndDateByMonths(startDate, months).compareTo(endDate) < 0)
			months += 1;

		return months;
	}

	/**
	 * 计算两个日期相隔的年数
	 */
	public static int getActualYears(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		int years = calendarEndDate.get(Calendar.YEAR)
				- calendarStartDate.get(Calendar.YEAR);
		if (calendarEndDate.get(Calendar.MONTH) < calendarStartDate
				.get(Calendar.MONTH)) {
			years = years - 1;
		} else if (calendarEndDate.get(Calendar.MONTH) == calendarStartDate
				.get(Calendar.MONTH)) {
			if (calendarEndDate.get(Calendar.DAY_OF_MONTH) < calendarStartDate
					.get(Calendar.DAY_OF_MONTH)) {
				years = years - 1;
			}
		}
		return years;
	}

	/**
	 * 根据起始日和相隔天数计算终止日
	 */
	public static Date getEndDateByDays(Date startDate, int days) {
		Calendar calendarStartDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarStartDate.add(Calendar.DAY_OF_YEAR, days);

		return calendarStartDate.getTime();
	}

	/**
	 * 根据起始日和相隔月数计算终止日
	 */
	public static Date getEndDateByMonths(Date startDate, int months) {
		Calendar calendarStartDate = Calendar.getInstance();
		calendarStartDate.setTime(startDate);
		calendarStartDate.add(Calendar.MONTH, months);
		return calendarStartDate.getTime();
	}

	/**
	 * 根据终止日和相隔天数计算起始日
	 */
	public static Date getStartDateByDays(Date endDate, int days) {
		Calendar calendarEndDate = Calendar.getInstance();
		calendarEndDate.setTime(endDate);
		calendarEndDate.add(Calendar.DAY_OF_YEAR, 0 - days);

		return calendarEndDate.getTime();
	}

	/**
	 * 根据终止日和相隔月数计算起始日
	 */
	public static Date getStartDateByMonths(Date endDate, int months) {
		Calendar calendarEndDate = Calendar.getInstance();
		calendarEndDate.setTime(endDate);
		calendarEndDate.add(Calendar.MONTH, 0 - months);

		return calendarEndDate.getTime();
	}

	/**
	 * 判断两个日期是否对日
	 */
	public static boolean isSameDate(Date startDate, Date endDate) {
		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);
		if (startDate.after(endDate)) {
			Calendar swap = calendarStartDate;
			calendarStartDate = calendarEndDate;
			calendarEndDate = swap;
		}

		if (calendarStartDate.get(Calendar.DATE) == calendarEndDate
				.get(Calendar.DATE))
			return true;

		if (calendarStartDate.get(Calendar.DATE) > calendarEndDate
				.get(Calendar.DATE)) {
			if (calendarEndDate.get(Calendar.DATE) == calendarEndDate
					.getActualMaximum(Calendar.DATE))
				return true;
		}

		return false;
	}

	/**
	 * 判断日期是否与指定的日期对日
	 */
	public static boolean isSameDate(Date date, String dd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = Integer.parseInt(dd);

		if (calendar.get(Calendar.DATE) == day)
			return true;

		if (calendar.get(Calendar.DATE) < day) {
			if (calendar.get(Calendar.DATE) == calendar
					.getActualMaximum(Calendar.DATE))
				return true;
		}

		return false;
	}

	/**
	 * 判断两个日期是否同一个月
	 */
	public static boolean isSameMonth(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return false;

		Calendar calendarStartDate = Calendar.getInstance();
		Calendar calendarEndDate = Calendar.getInstance();

		// 设日历为相应日期
		calendarStartDate.setTime(startDate);
		calendarEndDate.setTime(endDate);

		if (calendarStartDate.get(Calendar.YEAR) == calendarEndDate
				.get(Calendar.YEAR)
				&& calendarStartDate.get(Calendar.MONTH) == calendarEndDate
						.get(Calendar.MONTH))
			return true;

		return false;
	}

	/**
	 * 得到本月第一天的日期
	 */
	public static Date getFirstDate(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, 1);

		return calendar.getTime();
	}

	/**
	 * 得到本月最后一天的日期
	 */
	public static Date getLastDate(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		return calendar.getTime();
	}

	/**
	 * 2004-20-20 to: 2004年20月20日
	 */
	public static String dateToNianYueRi(String strDate) {
		return strDate.substring(0, 4).concat("年")
				.concat(strDate.substring(5, 7)).concat("月")
				.concat(strDate.substring(8, 10)).concat("日");
	}

	/*
	 * 取本周7天的第一天（周一的日期）
	 */
	public static String firstDayInThisWeek() {
		return firstDayInWeek(0);
	}

	public static String firstDayInWeek(int offset) {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		mondayPlus += 7 * offset;
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = toString(monday, datePattern);
		return preMonday;
	}

}