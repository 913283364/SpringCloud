package com.it.service;

import com.it.domain.Category;
import com.it.exception.CanNotDeleteException;

import java.util.List;

public interface CategoryService {
    //查询所有的分类数据
    List<Category> findAll();
    //后台系统添加商品分类
    void addCategory(String cname);
    //后台系统指定商品分类主键,查询分类信息
    Category findById(String cid);
    //更新分类数据
    void updateCategory(String cid,String cname);
    //删除分类数据,传递分类主键
    void delCategory(String cid) throws CanNotDeleteException;
}
