package com.hh.attendance.controller;

import com.hh.attendance.commons.*;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.UserService;
import com.hh.attendance.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    public ResultBody register(@RequestBody UserVo userVo) {
        checkUserAugrment(userVo);
        if (userVo.getType() == UserTypeEnum.STUDENT.getId()) {
            CommonUtil.ckeckAugrmentIsNull(userVo.getClassId(), "请选择所在班级");
        }
        if (!userVo.getPassword().equals(userVo.getPassword1())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        int userId = userService.addUser(userVo);
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

    @PostMapping("/updateUser")
    public ResultBody updateUser(@RequestBody User user) {
        checkUserAugrment(user);
        int c = userService.updateById(user);
        return ResultBody.success(c);
    }
    /***
     * 分页查询学生列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/stu/{page}/{size}" )
    public ResultBody findStuPage(@PathVariable  int page, @PathVariable  int size){
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        if (type==0) {
            Page<User> pageList = userService.findStuPage(page, size);
            PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
            return ResultBody.success(pageResult);
        }else {
            return ResultBody.success("");
        }
    }

    /***
     * 分页查询老师列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/tea/{page}/{size}" )
        public ResultBody findTeaPage(@PathVariable  int page, @PathVariable  int size){
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        if (type==0){
            Page<User> pageList = userService.findTeaPage(page, size);
            PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
            return ResultBody.success(pageResult);
        }else {
            return ResultBody.success("");
        }
    }



    @PostMapping("/findPassword")
    public ResultBody findPassword(@RequestBody UserVo userVo) {
        CommonUtil.ckeckAugrmentIsNull(userVo.getName(), "姓名不能为空");
        CommonUtil.ckeckAugrmentIsNull(userVo.getPhone(), "手机号不能为空");
        CommonUtil.ckeckAugrmentIsNull(userVo.getPassword(), "请输入新的密码");
        CommonUtil.ckeckAugrmentIsNull(userVo.getPassword1(), "请再次确认新的密码");
        if (!userVo.getPassword().equals(userVo.getPassword1())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        userService.updateUserPassword(userVo);
        return ResultBody.success("");
    }

    @RequestMapping("/delUser")
    public ResultBody delUser(@RequestParam("id") Integer id) {
        int i = userService.deleteById(id);
        return ResultBody.success(i);
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
