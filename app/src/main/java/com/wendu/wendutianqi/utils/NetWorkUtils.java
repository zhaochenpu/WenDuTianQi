package com.wendu.wendutianqi.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by el on 2016/3/14.
 */
public class NetWorkUtils {
    /**
     * 判断网络的链接状态
     * */
    public static boolean isConnected(Context mContext){

        ConnectivityManager manager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        //返回网络的链接状态  true 链接,false 未链接
        return (info != null && info.isAvailable() && info.isConnected());

    }

    /**
     * 判断网络的链接类型
     * */
    public static String connectType(Context mContext){

        ConnectivityManager conMan = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        //gprs
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();

        // WIFI
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();

        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            //手机网络
            return "mobile";

        } else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            //wifi网络
            return "wifi";
        }else{
            //未知网络
            return "other";
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
