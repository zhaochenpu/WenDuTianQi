package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.HoursWeather;

import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class HoursCard extends CardView{

    private View view;
    private RecyclerView hour_card_recyclerview;
    private List<HoursWeather> hoursWeathers;
    private Hourdapter mAdapter;

    public HoursCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(getContext()).inflate(R.layout.hours_card,null);

        addView(view);
        initView();
    }

    public void initView(){
        hour_card_recyclerview=(RecyclerView) view.findViewById(R.id.hour_card_recyclerview);

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
//            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return hoursWeathers.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
//                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }


}
