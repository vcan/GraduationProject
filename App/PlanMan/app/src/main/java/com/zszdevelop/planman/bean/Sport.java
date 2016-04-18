package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.base.BaseBean;

/**
 * Created by ShengZhong on 2016/2/24.
 */
public class Sport extends BaseBean{

    private String id;
    private String name;
    private double mets;



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
