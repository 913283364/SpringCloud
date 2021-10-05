import com.it.dao.AccountDao;
import com.it.domain.Account;
import com.it.domain.AccountUser;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.List;

public class TestMybatisOTO {

    /**
     * 查询所有的账户，不包含用户信息
     */
    @Test
    public void testFindAll(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        List<Account> accountList = accountDao.findAll();
        for (Account account : accountList) {
            System.out.println(account);
        }
        //关闭资源
        sqlSession.close();
    }

    /**
     * 查询所有账户,不包含用户信息,继承pojo(比较麻烦)
     */
    @Test
    public void testFindAllAccountUser(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        List<AccountUser> accountUserList = accountDao.findAllAccountUser();
        for (AccountUser  accountUser : accountUserList) {
            System.out.println(accountUser);
        }
        //关闭资源
        sqlSession.close();
    }

    /**
     * 查询所有的账户，包含用户信息,不继承pojo
     */
    @Test
    public void testFindAllAccount(){
        //获取SqlSession对象
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml")).openSession();
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        List<Account> accountList = accountDao.findAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
        //关闭资源
        sqlSession.close();
    }
    
}
