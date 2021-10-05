package com.it.dao;


import com.it.domain.User;

import java.sql.SQLException;

public interface UserDao {
    //添加用户注册方法
    void register(User user) throws SQLException;
    //添加登录方法
    User login(String username, String password) throws SQLException;
}