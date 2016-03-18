package com.wendu.wendutianqi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.view.flowingdrawer.MenuFragment;


public class MyMenuFragment extends MenuFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,false);

        return  setupReveal(view) ;
    }



}
