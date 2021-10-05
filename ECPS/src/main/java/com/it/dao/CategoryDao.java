package com.it.dao;

import com.it.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    //后台系统查询所有的分类数据
    List<Category> findAll() throws SQLException;
    //后台系统添加商品分类名称
    void addCategory(String cname)throws SQLException;
    //后台系统指定商品分类的主键查询分类信息
    Category findById(String cid) throws  SQLException;
    //后台系统更新分类数据
    void updateCategory(String cid, String cname)throws SQLException;
    //删除分类数据,传递分类主键
    void delCategory(String cid)throws SQLException;
}
