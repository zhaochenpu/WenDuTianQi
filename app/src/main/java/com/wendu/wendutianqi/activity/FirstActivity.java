package com.wendu.wendutianqi.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.LogUtil;
import com.wendu.wendutianqi.utils.SPUtils;
import com.wendu.wendutianqi.utils.SystemBarUtil;
import com.wendu.wendutianqi.view.SecretTextView;


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

			SPUtils.put(this,"first",false);
		}
		LogUtil.e("................"+first);
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
						startActivity(intent);
						finish();
					}
				});
    	    }   

    	 }, 1800);
	}
}
