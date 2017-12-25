package com.reggie.note4ppt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 24073 on 2017/12/12.
 */

public class TimeMillisToDateUtils {
    public static String TimeToDate(long msec1){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd");//将毫秒级long值转换成日期格式
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(msec1);
        String dateStr = dateformat.format(gc.getTime());
        return dateStr;
    }

    public static String TimeToDate2(long msec1){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月");//将毫秒级long值转换成日期格式
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(msec1);
        String dateStr = dateformat.format(gc.getTime());
        return dateStr;
    }
}
