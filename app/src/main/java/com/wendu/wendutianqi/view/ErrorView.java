package com.wendu.wendutianqi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.NetWorkUtils;

/**
 * Created by el on 2016/3/15.
 */
public class ErrorView extends RelativeLayout{

    public RelativeLayout netErrorRL;
    public RelativeLayout lodErrorRL;
    private  Context context;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        View neterrorview = LayoutInflater.from(context).inflate(R.layout.net_error,null, true);
        View loderrorview = LayoutInflater.from(context).inflate(R.layout.lod_error,null, true);
        addView(neterrorview);
        addView(loderrorview);

        netErrorRL=(RelativeLayout) neterrorview.findViewById(R.id.net_error);
        lodErrorRL=(RelativeLayout) neterrorview.findViewById(R.id.lod_error);

    }

    public void ShowError(){
        if(NetWorkUtils.isConnected(context)){
            LodError();
        }else {
            NetError();
        }
    }

    public void NetError(){
        if(netErrorRL.getVisibility()==View.VISIBLE){
            netErrorRL.setVisibility(View.GONE);
        }else{
            netErrorRL.setVisibility(View.VISIBLE);
        }
    }

    public void LodError(){
        if(lodErrorRL.getVisibility()==View.VISIBLE){
            lodErrorRL.setVisibility(View.GONE);
        }else{
            lodErrorRL.setVisibility(View.VISIBLE);
        }
    }

}
