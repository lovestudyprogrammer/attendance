package com.hh.attendance.controller;

import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.Constant;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: TestController
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/04/12 20:44
 **/
@RestController
@RequestMapping("/attendance/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    public ResultBody test() {
        User user = new User();
        user.setId(1);
        user.setName("");
        // 存session可以这样使用
        SessionHolder.put(Constant.USER_SESSION, user);
        // 获取session
        Long userId = SessionHolder.getUserId();
        // 判空工具类，，第二个参数抛空异常信息
        CommonUtil.ckeckAugrmentIsNull(userId, "暂无登陆信息");
        User userById = userService.getUserById(userId);
        return ResultBody.success(userById);
    }
}
