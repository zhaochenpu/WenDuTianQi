package com.wendu.wendutianqi.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.model.AllChinaPlace;
import com.wendu.wendutianqi.net.MyJson;
import com.wendu.wendutianqi.net.MyOkhttp;
import com.wendu.wendutianqi.net.Urls;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SPUtils;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.SecretTextView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class FirstActivity extends Activity {

	private SecretTextView secretTextView1;
	private String City1,City2;
	private boolean first=true;
	private Calendar calendar;
	private RelativeLayout first_rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
		SystemBarUtil.transparencyBar(FirstActivity.this);
		City2=(String) SPUtils.get(this,"City2","");
		first_rl=(RelativeLayout) findViewById(R.id.first_rl);
		secretTextView1 = (SecretTextView)findViewById(R.id.textview1);

		int random=(int)((Math.random())*4);
		calendar = Calendar.getInstance();
		int  hour=calendar.get(Calendar.HOUR_OF_DAY);

		if((hour>=19&&hour<=21)){
			first_rl.setBackgroundResource(R.color.colorPrimaryDark);
		}else if((hour>=0&&hour<=5)){
			first_rl.setBackgroundResource(R.color.indigo_dark);
		}else if(hour>21&&hour<=23){
			first_rl.setBackgroundResource(R.color.indigo);
		}

		if(random==1){
			if(hour>5&&hour<9){
				secretTextView1.setText("早安!");
			}else if(hour>=0&&hour<5){
				secretTextView1.setText(" 是什么，让你难以入眠？ ");

			}else if(hour>=23){
				secretTextView1.setText("嗨，晚睡的人儿哟");
			}else if(hour>=9&&hour<12){
				secretTextView1.setText("上午好，亲~");
			}else if (hour>=14&&hour<18){
				secretTextView1.setText("下午好~");
			}else if(hour>=19&&hour<23){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String todayWeather= (String) SPUtils.get(FirstActivity.this,"todayweather","");
				if(!TextUtils.isEmpty(todayWeather)&&todayWeather.contains(sdf+"")){
					if(todayWeather.contains("晴")){
						secretTextView1.setText("看星星 一颗两颗三颗四颗 连成线");
					}
				}else{
					secretTextView1.setText("这一天过得怎么样？");
				}
			}
		}else if(random==2){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String todayWeather= (String) SPUtils.get(FirstActivity.this,"todayweather","");
			if(!TextUtils.isEmpty(todayWeather)&&todayWeather.contains(sdf+"")){
//				if(todayWeather.contains("晴")){
//					secretTextView1.setText("今天天气还不错吧");
//				}else
				if (todayWeather.contains("霾")){
					secretTextView1.setText("忘记了从什么时候开始用颜色来描述空气");
				}else if (todayWeather.contains("雪")){
					secretTextView1.setText("人生最美，不过凭栏看落雪");
				}else if (todayWeather.contains("阴")){
					secretTextView1.setText("几朵云在阴天忘了该往哪儿走");
				}else if (todayWeather.contains("云")){
					secretTextView1.setText("我有好多奢望。我想爱，想吃，还想在一瞬间变成天上半明半暗的云");
				}else if(todayWeather.contains("雨")){
					first_rl.setBackgroundResource(R.color.cyan);
					secretTextView1.setText("下雨天你会想起谁？");
				}else {
					secretTextView1.setText("世界的美都是一样的，区别在于你发现美的那份心境");
				}
			}
		}else if(random==3){
			if(hour>5&&hour<11){
				secretTextView1.setText("祝你今天愉快，你明天的愉快留着我明天再祝");
			}else{
				secretTextView1.setText("你好哇");
			}
		}

		first=(boolean) SPUtils.get(this,"first",true);
		if(first){
			new GetAllCity().execute(Urls.ALL_CHINA_CITY);

		}


		secretTextView1.show();    // fade in
		secretTextView1.setDuration(1600);
        secretTextView1.setIsVisible(true);

    	new Handler().postDelayed(new Runnable(){

    	    public void run() {
    	    	secretTextView1.toggle();
    	    	 secretTextView1.setDuration(1200);

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

				if(!first){
					Intent intent=new Intent(FirstActivity.this,OneCityActivity.class);
					startActivity(intent);
					finish();
				}else{
					first=false;
				}
				SPUtils.put(FirstActivity.this,"first",false);
			}else{
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
