package com.zszdevelop.bean;

import java.util.ArrayList;

public class HomeInfo {
	private int showType;
	private ArrayList<GoalRecordInfo> goalRecordInfos;
	private ArrayList<ConsumeRecordInfo> consumeRecordInfos;
	private ArrayList<GoalInfo> goalInfos;
	
	public ArrayList<GoalInfo> getGoalInfos() {
		return goalInfos;
	}
	public void setGoalInfos(ArrayList<GoalInfo> goalInfos) {
		this.goalInfos = goalInfos;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public ArrayList<GoalRecordInfo> getGoalRecordInfos() {
		return goalRecordInfos;
	}
	public void setGoalRecordInfos(ArrayList<GoalRecordInfo> goalRecordInfos) {
		this.goalRecordInfos = goalRecordInfos;
	}
	public ArrayList<ConsumeRecordInfo> getConsumeRecordInfos() {
		return consumeRecordInfos;
	}
	public void setConsumeRecordInfos(ArrayList<ConsumeRecordInfo> consumeRecordInfos) {
		this.consumeRecordInfos = consumeRecordInfos;
	}
	
	
}
