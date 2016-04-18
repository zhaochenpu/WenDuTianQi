package com.wendu.wendutianqi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private Button net_error_bt,lod_error_bt;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        View neterrorview = LayoutInflater.from(context).inflate(R.layout.net_error,null);
        View loderrorview = LayoutInflater.from(context).inflate(R.layout.lod_error,null);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        addView(neterrorview, p);
        addView(loderrorview, p);

        netErrorRL=(RelativeLayout) neterrorview.findViewById(R.id.net_error);
        lodErrorRL=(RelativeLayout) loderrorview.findViewById(R.id.lod_error);

        lod_error_bt=(Button) loderrorview.findViewById(R.id.lod_error_bt);
        net_error_bt=(Button) neterrorview.findViewById(R.id.net_error_bt);
    }

    public void ShowError(){
        if(NetWorkUtils.isConnected(context)){
            LodError();
        }else {
            NetError();
        }
    }
    public void ErrorGone(){
        netErrorRL.setVisibility(View.GONE);
        lodErrorRL.setVisibility(View.GONE);
    }
    public void NetError(){
        if(netErrorRL.getVisibility()!=View.VISIBLE){
            netErrorRL.setVisibility(View.VISIBLE);
        }
    }

    public void LodError(){
        if(lodErrorRL.getVisibility()!=View.VISIBLE){
            lodErrorRL.setVisibility(View.VISIBLE);
        }
    }

    public void setOnLodErrorListener(View.OnClickListener listener){
        lod_error_bt.setOnClickListener(listener);
    }
    public void setOnNetErrorListener(View.OnClickListener listener){
        net_error_bt.setOnClickListener(listener);
    }

}
