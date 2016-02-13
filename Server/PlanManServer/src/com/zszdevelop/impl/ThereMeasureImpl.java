package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.RegisterData;
import com.zszdevelop.dao.ThereMeasureDao;
import com.zszdevelop.dao.ModifyUserinfoDao;

public class ThereMeasureImpl implements ThereMeasureDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean ModifyThereMeasure(GoalRecordInfo goalRecordInfo, int userId) {
		boolean b = false;

		// 根据更新
		String sql = "insert GoalRecordInfo (goalRecordChest,goalRecordLoin,goalRecordLeftArm,goalRecordRightArm,goalRecordWeight,goalRecordTime,goalRecordShoulder,userId) values(?,?,?,?,?,?,?,?)";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setFloat(1, goalRecordInfo.getGoalRecordChest());
			ps.setFloat(2, goalRecordInfo.getGoalRecordLoin());
			ps.setFloat(3, goalRecordInfo.getGoalRecordLeftArm());
			ps.setFloat(4, goalRecordInfo.getGoalRecordRightArm());
			ps.setFloat(5, goalRecordInfo.getGoalRecordWeight());
			ps.setString(6, goalRecordInfo.getGoalRecordTime());
			ps.setFloat(7, goalRecordInfo.getGoalRecordShoulder());
			ps.setInt(8, userId);
			int executeUpdate = ps.executeUpdate();
			System.out.println(executeUpdate+">>>>jige");
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
	public ArrayList<GoalRecordInfo> getThereMeasure(int userId) {
		
		ArrayList<GoalRecordInfo> lists = new ArrayList<>();
		String sql = "SELECT * FROM GoalRecordInfo WHERE userId=?";
		conn = BaseConnection.getConnection();
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userId);
			 rs = ps.executeQuery();
			while (rs.next()) {
				GoalRecordInfo goalRecordInfo = new GoalRecordInfo();
				goalRecordInfo.setGoalRecordId(rs.getInt("goalRecordId"));
				goalRecordInfo.setGoalRecordChest(rs.getFloat("goalRecordChest"));
				goalRecordInfo.setGoalRecordLeftArm(rs.getFloat("goalRecordLeftArm"));
				goalRecordInfo.setGoalRecordRightArm(rs.getFloat("goalRecordRightArm"));
				goalRecordInfo.setGoalRecordLoin(rs.getFloat("goalRecordLoin"));
				goalRecordInfo.setGoalRecordShoulder(rs.getFloat("goalRecordShoulder"));
				goalRecordInfo.setGoalRecordWeight(rs.getFloat("goalRecordWeight"));
				goalRecordInfo.setGoalRecordTime(rs.getString("goalRecordTime"));
				lists.add(goalRecordInfo);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return lists;
	}

}
