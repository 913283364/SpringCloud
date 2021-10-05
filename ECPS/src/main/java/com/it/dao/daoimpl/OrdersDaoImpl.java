package com.it.dao.daoimpl;

import com.it.dao.OrdersDao;
import com.it.domain.Constr;
import com.it.domain.OrderItem;
import com.it.domain.OrderItemView;
import com.it.domain.Orders;
import com.it.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrdersDaoImpl implements OrdersDao {

    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());


    /*submitOrder  添加数据到订单表,业务层传递订单对象*/
    public void submitOrder(Connection con, Orders orders) throws SQLException {
        String sql = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
        Object[] params = {
                orders.getOid(),orders.getOrdertime(),orders.getTotal(),orders.getState(),orders.getUid()
        };
        qr.update(con,sql,params);
    }


    /*submitOrderItem  添加数据到订单项,中间表业务层传递订单项对象*/
    public void submitOrderItem(Connection con, OrderItem orderItem) throws SQLException {
        String sql = "insert into orderitem values(?,?,?,?)";
        Object[] params = {
                orderItem.getCount(),orderItem.getSubTotal(),orderItem.getPid(),orderItem.getOid()
        };
        qr.update(con,sql,params);
    }


    /**
     *  分页查询订单
     *  分两次查询
     *   1,查询出这个用户的所有订单
     *     返回集合对象
     *
     *   2.遍历每个订单对象
     *     查询每个订单的订单项
     */
    public List<Orders> UserOrderWithPage(int currentPage, int pageSize, String uid) throws SQLException {
        //拼写sql查询该用户的所有订单
        String sql = "select * from orders where uid=? limit ?,?";
        //返回查询到的所有用户订单数据到List集合
        List<Orders> ordersList = qr.query(sql,new BeanListHandler<Orders>(Orders.class),uid, (currentPage-1)*pageSize,pageSize);
        //遍历集合
        for (Orders  order : ordersList){
            //order每个订单对象(数据),取出订单主键
            String oid = order.getOid();
            //拼写sql,根据订单主键,查询订单项
            sql="SELECT p.pid, p.pname, p.pimage, p.shop_price, o.count, o.subtotal \n" +
                    "FROM product  p,orderitem o\n" +
                    "WHERE p.pid = o.pid AND o.oid=?";
            //查询,获取这个用户每个订单中的订单项,存储到集合(将订单项显示的对象)
            List<OrderItemView> orderItemViewList = qr.query(sql,new BeanListHandler<OrderItemView>(OrderItemView.class),oid);
            //一个订单可以包含多个订单项,把这个用户的订单项装入一个订单对象
            order.setOrderViewList(orderItemViewList);
        }
        return ordersList;
    }


    /**
     * 查询用户的所有订单总数量
     */
    public long getOrdersCount(String uid) throws SQLException {
        String sql = "select count(*) from orders where uid=?";
        return qr.query(sql,new ScalarHandler<Long>(),uid);
    }


    /* Orders  根据订单查询订单项中的数据*/
    public Orders info(String oid) throws SQLException {
        //查询一个订单对象
        String sql = "select * from orders where oid=?";

        Orders orders =  qr.query(sql,new BeanHandler<Orders>(Orders.class),oid);
        //根据订单主键,查询订单项
        sql="SELECT p.pid, p.pname, p.pimage, p.shop_price, o.count, o.subtotal \n" +
                "FROM product  p,orderitem o\n" +
                "WHERE p.pid = o.pid AND o.oid=?";
        List<OrderItemView>  orderItemViewList = qr.query(sql,new BeanListHandler<OrderItemView>(OrderItemView.class),oid);
        orders.setOrderViewList(orderItemViewList);
        return orders;
    }


    //订单状态,修改已付款
    public void updateOrderState(String oid) throws SQLException {
        String sql = "update orders set state=? where oid=?";
        qr.update(sql, Constr.ORDER_YIFUKUAN,oid);
    }


    /**
     * 修改订单方法
     * 收货人,地址,电话
     */
    public void updateOrder(String name, String address, String telephone, String oid) throws SQLException {
        String sql = "update orders set name=?,address=?,telephone=? where oid=?";
        Object[] params = {
                name,address,telephone,oid
        };
        qr.update(sql,params);
    }

}
