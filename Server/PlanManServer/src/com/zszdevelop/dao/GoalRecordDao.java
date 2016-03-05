package com.zszdevelop.dao;

import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;

public interface GoalRecordDao {
	public boolean insertGoalRecord(GoalRecordInfo goalRecord,int userId);
}
