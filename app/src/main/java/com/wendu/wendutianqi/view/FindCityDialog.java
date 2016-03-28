package com.wendu.wendutianqi.view;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wendu.wendutianqi.R;

/**
 * Created by noname on 2016/3/26.
 */
public class FindCityDialog extends Dialog{

    private View mView;
    private TextInputEditText find_city_edit;
    private Button find,cancel;

    public FindCityDialog(Context context, int themeResId) {
        super(context, themeResId);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.find_city_dialog, null);

        initView();

        super.setContentView(mView);
    }

    public  void initView(){

        find_city_edit=(TextInputEditText) mView.findViewById(R.id.find_city_edit);
        find_city_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    public EditText getEditText(){
        return find_city_edit;
    }

}
