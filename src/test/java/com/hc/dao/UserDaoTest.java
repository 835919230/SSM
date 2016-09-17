package com.hc.dao;

import com.hc.model.User;
import com.hc.web.RootConfig;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by 诚 on 2016/9/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserDaoTest {

    Logger logger = Logger.getLogger(UserDaoTest.class);

    @Resource
    UserDao userDao;

    @Test
    public void findById() throws Exception {
        User user = userDao.findById(1001);
        logger.debug(user);
    }

    @Test
    public void insertOne() throws Exception {
        String salt = UUID.randomUUID().toString();
        byte[] bytes = ("123456789"+salt).getBytes();
        String encodePassword = Base64.encode(bytes);
        User user = new User("manager", encodePassword);
        user.setSalt(salt);
        user.setId(1003);
        user.setLocked(false);

        int i = userDao.insertOne(user);
        Assert.assertTrue(i==0);
    }

}