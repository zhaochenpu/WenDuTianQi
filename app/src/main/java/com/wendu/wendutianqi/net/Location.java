package com.wendu.wendutianqi.net;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.ToastUtil;

/**
 * Created by el on 2016/3/15.
 */
public class Location {

    public static void initLocation(LocationClient mLocationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static boolean result(Context context, BDLocation location,CoordinatorLayout coordinatorLayout){

        if(context!=null&&location!=null) {
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("time : ");
//            sb.append(location.getTime());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                ToastUtil.showShort(context,"gps定位成功");
                SnackbarUtil.showShort(coordinatorLayout," gps定位成功 ^_^");
                return  true;
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                ToastUtil.showShort(context,"网络定位成功");
                SnackbarUtil.showShort(coordinatorLayout," 网络定位成功 ^_^");
                return  true;
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                ToastUtil.showShort(context,"离线定位成功");
                SnackbarUtil.showShort(coordinatorLayout," 离线定位成功 ");
                return  true;
            } else if (location.getLocType() == BDLocation.TypeServerError) {
//                ToastUtil.showShort(context, "服务端网络定位失败");
                SnackbarUtil.showShort(coordinatorLayout," 服务端网络定位失败 ( ⊙ o ⊙ )");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                ToastUtil.showShort(context, "网络不通导致定位失败，请检查网络是否通畅");
                SnackbarUtil.showShort(coordinatorLayout,"网络不通导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                ToastUtil.showShort(context, "无法获取有效定位依据导致定位失败，请检查网络和本应用定位权限");
                SnackbarUtil.showShort(coordinatorLayout,"无法获取有效定位依据导致定位失败，请检查网络和本应用定位权限");
            }
        }
        return  false;
    }
}
