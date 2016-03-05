package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.dao.FirstGoalRecordDao;

public class FirstGoalRecordImpl implements FirstGoalRecordDao{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public GoalRecordInfo getFirstGoalRecord(int userId,int goalRecordType) {
		
		GoalRecordInfo goalRecordInfo = new GoalRecordInfo();
		String sql = "SELECT * FROM GoalRecordInfo where goalRecordType = ? and userId =? LIMIT 1";
		
		conn = BaseConnection.getConnection();
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, goalRecordType);
			ps.setInt(2, userId);
			 rs = ps.executeQuery();
			while (rs.next()) {
				goalRecordInfo.setGoalRecordData(rs.getFloat("goalRecordData"));
				goalRecordInfo.setGoalRecordId(rs.getInt("goalRecordId"));
				goalRecordInfo.setGoalRecordTime(rs.getString("goalRecordTime"));
				goalRecordInfo.setGoalRecordType(rs.getInt("goalRecordType"));
				goalRecordInfo.setGoalRecordData(rs.getFloat("goalRecordData"));
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		

		return goalRecordInfo;
	}

}
