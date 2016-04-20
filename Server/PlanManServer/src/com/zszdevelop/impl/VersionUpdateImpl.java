package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.UpdateInfo;
import com.zszdevelop.dao.AuthUserDao;
import com.zszdevelop.dao.VersionUpdateDao;

public class VersionUpdateImpl implements VersionUpdateDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public UpdateInfo getVersionUpdate() {
		UpdateInfo updateInfo = new UpdateInfo();
		// 根据查询的类型返回数据
				String query = "SELECT * FROM AppVersion";
				System.out.println("登陆验证查询 :" + query);
				try {
					conn = BaseConnection.getConnection();
					ps = (PreparedStatement) conn.prepareStatement(query);
					rs = ps.executeQuery();

					while (rs.next()) {
						updateInfo.setCode(rs.getInt("code"));
						updateInfo.setDescription(rs.getString("description"));
						updateInfo.setName(rs.getString("name"));
						updateInfo.setUrl(rs.getString("url"));
						updateInfo.setVersionName(rs.getString("versionName"));
						
					}
				} catch (SQLException e) {

					e.printStackTrace();
				} finally {
					BaseConnection.closeResource(rs, ps, conn);
				}

				return updateInfo;
	}

}
