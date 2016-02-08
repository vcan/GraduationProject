package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.dao.UuidDao;

public class UuidImpl implements UuidDao{
	private Connection conn=null;
	private PreparedStatement ps = null;
	private ResultSet rs=null;

	@Override
	public BaseUser getUuidinfo(String uuid) {
		
		boolean b=false;
		BaseUser baseUser = new BaseUser();
		
		// 根据查询的类型返回数据
		String queryUuid="SELECT * FROM UserInfo WHERE uuid=\""+uuid+"\"";
		System.out.println("查询uuid:"+queryUuid);
		try {
			conn=BaseConnection.getConnection();
			ps=(PreparedStatement) conn.prepareStatement(queryUuid);
			rs=ps.executeQuery();
			String line;
			StringBuffer sb=new StringBuffer();
			
			if (rs.next()) {// 如果有值，把值塞进去返回。
				
				baseUser.setUserId(rs.getInt("userId"));
				baseUser.setAuthToken(rs.getString("authToken"));
				
			}else {// 如果没有值，新增一条记录
		
				insertUuid(uuid);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			BaseConnection.closeResource(rs, ps, conn);
		}
		
		return baseUser;
		
	}

	
	@Override
	public void insertUuid(String uuid) {
		
		String insertAuthToken = "我要插入一条数据";
		// 插如uuid和token
		String insertUuid = "insert UserInfo (uuid,authToken) values(?,?)";
		System.out.println(insertUuid);
		
		try {
			conn=BaseConnection.getConnection();
			ps=(PreparedStatement) conn.prepareStatement(insertUuid);
		
			   ps.setString(1, uuid);
			   ps.setString(2, insertAuthToken);
			  boolean isExecute = ps.execute();
			   System.out.println(insertUuid);
			  
			   ps.close();
			   if (isExecute) {
				   getUuidinfo(uuid);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			BaseConnection.closeResource(rs, ps, conn);
		}
		
		
	}
	
	
}
