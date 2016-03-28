package com.wendu.wendutianqi.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AQI;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.model.HoursWeather;
import com.wendu.wendutianqi.model.WeatherNow;
import com.wendu.wendutianqi.net.MyJson;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.CitySPUtils;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.DailyCard;
import com.wendu.wendutianqi.view.ErrorView;
import com.wendu.wendutianqi.view.FindCityDialog;
import com.wendu.wendutianqi.view.HoursCard;
import com.wendu.wendutianqi.view.NowCard;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by noname on 2016/3/26.
 */
public class TemporaryFind extends AppCompatActivity{

    private int bgColor,titleColor;
    private String place,cityId;
    private boolean placeCan=false;
    private FindCityDialog findCityDialog;
    private boolean place2=false;
    private SwipeRefreshLayout mSwipeLayout;
    private ErrorView errorView;
    private CoordinatorLayout coordinatorLayout;
    private ImageView headImageView;
    private NowCard nowCard;
    private HoursCard hoursCard;
    private DailyCard dailyCard;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private NestedScrollView temporary_find_scroll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_find);

        SystemBarUtil.setStatusBarColor(this,R.color.colorPrimary);

        initView();

        setListener();

    }

    private void initView(){

        findCityDialog=new FindCityDialog(this,R.style.Dialog);


        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.temporary_find_CoordinatorLayout);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.temporary_find_swipe);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.blue,R.color.light_colorPrimary);
        mSwipeLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        toolbar = (Toolbar) findViewById(R.id.temporary_find_toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.menu_white);
//        setSupportActionBar(toolbar);
////        ab = getSupportActionBar();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.temporary_find_CollapsingToolbarLayout);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        headImageView=(ImageView) findViewById(R.id.temporary_find_iv);

        setHead();
//        one_city_scroll=(NestedScrollView) findViewById(R.id.one_city_scroll);
        nowCard=(NowCard) findViewById(R.id.temporary_find_nowcard);
        hoursCard=(HoursCard) findViewById(R.id.temporary_find_hourscard);
        dailyCard=(DailyCard) findViewById(R.id.temporary_find_dailycard);

        errorView=(ErrorView) findViewById(R.id.temporary_find_error);

        temporary_find_scroll=(NestedScrollView) findViewById(R.id.temporary_find_scroll);
        temporary_find_scroll.setVisibility(View.GONE);
    }

    public void setListener(){

        findCityDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText editText=findCityDialog.getEditText();
                place=editText.getText().toString();
                mSwipeLayout.setRefreshing(true);
                new GetWeatherData().execute(Urls.WEATHER_URL);
            }
        });
        findCityDialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCityDialog.cancel();
            }
        });
        findCityDialog.show();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetWeatherData().execute(Urls.WEATHER_URL);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection_city, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.collection_city:
                if(placeCan){
                    CitySPUtils.put(TemporaryFind.this,place,"1");
                }else{
                    SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，改地址不可用...");
                }
                return true;
            case R.id.find_city:
                if(!findCityDialog.isShowing()){
                    findCityDialog.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class GetWeatherData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> map =new HashMap<String, String>();
            map.put("city",place);

            return MyOkhttp.post(params[0],map);
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Gson gson=new Gson();
            if(!TextUtils.isEmpty(result)){
                LogUtil.e(result);

                JSONObject jsonObject = null;
                String jsonData=null;
                String status=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("HeWeather data service 3.0");
                    JSONArray jsonArray=new JSONArray(jsonData);
                    jsonObject=jsonArray.getJSONObject(0);
                    status=jsonObject.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(TextUtils.equals("ok",status)){

                    temporary_find_scroll.setVisibility(View.VISIBLE);

                    String  basic= MyJson.getString(jsonObject,"basic");
                    cityId= MyJson.getString(basic,"id");
                    if(!TextUtils.isEmpty(cityId)){

                        new  GetHoursWeatherData().execute(Urls.WEATHER_HOUR_URL+cityId.replace("CN", "")+".html");
                    }

                    String aqi =MyJson.getString(jsonObject,"aqi");
                    aqi =MyJson.getString(aqi,"city");
                    AQI aqi1 = null;
                    if(!TextUtils.isEmpty(aqi)){
                        aqi1= gson.fromJson(aqi, AQI.class);
                    }else{

                    }

                    String now =MyJson.getString(jsonObject,"now");
                    WeatherNow weatherNow= gson.fromJson(now, WeatherNow.class);
                    nowCard.setData(weatherNow,aqi1);
//                    int hour=calendar.get(Calendar.HOUR_OF_DAY);
                    if(weatherNow.getCond().getTxt().contains("晴")){
                        headImageView.setImageResource(R.mipmap.flower);
                    }else if(weatherNow.getCond().getTxt().contains("云")){
                        headImageView.setImageResource(R.mipmap.duoyun_day);
                    }else if(weatherNow.getCond().getTxt().contains("阴")){
                        headImageView.setImageResource(R.mipmap.yin_youth);
                    }else if(weatherNow.getCond().getTxt().contains("雾")||weatherNow.getCond().getTxt().contains("霾")){
                        headImageView.setImageResource(R.mipmap.wu);
                    }else if(weatherNow.getCond().getTxt().contains("雨")){
                        headImageView.setImageResource(R.mipmap.yu);
                    }

                    String daily_forecast =MyJson.getString(jsonObject,"daily_forecast");
                    LogUtil.e(daily_forecast);
                    List<DailyForecast> dailyForecast= gson.fromJson(daily_forecast, new TypeToken<List<DailyForecast>>() {}.getType());
                    dailyCard.setData(dailyForecast);

                    placeCan=true;
                    SnackbarUtil.showShortInfo(coordinatorLayout," 天气数据已更新 ~O(∩_∩)O~");
                }else if(TextUtils.equals("unknown city",status)){
                        if(place2){
                            placeCan=false;
                        SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
                        }else{
                            place2=true;

                            if(place.contains("市")){

                                place=place.replace("市","");
                                new GetWeatherData().execute(Urls.WEATHER_URL);

                            }else{
                                placeCan=false;
                                SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
                            }

                        }
                }else{
                    temporary_find_scroll.setVisibility(View.VISIBLE);
                    errorView.LodError();
                }

            }else {
                temporary_find_scroll.setVisibility(View.VISIBLE);
                errorView.ShowError();
            }

            mSwipeLayout.setRefreshing(false);

        }
    }

    private class GetHoursWeatherData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return MyOkhttp.get(params[0]);
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Gson gson=new Gson();
            if(!TextUtils.isEmpty(result)){
                LogUtil.e(result);

                JSONObject jsonObject;
                String jsonData=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("jh");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<HoursWeather> hoursWeathers= gson.fromJson(jsonData, new TypeToken<List<HoursWeather>>() {}.getType());
                if(hoursWeathers!=null){
                    hoursCard.setVisibility(View.VISIBLE);
                    hoursCard.setData(hoursWeathers);
                }else{
                    hoursCard.setVisibility(View.GONE);
                }


            }else {

            }

        }
    }


    /**
     * 获取图片主颜色 设置状态栏和工具栏
     */
    public void setHead () {

        Bitmap bitmap;
        headImageView.setDrawingCacheEnabled(true);
        bitmap =headImageView.getDrawingCache();
        if(bitmap==null){
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flower);
        }

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                bgColor=palette.getDarkVibrantColor(getResources().getColor(R.color.white));
                titleColor=palette.getLightMutedColor(getResources().getColor(R.color.white));
                collapsingToolbar.setContentScrimColor(bgColor);
                collapsingToolbar.setExpandedTitleColor(titleColor);
                SystemBarUtil.setStatusBarColor(TemporaryFind.this,bgColor);
//                Palette.Swatch swatch = palette.getVibrantSwatch();
//                Palette.Swatch swatch2 = palette.getLightVibrantSwatch();
//                if (swatch != null) {
//                    collapsingToolbar.setContentScrimColor(swatch.getRgb());
//                    collapsingToolbar.setExpandedTitleColor(swatch.getBodyTextColor());
//                }
//                if (swatch2 != null) {
//                    collapsingToolbar.setExpandedTitleColor(swatch2.getBodyTextColor());
////                    scrollView.setBackgroundColor(swatch2.getBodyTextColor());
//                }
            }
        });
        headImageView.setDrawingCacheEnabled(false);
    }

}
