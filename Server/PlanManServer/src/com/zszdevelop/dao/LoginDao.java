package com.zszdevelop.dao;

import com.zszdevelop.bean.BaseUser;

public interface LoginDao {
	public BaseUser getUserInfo(String phone);
}
