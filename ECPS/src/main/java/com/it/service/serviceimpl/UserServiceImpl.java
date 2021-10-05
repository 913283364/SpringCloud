package com.it.service.serviceimpl;

import com.it.dao.UserDao;
import com.it.domain.User;
import com.it.service.UserService;
import com.it.utils.BeanFactory;

import java.sql.SQLException;

/**
 * 用户业务实现
 */
public class UserServiceImpl implements UserService {
    //bean工厂,获取dao接口实现类对象
    private UserDao userDao = BeanFactory.newInstance(UserDao.class);

    /**
     * 用户登录业务	
     * Web层传递用户名密码调用dao层传递参,结果User对象,返回web
     */
    public User login(String username, String password) {	
    	
    	    User user = null;
     try {
             user = userDao.login(username,password);
         } catch (SQLException e) {
             e.printStackTrace();
         }     
	     return user;
    }

    /**
     *  用户注册业务
     *  接收web层传递user,调用dao层传递user
     */
    public void register(User user){  	     
     try {
             userDao.register(user);
         } catch (SQLException e) {
             e.printStackTrace();
         };     
    }
}
