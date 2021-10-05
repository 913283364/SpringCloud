package com.it.service;


import com.it.domain.PageBean;
import com.it.domain.Product;

import java.util.List;

public interface ProductService {
    //查询最热门商品
    List<Product> findIsHot();
    //查询最新商品
    List<Product> findNew();
    //根据主键查询商品详情
    Product findById(String pid);
    //根据商品分类,查询商品的分页数据,返回PageBean对象
    //参数:{当前页数(currentPage),每页显示的条数(pageSize),分类外键(cid)}
    PageBean<Product> findByPage(int currentPage, int pageSize, String cid);
    //查询所有商品的分页,不需要查询商品分类所有不用cid参数(重载)
    PageBean<Product> findByPage(int currentPage, int pageSize);
    //后台系统添加新商品数据
    void addProduct(Product product);
}
