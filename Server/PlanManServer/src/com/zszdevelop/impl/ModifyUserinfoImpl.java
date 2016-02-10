package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.bean.Userinfo;
import com.zszdevelop.dao.ModifyUserinfoDao;

public class ModifyUserinfoImpl implements ModifyUserinfoDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean modifyUser(Userinfo userinfo) {
		boolean b = false;

		// 根据更新
		String sql = "update UserInfo set sex=? , birthday＝ ？ , high ＝ ？where userId=?";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userinfo.getSex());
			ps.setString(2, userinfo.getBirthday());
			ps.setFloat(3, userinfo.getHigh());
			ps.setFloat(4, userinfo.getBaseUser().getUserId());

			int executeUpdate = ps.executeUpdate();
			if (executeUpdate > 0) {
				System.out.println("修改信息成功");
				b = true;
			} else {
				System.out.println("修改信息失败");
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
