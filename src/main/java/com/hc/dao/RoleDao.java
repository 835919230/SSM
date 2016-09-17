package com.hc.dao;

import com.hc.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * Created by 诚 on 2016/9/16.
 */
public interface RoleDao {
    Role findRoleById(@Param("id") long id);
    Role findRoleByRole(String roleName);
    Set<Role> listRole(long userId);
}
