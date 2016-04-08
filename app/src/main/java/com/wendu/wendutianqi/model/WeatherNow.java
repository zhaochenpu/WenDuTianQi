package com.wendu.wendutianqi.model;

/**
 * Created by el on 2016/3/21.
 */
public class WeatherNow {

//    "now":{
//        "cond":{
//            "code":"100",
//                    "txt":"晴"
//        },
//        "fl":"17",
//                "hum":"26",
//                "pcpn":"0",
//                "pres":"1023",
//                "tmp":"18",
//                "vis":"7",
//                "wind":{
//            "deg":"190",
//                    "dir":"西风",
//                    "sc":"4-5",
//                    "spd":"19"
//        }
//    },
    private  String fl;//体感温度
    private  String hum;//湿度%
    private  String  pcpn;//降雨量mm
    private  String tmp;//温度
    private  String vis;//能见度（km）

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    private  String pres;//气压(百帕)
    private  Wind wind;
    private   Cond cond;

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public static  class Cond{

        private String code;
        private String txt;//天气描述

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

    }


    public static  class Wind {

        private String dir;//风向(方向)
        private String sc;//风力等级
        private String spd;//风速(Kmph)
        private String deg;//风向(角度)

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }

    }

}
