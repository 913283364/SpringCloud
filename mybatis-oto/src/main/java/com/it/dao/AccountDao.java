package com.it.dao;

import com.it.domain.Account;
import com.it.domain.AccountUser;

import java.util.List;

public interface AccountDao {

    /**
     * 查询所有的账户
     * @return
     */
    public List<Account> findAll();

    /**
     * 查询所有的账户:不包含用户信息,需要继承pojo
     * @return
     */
    public List<AccountUser> findAllAccountUser();

    /**
     * 查询所有的账户:包含对应的用户信息,不需要继承pojo
     * @return
     */
    public List<Account> findAllAccount();
}
