package com.zszdevelop.bean;

public class BaseUser {
	private String authToken;
	private int userId;
	private boolean isFirstLogin;

	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isFirstLogin() {
		return isFirstLogin;
	}
	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	
	
	
	

}
