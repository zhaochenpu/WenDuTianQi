package com.wendu.wendutianqi.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wendu.wendutianqi.model.WeatherFirst;
import com.wendu.wendutianqi.utils.ToastUtil;

/**
 * Created by el on 2016/3/18.
 */
public class MyJson {

    public Gson gson=new Gson();

    public WeatherFirst result(String json){
        WeatherFirst weatherFirst=gson.fromJson(json,WeatherFirst.class);
        if(weatherFirst!=null&&!TextUtils.isEmpty(weatherFirst.status)){
            if(TextUtils.equals("ok",weatherFirst.status)||TextUtils.equals("unknown city",weatherFirst.status)){
                return  weatherFirst;
            }
        }
        return  null;
    }

}
