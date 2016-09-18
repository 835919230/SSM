package com.hc.realm;

import com.hc.model.Permission;
import com.hc.model.Role;
import com.hc.model.User;
import com.hc.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 诚 on 2016/9/16.
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {

    static Logger logger = Logger.getLogger(UserRealm.class);

    @Override
    public String getName() {
        return super.getName();
    }

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
        Set<String> roleNames = userService.findUserRoleNames(username);
        info.setRoles(roleNames);
        Set<String> perNames = userService.findUserPermissionNames(username);
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
        String password = new String((char[]) token.getCredentials());
        User user = userService.findUser(username);
        logger.debug("登录的密码"+password);
        if (user != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
            return info;//return了info其实内部还会有验证
        }
        return null;
    }
}
