package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class DailyCardLine extends CardView{

    private View view;
    private RecyclerView daily_card_recyclerview;
    private List<DailyForecast> dailyForecast;
    private boolean today,tomorrow,houtian;
    private String  day;
//    private Calendar calendar;
    private SimpleDateFormat sdf;
    private SmoothLineChartEquallySpaced daily_card_max_line,daily_card_min_line;

    public DailyCardLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.daily_card_line,null);

        addView(view);
        initView();
    }

    public void initView(){
        daily_card_max_line=(SmoothLineChartEquallySpaced) view.findViewById(R.id.daily_card_max_line);
        daily_card_min_line=(SmoothLineChartEquallySpaced) view.findViewById(R.id.daily_card_min_line);
//        calendar = Calendar.getInstance();
        sdf=new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setData(List<DailyForecast> dailyForecast){
        if(this.getVisibility()==GONE){
            this.setVisibility(VISIBLE);
        }

        this.dailyForecast=dailyForecast;

        float maxline[]=new float[7];
        float minline[]=new float[7];
        for(int i=0;i<dailyForecast.size();i++){
            maxline[i]=Float.parseFloat(dailyForecast.get(i).getTmp().getMax());
            minline[i]=Float.parseFloat(dailyForecast.get(i).getTmp().getMin());
        }
        daily_card_max_line.setData(maxline,0);
        daily_card_min_line.setData(minline,1);

    }






}
