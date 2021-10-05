package com.it.service;

import java.sql.SQLException;

import com.it.domain.User;

/**
 * 用户业务集合
 */
public interface UserService {
    //用户注册业务
    void register(User user);
    //用户登录业务
    User login(String username, String password);
}
