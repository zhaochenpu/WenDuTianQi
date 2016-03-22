package com.wendu.wendutianqi.activity;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.activeandroid.util.Log;
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
import com.wendu.wendutianqi.model.WeatherFirst;
import com.wendu.wendutianqi.model.WeatherNow;
import com.wendu.wendutianqi.net.Location;
import com.wendu.wendutianqi.net.MyJson;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.ErrorView;
import com.wendu.wendutianqi.view.NowCard;
import com.wendu.wendutianqi.view.SystemBarTintManager;
import com.wendu.wendutianqi.view.flowingdrawer.FlowingView;
import com.wendu.wendutianqi.view.flowingdrawer.LeftDrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;


public class OneCityActivity extends AppCompatActivity {

    private int bgColor,titleColor;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ActionBar ab;
    private LeftDrawerLayout mLeftDrawerLayout;
    private SystemBarTintManager tintManager;
    private SwipeRefreshLayout mSwipeLayout;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String place, cityId;
//    private boolean place2;
    private ErrorView errorView;
    private CoordinatorLayout coordinatorLayout;
    private ImageView headImageView;
    private ScrollView scrollView;
    private NowCard nowCard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_city);

        initView();

        mLocationClient = new LocationClient(OneCityActivity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        Location.initLocation(mLocationClient);
        mLocationClient.start();

        setListener();
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void initView(){

        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.one_city_CoordinatorLayout);

        tintManager = new SystemBarTintManager(this);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.one_city_swipe);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.blue,R.color.light_colorPrimary);
        mSwipeLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeLayout.setRefreshing(true);

        scrollView=(ScrollView) findViewById(R.id.one_city_scroll);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.one_city_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.one_city_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.one_city_flowing);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.one_city_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        toolbar = (Toolbar) findViewById(R.id.one_city_toolbar);

        toolbar.setLogo(R.mipmap.location_white);
        toolbar.setNavigationIcon(R.mipmap.menu_white);
        setSupportActionBar(toolbar);
//        ab = getSupportActionBar();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.one_city_CollapsingToolbarLayout);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        headImageView=(ImageView) findViewById(R.id.one_city_iv);

        setHead();

        nowCard=(NowCard) findViewById(R.id.one_city_nowcard);

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
                mLocationClient.start();
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


                        String daily_forecast =MyJson.getString(jsonObject,"daily_forecast");
                        LogUtil.e(daily_forecast);
                        List<DailyForecast> dailyForecast= gson.fromJson(daily_forecast, new TypeToken<List<DailyForecast>>() {}.getType());



                        SnackbarUtil.showShortInfo(coordinatorLayout," 天气数据已更新 ~O(∩_∩)O~");
                    }else if(TextUtils.equals("unknown city",status)){
//                        if(place2){
                            SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
//                        }else{
//                            place2=true;
//                            mLocationClient.start();
//                        }
                    }else{
                        errorView.LodError();
                    }

            }else {
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
                LogUtil.e("jsonData:..."+jsonData);
                List<HoursWeather> hoursWeathers= gson.fromJson(jsonData, new TypeToken<List<HoursWeather>>() {}.getType());


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
                SystemBarUtil.setStatusBarColor(OneCityActivity.this,bgColor);
//                Palette.Swatch swatch = palette.getVibrantSwatch();
//                Palette.Swatch swatch2 = palette.getLightVibrantSwatch();
//                if (swatch != null) {
//                    collapsingToolbar.setContentScrimColor(swatch.getRgb());
//                    collapsingToolbar.setExpandedTitleColor(swatch.getBodyTextColor());
//                    SystemBarUtil.setStatusBarColor(MainActivity.this,tintManager,swatch.getRgb());
//                }
//                if (swatch2 != null) {
//                    collapsingToolbar.setExpandedTitleColor(swatch2.getBodyTextColor());
////                    scrollView.setBackgroundColor(swatch2.getBodyTextColor());
//                }
            }
        });
        headImageView.setDrawingCacheEnabled(false);
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

                LogUtil.e(place);
                collapsingToolbar .setTitle(place);
                new GetWeatherData().execute(Urls.WEATHER_URL);
                mLocationClient.stop();
            }
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
            super.onBackPressed();
        }
    }





}
