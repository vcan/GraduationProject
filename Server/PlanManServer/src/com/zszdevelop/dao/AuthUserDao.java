package com.zszdevelop.dao;

public interface AuthUserDao {
	
	public boolean isAuthUser(int userId,String authToken);

}
