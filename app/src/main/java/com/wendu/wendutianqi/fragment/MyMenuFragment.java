package com.wendu.wendutianqi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.SnackbarUtil;
import com.wendu.wendutianqi.utils.ToastUtil;
import com.wendu.wendutianqi.view.flowingdrawer.MenuFragment;


public class MyMenuFragment extends MenuFragment {

    private  View view,headview;
    private ImageView menu_header_iv;
    private NavigationView navigationView;
    public  MenuItemSelectedListener misl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_menu, container,false);
        initView();
        return  setupReveal(view) ;
    }


    public  void  initView(){

        navigationView=(NavigationView) view.findViewById(R.id.Navigation);
        headview=navigationView.getHeaderView(0);
        menu_header_iv=(ImageView) headview.findViewById(R.id.menu_header_iv);
        menu_header_iv.setImageResource(R.mipmap.time2016);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                misl.MenuItemSelectedListener(item);
                return false;
            }
        });
    }

//    public NavigationView getNavigationView(){
//        return navigationView;
//    }

    public interface MenuItemSelectedListener{
        public void MenuItemSelectedListener(MenuItem item);
    }

    public void setMenuItemSelectedListener(MenuItemSelectedListener misl){
        this.misl=misl;
    }

}
