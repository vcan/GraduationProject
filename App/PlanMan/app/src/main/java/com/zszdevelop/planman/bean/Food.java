package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.base.BaseBean;

/**
 * Created by ShengZhong on 2016/2/24.
 */
public class Food extends BaseBean{

    private String id;
    private String name;
    private double calory;

    public double getCalory() {
        return calory;
    }

    public void setCalory(double calory) {
        this.calory = calory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
