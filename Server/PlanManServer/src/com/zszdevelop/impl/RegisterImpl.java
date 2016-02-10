package com.zszdevelop.impl;

import java.awt.geom.Ellipse2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.dao.RegisterDao;

public class RegisterImpl implements RegisterDao{
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	
	@Override
	public boolean registerUserInfo(String phone,String userId,String authToken) {
		
		boolean b = false;
		

		// 根据更新
		String sql = "update UserInfo set phone=? where userId=? and authToken = ?";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setString(2, userId);
			ps.setString(3, authToken);
			
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate > 0) {
				b = true;
			}else {
				b = false;
			}
			ps.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return b;
	}


	

}
