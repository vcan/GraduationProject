package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.ConsumeRecordInfo;
import com.zszdevelop.bean.GoalInfo;

public interface ConsumeRecordDao {
	
	public boolean insertConsumeRecord(ConsumeRecordInfo consumeRecordInfo,int userId);
	public ArrayList<ConsumeRecordInfo> getConsumeRecordInfo(int userId,int currentPage);
}
