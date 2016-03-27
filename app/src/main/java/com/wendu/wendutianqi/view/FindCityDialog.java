package com.wendu.wendutianqi.view;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.wendu.wendutianqi.R;

/**
 * Created by noname on 2016/3/26.
 */
public class FindCityDialog extends Dialog{

    private View mView;
    private TextInputEditText find_city_edit;
    private Button find,cancel;

    protected FindCityDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.find_city_dialog, null);

        initView();

        super.setContentView(mView);
    }

    public  void initView(){

        find_city_edit=(TextInputEditText) mView.findViewById(R.id.find_city_edit);
        find=(Button)  mView.findViewById(R.id.find_city_find);
        cancel=(Button)  mView.findViewById(R.id.find_city_cancel);

    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        find.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener){
        cancel.setOnClickListener(listener);
    }

    public View getEditText(){
        return find_city_edit;
    }

}
