package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.BaseData;
import com.zszdevelop.dao.ModifyUserinfoDao;
import com.zszdevelop.utils.BirthDayUtil;

public class ModifyUserinfoImpl implements ModifyUserinfoDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public ComsumeCCInfo modifyUser(BaseData userinfo) {
		float goalRecordWeight = userinfo.getGoalRecordWeight();
		float high = userinfo.getHigh();

		// 活 动 内 容 活动系数
		// 卧床（全天） 1.2
		// 轻活动生活模式（多坐或缓步） 1.3
		// 一般活动度 1.5~1.75
		// 活动量大的生活模式（重工作者） 2.0
		float actionType = userinfo.getActionType();

		// 男性静态能量消耗值 男性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) ＋ 5
		// 女性静态能量消耗值 女性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) - 161
		// 其每天所需的热量 = REE × 活动系数 = 1294 × 1.5 = 1941(大卡)
		int intakeCC = 1500;
		float consumeREE = 1500;
		try {
			if (userinfo.getSex() == 0) {// 男性
				consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)- (5 * BirthDayUtil.BirthDayToAge(userinfo.getBirthday())) + 5);
			} else {// 女性
				consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)- (5 * BirthDayUtil.BirthDayToAge(userinfo.getBirthday())) - 161);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		intakeCC = (int) (consumeREE * actionType);

		// 需要消耗的热量
		// 		一旦热量累积到达7700大卡，你的体重就会增加一公斤
		// 		怎么样的减肥速度最健康、最有效率呢？答案是每周减0.5-1公斤。
		//  	新手：每周瘦0.5公斤，7000/2/7 = 550 每天要多消耗550大卡 
		int consumeCC = (int) (intakeCC +550);
		
		
		ComsumeCCInfo comsumeCCInfo = new ComsumeCCInfo();
		float highM = high/100;
		comsumeCCInfo.setBMI(goalRecordWeight / (highM * highM));
		comsumeCCInfo.setConsumeCC(consumeCC);
		comsumeCCInfo.setIntakeCC(intakeCC);
		comsumeCCInfo.setConsumeREE((int)consumeREE);

		
		
		// 基本数据更新更新
		String sqlUserInfo = "update UserInfo set sex = ?,birthday = ?,high = ?,consumeCC = ?,intakeCC = ?,consumeREE = ?where userId = ?";
		System.out.println("基本数据更新更新的sql:" + sqlUserInfo);
		// 更新当前三围体重
//		String sqlGoalRecordInfo = "update GoalRecordInfo set goalRecordChest = ?,goalRecordLoin = ?,goalRecordLeftArm = ? ,goalRecordRightArm = ? ,goalRecordWeight = ? ,goalRecordTime = ? where userId = ?";
		String sqlGoalRecordInfo = "insert GoalRecordInfo (goalRecordChest,goalRecordLoin,goalRecordLeftArm,goalRecordRightArm,goalRecordWeight,goalRecordTime,goalRecordShoulder,userId) values(?,?,?,?,?,?,?,?)";
		
		System.out.println("更新当前三围体重的sql:" + sqlGoalRecordInfo);
		try {
			conn = BaseConnection.getConnection();
			//点禁止自动提交，设置回退  
			conn.setAutoCommit(false);   
			
			// 更新基本数据的参数
			ps = (PreparedStatement) conn.prepareStatement(sqlUserInfo);
			ps.setInt(1, userinfo.getSex());
			ps.setString(2, userinfo.getBirthday());
			ps.setFloat(3, userinfo.getHigh());
			ps.setFloat(4, userinfo.getHigh());
			ps.setInt(5, consumeCC);
			ps.setInt(6, intakeCC);
			ps.setInt(7, (int)consumeREE);
			ps.setInt(8, userinfo.getBaseUser().getUserId());
			int exeSqlUserInfo = ps.executeUpdate();
			
			// 更新三围信息的事物参数
			PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(sqlGoalRecordInfo);
			preparedStatement.setFloat(1, userinfo.getGoalRecordChest());
			preparedStatement.setFloat(2, userinfo.getGoalRecordLoin());
			preparedStatement.setFloat(3, userinfo.getGoalRecordLeftArm());
			preparedStatement.setFloat(4, userinfo.getGoalRecordRightArm());
			preparedStatement.setFloat(5, userinfo.getGoalRecordWeight());
			preparedStatement.setString(6, String.valueOf(System.currentTimeMillis()/1000));
			preparedStatement.setFloat(7, userinfo.getGoalRecordShoulder());
			preparedStatement.setInt(8, userinfo.getBaseUser().getUserId());
			int exeSqlGoalRecordInfo = preparedStatement.executeUpdate();
			
			//事务提交  
			conn.commit();  
			
			
			if (exeSqlUserInfo > 0 && exeSqlGoalRecordInfo>0) {
				System.out.println("修改信息成功");
				return comsumeCCInfo;

			} else {
				System.out.println("修改信息失败");
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
			BaseConnection.closeResource(rs, ps, conn);
		}
		return comsumeCCInfo;

	}

}
