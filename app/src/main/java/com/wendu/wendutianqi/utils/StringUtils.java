package com.wendu.wendutianqi.utils;

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
}
