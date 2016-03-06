package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.base.BaseBean;

public class GoalInfo extends BaseBean{
	
	private int goalId;
	private float startGoal;
	private float stopGoal;
	private long startTime;
	private long stopTime;
	private int goalType;

	public int getGoalStatus() {
		return goalStatus;
	}

	public void setGoalStatus(int goalStatus) {
		this.goalStatus = goalStatus;
	}

	private int goalStatus;
	private String goalDescribe;



	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public float getStartGoal() {
		return startGoal;
	}

	public void setStartGoal(float startGoal) {
		this.startGoal = startGoal;
	}

	public float getStopGoal() {
		return stopGoal;
	}

	public void setStopGoal(float stopGoal) {
		this.stopGoal = stopGoal;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public int getGoalType() {
		return goalType;
	}

	public void setGoalType(int goalType) {
		this.goalType = goalType;
	}

	public String getGoalDescribe() {
		return goalDescribe;
	}

	public void setGoalDescribe(String goalDescribe) {
		this.goalDescribe = goalDescribe;
	}
}
