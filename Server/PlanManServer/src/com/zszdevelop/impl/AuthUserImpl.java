package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.dao.AuthUserDao;

public class AuthUserImpl implements AuthUserDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean isAuthUser(int userId, String authToken) {
		boolean b = false;
		// 根据查询的类型返回数据
		String queryUuid = "SELECT authToken FROM UserInfo WHERE userId=?";
		System.out.println("登陆验证查询 :" + queryUuid);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(queryUuid);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (authToken.equals(rs.getString("authToken"))) {
					System.out.println("验证通过");
					b = true;
				} else {
					b = false;
					System.out.println("没有通过验证");
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return b;
	}

}
