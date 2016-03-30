package com.wendu.wendutianqi.model;

import org.litepal.crud.DataSupport;

/**
 * Created by el on 2016/3/30.
 */
public class City extends DataSupport {

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    private int type;
    private String city1;
    private String city2;

}
