package com.zszdevelop.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.base.BaseConnection;
import com.zszdevelop.bean.ConsumeRecordInfo;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.dao.ConsumeRecordDao;

public class ConsumeRecordImpl implements ConsumeRecordDao{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	@Override
	public boolean insertConsumeRecord(ConsumeRecordInfo consumeRecordInfo, int userId) {
		boolean b = false;

		// 根据更新
		String sql = "insert ConsumeRecordInfo (consumeRecordTime,consumeCC,consumeRecordType,consumeRecordContent,userId) values(?,?,?,?,?)";
		System.out.println("查询的sql:" + sql);
		try {
			conn = BaseConnection.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, consumeRecordInfo.getConsumeRecordTime());
			ps.setInt(2, consumeRecordInfo.getConsumeCC());
			ps.setInt(3, consumeRecordInfo.getConsumeRecordType());
			ps.setString(4, consumeRecordInfo.getConsumeRecordContent());
			ps.setInt(5, userId);
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
	public ArrayList<ConsumeRecordInfo> getConsumeRecordInfo(int userId) {
		ArrayList<ConsumeRecordInfo> lists = new ArrayList<>();
		String sql = "SELECT * FROM ConsumeRecordInfo WHERE userId=?";
		conn = BaseConnection.getConnection();
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, userId);
			 rs = ps.executeQuery();
			while (rs.next()) {
				ConsumeRecordInfo consumeRecordInfo = new ConsumeRecordInfo();
				consumeRecordInfo.setConsumeCC( rs.getInt("consumeCC"));
				consumeRecordInfo.setConsumeRecordContent( rs.getString("consumeRecordContent"));
				consumeRecordInfo.setConsumeRecordId(rs.getInt("consumeRecordId"));
				consumeRecordInfo.setConsumeRecordTime( rs.getString("consumeRecordTime"));
				consumeRecordInfo.setConsumeRecordType(rs.getInt("consumeRecordType"));
				lists.add(consumeRecordInfo);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return lists;
	}

	
}
