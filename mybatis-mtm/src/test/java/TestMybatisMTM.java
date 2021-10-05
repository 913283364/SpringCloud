import com.it.dao.RoleDao;
import com.it.dao.UserDao;
import com.it.domain.Role;
import com.it.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.List;

public class TestMybatisMTM {

    /**
     * 查询所有的用户，包含角色信息
     */
    @Test
    public void testFindAllUser(){
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();

        //获取动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //执行方法
        List<User> userList = userDao.findAll();
        // 遍历结果
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }


    /**
     * 查询所有的角色，包含用户信息
     */
    @Test
    public void testFindAllRole(){
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();

        //获取动态代理对象
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        //执行方法
        List<Role> roleList = roleDao.findAll();
        // 遍历结果
        for (Role role : roleList) {
            System.out.println(role);
        }
        sqlSession.close();
    }
}
