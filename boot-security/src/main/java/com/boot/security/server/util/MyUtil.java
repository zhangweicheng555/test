package com.boot.security.server.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务逻辑工具类
 */
public class MyUtil {

	public static String dateFormat = "yyyy-MM-dd";
	public static String dateFormatMinute = "yyyy/MM/dd HH:mm";
	public static String timeFormat = "yyyy-MM-dd HH:mm:ss";

	private static double EARTH_RADIUS = 6378.137;// 单位千米

	/**
	 * String to Date
	 */
	public static Date stringToDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormatMinute);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 经纬度计算两点之间的距离
	 * 
	 * 单位为米
	 * 
	 * 纬度lat1 经度lng1
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = getRadian(lat1);
		double radLat2 = getRadian(lat2);
		double a = radLat1 - radLat2;// 两点纬度差
		double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s * 1000;
	}

	private static double getRadian(double degree) {
		return degree * Math.PI / 180.0;
	}

	/**
	 * 当前日期格式化为指定的格式 默认：yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDate(String format) {
		if (StringUtils.isBlank(format)) {
			format = timeFormat;
		}
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 将yyyy-MM-dd日期格式化为 yyyy-MM
	 */
	public static String getFormatDate(String date) throws Exception {
		return date.substring(0, date.lastIndexOf("-"));
	}

	/**
	 * 得到处理过的uuid
	 */
	public static String getSequesceUuid() {
		String uuidStr = java.util.UUID.randomUUID().toString();
		String[] uuidParts = uuidStr.split("-");
		StringBuffer builder = new StringBuffer();
		builder.append(uuidParts[2]);
		builder.append("-");
		builder.append(uuidParts[1]);
		builder.append("-");
		builder.append(uuidParts[0]);
		builder.append("-");
		builder.append(uuidParts[3]);
		builder.append("-");
		builder.append(uuidParts[4]);
		return builder.toString();
	}

	/**
	 * 根据给定日期返回一年前的这个时间 可扩展 月 日 endDate:
	 * 注意这个date的格式是formatDateLast-formatDateLast-formatDateLast
	 */
	public static String getDateBeforeYear(String endDate) {
		String[] str = endDate.split("-");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		// ca.setTime(new Date()); // 设置时间为当前时间
		ca.set(Integer.parseInt(str[0]), Integer.parseInt(str[1]) - 1, Integer.parseInt(str[2]));// 月份是从0开始的，所以11表示12月
		ca.add(Calendar.YEAR, -1); // 年份减1
		// ca.add(Calendar.MONTH, -1);// 月份减1
		// ca.add(Calendar.DATE, -1);// 日期减1
		Date resultDate = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(resultDate);
	}

	/**
	 * 将给定的日期转化为毫秒 日期的格式最好是一般保证 yyyy-MM-dd HH:mm:ss
	 */
	public static long dateToMillSecond(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		long diff = 0;
		try {
			d1 = format.parse(date);
			diff = d1.getTime();
		} catch (ParseException e) {
		}
		return diff;
	}

	/**
	 * 将毫秒转化为x天-x小时-x分钟-x秒
	 */
	public static String millSecondToFormatString(long diff) {
		if (diff > 0) {
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			return diffDays + "天-" + diffHours + "小时-" + diffMinutes + "分钟-" + diffSeconds + "秒";
		} else {
			return "0天-0小时-0分钟-0秒";
		}
	}

	/**
	 * 计算两个日期之间的毫秒值 传入的日期的格式最好是(一般保证)yyyy-MM-dd HH:mm:ss 保证endDate 大于 beginDate
	 */
	public static long queryMillSecondByTwoDate(String beginDate, String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		long diff = 0;
		try {
			d1 = format.parse(beginDate);
			d2 = format.parse(endDate);
			// 毫秒ms
			diff = d2.getTime() - d1.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 获取当前日期的前第几天的日期 格式为 yyyy-MM-dd 前一天 传入 -1 前七天 传入 -7 传入0代表当前的日期
	 */
	public static String getDateBeforSevenDay(int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, num);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 根据当前日期 获取到前7天 共7天的日期 从昨天开始 存入 list集合里面 格式：yyyy-MM-dd
	 */
	public static List<String> getBeforeSevenDayByNow() {
		List<String> list = new ArrayList<String>();
		for (int i = -7; i < 0; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			Date monday = c.getTime();
			String preMonday = sdf.format(monday);
			list.add(preMonday);
		}
		return list;
	}

	/**
	 * 根据开始日期跟技术日期 得到中间的日期 list集合 Date格式
	 * 
	 * @throws Exception
	 */

	public static List<Date> getDateBetweenBeginAndEnd(String beginDate, String endDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(beginDate);
		Date dEnd = sdf.parse(endDate);

		/** 根据开始日期跟结束日期 得到date集合 */
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dBegin);
		boolean bContinue = true;
		while (bContinue) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (dEnd.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(dEnd);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 获取当前月的最后一天
	 */
	public static String getLastDayByMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	/**
	 * 获取当前月的第一天
	 */
	public static String getFirdtDayByMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 得到某个月的第一天 0为当前月 -1 为前一个月
	 */
	public static String getFirdtDayBySomeMonth(int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, num);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 将list date集合 转化为list string集合
	 */
	public static List<String> listDateToStr(List<Date> liDates) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<String>();
		if (liDates.size() > 0) {
			for (int i = 0; i < liDates.size(); i++) {
				if (!list.contains(sdf.format(liDates.get(i)))) {
					list.add(sdf.format(liDates.get(i)));
				}
			}
		}
		return list;
	}

	/***
	 * 根据指定日期获取 前几天 后几天的指定日期 dateStr :传入的指定日期 就是以这个日期为基础 num： 推移的天数 0：代表当天
	 * 1：代表表当前日期的退后一天 -1：代表当前日期的前一天
	 */
	public static String getSomeDateByDay(String dateStr, int num) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 根据开始日期、结束日期 计算包含的月份 返回 list<string>
	 */
	public static List<String> queryMonthListByDate(String beginDate, String endDate) throws Exception {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatDateLast = new SimpleDateFormat("yyyy-MM");
		Date date1 = simpleDate.parse(beginDate);
		Date date2 = simpleDate.parse(endDate);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);
		list.add(formatDateLast.format(c1.getTime()));

		while (c1.compareTo(c2) < 0) {
			if ((formatDateLast.format(c1.getTime())).equals(formatDateLast.format(c2.getTime()))) {
				c1.add(Calendar.MONTH, 0);
			} else {
				c1.add(Calendar.MONTH, 1);
			}
			Date ss = c1.getTime();
			String str = formatDateLast.format(ss);
			if (!list.contains(str)) {
				list.add(str);
			}
			if ((formatDateLast.format(c2.getTime())).equals(str)) {
				break;
			}
		}
		return list;
	}

	/***
	 * 传入日期 计算三个月前的日期 num 为负数代表 前 为正数代表 后
	 * 
	 * @throws Exception
	 */
	public static String getDateBeforeThreeMonths(String nowdate, int num) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(nowdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 传入 2015-09 传出 2015-06 可用于格式化日期 这个主意 例如传入2015-09-09 传出2015-09 num 为0 就可以
	 * 
	 */
	public static String getDateBeforeThreeOnlyMonths(String nowdate, int num) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = sdf.parse(nowdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 得到当前的日期字符串
	 */
	public static String getNowStrDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 格式化传入的字符串日期i 2015-09-09 2015-09
	 * 
	 * @throws Exception
	 */
	public static String getFormatStrDate(String dateStr) throws Exception {
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM");
		return new SimpleDateFormat("yyyy-MM").format(longSdf.parse(dateStr));
	}

	/**
	 * 当前年的开始时间， 模式即2012-01-01
	 */
	public static String getCurrentYearStartTime() {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String now = null;
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			now = shortSdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年的结束时间 模式即2012-12-31
	 */
	public static String getCurrentYearEndTime() {
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String now = null;
		try {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			now = longSdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 传入long毫秒数 计算出对应的小时数 保留两位小数
	 */
	public static String queryDoubleByMillSecond(long num) {
		DecimalFormat df = new DecimalFormat("######0.00");
		double s = num / 1000.0 / 60.0 / 60.0;
		return df.format(s);
	}

	/**
	 * 传入double毫秒数 计算出对应的double小时数 保留两位小数
	 */
	public static String queryDoubleHourByMillSecond(Double num) {
		DecimalFormat df = new DecimalFormat("######0.00");
		double s = num / 1000.0 / 60.0 / 60.0;
		return df.format(s);
	}

	/**
	 * 传入double 类型 传出保留两位小数
	 */
	public static String queryHourByDouble(double num) {
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(num);
	}

	/**
	 * java 根据年月获取本月的天数 yearMonth:格式是：yyyy-MM
	 */
	public static long getDaysByYearMonth(String yearMonth) {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
		Calendar rightNow = Calendar.getInstance();
		try {
			rightNow.setTime(simpleDate.parse(yearMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);// 根据年月 获取月份天数
		return days;
	}

	public static List<Date> getMyTestDate() {
		List<Date> list = new ArrayList<>();
		for (int i = 0; i < 388; i++) {
			String day = "2018-07-26 09:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(day);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, i + 1);// 24小时制
			date = cal.getTime();
			list.add(date);
		}
		return list;
	}

	/**
	 * 根据起始 终止时间 分割时间范围
	 * 注意这个时间的格式是yyyy-MM-dd HH-mm
	 */
	public static List<Date> getDateList(String beginDateStr, String endDateStr, int minute) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf.parse(endDateStr);

		List<Date> list = new ArrayList<Date>();
		for (int i = 0; i < 20000; i++) {
			beginDate = new Date(beginDate.getTime() + 60 * minute * 1000);
			if (beginDate.compareTo(endDate) > 0) {
				break;
			}
			list.add(beginDate);
		}
		return list;
	}
	
	
	public static List<Date> getDateListN(String beginDateStr, String endDateStr, int minute) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf1.parse(endDateStr);
		
		List<Date> list = new ArrayList<Date>();
		for (int i = 0; i < 20000; i++) {
			beginDate = new Date(beginDate.getTime() + 60 * minute * 1000);
			if (beginDate.compareTo(endDate) > 0) {
				break;
			}
			list.add(beginDate);
		}
		return list;
	}
	/**
	 * 根据起始 终止时间 分割时间范围
	 * 注意这个时间的格式是yyyy-MM-dd HH-mm
	 */
	public static List<String> getDateListStr(String beginDateStr, String endDateStr, int minute) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf.parse(endDateStr);
		
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20000; i++) {
			beginDate = new Date(beginDate.getTime() + 60 * minute * 1000);
			if (beginDate.compareTo(endDate) > 0) {
				break;
			}
			list.add(sdf.format(beginDate));
		}
		return list;
	}
}
