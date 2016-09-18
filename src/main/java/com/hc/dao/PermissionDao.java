package com.hc.dao;

import com.hc.model.Permission;

import java.util.Set;

/**
 * Created by 诚 on 2016/9/17.
 */
public interface PermissionDao {
    //直接查询
    Set<Permission> findPermissionByRole(long role_id);

    //通过left join查询
    Set<Permission> findPermissionByUser(long user_id);

    Set<String> findPermissionNameByUserId(long user_id);
}
