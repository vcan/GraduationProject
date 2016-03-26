package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.RegisterData;
import com.zszdevelop.bean.BaseData;
import com.zszdevelop.dao.ModifyBaseDataDao;
import com.zszdevelop.dao.ModifyUserinfoDao;
import com.zszdevelop.utils.BirthDayUtil;

public class ModifyBaseDataImpl implements ModifyBaseDataDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private PreparedStatement modifyWeightPs = null;
	private ResultSet rs = null;
	private ResultSet modifyWeightRs = null;


	@Override
	public boolean modifyBaseData(int userId, RegisterData baseData) {
		boolean b = false;
		
//private int sex;
//private long birthday;
//private float high;
//private float goalRecordData;
//private int goalRecordType;
//private float actionType;

//	    private float bmi;
//	    private int intakeCC = 1500;
//	    private float consumeREE = 1500;
//	    private float standardWeight = 60;

		// 根据更新
		String sql = "update UserInfo set sex = ?,birthday = ?,high = ? ,actionType = ? ,bmi = ?,intakeCC = ? ,consumeREE = ? ,standardWeight = ? where userId=?";
		String modifyWeight = "update GoalRecordInfo set goalRecordData = ?,goalRecordType = ?  where userId=?";
		
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			//点禁止自动提交，设置回退  
			conn.setAutoCommit(false);   
			
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1,baseData.getSex());
			ps.setString(2, baseData.getBirthday());
			ps.setFloat(3, baseData.getHigh());
			ps.setFloat(4, baseData.getActionType());
			ps.setFloat(5, baseData.getBmi());
			ps.setInt(6, baseData.getIntakeCC());
			ps.setFloat(7, baseData.getConsumeREE());
			ps.setFloat(8, baseData.getStandardWeight());
			ps.setInt(9, userId);
			int executeUpdate = ps.executeUpdate();
			
			modifyWeightPs = (PreparedStatement) conn.prepareStatement(modifyWeight);
			modifyWeightPs.setFloat(1, baseData.getGoalRecordData());
			modifyWeightPs.setInt(2, baseData.getGoalRecordType());
			modifyWeightPs.setInt(3, userId);
			int executeModifyWeight = modifyWeightPs.executeUpdate();
			
			//事务提交  
			conn.commit();  
			if (executeUpdate > 0) {
				if (executeModifyWeight > 0) {
					b = true;
				}else {
					b = false;
				}
				
			}else {
				b = false;
			}
			ps.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
		        if (rs!=null) {
		        	rs.close();

		        }
		        if (modifyWeightRs!=null) {
		        	modifyWeightRs.close();
		        	
		        }
		       
		        if (ps!=null) {
		        	ps.close();
		        }
		        if (modifyWeightPs!=null) {
		        	modifyWeightPs.close();
		        }
		       
		        } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        }
		
		}

		return b;	}

}
