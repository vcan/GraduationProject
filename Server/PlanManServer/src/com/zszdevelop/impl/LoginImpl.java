package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.dao.LoginDao;

public class LoginImpl implements LoginDao{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;


	@Override
	public BaseUser getUserInfo(String phone) {
		
		BaseUser baseUser = new BaseUser();

		// 根据查询的类型返回数据
		String queryUuid = "SELECT userId,authToken FROM UserInfo WHERE phone=?";
		System.out.println("查询uuid:" + queryUuid);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(queryUuid);
			ps.setString(1, phone);
			rs = ps.executeQuery();

			if (rs.next()) {// 如果有值，把值塞进去返回。
				baseUser.setUserId(rs.getInt("userId"));
				baseUser.setAuthToken(rs.getString("authToken"));
			}else {
				return null;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return baseUser;
	}

	
}
