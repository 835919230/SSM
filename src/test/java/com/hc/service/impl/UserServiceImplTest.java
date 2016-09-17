package com.hc.service.impl;

import com.hc.model.Permission;
import com.hc.model.Role;
import com.hc.model.User;
import com.hc.service.UserService;
import com.hc.web.RootConfig;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by 诚 on 2016/9/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserServiceImplTest {

    Logger logger = Logger.getLogger(UserServiceImplTest.class);

    @Resource
    UserService userService;

    @Test
    public void findUser() throws Exception {
        User user = userService.findUser("835919230");
        logger.debug(user);
    }

    @Test
    public void findUserRoles() throws Exception {
        Set<Role> roles = userService.findUserRoles("835919230");
        logger.debug(roles);
    }

    @Test
    public void findUserPermissions() throws Exception {
        Set<Permission> userPermissions = userService.findUserPermissions("835919230");
        logger.debug(userPermissions);
    }

}