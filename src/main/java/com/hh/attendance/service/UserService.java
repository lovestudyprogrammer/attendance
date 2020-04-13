package com.hh.attendance.service;

import com.hh.attendance.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

public interface UserService {
    User getUserById(@RequestParam("userId") Integer userId);

    User getUserByUserNameAndPassword(@RequestParam("username") String userName, @RequestParam("password") String password);

    int addUser(@RequestBody User user);

    /**
     * 根据用户类型查询user
     *
     * @param type
     * @return
     * @see com.hh.attendance.enums.UserTypeEnum
     */
    Collection<User> listByUserType(@RequestParam("type") int type);
}
