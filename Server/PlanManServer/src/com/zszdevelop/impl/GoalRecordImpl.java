package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.dao.GoalRecordDao;

public class GoalRecordImpl implements GoalRecordDao{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean insertGoalRecord(GoalRecordInfo goalRecord, int userId) {
		boolean b = false;

		// 根据更新
		
		String sql = "insert GoalRecordInfo (userId,goalRecordTime,goalRecordType,goalRecordData) values(?,?,?,?)";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, goalRecord.getGoalRecordTime());
			ps.setInt(3, goalRecord.getGoalRecordType());
			ps.setFloat(4, goalRecord.getGoalRecordData());
			
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate > 0) {
				b = true;
			} else {
				b = false;
			}

		} catch (SQLException e) {

			e.printStackTrace();
			b= false;
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return b;
	}

	
}
