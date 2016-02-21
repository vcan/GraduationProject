package com.zszdevelop.planman.bean;

import java.util.ArrayList;

public class HomeInfo {
	private int showType;
	private ArrayList<ConsumeRecordInfo> consumeRecordInfos;
	private ArrayList<GoalInfo> goalInfos;
	private ArrayList<WeightRecord> weightRecords;
	private ArrayList<ChestRecord> chestRecords;
	private ArrayList<LoinRecord> loinRecords;
	private ArrayList<LeftArmRecord> leftArmRecords;
	private ArrayList<RightArmRecord> rightArmRecords;
	private ArrayList<ShoulderRecord> shoulderRecords;

	
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

	public ArrayList<ConsumeRecordInfo> getConsumeRecordInfos() {
		return consumeRecordInfos;
	}
	public void setConsumeRecordInfos(ArrayList<ConsumeRecordInfo> consumeRecordInfos) {
		this.consumeRecordInfos = consumeRecordInfos;
	}
	public ArrayList<WeightRecord> getWeightRecords() {
		return weightRecords;
	}
	public void setWeightRecords(ArrayList<WeightRecord> weightRecords) {
		this.weightRecords = weightRecords;
	}
	public ArrayList<ChestRecord> getChestRecords() {
		return chestRecords;
	}
	public void setChestRecords(ArrayList<ChestRecord> chestRecords) {
		this.chestRecords = chestRecords;
	}
	public ArrayList<LoinRecord> getLoinRecords() {
		return loinRecords;
	}
	public void setLoinRecords(ArrayList<LoinRecord> loinRecords) {
		this.loinRecords = loinRecords;
	}
	public ArrayList<LeftArmRecord> getLeftArmRecords() {
		return leftArmRecords;
	}
	public void setLeftArmRecords(ArrayList<LeftArmRecord> leftArmRecords) {
		this.leftArmRecords = leftArmRecords;
	}
	public ArrayList<RightArmRecord> getRightArmRecords() {
		return rightArmRecords;
	}
	public void setRightArmRecords(ArrayList<RightArmRecord> rightArmRecords) {
		this.rightArmRecords = rightArmRecords;
	}
	public ArrayList<ShoulderRecord> getShoulderRecords() {
		return shoulderRecords;
	}
	public void setShoulderRecords(ArrayList<ShoulderRecord> shoulderRecords) {
		this.shoulderRecords = shoulderRecords;
	}
	
	
	
}
