package com.wendu.wendutianqi.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AQI;
import com.wendu.wendutianqi.model.AllChinaPlace;
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
import com.wendu.wendutianqi.view.FindCityAdapter;
import com.wendu.wendutianqi.view.FindCityDialog;
import com.wendu.wendutianqi.view.HoursCard;
import com.wendu.wendutianqi.view.NowCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by noname on 2016/3/26.
 */
public class TemporaryFind extends AppCompatActivity{

    private String place,cityId;
    private boolean placeCan=false;//地址是否可用
    private boolean place2=false;//第二次查询地址（去掉“市”）
    private boolean collection;
    private SwipeRefreshLayout mSwipeLayout;
    private ErrorView errorView;
    private CoordinatorLayout coordinatorLayout;
    private NowCard nowCard;
    private HoursCard hoursCard;
    private DailyCard dailyCard;
    private Toolbar toolbar;
    private ScrollView temporary_find_scroll;
    private SearchView searchView;
    private MenuItem menuItemSearch,menuItemCollection;
    private String from;//从哪来
    private RecyclerView temporary_find_recycler;
    private FindCityAdapter findCityAdapter;
    private List<AllChinaPlace>  citylist,queryData=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_find);

        SystemBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));

        Intent intent=getIntent();
        from=intent.getStringExtra("where");


        initView();

        setListener();

    }

    private void initView(){

        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.temporary_find_CoordinatorLayout);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.temporary_find_swipe);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.blue,R.color.light_colorPrimary);
        mSwipeLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeLayout.setVisibility(View.GONE);

        toolbar = (Toolbar) findViewById(R.id.temporary_find_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        setSupportActionBar(toolbar);

        nowCard=(NowCard) findViewById(R.id.temporary_find_nowcard);
        hoursCard=(HoursCard) findViewById(R.id.temporary_find_hourscard);
        dailyCard=(DailyCard) findViewById(R.id.temporary_find_dailycard);

        errorView=(ErrorView) findViewById(R.id.temporary_find_error);

        temporary_find_scroll=(ScrollView) findViewById(R.id.temporary_find_scroll);
        temporary_find_scroll.setVisibility(View.GONE);


        temporary_find_recycler=(RecyclerView) findViewById(R.id.temporary_find_recycler);
        temporary_find_recycler .setLayoutManager(new LinearLayoutManager(TemporaryFind.this));
        temporary_find_recycler.setAdapter(findCityAdapter=new FindCityAdapter(TemporaryFind.this,queryData));

        initRecyclerview();
    }

    public void setListener(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBack();
            }
        });

        findCityAdapter.setOnItemClickLitener(new FindCityAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick( String itemPlace) {
                getWeather(itemPlace);
            }
        });
        setSwipeLayoutRefreshListener();
    }

    public void setSwipeLayoutRefreshListener(){
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!TextUtils.isEmpty(place)){
                    new GetWeatherData().execute(Urls.WEATHER_URL);
                }else{
                    SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，改地址不可用...");
                }
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection_city, menu);
        menuItemCollection=menu.findItem(R.id.collection_city);
        menuItemSearch=menu.findItem(R.id.find_city);
        if(menuItemSearch!=null){
            menuItemSearch.setChecked(true);
            menuItemSearch.expandActionView();
            searchView=(SearchView) MenuItemCompat.getActionView(menuItemSearch);

            if(searchView!=null){
                searchView.onActionViewExpanded();
                searchView.setQueryHint("请输入城市名称");
                searchView.setSubmitButtonEnabled(true);
                searchView.setIconifiedByDefault(true);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        getWeather(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if(temporary_find_recycler!=null&&temporary_find_recycler.getVisibility()==View.GONE){
                            temporary_find_recycler.setVisibility(View.VISIBLE);
                        }
                        if(mSwipeLayout.getVisibility()==View.VISIBLE){
                            mSwipeLayout.setVisibility(View.GONE);
                            temporary_find_scroll.setVisibility(View.GONE);
                        }

                        if(citylist!=null){
                            query(newText);
                        }
                        return false;
                    }
                });

            }

        }

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.collection_city:
                if(collection){
                    collection=false;
                    CitySPUtils.remove(TemporaryFind.this,place);
                    menuItemCollection.setIcon(R.mipmap.ic_star_border_white);
                    SnackbarUtil.showLongConfirm(coordinatorLayout," 该地址已取消收藏");
                }else{
                    if(placeCan){
                        CitySPUtils.put(TemporaryFind.this,place,"1");
                        collection=true;
                        menuItemCollection.setIcon(R.mipmap.ic_star_white);
                        SnackbarUtil.showLongConfirm(coordinatorLayout," 该地址已添加至收藏夹");
                    }else{
                        SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，改地址不可用...");
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initRecyclerview(){

        new Thread(new Runnable(){
            @Override
            public void run() {
               citylist= DataSupport.findAll(AllChinaPlace.class);
            }
        }).start();

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
                    place=MyJson.getString(basic,"city");
                    toolbar.setTitle(place);

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
                    dailyCard.setData(dailyForecast);

                    placeCan=true;

                    menuItemSearch.setChecked(false);
                    menuItemSearch.collapseActionView();
                    searchView.onActionViewCollapsed();
                    SnackbarUtil.showShortInfo(coordinatorLayout," 天气数据已更新 ~O(∩_∩)O~");
                    if(temporary_find_recycler!=null){
                        temporary_find_recycler.setVisibility(View.GONE);
                    }
                    mSwipeLayout.setVisibility(View.VISIBLE);
                    temporary_find_scroll.setVisibility(View.VISIBLE);
                }else if(TextUtils.equals("unknown city",status)){
                        if(place2){
                            placeCan=false;
                        SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
                            mSwipeLayout.setVisibility(View.GONE);
                        }else{
                            place2=true;

                            if(place.contains("市")){

                                place=place.replace("市","");
                                new GetWeatherData().execute(Urls.WEATHER_URL);

                            }else{
                                placeCan=false;
                                SnackbarUtil.showLongWarning(coordinatorLayout," 额，很抱歉，没有该地区信息...");
                                mSwipeLayout.setVisibility(View.GONE);
                            }

                        }
                }else{
                    temporary_find_scroll.setVisibility(View.VISIBLE);
//                    mSwipeLayout.setOnRefreshListener(null);
                    mSwipeLayout.setVisibility(View.VISIBLE);

                    errorView.LodError();
                }

            }else {
                temporary_find_scroll.setVisibility(View.VISIBLE);
//                mSwipeLayout.setOnRefreshListener(null);
                mSwipeLayout.setVisibility(View.VISIBLE);
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


    public void onBackPressed() {
        setBack();
    }

    public void setBack(){
        if(menuItemSearch.isActionViewExpanded()&&placeCan){
            searchView.onActionViewCollapsed();
            temporary_find_recycler.setVisibility(View.GONE);
            mSwipeLayout.setVisibility(View.VISIBLE);
            temporary_find_scroll.setVisibility(View.VISIBLE);
        }
        if(TextUtils.equals(from,"SelectCity")&&collection){
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
        }

        finish();
    }


    public void getWeather(String where){
        place=where;
        mSwipeLayout.setVisibility(View.VISIBLE);
        mSwipeLayout.setRefreshing(true);
        new GetWeatherData().execute(Urls.WEATHER_URL);
//        temporary_find_recycler.setVisibility(View.GONE);
    }

    public void query(String queryText){
        queryData.clear();
        for(AllChinaPlace place:citylist){
            if(place.getCity().contains(queryText)){
                queryData.add(place);
            }
        }
        findCityAdapter.notifyDataSetChanged();
    }

}
