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
import android.widget.Button;
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
    private List<HoursWeather> hoursWeathers,hoursWeathersall;
    private Hourdapter mAdapter;
    private boolean today,tomorrow,houtian;
    private String  day;
    private Calendar calendar;
    private Button hour_card_more;
    private boolean hoursmore;
    private int todayposition,tomorrowposition,houtianposition;
    private int downX;
    private int downY;
    private int mTouchSlop;

    public HoursCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.hours_card,null);

        addView(view);
        initView();
    }

    public void initView(){
        hour_card_recyclerview=(RecyclerView) view.findViewById(R.id.hour_card_recyclerview);
        calendar = Calendar.getInstance();
        hour_card_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        hour_card_more=(Button)  view.findViewById(R.id.hour_card_more);
        hour_card_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursmore=!hoursmore;
                setData(hoursWeathersall,hoursmore);
                if(hoursmore){
                    hour_card_more.setText("收起列表↑");
                }else {
                    hour_card_more.setText("查看更多↓");
                }

            }
        });
    }


    public void setData(List<HoursWeather> hoursWeathers){
        hoursWeathersall=hoursWeathers;

        today=false;
        tomorrow=false;
        houtian=false;
        for (int i=0;i<hoursWeathersall.size();i++){
            String time=jf2time(hoursWeathersall.get(i).getJf());
            hoursWeathersall.get(i).setJf(time);
            if(!today&&TextUtils.equals(day,calendar.get(Calendar.DAY_OF_MONTH)+"")){
                todayposition=i;
                today=true;
            }else if(!tomorrow&&!TextUtils.equals(day,(calendar.get(Calendar.DAY_OF_MONTH))+"")){
                tomorrowposition=i;
                tomorrow=true;
            }
        }

        if(today){
            HoursWeather addwen=new HoursWeather();
            addwen.setJf("今天");
            hoursWeathersall.add(todayposition,addwen);
        }
        if(today&&tomorrow){
            HoursWeather addwen=new HoursWeather();
            addwen.setJf("明天");
            hoursWeathersall.add(tomorrowposition+1,addwen);
        }else if(tomorrow) {
            HoursWeather addwen=new HoursWeather();
            addwen.setJf("明天");
            hoursWeathersall.add(tomorrowposition,addwen);
        }

        if(hoursWeathersall!=null&&hoursWeathersall.size()>10){
            this.hoursWeathers=hoursWeathersall.subList(0,10);
            hour_card_more.setVisibility(VISIBLE);
        }else if(hoursWeathersall!=null){
            this.hoursWeathers=hoursWeathersall;
            hour_card_more.setVisibility(GONE);
        }

        if(mAdapter==null){
            hour_card_recyclerview.setAdapter(mAdapter = new Hourdapter());
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }

    public void setData(List<HoursWeather> hoursWeathers,boolean more){
        if(more){
            this.hoursWeathers=hoursWeathers;
        }else{
            this.hoursWeathers=hoursWeathers.subList(0,10);
        }

        mAdapter.notifyDataSetChanged();

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

            if(TextUtils.equals(hoursWeathers.get(position).getJf(),"今天")){
                holder.hour_item_weather.setVisibility(GONE);
                holder.hour_item_wind.setVisibility(GONE);
            }else if(TextUtils.equals(hoursWeathers.get(position).getJf(),"明天")){
                holder.hour_item_weather.setVisibility(GONE);
                holder.hour_item_wind.setVisibility(GONE);
            }else{
                holder.hour_item_weather.setVisibility(VISIBLE);
                holder.hour_item_wind.setVisibility(VISIBLE);
                holder.hour_item_weather.setText(hoursWeathers.get(position).getJa()+"  "+hoursWeathers.get(position).getJb()+"℃");
                holder.hour_item_wind.setText(hoursWeathers.get(position).getJc()+" "+hoursWeathers.get(position).getJd());
            }
            holder.hour_item_time.setText(hoursWeathers.get(position).getJf());


        }

        @Override
        public int getItemCount()
        {
            return hoursWeathers.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView hour_item_time,hour_item_weather,hour_item_wind;

            public MyViewHolder(View view)
            {
                super(view);
                hour_item_time = (TextView) view.findViewById(R.id.hour_item_time);
                hour_item_weather = (TextView) view.findViewById(R.id.hour_item_weather);
                hour_item_wind = (TextView) view.findViewById(R.id.hour_item_wind);
            }
        }
    }

//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) e.getRawX();
//                downY = (int) e.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) e.getRawY();
//                if (Math.abs(moveY - downY) > mTouchSlop) {
//                    return false;
//                }
//        }
//        return super.onInterceptTouchEvent(e);
//    }

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
