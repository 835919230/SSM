package com.hc.dao;

import com.hc.model.Role;
import com.hc.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 诚 on 2016/9/14.
 */
public interface UserDao {
    User findById(@Param("id") long id);
    User findByUsername(@Param("username")String username);
    int insertOne(@Param("user") User user);
    int updatePassword(@Param("id") long id,@Param("password") String password);
}
