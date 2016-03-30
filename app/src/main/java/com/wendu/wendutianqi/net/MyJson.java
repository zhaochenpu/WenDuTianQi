package com.wendu.wendutianqi.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wendu.wendutianqi.model.WeatherFirst;
import com.wendu.wendutianqi.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by el on 2016/3/18.
 */
public class MyJson {

//    public Gson gson=new Gson();

//    public WeatherFirst result(String json){
//        WeatherFirst weatherFirst=gson.fromJson(json,WeatherFirst.class);
//        if(weatherFirst!=null&&!TextUtils.isEmpty(weatherFirst.status)){
//            if(TextUtils.equals("ok",weatherFirst.status)||TextUtils.equals("unknown city",weatherFirst.status)){
//                return  weatherFirst;
//            }
//        }
//        return  null;
//    }




    public static String getString(String content, String content_title) {
        JSONObject jsonObject;
        try {
            if(!TextUtils.isEmpty(content)) {
                jsonObject = new JSONObject(content);
                String result = jsonObject.getString(content_title);
                return result;
            }else{
                return null;
            }
        } catch (JSONException e) {
            return null;

        }
    }

    public static String getString(JSONObject jsonObjectt, String content_title) {
        JSONObject jsonObjectt2 = jsonObjectt;
        try {
            if(!TextUtils.isEmpty(content_title)) {
            String result = jsonObjectt2.getString(content_title);
            return result;
            }else{
                return null;
            }
        } catch (JSONException e) {
            return null;

        }
    }
}
