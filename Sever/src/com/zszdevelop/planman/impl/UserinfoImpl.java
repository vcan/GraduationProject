package com.zszdevelop.planman.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.zszdevelop.planman.base.BaseConnection;
import com.zszdevelop.planman.bean.UserinfoBean;
import com.zszdevelop.planman.dao.UserinfoDao;

public class UserinfoImpl implements UserinfoDao {

	Connection conn = null;

	public void getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connUrl = "jdbc:mysql://localhost:3306/PlanManSql";
			conn = DriverManager.getConnection(connUrl, "root", "SF948640.");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<UserinfoBean> getAllGodList(String queryType) {
		boolean b=false;
		ArrayList<UserinfoBean> arrayList=new ArrayList<>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		// 根据查询的类型返回数据
		String getAllManSQL="SELECT * FROM userinfo";
		if ("1".equals(queryType)) {
			getAllManSQL="SELECT * FROM userinfo WHERE sex=1";
		}else if ("2".equals(queryType)) {
			getAllManSQL="SELECT * FROM userinfo WHERE sex=2";
		}
		try {
			conn=BaseConnection.getConnection();
			ps=(PreparedStatement) conn.prepareStatement(getAllManSQL);
//			ps.setString(1, index);
//			ps.setString(2, filter);
			rs=ps.executeQuery();
			String line;
			StringBuffer sb=new StringBuffer();
			while (rs.next()) {
				UserinfoBean godListBean = new UserinfoBean();
				godListBean.setUserid(rs.getInt("userid"));
				
//				godListBean.setUid(rs.getString("uid"));
				arrayList.add(godListBean);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			BaseConnection.closeResource(rs, ps, conn);
		}
		
		return arrayList;
	}

}
