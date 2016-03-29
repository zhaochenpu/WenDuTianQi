package com.wendu.wendutianqi.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendu.wendutianqi.R;

/**
 * Created by el on 2016/3/29.
 */
public class FindCityAdapt extends RecyclerView.Adapter<FindCityAdapt.MyViewHolder>{
    @Override
    public FindCityAdapt.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FindCityAdapt.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView hour_item_time;

        public MyViewHolder(View view)
        {
            super(view);
            hour_item_time = (TextView) view.findViewById(R.id.hour_item_time);

        }
    }
}
