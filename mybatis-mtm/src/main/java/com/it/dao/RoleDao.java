package com.it.dao;

import com.it.domain.Role;

import java.util.List;

public interface RoleDao {

    /**
     * 查询所有的角色，包含用户对象
     * @return
     */
    public List<Role> findAll();
}
