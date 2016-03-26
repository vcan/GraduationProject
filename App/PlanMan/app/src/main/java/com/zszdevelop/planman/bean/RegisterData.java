package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.utils.TimeUtil;

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
    private float bmi;
    private int intakeCC = 1500;
    private float consumeREE = 1500;
    private float standardWeight = 60;

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

    public float getBmi() {
//       bmi = goalRecordWeight / (highM * highM)
        bmi = getGoalRecordData()/(getHigh()*getHigh());
        return bmi;
    }

    public float getConsumeREE() {
        try {
            String birthdayStr = String.valueOf(getBirthday());
            if (getSex() == UserConfig.MAN) {// 男性
                consumeREE = (int) ((10 * getGoalRecordData()) + (6.25 * high) - (5 * TimeUtil.BirthDayToAge(birthdayStr)) + 5);
            } else {// 女性
                consumeREE = (int) ((10 * getGoalRecordData()) + (6.25 * high) - (5 * TimeUtil.BirthDayToAge(birthdayStr)) - 161);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return consumeREE;
    }

    public int getIntakeCC() {

        intakeCC = (int) (getConsumeREE() * getActionType());

        return intakeCC;
    }



    public float getStandardWeight() {
        if (getSex() == UserConfig.MAN) {// 男性
            standardWeight = (float) ((high - 80) * 0.7);
        } else {// 女性
            standardWeight = (float) ((high - 70) * 0.6);
        }
        return standardWeight;
    }

}
