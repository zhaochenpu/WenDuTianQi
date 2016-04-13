package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class DailyCardLine extends CardView{

    private View view;
//    private RecyclerView daily_card_recyclerview;
    private List<DailyForecast> dailyForecast;
    private boolean today,tomorrow,houtian;
    private String  day;
//    private Calendar calendar;
    private SimpleDateFormat sdf;
    private SmoothLineChartEquallySpaced daily_card_max_line,daily_card_min_line;
//    private Hourdapter mAdapter;
    private Integer[] daily_forecats_date=new Integer[] {R.id.daily_forecats_date0,R.id.daily_forecats_date1,R.id.daily_forecats_date2,R.id.daily_forecats_date3,R.id.daily_forecats_date4,R.id.daily_forecats_date5,R.id.daily_forecats_date6};
    private Integer[] daily_forecats_week=new Integer[] {R.id.daily_forecats_week0,R.id.daily_forecats_week1,R.id.daily_forecats_week2,R.id.daily_forecats_week3,R.id.daily_forecats_week4,R.id.daily_forecats_week5,R.id.daily_forecats_week6};
    private Integer[] daily_forecats_day=new Integer[] {R.id.daily_forecats_day0,R.id.daily_forecats_day1,R.id.daily_forecats_day2,R.id.daily_forecats_day3,R.id.daily_forecats_day4,R.id.daily_forecats_day5,R.id.daily_forecats_day6};
    private Integer[] daily_forecats_night=new Integer[] {R.id.daily_forecats_night0,R.id.daily_forecats_night1,R.id.daily_forecats_night2,R.id.daily_forecats_night3,R.id.daily_forecats_night4,R.id.daily_forecats_night5,R.id.daily_forecats_night6};

    public DailyCardLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.daily_card_line,null);

        addView(view);
        initView();
    }

    public void initView(){
        daily_card_max_line=(SmoothLineChartEquallySpaced) view.findViewById(R.id.daily_card_max_line);
//        daily_card_min_line=(SmoothLineChartEquallySpaced) view.findViewById(R.id.daily_card_min_line);
//        calendar = Calendar.getInstance();
        sdf=new SimpleDateFormat("yyyy-MM-dd");
//        daily_card_recyclerview=(RecyclerView) view.findViewById(R.id.daily_card_recyclerview);
    }

    public void setData(List<DailyForecast> dailyForecast){
        if(this.getVisibility()==GONE){
            this.setVisibility(VISIBLE);
        }

        this.dailyForecast=dailyForecast;

//        daily_card_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        daily_card_recyclerview.setAdapter(mAdapter = new Hourdapter());

        float maxline[]=new float[7];
        float minline[]=new float[7];
        for(int i=0;i<dailyForecast.size();i++){
            maxline[i]=Float.parseFloat(dailyForecast.get(i).getTmp().getMax());
            minline[i]=Float.parseFloat(dailyForecast.get(i).getTmp().getMin());

            TextView daily_forecats_date_tv=(TextView)findViewById(daily_forecats_date[i]);
            daily_forecats_date_tv.setText(StringUtils.getMonthDay(dailyForecast.get(i).getDate()));

            TextView daily_forecats_week_tv=(TextView)findViewById(daily_forecats_week[i]);
            if(!today&&TextUtils.equals(sdf.format(new Date()),dailyForecast.get(i).getDate())){
                daily_forecats_week_tv.setText("今天");
                today=true;
            }else{
                daily_forecats_week_tv.setText(StringUtils.getWeekOfDate(dailyForecast.get(i).getDate()));
            }

            TextView daily_forecats_day_tv=(TextView)findViewById(daily_forecats_day[i]);
            daily_forecats_day_tv.setText(dailyForecast.get(i).getCond().getTxt_d()+"\n"+dailyForecast.get(i).getTmp().getMax()+"℃");

            TextView daily_forecats_night_tv=(TextView)findViewById(daily_forecats_night[i]);
            daily_forecats_night_tv.setText(dailyForecast.get(i).getCond().getTxt_n()+"\n"+dailyForecast.get(i).getTmp().getMin()+"℃");
        }
        daily_card_max_line.setData(maxline,minline);
//        daily_card_min_line.setData(minline,1);

    }

//    class Hourdapter extends RecyclerView.Adapter<Hourdapter.MyViewHolder>
//    {
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//        {
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
//                    getContext()).inflate(R.layout.daily_forecast_line_item, parent,
//                    false));
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position)
//        {
//
//            if(TextUtils.equals(sdf.format(new Date()),dailyForecast.get(position).getDate())){
//                holder.daily_forecats_week.setText("今天");
//            }else{
//                holder.daily_forecats_week.setText(StringUtils.getWeekOfDate(dailyForecast.get(position).getDate()));
//            }
//
//            holder.daily_forecats_date.setText(StringUtils.getMonthDay(dailyForecast.get(position).getDate()));
//            holder.daily_forecats_day.setText(dailyForecast.get(position).getCond().getTxt_d()+" "+dailyForecast.get(position).getTmp().getMax()+"℃");
//            holder.daily_forecats_night.setText(dailyForecast.get(position).getCond().getTxt_n()+" "+dailyForecast.get(position).getTmp().getMin()+"℃");
////            holder.daily_forecats_fengxiang.setText(dailyForecast.get(position).getWind().getDir());
////            holder.daily_forecats_fengli.setText(dailyForecast.get(position).getWind().getSc());
//        }
//
//        @Override
//        public int getItemCount()
//        {
//            return dailyForecast.size();
//        }
//
//
//        class MyViewHolder extends RecyclerView.ViewHolder
//        {
//
//            TextView daily_forecats_date,daily_forecats_week,daily_forecats_day,daily_forecats_night,daily_forecats_fengxiang,daily_forecats_fengli;
//
//            public MyViewHolder(View view)
//            {
//                super(view);
//                daily_forecats_date = (TextView) view.findViewById(R.id.daily_forecats_date);
//                daily_forecats_week = (TextView) view.findViewById(R.id.daily_forecats_week);
//                daily_forecats_day = (TextView) view.findViewById(R.id.daily_forecats_day);
//                daily_forecats_night = (TextView) view.findViewById(R.id.daily_forecats_night);
////                daily_forecats_fengxiang = (TextView) view.findViewById(R.id.daily_forecats_fengxiang);
////                daily_forecats_fengli = (TextView) view.findViewById(R.id.daily_forecats_fengli);
//            }
//        }
//    }




}
