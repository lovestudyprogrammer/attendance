package com.hh.attendance.dao;

import com.hh.attendance.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface UserMapper {
    User getUserById(@Param("userId") Integer userId);

    User getUserByUserNameAndPassword(@Param("username") String userName, @Param("password") String password);

    Collection<User> listUserByType(@Param("type") int type);

    int insert(@Param("vo") User user);
}
