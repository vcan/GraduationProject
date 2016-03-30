package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.ConsumeRecordInfo;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.HomeInfo;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.HomeDao;

public class HomeImpl implements HomeDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private PreparedStatement psQueryGoalRecordInfo = null;
	private PreparedStatement psQueryConsumeRecordInfo = null;
	private PreparedStatement psGoalInfo = null;
	private ResultSet rs = null;
	private ResultSet rsQueryGoalRecordInfo = null;
	private ResultSet rsQueryConsumeRecordInfo = null;
	private ResultSet rsGoalInfo = null;

	@Override
	public HomeInfo getHomeData(String userId) {
		HomeInfo homeInfo = new HomeInfo();
		ArrayList<ConsumeRecordInfo> cRIs=new ArrayList<>();
		ArrayList<GoalInfo> gIs=new ArrayList<>();
		
		ArrayList<GoalRecordInfo> gris=new ArrayList<>();
		
		// 根据查询的类型返回数据
		String queryShowType = "SELECT showType FROM UserInfo WHERE userId=?";
		String queryGoalRecordInfo = "SELECT * FROM GoalRecordInfo WHERE userId=?";
		String queryConsumeRecordInfo = "SELECT * FROM ConsumeRecordInfo WHERE userId=?";
		String queryGoalInfo = "SELECT * FROM GoalInfo WHERE userId=? and goalStatus = 0";//只查询已完成的记录

		try {
			conn = BaseConnection.getConnection();
			//点禁止自动提交，设置回退  
			conn.setAutoCommit(false);   
			ps = (PreparedStatement) conn.prepareStatement(queryShowType);
			ps.setString(1, userId);
			rs = ps.executeQuery();

			psQueryGoalRecordInfo = (PreparedStatement) conn.prepareStatement(queryGoalRecordInfo);
			psQueryGoalRecordInfo.setString(1, userId);
			rsQueryGoalRecordInfo = psQueryGoalRecordInfo.executeQuery();

			psQueryConsumeRecordInfo = (PreparedStatement) conn.prepareStatement(queryConsumeRecordInfo);
			psQueryConsumeRecordInfo.setString(1, userId);
			rsQueryConsumeRecordInfo = psQueryConsumeRecordInfo.executeQuery();
			
			psGoalInfo = (PreparedStatement) conn.prepareStatement(queryGoalInfo);
			psGoalInfo.setString(1, userId);
			rsGoalInfo = psGoalInfo.executeQuery();

			//事务提交  
			conn.commit();  
			
			
			if (rs.next()) {// 如果有值，把值塞进去返回。
				homeInfo.setShowType(rs.getInt("showType"));
				while (rsQueryGoalRecordInfo.next()) {
					GoalRecordInfo goalRecordInfo = new GoalRecordInfo();
					goalRecordInfo.setGoalRecordType(rsQueryGoalRecordInfo.getInt("goalRecordType"));
					goalRecordInfo.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
					goalRecordInfo.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
					goalRecordInfo.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
					gris.add(goalRecordInfo);
				
					}
					
				
				while (rsQueryConsumeRecordInfo.next()) {
					ConsumeRecordInfo consumeRecordInfo = new ConsumeRecordInfo();
					consumeRecordInfo.setConsumeCC(rsQueryConsumeRecordInfo.getInt("consumeCC"));
					consumeRecordInfo.setConsumeRecordContent(rsQueryConsumeRecordInfo.getString("consumeRecordContent"));
					consumeRecordInfo.setConsumeRecordId(rsQueryConsumeRecordInfo.getInt("consumeRecordId"));
					consumeRecordInfo.setConsumeRecordTime(rsQueryConsumeRecordInfo.getString("consumeRecordTime"));
					consumeRecordInfo.setConsumeRecordType(rsQueryConsumeRecordInfo.getInt("consumeRecordType"));
					cRIs.add(consumeRecordInfo);
				}
				while (rsGoalInfo.next()) {
					GoalInfo goalInfo = new GoalInfo();
					goalInfo.setGoalId(rsGoalInfo.getInt("goalId"));
					goalInfo.setGoalStatus(rsGoalInfo.getInt("goalStatus"));
					goalInfo.setGoalType(rsGoalInfo.getInt("goalType"));
					goalInfo.setStartGoal(rsGoalInfo.getFloat("startGoal"));
					goalInfo.setStopGoal(rsGoalInfo.getFloat("stopGoal"));
					goalInfo.setStartTime(rsGoalInfo.getString("startTime"));
					goalInfo.setStopTime(rsGoalInfo.getString("stopTime"));
					gIs.add(goalInfo);
				}
				homeInfo.setConsumeRecordInfos(cRIs);
				homeInfo.setGoalInfos(gIs);
				homeInfo.setGoalRecordInfos(gris);

			} else {
				return null;
			}

		} catch (SQLException e) {
			//操作不成功则回退  
			   
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			e.printStackTrace();
		} finally {
			try {
		        if (rsGoalInfo!=null) {
		        	rsGoalInfo.close();

		        }
		        if (rsQueryConsumeRecordInfo!=null) {
		        	rsQueryConsumeRecordInfo.close();
		        	
		        }
		        if (rsQueryGoalRecordInfo!=null) {
		        	rsQueryGoalRecordInfo.close();
		        	
		        }
		        if (rs!=null) {
		        	rs.close();
		        	
		        }
		        if (psGoalInfo!=null) {
		        	psGoalInfo.close();
		        }
		        if (psQueryConsumeRecordInfo!=null) {
		        	psQueryConsumeRecordInfo.close();
		        }
		        if (psQueryGoalRecordInfo!=null) {
		        	psQueryGoalRecordInfo.close();
		        }
		        if (ps!=null) {
		        	ps.close();
		        }
		        if (conn!=null) {
		            conn.close();
		        }
		        } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        }

		}

		return homeInfo;

	}

}
