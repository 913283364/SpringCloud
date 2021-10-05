package com.it.dao;

import com.it.domain.User;

import java.util.List;

public interface UserDao {

    /**
     * 返回所有的user对象,包含用户对应账户信息(一对多)
     * @return
     */
    public List<User> findAll();

}
