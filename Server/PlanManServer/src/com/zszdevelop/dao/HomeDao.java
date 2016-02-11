package com.zszdevelop.dao;


import com.zszdevelop.bean.HomeInfo;

public interface HomeDao {

	/**
	 * 查询首页数据
	 * @param userId
	 * @return
	 */
	public HomeInfo getHomeData(String userId);
}
