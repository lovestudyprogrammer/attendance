package com.hh.attendance.controller;

import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.Constant;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.UserService;
import com.hh.attendance.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: UserController
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/04/12 20:44
 **/
@RestController
@RequestMapping("/attendance/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public ResultBody test() {
        User user = new User();
        user.setId(1);
        user.setName("");
        // 存session可以这样使用
        SessionHolder.put(Constant.USER_SESSION, user);
        // 获取session
        Integer userId = SessionHolder.getUserId();
        // 判空工具类，，第二个参数抛空异常信息
        CommonUtil.ckeckAugrmentIsNull(userId, "暂无登陆信息");
        User userById = userService.getUserById(userId);
        return ResultBody.success(userById);
    }

    @GetMapping("/getUser")
    public ResultBody getUser() {
        Integer userId = SessionHolder.getUserId();
        CommonUtil.ckeckAugrmentIsNull(userId, "请先登录");
        User user = userService.getUserById(userId);
        return ResultBody.success(user);
    }

    @PostMapping("/login")
    public ResultBody login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        CommonUtil.ckeckAugrmentIsNull(userName, "请输入用户昵称");
        CommonUtil.ckeckAugrmentIsNull(password, "请输入密码");
        User user = userService.getUserByUserNameAndPassword(userName, password);
        if (user == null) {
            return ResultBody.failed("用户名或密码有误，请重新输入");
        }
        SessionHolder.put(Constant.USER_SESSION, user);
        return ResultBody.success(user);
    }

    @PostMapping("/register")
    public ResultBody register(@RequestBody User user) {
        checkUserAugrment(user);
        int userId = userService.addUser(user);
        // TODO: 2020/4/13 考虑注册之后直接登录，不再需要跳转到登陆页面
        return ResultBody.success(userId);
    }

    @GetMapping("/getUserByType")
    public ResultBody getUserByType(@RequestParam("type") int type) {
        UserTypeEnum userTypeEnum = UserTypeEnum.getValue(type);
        Collection<UserVo> userVoList = new ArrayList<>();
        if (userTypeEnum != null) {
            Collection<User> userList = userService.listByUserType(userTypeEnum.getId());
            if (!userList.isEmpty()) {
                for (User user : userList) {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(user, userVo);
                    userVo.setTypeName(userTypeEnum.getName());
                    userVoList.add(userVo);
                }
            }
        }
        return ResultBody.success(userVoList);
    }

    private void checkUserAugrment(User user) {
        CommonUtil.ckeckAugrmentIsNull(user.getUserName(), "用户昵称不能为空");
        CommonUtil.ckeckAugrmentIsNull(user.getPassword(), "密码不能为空");
        CommonUtil.ckeckAugrmentIsNull(user.getName(), "请输入真实姓名");
        CommonUtil.ckeckAugrmentIsNull(user.getPhone(), "手机号码不能为空");
        CommonUtil.ckeckAugrmentIsNull(user.isSex(), "性别不能为空");
        CommonUtil.ckeckAugrmentIsNull(user.getType(), "请选择类型");
    }
}
