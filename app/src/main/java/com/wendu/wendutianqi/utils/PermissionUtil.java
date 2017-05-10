package com.wendu.wendutianqi.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查权限的工具类
 *
 *
 * */
public class PermissionUtil {
    private final Context mContext;
    public static final int REQUEST_PERMISSION_CODE = 1;

    public PermissionUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean isNeedPermission(String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public String[] getLacksPermissions(String[] permissions){

        if(permissions == null || permissions.length == 0){
            return null;
        }
        List<String> lacks = new ArrayList<String>();

        for(String permisson : permissions){

            if(this.lacksPermission(permisson)){

                lacks.add(permisson);
            }
        }

        String[] lacksArray = new String[lacks.size()];
        for(int i = 0; i < lacks.size(); i++){
            lacksArray[i] = lacks.get(i);
        }

        return lacksArray;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }


    public boolean isOwnPermissionOfPhoneReadState(){

        if(lacksPermission(Manifest.permission.READ_PHONE_STATE)){

            return false;
        }
        return true;
    }

}
