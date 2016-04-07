package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.GoalInfoDao;

public class GoalInfoImpl implements GoalInfoDao{
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;


	@Override
	public boolean insertGoalInfo(GoalInfo goalInfo, int userId) {
		boolean b = false;

		// 根据更新
		String sql = "insert GoalInfo (startGoal,stopGoal,startTime,stopTime,goalType,goalStatus,goalDescribe,userId) values(?,?,?,?,?,?,?,?)";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setFloat(1, goalInfo.getStartGoal());
			ps.setFloat(2, goalInfo.getStopGoal());
			ps.setString(3, goalInfo.getStartTime());
			System.out.println("kaishi shijian"+ goalInfo.getStartTime());
			ps.setString(4, goalInfo.getStopTime());
			ps.setInt(5, goalInfo.getGoalType());
			ps.setInt(6, goalInfo.getGoalStatus());
			ps.setString(7, goalInfo.getGoalDescribe());
			System.out.println(">>>>>"+goalInfo.getGoalDescribe());
			ps.setInt(8, userId);
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

	@Override
	public ArrayList<GoalInfo> getGoalInfo(int userId,int goalStatus) {
		ArrayList<GoalInfo> lists = new ArrayList<>();
		String sql;
		if (goalStatus == ResultCode.ALL_GOAL_RECORED_CODE) {
			sql = "SELECT * FROM GoalInfo WHERE userId=? order by goalId desc";
		}else {
			sql = "SELECT * FROM GoalInfo WHERE userId=? and goalStatus = 0 ";
		}
		 
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
				goalInfo.setGoalDescribe(rs.getString("goalDescribe"));
				lists.add(goalInfo);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lists;
	}

	@Override
	public boolean modifyGoalStatus(int goalId, int userId) {

		boolean b = false;
		

		// 根据更新
		String sql = "update GoalInfo set goalStatus = 1 where userId=? and goalId = ?";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1,userId);
			ps.setInt(2, goalId);
			
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
