package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.base.BaseBean;

/**
 * Created by zhangshengzhong on 16/2/22.
 */
public class ShowType extends BaseBean {
    public String getShowTypeStr() {
        return showTypeStr;
    }

    public void setShowTypeStr(String showTypeStr) {
        this.showTypeStr = showTypeStr;
    }

    private String showTypeStr;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    private int showType;


}
