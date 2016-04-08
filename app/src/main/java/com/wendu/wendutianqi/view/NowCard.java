package com.wendu.wendutianqi.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AQI;
import com.wendu.wendutianqi.model.WeatherNow;

import java.util.Calendar;

/**
 * Created by el on 2016/3/22.
 */
public class NowCard extends CardView{

    private View view;
    private TextView now_card_date,now_card_weather,now_card_aqi,now_card_aqi_text,now_card_qlty,now_card_tigan,now_card_wind,now_card_du,now_card_more,now_card_jiangshui_kejian,now_card_qiya,now_card_shidu;
    private LinearLayout now_card_aqiL;
    private String color="#E6FFFFFF";

    public NowCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        view = LayoutInflater.from(getContext()).inflate(R.layout.now_card,null);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
        addView(view);

        initView();

    }

    private void initView(){

        now_card_date=(TextView) view.findViewById(R.id.now_card_date);
        now_card_weather=(TextView) view.findViewById(R.id.now_card_weather);
        now_card_aqi=(TextView) view.findViewById(R.id.now_card_aqi);
        now_card_qlty=(TextView) view.findViewById(R.id.now_card_qlty);
        now_card_tigan=(TextView) view.findViewById(R.id.now_card_tigan);
        now_card_wind=(TextView) view.findViewById(R.id.now_card_wind);
        now_card_du=(TextView) view.findViewById(R.id.now_card_du);
        now_card_aqiL=(LinearLayout) view.findViewById(R.id.now_card_aqiL);
        now_card_aqi_text=(TextView) view.findViewById(R.id.now_card_aqi_text);
        now_card_jiangshui_kejian=(TextView) view.findViewById(R.id.now_card_jiangshui_kejian);
        now_card_qiya=(TextView) view.findViewById(R.id.now_card_qiya);
        now_card_shidu=(TextView) view.findViewById(R.id.now_card_shidu);
    }

    public void setData(WeatherNow weatherNow, AQI aqi){

        if(this.getVisibility()==GONE){
            this.setVisibility(VISIBLE);
        }

        if(aqi==null){
            now_card_aqiL.setVisibility(GONE);
        }else{
            now_card_aqi.setText(aqi.getAqi());
            now_card_qlty.setText(aqi.getQlty());
        }

        Calendar calendar = Calendar.getInstance();
        now_card_date.setText((calendar.get(Calendar.MONTH)+1)+"月"+(calendar.get(Calendar.DAY_OF_MONTH))+"日");

        if(weatherNow!=null){
            now_card_weather.setText(weatherNow.getCond().getTxt());

            if(weatherNow.getCond().getTxt().contains("雨")){
                now_card_jiangshui_kejian.setText("降水量:"+weatherNow.getPcpn()+"mm");
            }else if(weatherNow.getCond().getTxt().contains("雾")||weatherNow.getCond().getTxt().contains("霾")){
                now_card_jiangshui_kejian.setText("能见度:"+weatherNow.getPcpn()+"km");
            }else{
                now_card_jiangshui_kejian.setVisibility(GONE);
            }

            now_card_tigan.setText("体感:"+weatherNow.getFl()+"℃");
            now_card_wind.setText(weatherNow.getWind().getDir()+" "+weatherNow.getWind().getSc()+"级");
            now_card_du.setText(weatherNow.getTmp()+"℃");
            now_card_qiya.setText("气压:"+weatherNow.getPres()+"百帕");
            now_card_shidu.setText("湿度:"+weatherNow.getHum()+"%");
        }

    }


}
