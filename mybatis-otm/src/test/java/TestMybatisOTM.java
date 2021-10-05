import com.it.dao.UserDao;
import com.it.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.List;

public class TestMybatisOTM {

    /**
     * 查询所有的用户,包含账户信息(一对多)
     */
    @Test
    public void testFindAll(){
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
}
