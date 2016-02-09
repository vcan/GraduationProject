package com.zszdevelop.dao;

import java.util.ArrayList;

import com.zszdevelop.bean.BaseUser;


public interface UuidDao {
	
	public BaseUser getUuidinfo(String uuid);
	public void insertUuid(String uuid);
	public boolean isExist(String uuid);
}
