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
import com.wendu.wendutianqi.model.HoursWeather;
import com.wendu.wendutianqi.utils.LogUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class HoursCard extends CardView{

    private View view;
    private RecyclerView hour_card_recyclerview;
    private List<HoursWeather> hoursWeathers;
    private Hourdapter mAdapter;
    private boolean today,tomorrow,houtian;
    private String  day;
    private Calendar calendar;

    public HoursCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.hours_card,null);

        addView(view);
        initView();
    }

    public void initView(){
        hour_card_recyclerview=(RecyclerView) view.findViewById(R.id.hour_card_recyclerview);
        calendar = Calendar.getInstance();
    }

    public void setData(List<HoursWeather> hoursWeathers){
        this.hoursWeathers=hoursWeathers;

        hour_card_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        hour_card_recyclerview.setAdapter(mAdapter = new Hourdapter());
    }



    class Hourdapter extends RecyclerView.Adapter<Hourdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.hour_weather_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            String time=jf2time(hoursWeathers.get(position).getJf());

            if(!today&&TextUtils.equals(day,calendar.get(Calendar.DAY_OF_MONTH)+"")){
                holder.hour_item_when.setText("今天");
                holder.hour_item_when.setVisibility(VISIBLE);
                today=true;
            }else if(today&&!tomorrow){
                holder.hour_item_when.setText("明天");
                holder.hour_item_when.setVisibility(VISIBLE);
                tomorrow=true;
            }else{
                holder.hour_item_when.setVisibility(GONE);
            }

            holder.hour_item_time.setText(time);
            holder.hour_item_weather.setText(hoursWeathers.get(position).getJa());
            holder.hour_item_wind.setText(hoursWeathers.get(position).getJc()+" "+hoursWeathers.get(position).getJd());
        }

        @Override
        public int getItemCount()
        {
            return hoursWeathers.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView hour_item_when,hour_item_time,hour_item_weather,hour_item_wind;

            public MyViewHolder(View view)
            {
                super(view);
                hour_item_when = (TextView) view.findViewById(R.id.hour_item_when);
                hour_item_time = (TextView) view.findViewById(R.id.hour_item_time);
                hour_item_weather = (TextView) view.findViewById(R.id.hour_item_weather);
                hour_item_wind = (TextView) view.findViewById(R.id.hour_item_wind);
            }
        }
    }


    public String jf2time(String jf){
        String jf2=jf.trim();

        if(jf.length()==12){
            jf=jf2.substring(jf2.length()-4,jf2.length()-2)+":"+"00";
        }
        if(!today||!tomorrow){
            day=jf2.substring(jf2.length()-6,jf2.length()-4);
            LogUtil.e(day);
        }
        return  jf;
    }

}
