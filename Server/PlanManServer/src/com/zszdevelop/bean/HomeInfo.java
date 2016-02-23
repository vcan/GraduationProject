package com.zszdevelop.bean;

import java.util.ArrayList;

public class HomeInfo {
	private int showType;
	private ArrayList<ConsumeRecordInfo> consumeRecordInfos;
	private ArrayList<GoalInfo> goalInfos;
	private ArrayList<GoalRecordInfo> goalRecordInfos;
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public ArrayList<ConsumeRecordInfo> getConsumeRecordInfos() {
		return consumeRecordInfos;
	}
	public void setConsumeRecordInfos(ArrayList<ConsumeRecordInfo> consumeRecordInfos) {
		this.consumeRecordInfos = consumeRecordInfos;
	}
	public ArrayList<GoalInfo> getGoalInfos() {
		return goalInfos;
	}
	public void setGoalInfos(ArrayList<GoalInfo> goalInfos) {
		this.goalInfos = goalInfos;
	}
	public ArrayList<GoalRecordInfo> getGoalRecordInfos() {
		return goalRecordInfos;
	}
	public void setGoalRecordInfos(ArrayList<GoalRecordInfo> goalRecordInfos) {
		this.goalRecordInfos = goalRecordInfos;
	}
	
	
	
	
}
