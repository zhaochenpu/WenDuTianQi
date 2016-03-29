package com.wendu.wendutianqi.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.net.Location;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.CitySPUtils;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.FindCityDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by el on 2016/3/25.
 */
public class SelectCity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CoordinatorLayout select_city_coordinatorLayou;
    private Map citymap;
    private List<String> citylist;
    private  Selectdapter mAdapter;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private  String place;
    private TextView select_city_location_name;
    private LinearLayout select_city_location_ll;
    private FindCityDialog findCityDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);

        SystemBarUtil.setStatusBarColor(SelectCity.this,getResources().getColor(R.color.colorPrimary));

        initView();

        mLocationClient = new LocationClient(SelectCity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        Location.initLocation(mLocationClient);
        mLocationClient.start();

        citymap= CitySPUtils.getAll(SelectCity.this);
        if(citymap!=null){
            citylist=new ArrayList<>();
            for (Object entry : citymap.keySet()){
                citylist.add((String)entry);
            }
            recyclerView=(RecyclerView) findViewById(R.id.select_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(SelectCity.this));
            recyclerView.setAdapter(mAdapter = new Selectdapter());
        }

    }

    public void initView(){
        select_city_coordinatorLayou=(CoordinatorLayout) findViewById(R.id.select_city_coordinatorLayou);
        toolbar = (Toolbar) findViewById(R.id.select_toolbar);
        toolbar.setTitle("选择城市");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        setSupportActionBar(toolbar);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select_city_location_ll=(LinearLayout) findViewById(R.id.select_city_location_ll);
        select_city_location_name=(TextView) findViewById(R.id.select_city_location_name);

        select_city_location_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("select_place","location");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        findCityDialog=new FindCityDialog(this,R.style.Dialog);
        findCityDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=findCityDialog.getEditText();
                place=editText.getText().toString();

                findCityDialog.cancel();
            }
        });
        findCityDialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCityDialog.cancel();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_city, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_city:
                findCityDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class Selectdapter extends RecyclerView.Adapter<Selectdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    SelectCity.this).inflate(R.layout.select_city_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    citylist.remove(position);
                    notifyItemRemoved(position);
                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.putExtra("select_place",(String)citylist.get(position));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

            holder.select_city_name.setText((String)citylist.get(position)+" ");

        }

        @Override
        public int getItemCount()
        {
            if(citylist==null){
                return 0;
            }else{
                return citylist.size();
            }

        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView select_city_name;

            public MyViewHolder(View view)
            {
                super(view);
                select_city_name = (TextView) view.findViewById(R.id.select_city_name);
            }
        }
    }


    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(Location.result(SelectCity.this,bdLocation,select_city_coordinatorLayou)){
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
                select_city_location_name.setText(place);
            }
            mLocationClient.stop();
        }
    }

}
