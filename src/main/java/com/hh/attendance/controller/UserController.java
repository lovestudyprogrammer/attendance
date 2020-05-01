package com.hh.attendance.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.Constant;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.ClassService;
import com.hh.attendance.service.MdUserService;
import com.hh.attendance.service.UserService;
import com.hh.attendance.vo.IdVo;
import com.hh.attendance.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private MdUserService mdUserService;
    @Autowired
    private ClassService classService;

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
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        if (userVo.getType() == UserTypeEnum.STUDENT.getId()) {
            ClassMdUser mdUserById = mdUserService.getMdUserById(userId);
            if (mdUserById != null) {
                Class classById = classService.getClassById(mdUserById.getClassId());
                if (classById != null) {
                    userVo.setClassName(classById.getClassName());
                }
            }
        } else if (userVo.getType() == UserTypeEnum.TEACHER.getId()) {
            Collection<ClassMdUser> mdUserByTeaId = mdUserService.getMdUserByTeaId(userId);
            int[] classIds = mdUserByTeaId.stream().mapToInt(ClassMdUser::getClassId).distinct().toArray();
            if (classIds.length > 0) {
                Collection<Class> classes = classService.listByIDs(classIds);
                StringBuffer stringBuffer = new StringBuffer();
                for (Class aClass : classes) {
                    if (StringUtils.isEmpty(stringBuffer.toString())) {
                        stringBuffer.append(aClass.getClassName());
                    } else {
                        stringBuffer.append(",").append(aClass.getClassName());
                    }
                }
                userVo.setClassName(stringBuffer.toString());
            }
        }
        return ResultBody.success(userVo);
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
        userService.updateById(user);
        return ResultBody.success("");
    }

    /***
     * 分页查询学生列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/getStuPage")
    public ResultBody findStuPage(int page, int size) {
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        List<UserVo> userVoList = new ArrayList<>();
        PageInfo<UserVo> userPage = new PageInfo<>(userVoList);
        if (type == UserTypeEnum.ADMIN.getId()) {
            PageHelper.startPage(page, size, true);
            Collection<User> userList = userService.listByUserType(UserTypeEnum.STUDENT.getId());
            PageInfo<User> userPageInfo = new PageInfo<>((List<User>) userList);
            BeanUtils.copyProperties(userPageInfo, userPage);
            if (!userList.isEmpty()) {
                int[] stuIDs = userList.stream().mapToInt(User::getId).toArray();
                Collection<ClassMdUser> classMdUsers = mdUserService.listByIds(stuIDs);
                Map<Integer, ClassMdUser> classMdUserMap = classMdUsers.stream().collect(Collectors.toMap(ClassMdUser::getStudentId, Function.identity()));
                int[] classIds = classMdUsers.stream().mapToInt(ClassMdUser::getClassId).distinct().toArray();
                Map<Integer, String> classMap = new HashMap<>();
                if (classIds.length > 0) {
                    Collection<Class> classeList = classService.listByIDs(classIds);
                    classMap = classeList.stream().collect(Collectors.toMap(Class::getId, Class::getClassName));
                }
                for (User user1 : userList) {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(user1, userVo);
                    ClassMdUser classMdUser = classMdUserMap.get(userVo.getId());
                    if (classMdUser != null) {
                        userVo.setClassId(classMdUser.getClassId());
                        userVo.setClassName(classMap.get(classMdUser.getClassId()));
                    }
                    userVoList.add(userVo);
                }
            }
        }
        userPage.setList(userVoList);
        return ResultBody.success(userPage);
    }

    /***
     * 分页查询老师列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/getTeaPage")
    public ResultBody findTeaPage(int page, int size) {
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        List<UserVo> userVoList = new ArrayList<>();
        PageInfo<UserVo> userPage = new PageInfo<>(userVoList);
        if (type == UserTypeEnum.ADMIN.getId()) {
            PageHelper.startPage(page, size, true);
            Collection<User> userList = userService.listByUserType(UserTypeEnum.TEACHER.getId());
            PageInfo<User> userPageInfo = new PageInfo<User>((List<User>) userList);
            BeanUtils.copyProperties(userPageInfo, userPage);
            if (!userList.isEmpty()) {
                int[] teacherIDs = userList.stream().mapToInt(User::getId).toArray();
                Collection<ClassMdUser> mdUserByTeaIds = mdUserService.getMdUserByTeaIds(teacherIDs);
                Map<Integer, List<ClassMdUser>> mdUserByTeaIdMap = mdUserByTeaIds.stream().collect(Collectors.groupingBy(ClassMdUser::getTeacherId));
                int[] classIds = mdUserByTeaIds.stream().mapToInt(ClassMdUser::getClassId).distinct().toArray();
                Map<Integer, String> classMap = new HashMap<>();
                if (classIds.length > 0) {
                    Collection<Class> classeList = classService.listByIDs(classIds);
                    classMap = classeList.stream().collect(Collectors.toMap(Class::getId, Class::getClassName));
                }
                for (User user1 : userList) {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(user1, userVo);
                    List<ClassMdUser> classMdUsers = mdUserByTeaIdMap.get(userVo.getId());
                    StringBuffer stringBuffer = new StringBuffer();
                    if (classMdUsers != null) {
                        List<Integer> classList = classMdUsers.stream().map(ClassMdUser::getClassId).distinct().collect(Collectors.toList());
                        for (Integer classId : classList) {
                            String className = classMap.get(classId);
                            if (className != null) {
                                if (StringUtils.isEmpty(stringBuffer.toString())) {
                                    stringBuffer.append(className);
                                } else {
                                    stringBuffer.append(",").append(className);
                                }
                            }
                        }
                    }
                    userVo.setClassName(stringBuffer.toString());
                    userVoList.add(userVo);
                }
            }
        }
        userPage.setList(userVoList);
        return ResultBody.success(userPage);
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
    public ResultBody delUser(@RequestBody IdVo idVo) {
        int[] userIds = idVo.getUserIds();
        CommonUtil.ckeckAugrmentIsNull(userIds, "请选择删除的用户");
        for (int userId : userIds) {
            userService.deleteById(userId);
        }
        return ResultBody.success("");
    }

    @PostMapping("/settingUserClass")
    public ResultBody settingUserClass(@RequestBody IdVo idVo) {
        int[] userIds = idVo.getUserIds();
        if (userIds == null) {
            return ResultBody.failed("请选择用户");
        }
        int[] classIds = idVo.getClassIds();
        if (userIds == null) {
            return ResultBody.failed("请选择设置的班级");
        }
        for (int userId : userIds) {
            mdUserService.saveClassMdUser(userId, classIds);
        }
        return ResultBody.success("");
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
