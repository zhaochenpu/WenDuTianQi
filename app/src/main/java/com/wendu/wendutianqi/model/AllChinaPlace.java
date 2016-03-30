package com.wendu.wendutianqi.model;

import org.litepal.crud.DataSupport;

/**
 * Created by el on 2016/3/30.
 */
public class AllChinaPlace extends DataSupport {

    private String city;//地点

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    private String prov;//省份
}
