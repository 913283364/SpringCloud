package com.it.utils;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * con 连接对象的管理类：
 *
 *  1.获取连接,开启事务
 *  2.提交事务,回滚事务
 *  3.释放资源
 */
public class ConnectionManager {
    //成员位置,创建ThreadLocal对象
    private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
    private static Connection con;

    //获取连接的方法
    public static Connection getConnection() throws SQLException {
        //取出连接,从local对象获取
        con = local.get();
        if(con==null){
            //第一次运行,向local对象中,先存储连接
            local.set(C3P0Utils.getConn());
            con = local.get();
        }
        return con;
        }

    //开启事务的方法
    public static void begin() throws SQLException{
        getConnection();
        con.setAutoCommit(false);
    }

    //提交事务的方法
    public static void commit() throws SQLException{
        con.commit();
    }

    //回滚事务
    public static void rollback() throws SQLException{
        con.rollback();
    }

    //释放资源
    public static void close(){
        try {
            con.close();
            local.remove();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}