package com.it.domain;

import java.util.List;

public class Orders {
    // 订单id
    private String oid;
    // 订单时间
    private String ordertime;
    // 订单金额
    private Double total;
    // 订单状态 0:未付款 1:已付款 2:已发货 3.已完成
    private Integer state;
    // 收获地址
    private String address;
    // 收货人姓名
    private String name;
    // 收获人电话
    private String telephone;
    private String uid;

    // 订单中可以同时包含多个订单项
    private List<OrderItemView> orderViewList ;

    public List<OrderItemView> getOrderViewList() {
        return orderViewList;
    }

    public void setOrderViewList(List<OrderItemView> orderViewList) {
        this.orderViewList = orderViewList;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
