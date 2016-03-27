package com.wendu.wendutianqi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.SystemBarUtil;

/**
 * Created by noname on 2016/3/26.
 */
public class TemporaryFind extends AppCompatActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_city);

        SystemBarUtil.setStatusBarColor(this,R.color.colorPrimary);

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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
