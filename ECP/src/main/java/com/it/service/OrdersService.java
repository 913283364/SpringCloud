package com.it.service;

import com.it.domain.OrderItem;
import com.it.domain.Orders;
import com.it.domain.PageBean;

import java.util.List;

public interface OrdersService {
    //添加订单方法,传递Orders对象
    //void submitOrder(Orders orders) ;
    //使用List集合添加订单项方法,传递OrderItem对象(否则OrderServlet遍历一次调用一次Dao层)
    //void submitOrderItem( List<OrderItem> orderItemList)  ;

    //使用List集合添加订单和订单项的数据
    void addOrders(Orders orders, List<OrderItem> orderItemList);

    //查询分页方法,传递当前页,每页个数,用户主键.(返回PageBean)
    PageBean<Orders> UserOrderWithPage(int currentPage, int pageSize, String uid);
    //查询订单详情方法,传递订单主键,返回订单对象
    Orders info(String oid);
    //修改订单数据,收货人,地址,电话
    void updateOrder(String name,String address,String telephone,String oid);
    //修改订单状态,已经付款
    void updateOrderState(String oid);
}

