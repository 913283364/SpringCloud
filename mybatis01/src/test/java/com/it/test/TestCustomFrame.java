package com.it.test;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestCustomFrame {

    @Test
    public void test(){

        //获取配置文件的输入流对象
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //创建SqlSessionFactory对象
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        //获取sqlSession对象
        SqlSession sqlSession = sessionFactory.openSession();
        //执行sql语句(selectList指定包名,类名,id)
        List list = sqlSession.selectList("com.it.domain.User.findAll");
        //遍历结果集
        for (Object o : list) {
            System.out.println(o);
        }
        //关闭资源
        sqlSession.close();
    }
}
