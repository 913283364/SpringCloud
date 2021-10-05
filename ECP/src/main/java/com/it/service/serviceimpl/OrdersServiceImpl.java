package com.it.service.serviceimpl;

import com.it.dao.OrdersDao;
import com.it.domain.OrderItem;
import com.it.domain.Orders;
import com.it.domain.PageBean;
import com.it.service.OrdersService;
import com.it.utils.BeanFactory;
import com.it.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrdersServiceImpl implements OrdersService{

    //bean工厂床架orderDao接口实现类
    private OrdersDao ordersDao = BeanFactory.newInstance(OrdersDao.class);


    //订单表添加数据
    /*public void submitOrder(Orders orders) {
        try {
            ordersDao.submitOrder(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //订单项表添加多个购物项,添加一次调用一次Dao,每遍历一次都增加一张中间表数据
    //如果在添加第一个购物项时出现问题,程序会出现异常添加停产,商品只有一个
    //所以要么全成功要么全失败,需要添加事务,事务由连接控制,不可以自己new连接池
    //只能传递Connection Con参数,不能写2个方法控制事务要把Order和OrderItem合成一个方法

    public void submitOrderItem(List<OrderItem> orderItemList) {
       try {
            for (OrderItem orderItem : orderItemList) {
                ordersDao.submitOrderItem(orderItem);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/


    /**
     *  实现订单和订单项添加数据
     *  事务的控制
     */
    public void addOrders(Orders orders, List<OrderItem> orderItemList) {

        Connection con = null;
        try {
            // 连接管理类
            con = ConnectionManager.getConnection();
            // 开启事务
            ConnectionManager.begin();
            // 添加订单和订单表数据
            ordersDao.submitOrder(con,orders);
            // 遍历一次添加一次数据
            for (OrderItem orderItem : orderItemList) {
                ordersDao.submitOrderItem(con,orderItem);
            }
                // 提交事务
                ConnectionManager.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                // 回滚事务
                ConnectionManager.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
                // 释放资源
                ConnectionManager.close();
          }
           System.out.println(con);
    }



    /**
     *  用户订单的分页显示方法
     *  接收WEB参数
     *  查询dao,封装PageBean对象
     */
    public PageBean<Orders> UserOrderWithPage(int currentPage, int pageSize, String uid) {
        PageBean<Orders> pb = new PageBean<Orders>();
        //封装pb对象的数据
        try {
            //当前页
            pb.setCurrentPage(currentPage);
            //每页显示条数
            pb.setPageSize(pageSize);
            //查询dao层,获取用户订单对象(数据)
            List<Orders> ordersList =  ordersDao.UserOrderWithPage(currentPage, pageSize, uid);
            //将订单放入集合,存储pb对象
            pb.setList(ordersList);
            //获取所有用户的订单总数
            long totalCount = ordersDao.getOrdersCount(uid);
            //pb把订单总数设置成总数量
            pb.setTotalCount(totalCount);
            //设置总页数
            pb.setTotalPage( (int)(Math.ceil(totalCount*1.0/pageSize)) );

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return pb;
    }


    public Orders info(String oid) {
        Orders orders = null;
        try {
            orders = ordersDao.info(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    public void updateOrderState(String oid) {
        try {
            ordersDao.updateOrderState(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateOrder(String name, String address, String telephone, String oid) {
        try {
            ordersDao.updateOrder(name,address,telephone,oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
