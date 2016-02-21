package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.ChestRecord;
import com.zszdevelop.bean.ConsumeRecordInfo;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.HomeInfo;
import com.zszdevelop.bean.LeftArmRecord;
import com.zszdevelop.bean.LoinRecord;
import com.zszdevelop.bean.RightArmRecord;
import com.zszdevelop.bean.ShoulderRecord;
import com.zszdevelop.bean.WeightRecord;
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
		
		ArrayList<WeightRecord> wrs=new ArrayList<>();
		ArrayList<ChestRecord> crs=new ArrayList<>();
		ArrayList<LoinRecord> lrs=new ArrayList<>();
		ArrayList<LeftArmRecord> lars=new ArrayList<>();
		ArrayList<RightArmRecord> rars=new ArrayList<>();
		ArrayList<ShoulderRecord> srs=new ArrayList<>();

		// 根据查询的类型返回数据
		String queryShowType = "SELECT showType FROM UserInfo WHERE userId=?";
		String queryGoalRecordInfo = "SELECT * FROM GoalRecordInfo WHERE userId=?";
		String queryConsumeRecordInfo = "SELECT * FROM ConsumeRecordInfo WHERE userId=?";
		String queryGoalInfo = "SELECT * FROM GoalInfo WHERE userId=?";

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
					int goalRecordType = rsQueryGoalRecordInfo.getInt("goalRecordType");
					switch (goalRecordType) {
					case ResultCode.WEIGHT_CODE:
						WeightRecord weightRecord = new WeightRecord();
						weightRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						weightRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						weightRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						wrs.add(weightRecord);
						break;
					case ResultCode.CHEST_CODE:
						ChestRecord chestRecord = new ChestRecord();
						chestRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						chestRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						chestRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						crs.add(chestRecord);
						break;
					case ResultCode.LOIN_CODE:
						LoinRecord loinRecord = new LoinRecord();
						loinRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						loinRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						loinRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						lrs.add(loinRecord);
						break;
					case ResultCode.LEFT_ARM_CODE:
						LeftArmRecord leftArmRecord = new LeftArmRecord();
						leftArmRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						leftArmRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						leftArmRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						lars.add(leftArmRecord);
						break;
					case ResultCode.RIGHT_ARM_CODE:
						RightArmRecord rightArmRecord = new RightArmRecord();
						rightArmRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						rightArmRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						rightArmRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						rars.add(rightArmRecord);
						break;
					case ResultCode.SHOULDER_CODE:
						ShoulderRecord shoulderRecord = new ShoulderRecord();
						shoulderRecord.setGoalRecordTime(rsQueryGoalRecordInfo.getString("goalRecordTime"));
						shoulderRecord.setGoalRecordId(rsQueryGoalRecordInfo.getInt("goalRecordId"));
						shoulderRecord.setGoalRecordData(rsQueryGoalRecordInfo.getFloat("goalRecordData"));
						srs.add(shoulderRecord);
						break;

					default:
						break;
					}
					
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
				homeInfo.setWeightRecords(wrs);
				homeInfo.setChestRecords(crs);
				homeInfo.setLoinRecords(lrs);
				homeInfo.setLeftArmRecords(lars);
				homeInfo.setRightArmRecords(rars);
				homeInfo.setShoulderRecords(srs);

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
