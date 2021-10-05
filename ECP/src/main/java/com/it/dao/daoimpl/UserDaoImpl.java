package com.it.dao.daoimpl;

import com.it.domain.User;
import com.it.utils.C3P0Utils;
import com.it.dao.UserDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

//用户功能实现
public class UserDaoImpl implements UserDao {
	
	// QueryRunner从DataSource获取连接池
    private  QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    
    /**	
     *  用户登录方法
     *  业务层传递用户名和密码
     *  作为条件查询数据表
     *  返回User对象
     */
    public User login(String username, String password) throws SQLException {
        //拼写查询SQL语句
        String sql = "select * from user where username=? and password=?";
        return  qr.query(sql,new BeanHandler<User>(User.class),username,password);
    }

    /**
     * 实现用户注册功能，user对象数据,存储数据库
     * user对象,业务层传递
     */
    public void register(User u) throws SQLException {
      // 1.发送sql语句：向数据库中的user表插入数据,?号必须对应domain的user类的方法
      String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";   
      // 2.调用domain里User类方法中的数据，封装成QR的参数
      Object[] params =
      {
         u.getUid(),u.getUsername(),u.getPassword(),
         u.getName(),u.getEmail(),u.getBirthday(),
         u.getGender(),u.getState(),u.getCode(),u.getRemark()
      }; 
      // 3.qr的update通过params里的数据放进sql,添加数据
      qr.update(sql,params);
    }


}
