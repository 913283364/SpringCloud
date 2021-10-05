import com.it.dao.UserDao;
import com.it.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMybatisDynamic {

    /*查询所有*/
    @Test
    public void testFindAll(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        //获取动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);;

        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }


    @Test
    public void testFindByCondition(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        //获取动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        User user = new User();
        user.setUsername("王");
        user.setSex("男");

        //执行方法
        List<User> userList = userDao.findByCondition(user);
        //遍历结果
        for (User u : userList) {
            System.out.println(u);
        }
        //关闭资源
        sqlSession.close();
    }

    /*根据数组删除多个用户对象*/
    @Test
    public void testDelByArray(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        //获取动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        //定义一个整数的数组传入要删除的id
        Integer[] ids = new Integer[]{9,10};

        userDao.delByArray(ids);
        //提交
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }

    @Test
    public void testDelByList(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        //获取动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        List<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(10);

        userDao.delByList(list);
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }
}
