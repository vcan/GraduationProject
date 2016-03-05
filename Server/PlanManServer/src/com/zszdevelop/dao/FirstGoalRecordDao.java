package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;

public interface FirstGoalRecordDao {
	public GoalRecordInfo getFirstGoalRecord(int userId,int goalRecordType);
}
