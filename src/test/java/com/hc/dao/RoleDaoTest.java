package com.hc.dao;

import com.hc.model.Role;
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
public class RoleDaoTest {

    static Logger logger = Logger.getLogger(RoleDaoTest.class);

    @Resource
    private RoleDao roleDao;

    @Test
    public void findRole() throws Exception {
        Role role = roleDao.findRoleById(1001L);
        logger.debug(role);
    }

    @Test
    public void findRole1() throws Exception {
        Role student = roleDao.findRoleByRole("student");
        logger.debug(student);
    }


    @Test
    public void listRoleName() throws Exception {
        Set<String> roleNames = roleDao.listRoleName(1001L);
        for (String role : roleNames)
            logger.debug("name:"+role);
    }


}