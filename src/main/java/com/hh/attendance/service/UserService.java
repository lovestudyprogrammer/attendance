package com.hh.attendance.service;

import com.github.pagehelper.Page;
import com.hh.attendance.pojo.User;
import com.hh.attendance.vo.UserVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

public interface UserService {
    User getUserById(@RequestParam("userId") Integer userId);

    User getUserByUserNameAndPassword(@RequestParam("username") String userName, @RequestParam("password") String password);

    int addUser(@RequestBody UserVo userVo);

    /**
     * 根据用户类型查询user
     *
     * @param type
     * @return
     * @see com.hh.attendance.enums.UserTypeEnum
     */
    Collection<User> listByUserType(@RequestParam("type") int type);

    void updateUserPassword(@RequestBody User user);

    /***
     * 分页查询学生列表
     * @param page
     * @param size
     * @return
     */
    Page<User> findStuPage(int page, int size);

    /***
     * 分页查询老师列表
     * @param page
     * @param size
     * @return
     */
    Page<User> findTeaPage(int page, int size);


    int updateById(@RequestBody User record);

    int deleteById(@RequestParam("id") Integer id);
}
