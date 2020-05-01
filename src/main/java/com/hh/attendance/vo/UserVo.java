package com.hh.attendance.vo;

import com.hh.attendance.pojo.User;
import lombok.Data;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: UserVo
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/04/13 18:18
 **/
@Data
public class UserVo extends User {
    /**
     * 用户类型名称
     */
    private String typeName;
    /**
     * 班级ID
     */
    private Integer classId;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 确认的密码
     */
    private String password1;

}
