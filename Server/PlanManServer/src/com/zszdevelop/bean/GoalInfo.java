package com.zszdevelop.bean;

public class GoalInfo {
	
	private int goalId;
	private float startGoal;
	private float stopGoal;
	private String startTime;
	private String stopTime;
	private int goalType;
	public int getGoalType() {
		return goalType;
	}
	public void setGoalType(int goalType) {
		this.goalType = goalType;
	}
	private int goalStatus;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getGoalStatus() {
		return goalStatus;
	}
	public void setGoalStatus(int goalStatus) {
		this.goalStatus = goalStatus;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	

}
