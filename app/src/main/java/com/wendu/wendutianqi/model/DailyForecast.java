package com.wendu.wendutianqi.model;

/**
 * Created by el on 2016/3/21.
 */
public class DailyForecast {

//    "daily_forecast":[
//    {
//        "astro":{
//        "sr":"06:15",
//                "ss":"18:27"
//    },
//        "cond":{
//        "code_d":"100",
//                "code_n":"101",
//                "txt_d":"晴",
//                "txt_n":"多云"
//    },
//        "date":"2016-03-21",
//            "hum":"17",
//            "pcpn":"0.0",
//            "pop":"0",
//            "pres":"1020",
//            "tmp":{
//        "max":"20",
//                "min":"7"
//    },
//        "vis":"10",
//            "wind":{
//        "deg":"208",
//                "dir":"无持续风向",
//                "sc":"微风",
//                "spd":"4"
//    }
//    },

    private Astro astro;//天文数值
    private Cond cond;//天气情况
    private Tmp tmp;//温度情况
    private Wind wind;//风力情况
    private String date;//日期
    private String hum;//湿度
    private String pcpn;//降雨量
    private String pop;//降水概率
    private String pres;//气压
    private String vis;//能见度


    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }



    public static class Astro{
        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        private String sr;//日出
        private String ss;//日落
    }

    private static class  Cond{
        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }

        //        "txt_d":"晴",
//                "txt_n":"多云"
        private String txt_d;//白天天气描述
        private String txt_n;//夜间天气描述
    }

    public static class Tmp{
        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        private String max;//最高温度
        private String min;//最低温度
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
