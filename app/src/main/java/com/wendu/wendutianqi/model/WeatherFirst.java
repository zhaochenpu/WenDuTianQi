package com.wendu.wendutianqi.model;

/**
 * Created by el on 2016/3/18.
 */
public class WeatherFirst {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status ;
    public String basic;//城市基本信息

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(String daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public String getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(String hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public String aqi ;
    public String suggestion ;//生活指数
    public String now;//实况天气
    public String daily_forecast ;//天气预报
    public String hourly_forecast ;//每小时天气预报
}
