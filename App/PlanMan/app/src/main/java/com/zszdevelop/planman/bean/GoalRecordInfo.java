package com.zszdevelop.planman.bean;

public class GoalRecordInfo {
	private int goalRecordId;
	private String goalRecordTime;
	private float goalRecordData;
	private int goalRecordType;
	
	public int getGoalRecordType() {
		return goalRecordType;
	}
	public void setGoalRecordType(int goalRecordType) {
		this.goalRecordType = goalRecordType;
	}
	public int getGoalRecordId() {
		return goalRecordId;
	}
	public void setGoalRecordId(int goalRecordId) {
		this.goalRecordId = goalRecordId;
	}
	public String getGoalRecordTime() {
		return goalRecordTime;
	}
	public void setGoalRecordTime(String goalRecordTime) {
		this.goalRecordTime = goalRecordTime;
	}
	public float getGoalRecordData() {
		return goalRecordData;
	}
	public void setGoalRecordData(float goalRecordData) {
		this.goalRecordData = goalRecordData;
	}

}
