package com.wendu.wendutianqi.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.StatusBarUtil;

public class About extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private WebView about_webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();

    }

    public void initView(){
//        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.about_CoordinatorLayout);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            coordinatorLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//                @Override
//                public boolean onPreDraw() {
//                    coordinatorLayout.getViewTreeObserver().removeOnPreDrawListener(this);
//                    Animator animator = ViewAnimationUtils.createCircularReveal(coordinatorLayout,(coordinatorLayout.getWidth()/2),(coordinatorLayout.getHeight()/2)
//                            ,0,coordinatorLayout.getWidth());
//                    animator.setInterpolator(new AccelerateInterpolator());
//                    animator.setDuration(500);
//                    animator.start();
//                    return true;
//                }
//            });
//        }
//
//        about_webview=(WebView) findViewById(R.id.about_webview);
//        about_webview.loadUrl("http://zhaochenpu.github.io/2016/05/15/WenDu/");
    }

}
