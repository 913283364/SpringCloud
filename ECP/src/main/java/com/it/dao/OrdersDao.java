package com.it.dao;

import com.it.domain.OrderItem;
import com.it.domain.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrdersDao {

    //添加订单方法,传递Orders对象
    void submitOrder(Connection con, Orders orders) throws SQLException;
    //添加订单项方法,传递OrderItem对象
    void submitOrderItem(Connection con,OrderItem orderItem) throws SQLException;
    //分页查询用户订单的方法
    List<Orders> UserOrderWithPage(int currentPage, int pageSize, String uid)throws SQLException;
    //查询用户的所有订单总数量
    long getOrdersCount(String uid)throws SQLException;
    //查询订单详情方法,传递订单号
    Orders info (String oid)throws SQLException;
    //修改订单方法
    void updateOrder(String name,String address,String telephone,String oid)throws SQLException;
    //修改订单状态已经付款
    void updateOrderState(String oid)throws SQLException;
}
