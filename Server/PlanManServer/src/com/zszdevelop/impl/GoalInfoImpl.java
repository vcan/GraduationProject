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

public class GoalInfoImpl implements GoalInfoDao{
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;


	@Override
	public boolean insertGoalInfo(GoalInfo goalInfo, int userId) {
		boolean b = false;

		// 根据更新
		String sql = "insert GoalInfo (startGoal,stopGoal,startTime,stopTime,goalType,goalStatus,userId) values(?,?,?,?,?,?,?)";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setFloat(1, goalInfo.getStartGoal());
			ps.setFloat(2, goalInfo.getStopGoal());
			ps.setString(3, goalInfo.getStartTime());
			ps.setString(4, goalInfo.getStopTime());
			ps.setInt(5, goalInfo.getGoalType());
			ps.setInt(6, goalInfo.getGoalStatus());
			ps.setFloat(7, userId);
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate > 0) {
				b = true;
			} else {
				b = false;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return b;
	}

	@Override
	public ArrayList<GoalInfo> getGoalInfo(int userId) {
		ArrayList<GoalInfo> lists = new ArrayList<>();
		String sql = "SELECT * FROM GoalInfo WHERE userId=?";
		conn = BaseConnection.getConnection();
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userId);
			 rs = ps.executeQuery();
			while (rs.next()) {
				GoalInfo goalInfo = new GoalInfo();
				goalInfo.setGoalId(rs.getInt("goalId"));
				goalInfo.setGoalStatus(rs.getInt("goalStatus"));
				goalInfo.setGoalType(rs.getInt("goalType"));
				goalInfo.setStartGoal(rs.getFloat("startGoal"));
				goalInfo.setStopGoal(rs.getFloat("stopGoal"));
				goalInfo.setStartTime(rs.getString("startTime"));
				goalInfo.setStopTime(rs.getString("stopTime"));
				lists.add(goalInfo);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lists;
	}

}
