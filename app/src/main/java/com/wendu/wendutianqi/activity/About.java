package com.wendu.wendutianqi.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.SystemBarUtil;

public class About extends AppCompatActivity {

    private int bgColor,titleColor;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SystemBarUtil.setStatusBarColor(About.this,getResources().getColor(R.color.colorPrimary));
        initView();


    }

    public void initView(){
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.about_CoordinatorLayout);

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
