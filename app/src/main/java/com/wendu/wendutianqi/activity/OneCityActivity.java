package com.wendu.wendutianqi.activity;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.fragment.MyMenuFragment;
import com.wendu.wendutianqi.model.AQI;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.model.HoursWeather;
import com.wendu.wendutianqi.model.WeatherNow;
import com.wendu.wendutianqi.net.Location;
import com.wendu.wendutianqi.net.MyJson;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SPUtils;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.StatusBarUtil;
import com.wendu.wendutianqi.view.DailyCardLine;
import com.wendu.wendutianqi.view.ErrorView;
import com.wendu.wendutianqi.view.HoursCard;
import com.wendu.wendutianqi.view.NowCard;
import com.wendu.wendutianqi.view.flowingdrawer.FlowingView;
import com.wendu.wendutianqi.view.flowingdrawer.LeftDrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class OneCityActivity extends AppCompatActivity {

    private int bgColor,titleColor;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ActionBar ab;
    private LeftDrawerLayout mLeftDrawerLayout;
//    private SystemBarTintManager tintManager;
    private SwipeRefreshLayout mSwipeLayout;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private String place, cityId,City1;
//    private boolean place2;
    private ErrorView errorView;
    private CoordinatorLayout coordinatorLayout;
    private ImageView headImageView;
    private NowCard nowCard;
    private HoursCard hoursCard;
    private DailyCardLine dailyCard;
    private boolean location=true;
    private   MyMenuFragment mMenuFragment;
    private Calendar calendar;
    private String todayweather;
    private List<DailyForecast> dailyForecast;
    private NestedScrollView one_city_scroll;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_city);
        StatusBarUtil.transparencyBar(OneCityActivity.this);
        mLocationClient = new LocationClient(OneCityActivity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        Location.initLocation(mLocationClient);
        mLocationClient.start();

        initView();

        setListener();
    }


    public void initView(){

        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.one_city_CoordinatorLayout);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.one_city_swipe);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.blue,R.color.cyan);
        mSwipeLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeLayout.setRefreshing(true);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.one_city_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.one_city_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.one_city_flowing);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.one_city_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);


        toolbar = (Toolbar) findViewById(R.id.one_city_toolbar);
        if(location){
            toolbar.setLogo(R.mipmap.location_white);
        }

        toolbar.setNavigationIcon(R.mipmap.menu_white);
        setSupportActionBar(toolbar);
//        ab = getSupportActionBar();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.one_city_CollapsingToolbarLayout);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white90));

        headImageView=(ImageView) findViewById(R.id.one_city_iv);

//        setHead();
        one_city_scroll=(NestedScrollView) findViewById(R.id.one_city_scroll);
        nowCard=(NowCard) findViewById(R.id.one_city_nowcard);
        hoursCard=(HoursCard) findViewById(R.id.one_city_hourscard);
        dailyCard=(DailyCardLine) findViewById(R.id.one_city_dailycard);

        errorView=(ErrorView) findViewById(R.id.one_city_error);
    }

    public void setListener(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(location){
                    mLocationClient.start();
                }else{
                    new GetWeatherData().execute(Urls.WEATHER_URL);
                }

            }
        });

        errorView.setOnLodErrorListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location){
                    mLocationClient.start();
                }else{
                    new GetWeatherData().execute(Urls.WEATHER_URL);
                }
            }
        });
        errorView.setOnNetErrorListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location){
                    mLocationClient.start();
                }else{
                    new GetWeatherData().execute(Urls.WEATHER_URL);
                }
            }
        });

        mMenuFragment.setMenuItemSelectedListener(new MyMenuFragment.MenuItemSelectedListener() {
            @Override
            public void MenuItemSelectedListener(MenuItem item) {
                mLeftDrawerLayout.toggle();
                Intent intent =null;
                switch (item.getItemId()){
                    case R.id.menu_citylist:
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                Intent  intent2 =new Intent(OneCityActivity.this,SelectCity.class);
                                startActivityForResult(intent2,100);
                            }
                        },150);
                        break;
                    case R.id.menu_search:
                        intent=new Intent(OneCityActivity.this,TemporaryFind.class);
                        break;
                    case R.id.menu_about:
                             intent =new Intent(OneCityActivity.this,About.class);
                        break;
                }
                if(intent!=null){
                    final Intent finalIntent = intent;
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            startActivity(finalIntent);
                        }
                    },150);
                }

            }
        });

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
                        mSwipeLayout.setVisibility(View.VISIBLE);
                        errorView.ErrorGone();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            Animator animator = ViewAnimationUtils.createCircularReveal(coordinatorLayout,(coordinatorLayout.getWidth()/2),(coordinatorLayout.getHeight()/2)
                                    ,0,coordinatorLayout.getWidth());
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.setDuration(300);
                            animator.start();
                        }

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
//                        calendar.get(Calendar.HOUR_OF_DAY);
                        Bitmap bitmap=null;
                        todayweather=weatherNow.getCond().getTxt();
                        if(weatherNow.getCond().getTxt().contains("晴")){
                            headImageView.setImageResource(R.mipmap.flower);
                            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flower);
                        }else if(weatherNow.getCond().getTxt().contains("云")){
                            headImageView.setImageResource(R.mipmap.duoyun_day);
                            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.duoyun_day);
                        }else if(weatherNow.getCond().getTxt().contains("阴")){
                            headImageView.setImageResource(R.mipmap.yin_youth);
                            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yin_youth);
                        }else if(weatherNow.getCond().getTxt().contains("雾")||weatherNow.getCond().getTxt().contains("霾")){
                            headImageView.setImageResource(R.mipmap.wu);
                            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wu);
                        }else if(weatherNow.getCond().getTxt().contains("雨")){
                            headImageView.setImageResource(R.mipmap.yu);
                            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yu);
                        }
                        if(bitmap!=null){
                            setHead(bitmap);
                        }

                        String daily_forecast =MyJson.getString(jsonObject,"daily_forecast");
                        dailyForecast= gson.fromJson(daily_forecast, new TypeToken<List<DailyForecast>>() {}.getType());
                        dailyCard.setData(dailyForecast);

                        SnackbarUtil.showShortInfo(coordinatorLayout," 天气数据已更新 ~O(∩_∩)O~");
                    }else if(TextUtils.equals("unknown city",status)){
//                        if(place2){
                            SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
//                        }else{
//                            place2=true;
//                            mLocationClient.start();
//                        }
                    }else{
                        mSwipeLayout.setVisibility(View.GONE);
                        errorView.LodError();
                    }

            }else {
                mSwipeLayout.setVisibility(View.GONE);
                SnackbarUtil.showLongWarning(coordinatorLayout," 额，没网了...");
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
    public void setHead (Bitmap bitmap) {

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                bgColor=palette.getVibrantColor(getResources().getColor(R.color.colorPrimary));
//                titleColor=palette.getLightVibrantColor(getResources().getColor(R.color.white));

                collapsingToolbar.setContentScrimColor(bgColor);
//                collapsingToolbar.setExpandedTitleColor(titleColor);
//                SystemBarUtil.setStatusBarColor(OneCityActivity.this,bgColor);
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
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(Location.result(OneCityActivity.this,bdLocation,coordinatorLayout)){
                LogUtil.e("\n" + bdLocation.getCity()+":" + bdLocation.getCityCode()+"\n" + bdLocation.getDistrict());

//                if(place2){
                    place=bdLocation.getCity();
                    if(place.endsWith("市")){
                        place= bdLocation.getCity().substring(0,place.length()-1);
                    }
//                }else{
//                    place=bdLocation.getDistrict();
//                    if(place.endsWith("区")){
//                        place= bdLocation.getDistrict().substring(0,bdLocation.getDistrict().length()-1);
//                    }
//                }

                collapsingToolbar .setTitle(place);
                new GetWeatherData().execute(Urls.WEATHER_URL);

            }else{
                mSwipeLayout.setRefreshing(false);
                mSwipeLayout.setVisibility(View.GONE);
                SnackbarUtil.showLongWarning(coordinatorLayout," 额，定位失败了...");
                errorView.ShowError();
            }
            mLocationClient.stop();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    public void onBackPressed() {
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            if(!TextUtils.isEmpty(todayweather)){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                SPUtils.put(OneCityActivity.this,"todayweather",sdf+todayweather);
            }
            super.onBackPressed();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("select_place");

                    if(TextUtils.equals(returnedData,"location")){
                        if(!location){
                            toolbar.setLogo(R.mipmap.location_white);
                            mSwipeLayout.setRefreshing(true);
                            mLocationClient.start();
                        }
                        location=true;

                    }else if(!TextUtils.isEmpty(returnedData)){
                        location=false;
                        place=returnedData;
                        toolbar.setLogo(null);
                        collapsingToolbar .setTitle(place);
                        mSwipeLayout.setRefreshing(true);
                        new GetWeatherData().execute(Urls.WEATHER_URL);
                    }
                }
                break;
            default:
        }
    }




}
