package com.wendu.wendutianqi.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AllChinaCity;
import com.wendu.wendutianqi.model.AllChinaPlace;
import com.wendu.wendutianqi.model.DailyForecast;
import com.wendu.wendutianqi.net.MyJson;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SPUtils;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.SecretTextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class FirstActivity extends Activity {

	private SecretTextView secretTextView1;
	private String City1,City2;
	private boolean first=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
		SystemBarUtil.transparencyBar(FirstActivity.this);
		first=(boolean) SPUtils.get(this,"first",true);
		if(first){
			new GetAllCity().execute(Urls.ALL_CHINA_CITY);
			SPUtils.put(this,"first",false);
		}
//		LogUtil.e("................"+first);
		City2=(String) SPUtils.get(this,"City2","");
        secretTextView1 = (SecretTextView)findViewById(R.id.textview1);

		secretTextView1.show();    // fade in
       secretTextView1.setDuration(2000);
        secretTextView1.setIsVisible(true);

    	new Handler().postDelayed(new Runnable(){   

    	    public void run() {   
    	    	secretTextView1.toggle();
    	    	 secretTextView1.setDuration(2000);
				secretTextView1.SecretTextVieweAnimatorlintener(new SecretTextView.SecretTextVieweAnimator(){
					public void OnTAL(){
						Intent intent=null;
						if(TextUtils.isEmpty(City2)){
							intent=new Intent(FirstActivity.this,OneCityActivity.class);
						}
// 						else if(citys.get(0).type==1){
//							intent=new Intent(FirstActivity.this,OneCityActivity.class);
//							intent.putExtra("City1",citys.get(0).city1);
//						}
						if(!first){
							startActivity(intent);
							finish();
						}else {
							first=false;
						}

					}
				});
    	    }   

    	 }, 1800);
	}

	private class GetAllCity extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			return MyOkhttp.get(params[0]);
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(TextUtils.equals(MyJson.getString(result,"status"),"ok")&&!TextUtils.isEmpty(MyJson.getString(result,"city_info"))){
				LogUtil.e(result);
				Gson gson=new Gson();
				List<AllChinaPlace> citylist= gson.fromJson(MyJson.getString(result,"city_info"), new TypeToken<List<AllChinaPlace>>() {}.getType());
				DataSupport.saveAll(citylist);
//				List<AllChinaPlace> chinaPlaceslist=new ArrayList<>();
//				for(int i=0;i<citylist.size();i++){
//					AllChinaPlace allChinaPlace=new AllChinaPlace();
//					allChinaPlace.setCity(citylist.get(i).getCity());
//					allChinaPlace.setProv(citylist.get(i).getProv());
//
//				}
				if(!first){
					Intent intent=new Intent(FirstActivity.this,OneCityActivity.class);
					startActivity(intent);
					finish();
				}else{
					first=false;
				}

			}


		}
	}


}
