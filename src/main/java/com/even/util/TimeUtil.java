package com.even.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private static String TAG = "TimeUtil";

    public static String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static String FORMAT_MONTH_DAY = "MM-dd";
    public static String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * 根据格式获取时间
     * @param date                              需要转换的日期（Date类型）
     * @param format                            根据的格式（可以选择这个类中定义的格式）
     * @return
     */
    public static String getTimeFormatted(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String formattedTime = simpleDateFormat.format(date);
        return formattedTime;
    }

    /**
     * 根据格式获取时间
     * @param date                              需要转换的日期（时间戳 单位秒）
     * @param format                            根据的格式（可以选择这个类中定义的格式）
     * @return
     */
    public static String getTimeFormatted(long date, String format){
        return getTimeFormatted(new Date(date * 1000), format);
    }

    /**
     * 自定义时间格式
     * @param date                              需要转换的日期（Date类型）
     * @param visibleField                      可见字段 长度必须是6 设置true为对应位置字段可见 反之不可见
     * @param customString                      为可见字段后面设置单位
     * @return
     */
    public static String getTimeCustomFormatted(Date date, boolean[] visibleField, String[] customString){

        if (visibleField == null || visibleField.length != 6){
            System.out.println("get time custom formatted, field array length is not correct");
//            Log.e(TAG, "get time custom formatted, field array length is not correct");
            return null;
        }

        if (customString == null){
            System.out.println("get time custom formatted, str array length is null");
//            Log.w(TAG, "get time custom formatted, str array length is null");
        }else{
            if (customString.length != 6){
                System.out.println("get time custom formatted, str array length is not 6");
                return null;
            }
        }

        String format = "";
        String[] fields = {"yyyy", "MM", "dd", "HH", "mm", "ss"};
        for (int i = 0; i < 6; i++){
            if (visibleField[i]){
                format += fields[i];
                if (customString != null && customString[i] != null){
                    format += customString[i];
                }else{
                    format += "-";
                }
            }
        }

        return getTimeFormatted(date, format);
    }

    /**
     * 自定义时间格式
     * @param date                              需要转换的日期（时间戳 单位秒）
     * @param visibleField                      可见字段 长度必须是6 设置true为对应位置字段可见 反之不可见
     * @param customString                      为可见字段后面设置单位
     * @return
     */
    public static String getTimeCustomFormatted(long date, boolean[] visibleField, String[] customString){
        return getTimeCustomFormatted(new Date(date * 1000), visibleField, customString);
    }

    /**
     * 获取中文字符的星期几
     * @param date                              需要转换的日期（Date类）
     * @return
     */
    public static String getDayOfWeekInChinese(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String result;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case Calendar.MONDAY:
                result = "一";
                break;
            case Calendar.TUESDAY:
                result = "二";
                break;
            case Calendar.WEDNESDAY:
                result = "三";
                break;
            case Calendar.THURSDAY:
                result = "四";
                break;
            case Calendar.FRIDAY:
                result = "五";
                break;
            case Calendar.SATURDAY:
                result = "六";
                break;
            case Calendar.SUNDAY:
                result = "日";
                break;
            default:
                System.out.println("get day of week, out of range");
//                Log.e(TAG, "get day of week, out of range");
                result = "";
                break;
        }
        return result;
    }

    /**
     * 获取中文字符的星期几
     * @param date                              需要转换的日期（时间戳 单位秒）
     * @return
     */
    public static String getDayOfWeekInChinese(long date){
        return getDayOfWeekInChinese(new Date(date * 1000));
    }


    /**
     * 将时间转换为时间戳
     * @param dateStr 日期
     * @param format 传入日期的格式
     * @return
     */
    public static long dateToStamp(String dateStr,String format){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(dateStr);
            long ts = date.getTime();
            return ts/1000;
        }catch (ParseException e){
            System.out.println("date to stamp occur error");
//            Log.e(TAG, "date to stamp occur error");
            return 0;
        }
    }

}
