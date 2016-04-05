package com.zszdevelop.planman.bean;

public class GoalRecordInfo {
	private int goalRecordId;
	private long goalRecordTime;
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

	public float getGoalRecordData() {
		return goalRecordData;
	}
	public void setGoalRecordData(float goalRecordData) {
		this.goalRecordData = goalRecordData;
	}

	public long getGoalRecordTime() {
		return goalRecordTime;
	}

	public void setGoalRecordTime(long goalRecordTime) {
		this.goalRecordTime = goalRecordTime;
	}
}
