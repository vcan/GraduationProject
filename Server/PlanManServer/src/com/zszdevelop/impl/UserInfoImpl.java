package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.UserInfo;
import com.zszdevelop.dao.UserInfoDao;
import com.zszdevelop.utils.BirthDayUtil;

public class UserInfoImpl implements UserInfoDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public ComsumeCCInfo ModifyUserInfo(UserInfo userinfo) {
		

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
				consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)
						- (5 * BirthDayUtil.BirthDayToAge(userinfo.getBirthday())) + 5);
			} else {// 女性
				consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)
						- (5 * BirthDayUtil.BirthDayToAge(userinfo.getBirthday())) - 161);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		intakeCC = (int) (consumeREE * actionType);

		// 需要消耗的热量
		// 一旦热量累积到达7700大卡，你的体重就会增加一公斤
		// 怎么样的减肥速度最健康、最有效率呢？答案是每周减0.5-1公斤。
		// 新手：每周瘦0.5公斤，7000/2/7 = 550 每天要多消耗550大卡
		int consumeCC = (int) (intakeCC + 550);

		ComsumeCCInfo comsumeCCInfo = new ComsumeCCInfo();
		float highM = high / 100;
		comsumeCCInfo.setBMI(goalRecordWeight / (highM * highM));
		comsumeCCInfo.setConsumeCC(consumeCC);
		comsumeCCInfo.setIntakeCC(intakeCC);
		comsumeCCInfo.setConsumeREE((int) consumeREE);

		// 基本数据更新更新
		String sqlUserInfo = "update UserInfo set sex = ?,birthday = ?,high = ?,consumeCC = ?,intakeCC = ?,consumeREE = ?,signture = ?where userId = ?";

		try {
			conn = BaseConnection.getConnection();
			
			// 更新基本数据的参数
			ps = (PreparedStatement) conn.prepareStatement(sqlUserInfo);
			ps.setInt(1, userinfo.getSex());
			ps.setString(2, userinfo.getBirthday());
			ps.setFloat(3, userinfo.getHigh());
		
			ps.setInt(4, consumeCC);
			ps.setInt(5, intakeCC);
			ps.setInt(6, (int) consumeREE);
			ps.setString(7, userinfo.getSignture());
			ps.setInt(8, userinfo.getBaseUser().getUserId());
			int exeSqlUserInfo = ps.executeUpdate();
			if (exeSqlUserInfo<1) {
				return null;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		} finally {
			BaseConnection.closeResource(rs, ps, conn);
		}

		return comsumeCCInfo;
	}

	@Override
	public UserInfo getUserInfo(int userId) {
		
		UserInfo userInfo = new UserInfo();
		
		String sql = "SELECT * FROM UserInfo WHERE userId=?";
		conn = BaseConnection.getConnection();
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userId);
			 rs = ps.executeQuery();
			 if (rs.next()) {
			
					 
						userInfo.setActionType(rs.getFloat("actionType"));
						userInfo.setBirthday(rs.getString("birthday"));
						ComsumeCCInfo comsumeCCInfo = new ComsumeCCInfo();
						comsumeCCInfo.setBMI(rs.getFloat("BMI"));
						comsumeCCInfo.setConsumeCC( rs.getInt("consumeCC"));
						comsumeCCInfo.setConsumeREE(rs.getInt("consumeREE"));
						comsumeCCInfo.setIntakeCC(rs.getInt("intakeCC"));
						userInfo.setComsumeCCInfo(comsumeCCInfo);
						userInfo.setHigh(rs.getFloat("high"));
						userInfo.setNickname(rs.getString("nickname"));
						userInfo.setSex(rs.getInt("sex"));
						userInfo.setSignture(rs.getString("signture"));
					
			}else {
				return null;
			}
			
		} catch (SQLException e) {
			System.out.println("chucuole ");
			e.printStackTrace();
			return null;
		}finally {
			BaseConnection.closeResource(rs, ps, conn);
		}
		
		return userInfo;
	}

}
