package com.zbjdl.common.wx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.utils.StringUtils;

/**
 * 日期类型工具类
 * 
 * 
 * @since 2014-9-7 00:17:17
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 格式化时间
	 */
	public static String format(Date date , String pattern){
		if(date == null || StringUtils.isBlank(pattern)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间的年月日格式
	 */
	public static Date getCurrentDate(){
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return parse(sdf.format(d));
	}
	
	
	/**
	 * 将字符串转换成日期类型，日期类型：yyyyMMdd
	 */
	public static Date parse(String source) {
		return parse(source, "yyyyMMdd");
	}
	
	/**
	 * 将字符串转换成日期类型
	 */
	public static Date parse(String source, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			logger.error("格式化日期类型失败", e);
		}
		return null;
	}

	/**
	 * 比较两天之间相差几天
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			logger.error("日期转化失败" , e);
		}
		return -1;//转换失败没有可能，但是如果发生，在页面上显示-1天
	}
	
	/**
	 * 比较两日期类型之间的差距：
	 * 
	 * 最多两级时间差描述
	 */
	public static String between(Date startTime , Date endTime){
		if(startTime == null || endTime == null){
			return "";
		}
		Long time = (endTime.getTime() - startTime.getTime()) / 1000;
		if(time == null || time <= 0){
			return "1分钟";
		}
		//计算分
		Long m = time % 3600  / 60;
		//计算小时
		Long h = time % 86400 / 3600;
		//计算天
		Long d = time / 86400;
		StringBuffer bs = new StringBuffer();
		boolean sw = false;
		if(d > 0){
			sw = true;
			bs.append(d).append("天");
		}
		if(h > 0){
			bs.append(h).append("小时");
			if(sw){
				return bs.toString();
			}
			sw = true;
		}
		if(m > 0){
			bs.append(m).append("分钟");
			if(sw){
				return bs.toString();
			}
			sw = true;
		} else {
			bs.append("1分钟");
		}
		return bs.toString();
	}
	
	/**
	 * 比较两日期类型之间的差距：
	 * 
	 * 最多两级时间差描述
	 */
	public static String betweenTwoLevel(Date startTime , Date endTime){
		if(startTime == null || endTime == null){
			return "";
		}
		Long time = (endTime.getTime() - startTime.getTime()) / 1000;
		if(time == null || time <= 0){
			return "";
		}
		//计算秒
		Long s = time % 60;
		//计算分
		Long m = time % 3600  / 60;
		//计算小时
		Long h = time % 86400 / 3600;
		//计算天
		Long d = time / 86400;
		StringBuffer bs = new StringBuffer();
		boolean sw = false;
		if(d > 0){
			sw = true;
			bs.append(d).append("天");
		}
		if(h > 0){
			bs.append(h).append("小时");
			if(sw){
				return bs.toString();
			}
			sw = true;
		}
		if(m > 0){
			bs.append(m).append("分");
			if(sw){
				return bs.toString();
			}
			sw = true;
		} 
		if(s > 0){
			bs.append(s).append("秒");
			if(sw){
				return bs.toString();
			}
			sw = true;
		}
		return bs.toString();
	}
	
	
	/**
	 * 初始化特性时间
	 * 如果距当前时间小于一小时则返回距离当前分钟数
	 * 如果距离当前时间大于一小时小于一天则返回小时数
	 * 如果距离当前时间大于一天则显示日期
	 * 
	 * @param nowDate
	 * @param endDate
	 * @return
	 */
	public static String initSpecialTime(Date startDate,Date nowDate){
		if(nowDate == null){
			nowDate = new Date();
		}
		if(startDate == null){
			return "";
		}
		Long time = (nowDate.getTime() - startDate.getTime()) / 1000;//计算截至时间距当前日期的秒数
		if(time < 0){//当前时间小于过去的一个时间点，则交换时间，继续执行程序
			time = Math.abs(time);
		}
		if(time < 1*60){//如果小于1分钟，则显示秒数
			return String.valueOf(time+"秒钟前");
		}else if(time < 1*60*60){//如果小于1小时对应的秒数，则显示分钟数
			int minute = new Long(time / 60).intValue();
			return String.valueOf(minute+"分钟前");
		} else if(time >= 1*60*60 && time < 1*60*60*24){//大于一小时且小于1天则显示小时数
			int hour = new Long(time / 60 /60).intValue();
			return String.valueOf(hour+"小时前");
		} else{//如果大于1天则直接显示日期
			return format(startDate,"yyyy-MM-dd");
		}
	}
	
	/**
	 * 获取startDate日期后N个自然月后的日期
	 * 1月15日在3个月后是4月14日
	 * @param startDate
	 * @param months
	 */
	public static Date getMonthsDate(Date startDate,int months){
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MONTH, months);
		c.add(Calendar.DATE,-1);
		return c.getTime();
	}
	
	/**
	 * 获取当前日期之前或者是之后的某一天
	 * @param startDate   开始时间
	 * @param days        两个日期之间的间隔
	 * @param flag        true 之前，false 之后
	 * @return
	 */
	public static Date getBeforeOrLaterDate(Date startDate,int days,boolean flag){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if(flag){
			//求得startDate之前的某天
			calendar.add(Calendar.DATE, 0-days);
		}else{
			//求得startDate之后的某天
			calendar.add(Calendar.DATE,days);
		}
		return calendar.getTime();
	}
	
	
	/**
	 * 获取当前日期之前或者是之后的某个月份的日期
	 * @param startDate     开始时间
	 * @param months        两个日期之间的间隔月份
	 * @param flag          true 之前，false 之后
	 * @return
	 */
	public static Date getBeforeOrLaterMonth(Date startDate,int months,boolean flag){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if(flag){
			//求得startDate之前的某月
			calendar.add(Calendar.MONTH, 0-months);
		}else{
			//求得startDate之后的某月
			calendar.add(Calendar.MONTH,months);
		}
		return calendar.getTime();
	}
	
	
	/**
	 * 获取当前时间之前或者是之后的某个的时间点
	 * @param startDate     开始时间
	 * @param hours         两个日期之间的间隔小时
	 * @param flag          true 之前，false 之后
	 * @return
	 */
	public static Date getBeforeOrLaterHours(Date startDate,int hours,boolean flag){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if(flag){
			//求得startDate之前的某个时间点
			calendar.add(Calendar.HOUR, 0-hours);
		}else{
			//求得startDate之后的某个时间点
			calendar.add(Calendar.HOUR,hours);
		}
		return calendar.getTime();
	}
	
	
	
	/**
	 * 根据当前日期获取当月的1号的日期
	 * @return
	 */
	public static Date getMonthFirstDate(){
		Calendar c = Calendar.getInstance();
		// 这是已知的日期
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);
		// 1号的日期
		return c.getTime();
	}

	/**
	 * 比较两个日期的间隔是否等于指定的时间间隔
	 * @param startDate    起始日期
	 * @param endDate      结束日期
	 * @param days         时间间隔
	 * @return             true等于指定时间间隔，false不等于指定的时间间隔
	 */
	public static boolean compareDateEqual(Date startDate , Date endDate , int days){
		boolean flag = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, days);//起始日期加上时间间隔
		flag = org.apache.commons.lang.time.DateUtils.isSameDay(calendar.getTime(),endDate);//判断两个日期是否是同一天
		return flag;
	}
}
