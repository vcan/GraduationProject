package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.UserInfo;

public interface UserInfoDao {
	
	public ComsumeCCInfo ModifyUserInfo(UserInfo userInfo);
	public UserInfo getUserInfo(int userId);
}
