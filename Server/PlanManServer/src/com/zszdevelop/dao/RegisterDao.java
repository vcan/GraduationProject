package com.zszdevelop.dao;

import com.zszdevelop.bean.BaseUser;

public interface RegisterDao {
	public boolean registerUserInfo(String phone,String userId,String authToken);
}
