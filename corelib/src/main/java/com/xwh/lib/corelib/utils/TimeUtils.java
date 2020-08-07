package com.xwh.lib.corelib.utils;


import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.xwh.lib.corelib.WHUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import bodyfast.zerofasting.fastic.corelib.R;

public class TimeUtils {

    public static final String format = "yyyy/MM/dd";

    /**
     * @return 获取当前时间字符串
     */
    public static String getCurTime() {
        return getStrTime(getCurTimeStamp() + "");
    }

    /**
     * @return 获取当前时间字符串
     */
    public static String getCurTime(String form) {
        return getStrTime(getCurTimeStamp() + "", form);
    }

    /**
     * @return 获取当前时间戳
     */
    public static Long getCurTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 时间戳转为时间(年月日，时分秒)
     *
     * @param cc_time 时间戳
     * @return
     */

    public static String getStrTime(String cc_time) {
        return getStrTime(cc_time, WHUtil.getContext().getString(R.string.format_date_no_hour));
    }

    /**
     * 时间戳转为时间(年月日，时分秒)
     *
     * @param cc_time 时间戳
     * @param format  时间格式
     * @return
     */

    public static String getStrTime(String cc_time, String format) {
        long lcc_time = Long.valueOf(cc_time);
        return getStrTime(lcc_time, format);
    }

    public static String getStrTime(Long time, String format) {
        return DateFormat.format(format, new Date(time).getTime()) + "";
    }

    /* January 一月 Jan.

        February 二月 Feb.

        March 三月 Mar.

        April 四月 Apr.

        May 五月 May

        June 六月 Jun.

        July 七月 Jul.

        August 八月 Aug.

        September 九月 Sept.

        October 十月 Oct.

        November 十一月 Nov.

        December 十二月 Dec.
    */
    public static String replaceMonth(String time) {
        if (TextUtils.isEmpty(time)) return time;
        time = time.replace("January", "Jan.");
        time = time.replace("February", "Feb.");
        time = time.replace("March", "Mar.");
        time = time.replace("April", "Apr.");
        time = time.replace("May", "May.");
        time = time.replace("June", "Jun.");
        time = time.replace("July", "Jul.");
        time = time.replace("August", "Aug.");
        time = time.replace("September", "Sep.");
        time = time.replace("October", "Oct.");
        time = time.replace("November", "Nov.");
        time = time.replace("December", "Dec.");
        return time;

    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */

    public static long getTimeStamp(String timeStr, String format) {
        if (timeStr == null) return 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            Log.e("错误", "  " + timeStr + "  " + format);
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据输入字符串时间 判断距离现在时间的差值
     *
     * @param time 字符串时间
     * @return 12:21:00
     */
    public static String getTimeCountdown(String time) {
        long timeStamp = getTimeStamp(time, WHUtil.getContext().getString(R.string.format_date));

        Long curTimeStamp = getCurTimeStamp();
        long time1 = curTimeStamp - timeStamp;

        return getHourTimeFromTimeStamp(time1);
    }

    /**
     * 根据输入字符串时间 判断距离现在时间的差值
     *
     * @param time 时间搓
     * @return 12:21:00
     */
    public static String getTimeCountdown(Long time) {
        return getHourTimeFromTimeStamp(getCurTimeStamp() - time);
    }

    /**
     * 将时间搓转换成小时
     *
     * @param time
     * @return 返回格式 00:00:00
     */
    public static String getHourTimeFromTimeStamp(Long time) {
        DecimalFormat df = new DecimalFormat("00");
        String hourTime = "";
        if (time < 0) {
            hourTime = "- " + hourTime;
        }
        long abs = Math.abs(time / 1000);

        if (abs > 0) {
            if (abs <= 59) {
                return hourTime + "00:00:" + df.format(abs);
            } else if (abs > 59 && (60 * 60 - 1) > abs) {
                short seconds = (short) (abs % 60);
                short minutes = (short) (abs / 60 % 60);
                return hourTime + "00:" + df.format(minutes) + ":" + df.format(seconds);
            } else {
                short seconds = (short) (abs % 60);
                short minutes = (short) (abs / 60 % 60);
                short hour = (short) (abs / 60 / 60);

                if (hour > 10) {
                    return hourTime + hour + ":" + df.format(minutes) + ":" + df.format(seconds);
                } else {
                    return hourTime + "0" + hour + ":" + df.format(minutes) + ":" + df.format(seconds);
                }
            }
        } else {
            return "00:00:00";
        }

    }

    /**
     * 获取当前时间几个小时后的时间 hour<0 代表获取几个小时之前的时间支付传
     *
     * @param hour
     * @return 2020年3月12日
     */
    public static String beforeAfterDate(int hour) {

        long nowTime = System.currentTimeMillis();

        long changeTimes = hour * 60 * 60 * 1000L;

        return getStrTime(String.valueOf(nowTime + changeTimes));

    }

    /**
     * 获取给定时间几个小时后的时间 hour<0 代表获取几个小时之前的时间支付传
     *
     * @param curtime 原来的时间字符串
     * @param hour    时间差（h）
     * @return 2020年3月12日 12:12:12
     */
    public static String beforeAfterDate(String curtime, int hour) {
        String string = WHUtil.getContext().getString(R.string.format_date);
        long nowTime = getTimeStamp(curtime, string);
        long changeTimes = hour * 60 * 60 * 1000L;
        return getStrTime(String.valueOf(nowTime + changeTimes), string);

    }


    /**
     * 根据日期获取星期数
     *
     * @param dateTime
     * @return 1 2 3 4 5 6 7
     */
    private static int getDayofWeek(String dateTime) {

        Calendar cal = Calendar.getInstance();
        if (dateTime.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(WHUtil.getContext().getString(R.string.format_date_no_hour), Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 根据日期获取星期几的字符串
     *
     * @param dateTime
     * @return 星期几
     */
    public static String Week(String dateTime) {
        String week = "";
        switch (getDayofWeek(dateTime)) {
            case 1:
                week = WHUtil.getContext().getString(R.string.sunday);
                break;
            case 2:
                week = WHUtil.getContext().getString(R.string.monday);
                break;
            case 3:
                week = WHUtil.getContext().getString(R.string.tuesday);
                break;
            case 4:
                week = WHUtil.getContext().getString(R.string.wednesdays);
                break;
            case 5:
                week = WHUtil.getContext().getString(R.string.thursdays);
                break;
            case 6:
                week = WHUtil.getContext().getString(R.string.friday);
                break;
            case 7:
                week = WHUtil.getContext().getString(R.string.saturday);
                break;
        }
        return week;
    }


    /**
     * 将小时转换成秒
     *
     * @param hourTime 小时（hh:mm:ss）
     * @return 秒数
     */
    public static Long getHourTimeStamp(String hourTime) {
        if (hourTime == null || hourTime.length() == 0) return 0L;
        try {
            if (hourTime.contains(":")) {
                String[] split = hourTime.split(":");
                if (split.length > 0) {
                    short hour = Short.valueOf(split[0]);
                    short minutes = Short.valueOf(split[1]);
                    short seconds = Short.valueOf(split[2]);
                    return (hour * 60 * 60L + minutes * 60 + seconds);
                }
            } else {
                return Long.valueOf(hourTime);
            }
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 获取时间段在某天的 分钟段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param curDay    日期
     * @return 开始时间到结束时间的过程经过日期的分钟段
     */
    public static int[] getUseTimes(String startTime, String endTime, String curDay) {
        long startTimeStamp = getTimeStamp(startTime, WHUtil.getString(R.string.format_date));
        long endTimeStamp = getTimeStamp(endTime, WHUtil.getString(R.string.format_date));
        long curDayStamp = getTimeStamp(curDay, WHUtil.getString(R.string.format_date));            // 给定日期的日期的0点
        long curDayEndStamp = curDayStamp + (24 * 60 * 60 * 1000);                                  // 给定日期的日期的24点
        // 结束时间小于给定日期的0点
        if (endTimeStamp < curDayStamp) {
            return null;
        }
        //开始时间大于给定日期的24点
        if (startTimeStamp > curDayEndStamp) {
            return null;
        }
        // 开始日期在0点或之前
        if (startTimeStamp <= curDayStamp) {
            //结束时间在24点或之后
            if (endTimeStamp >= curDayEndStamp) {
                return new int[]{0, 24 * 60};
            } else {
                return new int[]{0, (int) ((endTimeStamp - curDayStamp) / 1000 / 60)};
            }
        }
        // 开始日期在0点之后
        else {
            //结束时间在24点之前
            if (endTimeStamp < curDayEndStamp) {
                return new int[]{(int) ((startTimeStamp - curDayStamp) / 1000 / 60), (int) ((endTimeStamp - curDayStamp) / 1000 / 60)};
            } else {
                return new int[]{(int) ((startTimeStamp - curDayStamp) / 1000 / 60), 24 * 60};
            }
        }
    }

    public static long getMinutesFromTime1ToTime2(String time1, String time2) {
        long timeStamp = getTimeStamp(time1, WHUtil.getString(R.string.format_date));
        long timeStamp2 = getTimeStamp(time2, WHUtil.getString(R.string.format_date));
        return getMinutesFromTime1ToTime2(timeStamp, timeStamp2);

    }

    public static long getMinutesFromTime1ToTime2(long time1, long time2) {
        return (time2 - time1) / 1000 / 60;
    }


    /**
     * 将日期进行拆分成 日期 小时  分钟 秒; 输入格式必须是(xxxx hh:mm:ss   xxxx不包含: )
     *
     * @param dataString
     * @return
     */
    public static String[] getTimeFromDateString(String dataString) {
        String[] dataList = null;
        if (dataString != null && dataString.length() > 10) {
            if (dataString.contains(":")) {
                String[] split = dataString.split(":");
                if (split != null && split.length == 3) {
                    String substring = split[0].substring(0, split[0].length() - 2);
                    String dayString = substring.endsWith(" ") ? substring.substring(0, substring.length() - 1) : substring;
                    String substring1 = split[0].substring(split[0].length() - 2, split[0].length());
                    String hourString = substring1.startsWith(" ") ? substring1.substring(1, substring1.length()) : substring1;

                    if (dataList == null)
                        dataList = new String[]{dayString, hourString, split[1], split[2]};

                }

            }
        }
        return dataList;
    }

    public static Calendar getTimeDra(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar calendar = null;
        try {
            Date date = format.parse(dateStr);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


    /**
     * 时间字符串比较
     *
     * @param time1 时间字符串1
     * @param time2 时间字符串2
     * @return -1  time1小于time2， 1 大于 0 字符串格式错误;
     */
    public static int getDateCompare(String time1, String time2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(WHUtil.getString(R.string.format_date));
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            long time = (d1.getTime() - d2.getTime());
            if (time >= 0) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return 0;
        }

    }


    /**
     * 判断两个日期是否是同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDay(String time1, String time2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(WHUtil.getString(R.string.format_date));
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            int i = 0;
            if (d1.getYear() == d2.getYear()) {
                return true;

            }
        } catch (Exception e) {

        }
        return false;
    }


    public static long getOneDayMS() {
        return 1000 * 60 * 60 * 24L;
    }

    public static int year(Long curTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(curTime));

        return cal.get(Calendar.YEAR);
    }

    public static int day(Long curTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(curTime));

        return cal.get(Calendar.DATE);

    }

    public static int month(Long curTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(curTime));
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int hour(Long curTime) {
        Date date = new Date(curTime);
        return date.getHours();
    }

    public static int minutes(Long curTime) {
        Date date = new Date(curTime);
        return date.getMinutes();
    }

    public static int seconds(Long curTime) {
        Date date = new Date(curTime);
        return date.getSeconds();
    }

    public static Calendar getCalendar(Long curTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(curTime));
        return cal;
    }


    /**
     * 将时间戳  转换为该日期的  零点零分的时间戳
     *
     * @param curTime 时间戳
     * @return 时间戳
     */
    public static long getUnifyTimeFromDay(long curTime) {
        long timeStamp = getTimeStamp(year(curTime) + "/" + month(curTime) + "/" + day(curTime) + " 00:00:00", "yyyy/MM/dd HH:mm:ss");
        return timeStamp;
    }

    /**
     * 将时间 转换为该日期的  零点零分的时间戳
     *
     * @param timeStrng 时间
     * @return 时间戳
     */
    public static long getUnifyTimeFromDay(String timeStrng) {

        return getUnifyTimeFromDay(getTimeStamp(timeStrng, WHUtil.getString(R.string.format_date)));

    }


}
