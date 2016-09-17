package com.hc.service;

import com.hc.model.Permission;
import com.hc.model.Role;
import com.hc.model.User;

import java.util.Set;

/**
 * Created by 诚 on 2016/9/15.
 */
public interface UserService {
    User findUser(String username);

    Set<Role> findUserRoles(User user);

    Set<Permission> findUserPermissions(Set<Role> roles);
}
