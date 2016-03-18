package com.wendu.wendutianqi.net;

import android.text.TextUtils;

import com.wendu.wendutianqi.R;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by el on 2016/3/14.
 */
public class MyOkhttp {

    public static OkHttpClient client = new OkHttpClient();


    public static String get(String url){
        try {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> params){
        try {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder,params);
            Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
             Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                 return response.body().string();
            } else {
                 throw new IOException("Unexpected code " + response);
            }
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
        return null;
    }

    private static void addParams(FormBody.Builder builder, Map<String, String> params)
    {
        builder.add("key",R.string.weatherkey);
        for (String key : params.keySet())
        {
            if(!TextUtils.isEmpty(params.get(key))){
                builder.add(key, params.get(key));
            }else{
                builder.add(key,"");
            }
        }
    }



}
