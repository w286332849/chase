package com.chase.framerwork.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;


public class DateUtil {

	/**
	 * @param yyyy-MM-dd
	 * @return
	 */
	public static final String patternA = "yyyy-MM-dd";
	/**
	 * @param yyyyMMdd
	 * @return
	 */
	public static final String patternB = "yyyyMMdd";
	/**
	 * @param yyyy-MM-dd HH-mm-ss
	 * @return
	 */
	public static final String patternC = "yyyy-MM-dd HH-mm-ss";
	/**
	 * @param yyyy:MM:dd HH:mm:ss
	 * @return
	 */
	public static final String patternD = "yyyy-MM-dd HH:mm:ss";
	/**
	 * @param yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static final String patternE = "yyyy-MM-dd HH:mm";
	
	/**
	 * @param yyyyMMddHHmmss
	 * @return
	 */
	public static final String patternF = "yyyyMMddHHmmss";
	
	private static DateUtil instance;

	public static DateUtil getInstance() {
		if (instance == null) {
			instance = new DateUtil();
		}
		return instance;
	}
	/**
	 * 格式化日期为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public String formateDate(Date date){
		return dateToString(date,patternA);
	}
	/**
	 * @param 取当天日期
	 * @return
	 */
    public Date getDate() {
        return Calendar.getInstance().getTime();
    }
	/**
	 * @param 取指定年月日的日期,格式为yyyy-MM-dd,HH-mm-ss 00-00-00
	 * @return
	 */
    public Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day,0,0,0);
        return cal.getTime();
        
    }
	/**
	 * @param 取指定年,月,日,小时,分,秒的时间
	 * @return
	 */
    public Date getDate(int year,int month,int date,int hour,int mintue,int second)
	{
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.DATE,date);
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,mintue);
		cal.set(Calendar.SECOND,second);
		return cal.getTime();
	}


	/**
	 * @param days=n n为-,则取n天前,n为+,则取n天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public Date getSomeDaysBeforeAfter(Date date, int days){
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, days);
		gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
		return gc.getTime();
	}
	/**
	 * @param 取指定日期年份
	 * @return
	 */
	public int getDateYear(Date date){
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	/**
	 * @param 取指定日期月份
	 * @return
	 */
	public int getDateMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}
	/**
	 * @param 取指定日期日份
	 * @return
	 */
	public int getDateDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}	
	/**
	 * @param 取指定日期小时
	 * @return
	 */
	public int getDateHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * @param 取指定日期分钟
	 * @return
	 */
	public int getDateMinute(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	/**
	 * @param 取指定日期的第二天的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
    public Date getNextDayStartTime(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return getNextDayStart(c.get(Calendar.YEAR),
    			c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
    }


	/**
	 * @param 取指定年,月,日的下一日的开始时间,小时,分,秒为00:00:00
	 * @param 主要是用来取跨月份的日期
	 * @return
	 */
    public Date getNextDayStart(int year, int month, int date) {
    	month = month - 1;
    	boolean lastDayOfMonth = false;
    	boolean lastDayOfYear = false;
    	
    	Calendar time = Calendar.getInstance();
    	time.set(year, month, date, 0, 0, 0);
    	Calendar nextMonthFirstDay = Calendar.getInstance();
    	nextMonthFirstDay.set(year, month + 1, 1, 0, 0, 0);
    	
    	if (time.get(Calendar.DAY_OF_YEAR) + 1 == nextMonthFirstDay.get(Calendar.DAY_OF_YEAR))
    		lastDayOfMonth = true;
    	
    	if (time.get(Calendar.DAY_OF_YEAR) == time.getMaximum(Calendar.DATE))
    		lastDayOfYear = true;
    	
    	time.roll(Calendar.DATE, 1);
    	
    	if (lastDayOfMonth)
    		time.roll(Calendar.MONTH, 1);
    	
    	if (lastDayOfYear)
    		time.roll(Calendar.YEAR, 1);
    	
    	
    	return time.getTime();
    }

	/**
	 * @param 取指定日期的下一日的时间
	 * @return
	 */
    public Date nextDate(Date date)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE,1);
    	return cal.getTime();
    }

	/**
	 * @param 指定日期的下一日的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
    public Date getStartDateNext(Date date)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE,1);
    	cal.set(Calendar.HOUR_OF_DAY,0);
    	cal.set(Calendar.MINUTE,0);
    	cal.set(Calendar.SECOND,0);
    	return cal.getTime();
    }

	/**
	 * @param 指定日期的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
    public Date getStartDateDay(Date date)
    {
    	if(date == null)
    		return null;
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR_OF_DAY,0);
    	cal.set(Calendar.MINUTE,0);
    	cal.set(Calendar.SECOND,0);
    	return cal.getTime();
    }

	/**
	 * @param 指定日期的结束时间,小时,分,秒为23:59:59
	 * @return
	 */
    public Date getEndDateDay(Date date)
    {
    	if(date == null)
    		return null;
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR_OF_DAY,23);
    	cal.set(Calendar.MINUTE,59);
    	cal.set(Calendar.SECOND,59);
    	return cal.getTime();
    }
    
	/**
	 * @param 将指定日期,以指定pattern格式,输出String值
	 * @return
	 */
    public String dateToString(Date date ,String pattern) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		}
    }


	/**
	 * @param 将指定年,月,日的日期转为字符型,格式为yyyy-MM-dd
	 * @return
	 */
    public String dateToString(int year, int month, int day, String pattern) {
    	return dateToString(getDate(year, month, day), pattern);
    }


	/**
	 * @param 将指定字符型日期转为日期型,,格式为指定的pattern
	 * @return
	 */
    public Date stringToDate(String string, String pattern){
        SimpleDateFormat format = (SimpleDateFormat)DateFormat.getDateInstance();
        format.applyPattern(pattern);
        try {
            return format.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

	/**
	 * @param 将指定字符型日期转为日期型,指定格式为yyyy-MM-dd
	 * @return
	 */
    public Date stringToDate(String string){
        return stringToDate(string, patternA);
    }

	/**
	 * 获得两个日期之间间隔的天数
	 * @param startDate 起始年月日
	 * @param endDate 结束年月日
	 * @return int
	 */
	public int getDays(Date startDate, Date endDate) {
		int elapsed = 0;
		Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        Date d1 = cal.getTime();
        
        cal.setTime(endDate);
        Date d2 = cal.getTime();
        
        long daterange = d2.getTime() - d1.getTime();
        long time = 1000*3600*24; //一天的毫秒数
        elapsed = (int) (daterange/time);
		return elapsed;
   }
	/**
	 * @param date
	 * @param startTime 格式为0800，表示上午8点00分
	 * @param endTime格式为2200
	 * @return
	 */
	public boolean isWorkHour(Date date,String startTime,String endTime){//是否是工作时间
		if(StringUtils.isEmpty(startTime))
			startTime = "0800";
		if(StringUtils.isEmpty(endTime))
			endTime = "2200";
		int start = Integer.parseInt(startTime);
		int end = Integer.parseInt(endTime);
		int hour = getDateHour(date);
		int m = getDateMinute(date);
		String hstr = hour<=9?"0"+hour:hour+"";
		String mstr = m<=9?"0"+m:m+"";
		int dateInt = Integer.parseInt(hstr+mstr);
		if(dateInt>=start&&dateInt<=end){
			return true;
		}
		return false;
	}
	//取日期的当前月第一天
	public Date getMonthFirstDay(Date date){
		return getDate(getDateYear(date), getDateMonth(date), 1);
	}
	public void main(String[] args) {
		//System.out.println(DateUtil.dateToString(DateUtil.getDate(), pattern6));
		System.out.println(DateUtil.getInstance().dateToString(getMonthFirstDay(new Date()), patternC));
	}
		
}