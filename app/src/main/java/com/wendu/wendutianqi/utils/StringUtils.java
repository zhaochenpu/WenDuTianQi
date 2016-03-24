package com.wendu.wendutianqi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by el on 2016/3/22.
 */
public class StringUtils {

    public String jf2time(String jf){
        jf=jf.trim();
        if(jf.length()==12){
            char [] stringArr = jf.toCharArray();
            jf=stringArr[8]+stringArr[9]+":"+stringArr[10]+stringArr[11];
        }
        return  jf;
    }

    public static String getWeekOfDate(String datestring) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = sdf.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getMonthDay(String datestring) {
        String strs[]=datestring.split("-");
        String date="";
        if(strs.length==3){
            try {
                int month=Integer.parseInt(strs[1]);
                int day=Integer.parseInt(strs[2]);
                date=month+"月"+day+"日";
            }catch (Exception e){

            }
        }
        return  date;
    }
}
