package com.hc.realm;

import com.hc.model.Permission;
import com.hc.model.Role;
import com.hc.model.User;
import com.hc.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 诚 on 2016/9/16.
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<Role> roles = userService.findUserRoles(userService.findUser(username));
        Set<String> roleNames = new HashSet<>();
        for (Role role : roles) {
            roleNames.add(role.getRole());
        }
        Set<Permission> permissions = userService.findUserPermissions(roles);
        info.setRoles(roleNames);
        Set<String> perNames = new HashSet<>();
        for (Permission p : permissions) {
            perNames.add(p.getPermissionName());
        }
        info.setStringPermissions(perNames);
        return info;
    }

    /**
     * 获取登录验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String password = token.getCredentials().toString();
        User user = userService.findUser(username);
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(username, password, "realm");
            return info;
        }
        return null;
    }
}
