package com.it.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class C3P0Utils {
	
	/* public static void main(String[] args) {
	
           getConn();
  }*/
	
	static ComboPooledDataSource dataSource = null;
	static {
		     dataSource = new ComboPooledDataSource();
	    }
	
	
/**
  * 获取 dataSource变量
 * @return dataSource
 */	
public static DataSource getDataSource() {
		 return dataSource;
	 }

/**
  * 获取C3P0连接池的连接
 * @throws SQLException
 */	
 public static Connection getConn() throws SQLException {
	 return dataSource.getConnection();
  }
 

 /**
    * 释放资源方法
  */
 public static void close(ResultSet rs,Statement stmt,Connection conn) {
	if(rs != null) {	 
       try {
			rs.close();
		   } catch (SQLException e) {
			 e.printStackTrace();
		     throw new RuntimeException(e);
		  }
	    } 
	
	if(stmt != null) {
		try {
			stmt.close();
		    } catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		  }
	   }
	
	if(conn != null) {
		try {
			  conn.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		      throw new RuntimeException(e);
		  }
	  }
  }
 
}




