package com.hh.attendance.service.impl;

import com.hh.attendance.dao.UserMapper;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: 用户服务
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/02/25 19:18
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }


}
