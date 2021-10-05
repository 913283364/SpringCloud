package com.it.dao;

import com.it.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    //查询最新商品
    List<Product> findNew() throws SQLException;
    //查询最热门商品
    List<Product> findIsHot() throws SQLException;
    //根据商品图片链接的主键,查询商品详情
    Product findById(String pid)throws SQLException;
    //根据商品分类,查询商品分页数据
    //参数:{当前页数(currentPage),每页显示条数(pageSize),分类外键(cid)}
    List<Product> findByPage(int currentPage, int pageSize, String cid)throws SQLException;
    //根据商品分类,查询总记录数
    long getTotalCount(String cid)throws SQLException;
    //根据商品分类外键查询商品表是否有数据
    long getProductCount(String cid)throws SQLException;
    //查询所有的商品数据,分页查询,不需要查询商品分类所以不用加cid(重载)
    List<Product> findByPage(int currentPage, int pageSize)throws SQLException;
    //查询所有商品总记录数(重载)
    long getTotalCount()throws SQLException;
    //后台系统      添加新商品数据
    void addProduct(Product product) throws SQLException;
}
