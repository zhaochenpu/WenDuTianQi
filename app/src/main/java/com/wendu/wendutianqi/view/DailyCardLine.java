package com.wendu.wendutianqi.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by el on 2016/3/22.
 */
public class DailyCardLine extends CardView{

    private View view;

    private List<DailyForecast> dailyForecast;
    private List<View> dailyCardviews=new ArrayList<>();
    private boolean today;
    private String  day;
//    private Calendar calendar;
    private SimpleDateFormat sdf;
    private SmoothLineChartEquallySpaced daily_card_max_line,daily_card_min_line;
    private TabLayout daily_card_tablayout;
    private ViewPager daily_card_viewpage;
    private SimplePagerAdapter adapter;
    private Integer[] daily_forecats_date=new Integer[] {R.id.daily_forecats_date0,R.id.daily_forecats_date1,R.id.daily_forecats_date2,R.id.daily_forecats_date3,R.id.daily_forecats_date4,R.id.daily_forecats_date5,R.id.daily_forecats_date6};
    private Integer[] daily_forecats_week=new Integer[] {R.id.daily_forecats_week0,R.id.daily_forecats_week1,R.id.daily_forecats_week2,R.id.daily_forecats_week3,R.id.daily_forecats_week4,R.id.daily_forecats_week5,R.id.daily_forecats_week6};
    private Integer[] daily_forecats_day=new Integer[] {R.id.daily_forecats_day0,R.id.daily_forecats_day1,R.id.daily_forecats_day2,R.id.daily_forecats_day3,R.id.daily_forecats_day4,R.id.daily_forecats_day5,R.id.daily_forecats_day6};
    private Integer[] daily_forecats_night=new Integer[] {R.id.daily_forecats_night0,R.id.daily_forecats_night1,R.id.daily_forecats_night2,R.id.daily_forecats_night3,R.id.daily_forecats_night4,R.id.daily_forecats_night5,R.id.daily_forecats_night6};
    private Context mcontext;

    public DailyCardLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        view = LayoutInflater.from(getContext()).inflate(R.layout.daily_card_line,null);

        addView(view);
        initView();
    }

    public void initView(){
        daily_card_max_line=(SmoothLineChartEquallySpaced) view.findViewById(R.id.daily_card_max_line);
        daily_card_tablayout=(TabLayout) view.findViewById(R.id.daily_card_tablayout);
        daily_card_viewpage=(ViewPager) view.findViewById(R.id.daily_card_viewpage);


        daily_card_tablayout.setTabMode(TabLayout.MODE_FIXED);

        sdf=new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setData(List<DailyForecast> dailyForecast){
        if(this.getVisibility()==GONE){
            this.setVisibility(VISIBLE);
        }
        today=false;
        this.dailyForecast=dailyForecast;

        int maxline[]=new int[7];
        int minline[]=new int[7];
        for(int i=0;i<dailyForecast.size();i++){

            maxline[i]=Integer.parseInt(dailyForecast.get(i).getTmp().getMax());
            minline[i]=Integer.parseInt(dailyForecast.get(i).getTmp().getMin());

            TextView daily_forecats_date_tv=(TextView)findViewById(daily_forecats_date[i]);
            daily_forecats_date_tv.setText(StringUtils.getMonthDay(dailyForecast.get(i).getDate()));

            TextView daily_forecats_week_tv=(TextView)findViewById(daily_forecats_week[i]);
            if(!today&&TextUtils.equals(sdf.format(new Date()),dailyForecast.get(i).getDate())){
                daily_forecats_week_tv.setText("今天");
                today=true;
            }else{
                daily_forecats_week_tv.setText(StringUtils.getWeekOfDate(dailyForecast.get(i).getDate()));
            }

            TextView daily_forecats_day_tv=(TextView)findViewById(daily_forecats_day[i]);
            daily_forecats_day_tv.setText(dailyForecast.get(i).getCond().getTxt_d());

            TextView daily_forecats_night_tv=(TextView)findViewById(daily_forecats_night[i]);
            daily_forecats_night_tv.setText(dailyForecast.get(i).getCond().getTxt_n());


            if(dailyCardviews.size()<7){
                View view1 = new DailyCardView(getContext(),dailyForecast.get(i));
//                DailyCardView dailyCardView=(DailyCardView)  view1.findViewById(R.id.daily_card_view);
//                dailyCardView.setData(dailyForecast.get(i));
                dailyCardviews.add(view1);
            }

        }

        daily_card_max_line.setData(maxline,minline);
        if(adapter==null){
            adapter=new SimplePagerAdapter();
            daily_card_viewpage.setAdapter(adapter);
            daily_card_tablayout.setupWithViewPager(daily_card_viewpage);

        }else {
            adapter.notifyDataSetChanged();
        }

    }


    class SimplePagerAdapter extends PagerAdapter {

        private String tabTitles[] = new String[]{"","","","","","",""};

        @Override
        public int getCount() {
            return dailyCardviews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             container.addView(dailyCardviews.get(position));
            return dailyCardviews.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(dailyCardviews.get(position));
        }
    }

}
