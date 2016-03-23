package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.model.HoursWeather;
import com.wendu.wendutianqi.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class DailyCard extends CardView{

    private View view;
    private RecyclerView daily_card_recyclerview;
    private List<DailyForecast> dailyForecast;
    private Hourdapter mAdapter;
    private boolean today,tomorrow,houtian;
    private String  day;
    private Calendar calendar;
    private SimpleDateFormat sdf;

    public DailyCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.daily_card,null);

        addView(view);
        initView();
    }

    public void initView(){
        daily_card_recyclerview=(RecyclerView) view.findViewById(R.id.daily_card_recyclerview);
        calendar = Calendar.getInstance();
    }

    public void setData(List<DailyForecast> dailyForecast){
        this.dailyForecast=dailyForecast;
        SimpleDateFormat sdf=new SimpleDateFormat();
        daily_card_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        daily_card_recyclerview.setAdapter(mAdapter = new Hourdapter());
    }



    class Hourdapter extends RecyclerView.Adapter<Hourdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.daily_forecast_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            try {
                Date date=sdf.parse(dailyForecast.get(position).getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount()
        {
            return dailyForecast.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView daily_forecats_date,daily_forecats_week,daily_forecats_day,daily_forecats_night,daily_forecats_fengxiang,daily_forecats_fengli;

            public MyViewHolder(View view)
            {
                super(view);
                daily_forecats_date = (TextView) view.findViewById(R.id.daily_forecats_date);
                daily_forecats_week = (TextView) view.findViewById(R.id.daily_forecats_week);
                daily_forecats_day = (TextView) view.findViewById(R.id.daily_forecats_day);
                daily_forecats_night = (TextView) view.findViewById(R.id.daily_forecats_night);
                daily_forecats_fengxiang = (TextView) view.findViewById(R.id.daily_forecats_fengxiang);
                daily_forecats_fengli = (TextView) view.findViewById(R.id.daily_forecats_fengli);
            }
        }
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }


}
