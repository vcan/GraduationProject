package com.zszdevelop.bean;

/**
 * Created by zhangshengzhong on 16/3/13.
 */
public class BodyData {

    private int sex;
    private String birthday;
    private float high;
    private float goalRecordData;
    private int goalRecordType;
    private float actionType;
    private float bmi;
    private int intakeCC = 1500;
    private float consumeREE = 1500;
    private float standardWeight = 60;
    private float maxHeart = 200;
    
    
    public float getMaxHeart() {
		return maxHeart;
	}

	public void setMaxHeart(float maxHeart) {
		this.maxHeart = maxHeart;
	}

	public float getBmi() {
		return bmi;
	}

	public void setBmi(float bmi) {
		this.bmi = bmi;
	}

	public int getIntakeCC() {
		return intakeCC;
	}

	public void setIntakeCC(int intakeCC) {
		this.intakeCC = intakeCC;
	}

	public float getConsumeREE() {
		return consumeREE;
	}

	public void setConsumeREE(float consumeREE) {
		this.consumeREE = consumeREE;
	}

	public float getStandardWeight() {
		return standardWeight;
	}

	public void setStandardWeight(float standardWeight) {
		this.standardWeight = standardWeight;
	}

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

  

    public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
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
