package com.hh.attendance.dao;

import com.hh.attendance.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserById(@Param("userId") Integer userId);
}
