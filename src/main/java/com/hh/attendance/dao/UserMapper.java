package com.hh.attendance.dao;

import com.hh.attendance.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface UserMapper {
    User getUserById(@Param("userId") Integer userId);

    User getUserByUserNameAndPassword(@Param("username") String userName, @Param("password") String password);

    User getUserByPhone(@Param("phone") String phone);

    Collection<User> listUserByType(@Param("type") int type);

    int insert(@Param("vo") User user);

    int updateById(User record);

    List<User> selectStuList();

    List<User> selectTeaList();

    int deleteById(Integer id);
}
