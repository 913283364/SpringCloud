package com.it.dao;

import com.it.domain.User;

import java.util.List;

public interface UserDao {

    /**
     * 条件查询
     *
     * 根据username模糊查询
     * 根据性别sex 等于查询
     *
     * @param user
     * @return
     */
    public List<User> findByCondition(User user);


    /**
     * 查全部
     * @return
     */
    public List<User> findAll();

    /**
     * 根据数组删除多个用户对象
     * @param ids
     */
    public void delByArray(Integer[] ids);



    /**
     * 根据集合删除多个用户对象
     * @param ids
     */
    public void delByList(List<Integer> ids);


    /**
     * 保存用户
     * @param user
     */
    public void save(User user);
}
