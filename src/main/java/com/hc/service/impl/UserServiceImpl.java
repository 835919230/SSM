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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    @Transactional(readOnly = true)
    public User findUser(String username) {
        if (username == null || "".equals(username))
            return new User();
        User user = userDao.findByUsername(username);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> findUserRoles(String username) {
        if (username == null || "".equals(username))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<Role> roles = roleDao.listRole(user.getId());
        return roles;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Permission> findUserPermissions(String username) {
        if (username == null || "".equals(username.trim()))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<Permission> permissions = permissionDao.findPermissionByUser(user.getId());
        return permissions;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findUserRoleNames(String username) {
        if (username == null || "".equals(username))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<String> roleNames = roleDao.listRoleName(user.getId());
        return roleNames;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findUserPermissionNames(String username) {
        if (username == null || "".equals(username))
            return new HashSet<>(0);
        User user = userDao.findByUsername(username);
        Set<String> permissionNames = permissionDao.findPermissionNameByUserId(user.getId());
        return permissionNames;
    }
}
