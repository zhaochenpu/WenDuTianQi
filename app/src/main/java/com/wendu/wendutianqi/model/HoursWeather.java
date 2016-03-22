package com.wendu.wendutianqi.model;

/**
 * Created by el on 2016/3/22.
 */
public class HoursWeather {

//    [{"ja":"01","jb":"13","jc":"0","jd":"3","je":"47","jf":"201603221000"},

    private  String ja;//天气代码
    private String jb;//温度
    private String jc;//风向

    public String getJa() {
        return ja2weather(ja);
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getJb() {
        return jb;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public String getJc() {
        return ja2fengxiang(jc);
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getJd() {
        return ja2fengji(jd);
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    private String jd;//风力
    private String jf;//时间

    public String ja2weather(String ja){
        int code=99;
        try {
            code=Integer.parseInt(ja);
            ja="";
        }catch (Exception e){

        }
        switch (code){
            case 0:
                ja="晴";
                break;
            case 1:
                ja="多云";
                break;
            case 2:
                ja="阴";
                break;
            case 3:
                ja="阵雨";
                break;
            case 4:
                ja="雷阵雨";
                break;
            case 5:
                ja="雷阵雨伴有冰雹";
                break;
            case 6:
                ja="雨夹雪";
                break;
            case 7:
                ja="小雨";
                break;
            case 8:
                ja="中雨";
                break;
            case 9:
                ja="大雨";
                break;
            case 10:
                ja="暴雨";
                break;
            case 11:
                ja="大暴雨";
                break;
            case 12:
                ja="特大暴雨";
                break;
            case 13:
                ja="阵雪";
                break;
            case 14:
                ja="小雪";
                break;
            case 15:
                ja="中雪";
                break;
            case 16:
                ja="大雪";
                break;
            case 17:
                ja="暴雪";
                break;
            case 18:
                ja="雾";
                break;
            case 19:
                ja="冻雨";
                break;
            case 20:
                ja="沙尘暴";
                break;
            case 21:
                ja="小到中雨";
                break;
            case 22:
                ja="中到大雨";
                break;
            case 23:
                ja="大到暴雨";
                break;
            case 24:
                ja="暴雨到大暴雨";
                break;
            case 25:
                ja="大暴雨到特大暴雨";
                break;
            case 26:
                ja="小到中雪";
                break;
            case 27:
                ja="中到大雪";
                break;
            case 28:
                ja="大到暴雪";
                break;
            case 29:
                ja="浮尘";
                break;
            case 30:
                ja="扬沙";
                break;
            case 31:
                ja="强沙尘暴";
                break;
            case 53:
                ja="霾";
                break;
            default:
                break;
        }
        return  ja;
    }

    public String ja2fengxiang(String jc){
        int code=99;
        try {
            code=Integer.parseInt(jc);
            jc="";
        }catch (Exception e){

        }
        switch (code){
            case 0:
                jc="无持续风向";
                break;
            case 1:
                jc="东北风";
                break;
            case 2:
                jc="东风";
                break;
            case 3:
                jc="东南风";
                break;
            case 4:
                jc="南风";
                break;
            case 5:
                jc="西南风";
                break;
            case 6:
                jc="西风";
                break;
            case 7:
                jc="西北风";
                break;
            case 8:
                jc="北风";
                break;
            case 9:
                jc="旋转风";
                break;
            default:
                break;
        }
        return  jc;
    }

    public String ja2fengji(String jd){
        int code=99;
        try {
            code=Integer.parseInt(jd);
            jd="";
        }catch (Exception e){

        }
        switch (code){
            case 3:
                jd="微风";
                break;
            case 5:
                jd="3-4级";
                break;

            default:
                break;
        }
        return  jd;
    }


}
