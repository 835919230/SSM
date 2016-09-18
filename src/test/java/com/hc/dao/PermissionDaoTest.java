package com.hc.dao;

import com.hc.model.Permission;
import com.hc.web.RootConfig;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
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
public class PermissionDaoTest {

    Logger logger = Logger.getLogger(PermissionDaoTest.class);

    @Resource
    private PermissionDao permissionDao;

    @Test
    public void findPermissionByRole() throws Exception {
        Set<Permission> permissions = permissionDao.findPermissionByRole(1001L);
        for (Permission permission : permissions) {
            logger.debug(permission);
        }
    }

    @Test
    public void findPermissionByUser() throws Exception {
        Set<Permission> permissions = permissionDao.findPermissionByUser(1001L);
        for (Permission permission : permissions) {
            logger.debug(permission);
        }
    }

    @Test
    public void findPermissionNameByUserId() throws Exception {
        Set<String> permissionNames = permissionDao.findPermissionNameByUserId(1001L);
        for (String permission : permissionNames) {
            logger.debug("name:"+permission);
        }
    }

}