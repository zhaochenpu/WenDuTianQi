package com.wendu.wendutianqi.activity;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.fragment.MyMenuFragment;
import com.wendu.wendutianqi.model.WeatherFirst;
import com.wendu.wendutianqi.net.Location;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.utils.ToastUtil;
import com.wendu.wendutianqi.view.ErrorView;
import com.wendu.wendutianqi.view.SystemBarTintManager;
import com.wendu.wendutianqi.view.flowingdrawer.FlowingView;
import com.wendu.wendutianqi.view.flowingdrawer.LeftDrawerLayout;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private int bgColor,titleColor;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ActionBar ab;
    private LeftDrawerLayout mLeftDrawerLayout;
    private SystemBarTintManager tintManager;
    private SwipeRefreshLayout mSwipeLayout;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String place;
    private boolean place2;
    private ErrorView errorView;
    private CoordinatorLayout coordinatorLayout;
    private ImageView headImageView;
    private ScrollView scrollView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_one_city);

        initView();

        mLocationClient = new LocationClient(MainActivity.this);     //声明LocationClient类
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

        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.location_fragment_cl);

        tintManager = new SystemBarTintManager(this);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.location_swipe);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.blue,R.color.light_colorPrimary);
        mSwipeLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeLayout.setRefreshing(true);

        scrollView=(ScrollView) findViewById(R.id.location_sv);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        toolbar = (Toolbar) findViewById(R.id.location_fragment_toolbar);

        toolbar.setLogo(R.mipmap.location_white);
        toolbar.setNavigationIcon(R.mipmap.menu_white);
        setSupportActionBar(toolbar);
//        ab = getSupportActionBar();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.location_fragment_ctl);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        headImageView=(ImageView) findViewById(R.id.location_fragment_iv);

        setHead();

        errorView=(ErrorView) findViewById(R.id.location_fragment_ev);
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
                WeatherFirst weatherFirst=gson.fromJson(result,WeatherFirst.class);
                if(weatherFirst!=null&&!TextUtils.isEmpty(weatherFirst.status)){
                    if(TextUtils.equals("ok",weatherFirst.status)){




                    }else if(TextUtils.equals("unknown city",weatherFirst.status)){
                        if(place2){
                            SnackbarUtil.showLong(coordinatorLayout,"额，很抱歉，没有该地区信息");
                        }else{
                            place2=true;
                            mLocationClient.start();
                        }
                    }else{
                        errorView.LodError();
                    }
                }
            }else {
                errorView.ShowError();
            }

            mSwipeLayout.setRefreshing(false);

        }
    }

    /**
     * 获取图片主颜色 设置状态栏和工具栏
     */
    public void setHead () {


//        headImageView.setDrawingCacheEnabled(true);
//        Bitmap bitmap =headImageView.getDrawingCache();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.time2016);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                bgColor=palette.getDarkVibrantColor(getResources().getColor(R.color.white));
                titleColor=palette.getLightVibrantColor(getResources().getColor(R.color.blue_light));
                collapsingToolbar.setContentScrimColor(bgColor);
                collapsingToolbar.setExpandedTitleColor(titleColor);
                SystemBarUtil.setStatusBarColor(MainActivity.this,bgColor);
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
            if(Location.result(MainActivity.this,bdLocation,coordinatorLayout)){
                LogUtil.e("\n" + bdLocation.getCity()+":" + bdLocation.getCityCode()+"\n" + bdLocation.getDistrict());

                if(place2){
                    place=bdLocation.getCity();
                    if(place.endsWith("市")){
                        place= bdLocation.getDistrict().substring(0,bdLocation.getDistrict().length()-1);
                    }
                }else{
                    place=bdLocation.getDistrict();
                    if(place.endsWith("区")){
                        place= bdLocation.getDistrict().substring(0,bdLocation.getDistrict().length()-1);
                    }
                }

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
