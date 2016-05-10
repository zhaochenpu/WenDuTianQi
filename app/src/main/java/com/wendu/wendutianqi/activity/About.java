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

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.StatusBarUtil;

public class About extends AppCompatActivity {

    private int bgColor,titleColor;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarUtil.setStatusBarColor(About.this,R.color.colorPrimary);
        initView();

    }

    public void initView(){
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.about_CoordinatorLayout);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            coordinatorLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onPreDraw() {
                    coordinatorLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    Animator animator = ViewAnimationUtils.createCircularReveal(coordinatorLayout,(coordinatorLayout.getWidth()/2),(coordinatorLayout.getHeight()/2)
                            ,0,coordinatorLayout.getWidth());
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(500);
                    animator.start();
                    return true;
                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setTitle("关于本应用");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
