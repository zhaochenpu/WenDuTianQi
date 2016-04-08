package com.wendu.wendutianqi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.utils.SystemBarUtil;

import java.util.List;

/**
 * Created by el on 2016/4/8.
 */
public class DailyActivity extends AppCompatActivity {

    private List<DailyForecast> dailyForecast;
    private String place;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity);

        SystemBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));

        Bundle bundle = this.getIntent().getExtras();
        dailyForecast= (List<DailyForecast>) bundle.getSerializable("dailyForecast");
        place=bundle.getString("place");

        initView();

    }

    public void initView(){

        toolbar=(Toolbar)findViewById(R.id.daily_a_toolbar);
        toolbar.setTitle(place);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager=(ViewPager) findViewById(R.id.daily_a_viewpage);


        tabLayout=(TabLayout) findViewById(R.id.daily_a_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

}
