package com.wendu.wendutianqi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;

/**
 * Created by el on 2016/4/14.
 */
public class DailyCardView extends RelativeLayout{

    private View view;
    private TextView daily_card_fragment_wind,daily_card_fragment_richu,daily_card_fragment_riluo,daily_card_fragment_qiya,daily_card_fragment_shidu,daily_card_fragment_jiangshui;
    private DailyForecast dailyForecast;

    public DailyCardView(Context context,DailyForecast dailyForecast) {
        super(context);
        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.daily_card_fragment,null);

        }
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        this.dailyForecast=dailyForecast;
        initView();
        addView(view,p);

    }
    private void initView(){
        daily_card_fragment_wind=(TextView) view.findViewById(R.id.daily_card_fragment_wind);
        daily_card_fragment_richu=(TextView) view.findViewById(R.id.daily_card_fragment_richu);
        daily_card_fragment_riluo=(TextView) view.findViewById(R.id.daily_card_fragment_riluo);
        daily_card_fragment_qiya=(TextView) view.findViewById(R.id.daily_card_fragment_qiya);
        daily_card_fragment_jiangshui=(TextView) view.findViewById(R.id.daily_card_fragment_jiangshui);
        daily_card_fragment_shidu=(TextView) view.findViewById(R.id.daily_card_fragment_shidu);
        daily_card_fragment_wind.setText(dailyForecast.getWind().getDir()+" "+dailyForecast.getWind().getSc());
        daily_card_fragment_richu.setText("日出："+dailyForecast.getAstro().getSr());
        daily_card_fragment_riluo.setText("日落："+dailyForecast.getAstro().getSs());
        daily_card_fragment_qiya.setText("气压："+dailyForecast.getPres()+"百帕");
        daily_card_fragment_shidu.setText("湿度："+dailyForecast.getHum()+"%");
        daily_card_fragment_jiangshui.setText("降水概率："+dailyForecast.getPop()+"%");
    }

    public  void setData(DailyForecast dailyForecast){
        daily_card_fragment_wind.setText(dailyForecast.getWind().getDir()+" "+dailyForecast.getWind().getSc());
        daily_card_fragment_richu.setText("日出："+dailyForecast.getAstro().getSr());
        daily_card_fragment_riluo.setText("日落："+dailyForecast.getAstro().getSs());
        daily_card_fragment_qiya.setText("气压："+dailyForecast.getPres()+"百帕");
        daily_card_fragment_shidu.setText("湿度："+dailyForecast.getHum()+"%");
        daily_card_fragment_jiangshui.setText("降水概率："+dailyForecast.getPop()+"%");
    }
}
