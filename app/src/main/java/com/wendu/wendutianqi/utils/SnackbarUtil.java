package com.wendu.wendutianqi.utils;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.wendu.wendutianqi.R;

/**
 * Created by noname on 2016/3/20.
 */
public class SnackbarUtil {


    public static void showShort(CoordinatorLayout coordinatorLayout,String message){
        Snackbar snackbar =Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_SHORT);

        setSnackbarMessageTextColor(snackbar, Color.parseColor("#FFFFFF"));
        snackbar.show();
    }

    public static void showLong(CoordinatorLayout coordinatorLayout,String message){
        Snackbar snackbar =Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_LONG);

        setSnackbarMessageTextColor(snackbar, Color.parseColor("#FFFFFF"));
        snackbar.show();
    }

    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

}
