package com.wendu.wendutianqi.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wendu.wendutianqi.R;
import com.wendu.wendutianqi.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by matt on 5/27/14.
 */
public class SecretTextView extends TextView {
    private String mTextString;
    private SpannableString mSpannableString;
    private double[] mAlphas;
    private boolean mIsVisible;
    private boolean mIsTextResetting = false;
    private int mDuration = 1800;
    private Context context;
    public  SecretTextVieweAnimator sta;
    ValueAnimator animator,animator2;
    ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float percent = (Float)valueAnimator.getAnimatedValue();
            resetSpannableString(mIsVisible ? percent : 2.0f - percent);
            if(!mIsVisible){
                setAlpha(2.0f - percent);
            }
        }
    };

    public SecretTextView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public SecretTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init(){
        this.mIsVisible = true;
        animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.addUpdateListener(listener);
        animator.setDuration(mDuration);
        animator2 = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator2.addUpdateListener(listener);
        animator2.setDuration(mDuration);
        int random=(int)((Math.random())*4);
        Calendar calendar = Calendar.getInstance();
        int  hour=calendar.get(Calendar.HOUR_OF_DAY);

        if((hour>=19&&hour<=21)){
            setBackgroundResource(R.color.colorPrimaryDark);
        }else if((hour>=0&&hour<=5)){
            setBackgroundResource(R.color.indigo_dark);
        }else if(hour>21&&hour<=23){
            setBackgroundResource(R.color.indigo);
        }

        if(random==1){
            if(hour>5&&hour<9){
                setText("早安!");
            }else if(hour>=0&&hour<5){
                setText(" 是什么，让你难以入眠？ ");

            }else if(hour>=23){
                setText("嗨，晚睡的人儿哟");
            }else if(hour>=9&&hour<12){
                setText("上午好，亲~");
            }else if (hour>=14&&hour<18){
                setText("下午好~");
            }else if(hour>=19&&hour<23){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String todayWeather= (String) SPUtils.get(context,"todayweather","");
                if(!TextUtils.isEmpty(todayWeather)&&todayWeather.contains(sdf+"")){
                    if(todayWeather.contains("晴")){
                        setText("看星星 一颗两颗三颗四颗 连成线");
                    }
                }else{
                    setText("这一天过得怎么样？");
                }
            }
        }else if(random==2){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String todayWeather= (String) SPUtils.get(context,"todayweather","");
            if(!TextUtils.isEmpty(todayWeather)&&todayWeather.contains(sdf+"")){
//				if(todayWeather.contains("晴")){
//					setText("今天天气还不错吧");
//				}else
                if (todayWeather.contains("霾")){
                    setText("忘记了从什么时候开始用颜色来描述空气");
                }else if (todayWeather.contains("雪")){
                    setText("人生最美，不过凭栏看落雪");
                }else if (todayWeather.contains("阴")){
                    setText("几朵云在阴天忘了该往哪儿走");
                }else if (todayWeather.contains("云")){
                    setText("我有好多奢望。我想爱，想吃，还想在一瞬间变成天上半明半暗的云");
                }else if(todayWeather.contains("雨")){
                    setBackgroundResource(R.color.cyan);
                    setText("下雨天你会想起谁？");
                }else {
                    setText("世界的美都是一样的，区别在于你发现美的那份心境");
                }
            }
        }else if(random==3){
            if(hour>5&&hour<11){
                setText("祝你今天愉快，你明天的愉快留着我明天再祝");
            }else{
                setText("你好哇");
            }
        }
    }

    public interface SecretTextVieweAnimator {

        public void OnTAL();

    }
    public void SecretTextVieweAnimatorlintener (SecretTextVieweAnimator msta){

        this.sta=msta;

    }
    public void toggle(){
        if (mIsVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show(){
        mIsVisible = true;
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(sta!=null){
                    sta.OnTAL();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void hide(){
        mIsVisible = false;
        animator2.start();
        animator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(sta!=null){
                    sta.OnTAL();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public boolean IsVisible(){
        return mIsVisible;
    }

    public void setIsVisible(boolean isVisible){
        mIsVisible = isVisible;
        resetSpannableString(isVisible == true ? 2.0f : 0.0f);
    }

    private void resetSpannableString(double percent){
    	mIsTextResetting = true;
    	
        int color = getCurrentTextColor();
        for(int i=0; i < mSpannableString.length(); i++){
            mSpannableString.setSpan(
                    new ForegroundColorSpan(Color.argb(clamp(mAlphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color))), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(mSpannableString);
        invalidate();
        
        mIsTextResetting = false;
    }

    private void resetAlphas(int length){
        mAlphas = new double[length];
        for(int i=0; i < mAlphas.length; i++){
            mAlphas[i] = Math.random()-1;
        }
    }

    private void resetIfNeeded(){
        if (!mIsTextResetting){
            mTextString = getText().toString();
            mSpannableString = new SpannableString(this.mTextString);
            resetAlphas(mTextString.length());
            resetSpannableString(mIsVisible ? 2.0f : 0);
        }
    }

    public void setText(String text) {
        super.setText(text);
        resetIfNeeded();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        resetIfNeeded();
    }

    private int clamp(double f){
        return (int)(255*Math.min(Math.max(f, 0), 1));
    }

    public void setDuration(int mDuration){
        this.mDuration = mDuration;
        animator.setDuration(mDuration);
    }
}
