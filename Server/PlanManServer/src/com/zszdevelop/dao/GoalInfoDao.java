package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.GoalInfo;

public interface GoalInfoDao {
	
	public boolean insertGoalInfo(GoalInfo goalInfo,int userId);
	
	public ArrayList<GoalInfo> getGoalInfo(int userId);
	public boolean modifyGoalStatus(int goalId,int userId);

}
