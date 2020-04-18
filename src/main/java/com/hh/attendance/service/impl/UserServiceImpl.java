package com.hh.attendance.service.impl;

import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.dao.UserMapper;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.MdUserService;
import com.hh.attendance.service.UserService;
import com.hh.attendance.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

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
    @Autowired
    private MdUserService mdUserService;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByUserNameAndPassword(String userName, String password) {
        return userMapper.getUserByUserNameAndPassword(userName, password);
    }

    @Override
    @Transactional
    public int addUser(@RequestBody UserVo userVo) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userVo, user);
            if (user.getType() == UserTypeEnum.STUDENT.getId()) {
                // 设置学号
                user.setSno(CommonUtil.generateNumber());
            }
            userMapper.insert(user);
            // 班级
            mdUserService.saveClassMdUser(user.getId(), userVo.getClassId());
        } catch (DuplicateKeyException e) {
            duplicateKey(e);
        }
        return user.getId();
    }

    @Override
    public Collection<User> listByUserType(int type) {
        Collection<User> userList = userMapper.listUserByType(type);
        return userList;
    }

    @Override
    public int updateById(User record) {
        return userMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    private void duplicateKey(DuplicateKeyException e) {
        String message = e.getMessage();
        if (message != null) {
            if (message.contains("Duplicate entry")) {
                if (message.contains("u_name_password")) {
                    throw new RuntimeException("昵称已被使用");
                } else if (message.contains("u_phone")) {
                    throw new RuntimeException("手机号码已被使用");
                }
            }
        }
        throw new RuntimeException(message);
    }


}
