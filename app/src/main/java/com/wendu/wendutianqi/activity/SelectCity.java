package com.wendu.wendutianqi.activity;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.net.Location;
import com.wendu.wendutianqi.utils.CitySPUtils;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.StatusBarUtil;

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
    private Snackbar Snackbar_remove;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);

        StatusBarUtil.setStatusBarColor(SelectCity.this,R.color.colorPrimary);

        initView();
        initData();

    }

    public void initView(){
        select_city_coordinatorLayou=(CoordinatorLayout) findViewById(R.id.select_city_coordinatorLayou);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            select_city_coordinatorLayou.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onPreDraw() {
                    select_city_coordinatorLayou.getViewTreeObserver().removeOnPreDrawListener(this);
                    Animator animator = ViewAnimationUtils.createCircularReveal(select_city_coordinatorLayou,(select_city_coordinatorLayou.getWidth()/2),(select_city_coordinatorLayou.getHeight()/2)
                            ,0,select_city_coordinatorLayou.getWidth());
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(500);
                    animator.start();
                    return true;
                }
            });
        }

        toolbar = (Toolbar) findViewById(R.id.select_toolbar);
        toolbar.setTitle("选择城市");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
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
                finishActivity();
            }
        });

    }

    public void initData(){
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_city, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_city:
//                findCityDialog.show();
                Intent intent=new Intent(SelectCity.this,TemporaryFind.class);
                intent.putExtra("where","SelectCity");
                startActivityForResult(intent,200);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 200:
                if (resultCode == RESULT_OK) {
                    citylist.clear();
                    citymap= CitySPUtils.getAll(SelectCity.this);
                    for (Object entry : citymap.keySet()){
                        citylist.add((String)entry);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
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
                    final String removePlace=citylist.get(position);
                    CitySPUtils.remove(SelectCity.this,removePlace);
                    citylist.remove(position);
                    notifyItemRemoved(position);
                    Snackbar_remove=SnackbarUtil.LongSnackbar(select_city_coordinatorLayou,removePlace+"已移除收藏列表",SnackbarUtil.Warning).setAction("撤销删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!isFinishing()){
                                CitySPUtils.put(SelectCity.this,removePlace,"1");
                                citylist.add(position,removePlace);
                                notifyItemInserted(position);
                            }
                        }
                    }).setActionTextColor(SnackbarUtil.red);
                    Snackbar_remove.show();
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

        public void addItem(String add){
            citylist.add(add);
            notifyItemInserted(citylist.size());
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

    public void finishActivity(){
        if(Snackbar_remove!=null&&Snackbar_remove.isShown()){
            Snackbar_remove.dismiss();
        }
        finish();
    }


}
