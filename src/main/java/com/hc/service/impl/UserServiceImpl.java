package com.hc.service.impl;

import com.hc.dao.PermissionDao;
import com.hc.dao.RoleDao;
import com.hc.dao.UserDao;
import com.hc.model.Permission;
import com.hc.model.Role;
import com.hc.model.User;
import com.hc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 诚 on 2016/9/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUser(String username) {
        if (username == null || "".equals(username))
            return null;
        User user = userDao.findByUsername(username);
        return user;
    }

    @Override
    public Set<Role> findUserRoles(String username) {
        if (username == null || "".equals(username))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<Role> roles = roleDao.listRole(user.getId());
        return roles;
    }

    @Override
    public Set<Permission> findUserPermissions(String username) {
        if (username == null || "".equals(username.trim()))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<Permission> permissions = permissionDao.findPermissionByUser(user.getId());
        return permissions;
    }
}
