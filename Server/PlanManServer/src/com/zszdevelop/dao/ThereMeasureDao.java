package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.GoalRecordInfo;

public interface ThereMeasureDao {

	public boolean ModifyThereMeasure(GoalRecordInfo goalRecordInfo,int userId);
	public ArrayList<GoalRecordInfo> getThereMeasure(int userId);
}
