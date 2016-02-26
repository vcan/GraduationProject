package com.zszdevelop.planman.bean;

/**
 * Created by zhangshengzhong on 16/2/26.
 */
public class SlidingDeckModel {

    private String SlidingDeckTitle;
    private String SlidingDeckContent;
    private double calory;

    public String getSlidingDeckTitle() {
        return SlidingDeckTitle;
    }

    public void setSlidingDeckTitle(String slidingDeckTitle) {
        SlidingDeckTitle = slidingDeckTitle;
    }

    public String getSlidingDeckContent() {
        return SlidingDeckContent;
    }

    public void setSlidingDeckContent(String slidingDeckContent) {
        SlidingDeckContent = slidingDeckContent;
    }

    public double getCalory() {
        return calory;
    }

    public void setCalory(double calory) {
        this.calory = calory;
    }
}
