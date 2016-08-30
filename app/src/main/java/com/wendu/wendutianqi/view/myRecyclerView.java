package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wendu.wendutianqi.utils.LogUtil;

/**
 * Created by el on 2016/3/24.
 * 重写触摸事件，解决与NestedScrollView的冲突
 */
public class myRecyclerView extends RecyclerView {

    public myRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
        }

    public boolean onTouchEvent(MotionEvent ev){
        return false;
    }
}
