package com.zszdevelop.planman.dao;

import java.util.ArrayList;

import com.zszdevelop.planman.bean.UserinfoBean;

public interface UserinfoDao {
	
	/**
	 * 查询大神列表，并对大神列表进行分页与筛选
	 * @param index
	 * @return
	 */
	public ArrayList<UserinfoBean> getAllGodList(String queryType);

}
