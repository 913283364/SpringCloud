package com.it.domain;

public interface Constr {

    //把热门和上架的参数做成常量便于修改
    public static final  int ISHOT = 1;    //热门商品
    public static final  int NOISHOT = 0;  //非门商品
    public static final  int PFLAG = 0;    //上架
    public static final  int NOPFLAG = 1;  //下架
    public static final  int ORDER_WEIFUKUAN = 0;//未付款
    public static final  int ORDER_YIFUKUAN = 1;//已付款
    public static final  int ORDER_YIFAHUO = 2;//已发货
    public static final  int ORDER_YIWANC = 3;//已完成
}
