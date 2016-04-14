package com.wendu.wendutianqi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;

/**
 * Created by el on 2016/4/14.
 */
public class DailyCardFragment extends Fragment {

    private View view;
    private TextView daily_card_fragment_wind,daily_card_fragment_richu,daily_card_fragment_riluo,daily_card_fragment_qiya,daily_card_fragment_shidu,daily_card_fragment_jiangshui;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view = View.inflate(getActivity(), R.layout.daily_card_fragment, null);
        }

        initView();
        return  view;
    }

    private void initView(){

        daily_card_fragment_wind=(TextView) view.findViewById(R.id.daily_card_fragment_wind);
        daily_card_fragment_richu=(TextView) view.findViewById(R.id.daily_card_fragment_richu);
        daily_card_fragment_riluo=(TextView) view.findViewById(R.id.daily_card_fragment_riluo);
        daily_card_fragment_qiya=(TextView) view.findViewById(R.id.daily_card_fragment_qiya);
        daily_card_fragment_jiangshui=(TextView) view.findViewById(R.id.daily_card_fragment_jiangshui);
        daily_card_fragment_shidu=(TextView) view.findViewById(R.id.daily_card_fragment_shidu);

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
