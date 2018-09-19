package com.mmall.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Administrator on 2018/9/14.
 */
public class DateTimeUtil {

    //使用joda-time包对时间进行转换 str->date   date->str

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public  static DateTime strToDate(String dateTimeStr)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime;
    }
    public static  String dateToStr(Date date)
    {
        if(date == null)
        {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }


    public  static DateTime strToDate(String dateTimeStr,String dateTimeFormat)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateTimeFormat);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime;
    }
    public static  String dateToStr(Date date,String dateTimeFormat)
    {
        if(date == null)
        {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(dateTimeFormat);
    }
}
