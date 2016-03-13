package com.zszdevelop.planman.bean;

/**
 * Created by zhangshengzhong on 16/3/13.
 */
public class RegisterData {

    private int sex;
    private long birthday;
    private float high;
    private float goalRecordData;
    private int goalRecordType;
    private float actionType;

    public float getActionType() {
        return actionType;
    }

    public void setActionType(float actionType) {
        this.actionType = actionType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getGoalRecordData() {
        return goalRecordData;
    }

    public void setGoalRecordData(float goalRecordData) {
        this.goalRecordData = goalRecordData;
    }

    public int getGoalRecordType() {
        return goalRecordType;
    }

    public void setGoalRecordType(int goalRecordType) {
        this.goalRecordType = goalRecordType;
    }
}
