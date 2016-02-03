package com.zszdevelop.planman.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseConnection {
	public static Connection getConnection(){    

	    Connection conn=null;
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "zsz123456");
	    } catch (ClassNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return conn;
	}
	public static void closeResource(PreparedStatement ps,Connection conn){

	    
		try {
	      
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

	

public static void closeResource(ResultSet rs,PreparedStatement ps,Connection conn){

    
	try {
        if (rs!=null) {
        rs.close();

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

}
