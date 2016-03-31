/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AllChinaPlace;
import com.wendu.wendutianqi.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class FindCityAdapter extends RecyclerView.Adapter<FindCityAdapter.SimpleViewHolder> {

    public static final int LAST_POSITION = -1 ;
    private final Context mContext;
    private List<AllChinaPlace> mData;

//    public void add(AllChinaPlace s,int position) {
//        position = position == LAST_POSITION ? getItemCount()  : position;
//        mData.add(position,s);
//        notifyItemInserted(position);
//    }
//
//    public void remove(int position){
//        if (position == LAST_POSITION && getItemCount()>0)
//            position = getItemCount() -1 ;
//
//        if (position > LAST_POSITION && position < getItemCount()) {
//            mData.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

    public interface OnItemClickLitener
    {
        void onItemClick(String itemPlace);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView select_city_name;

        public SimpleViewHolder(View view) {
            super(view);
            select_city_name = (TextView) view.findViewById(R.id.select_city_name);
        }
    }

    public FindCityAdapter(Context context, List<AllChinaPlace> data) {
        mContext = context;
        if (data != null)
            mData =data;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.select_city_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {

        holder.select_city_name.setText(mData.get(position).getCity()+"     "+mData.get(position).getProv());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(mData.get(pos).getCity());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
