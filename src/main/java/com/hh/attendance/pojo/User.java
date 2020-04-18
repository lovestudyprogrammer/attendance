package com.hh.attendance.pojo;

import com.hh.attendance.enums.UserTypeEnum;
import lombok.Data;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: 学生用户实体
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/04/12 20:47
 **/
@Data
public class User {

    private Integer id;
    private String userName;
    private String password;
    private String name;
    private String phone;
    /**
     * true 男
     * false 女
     */
    private boolean sex;
    /**
     * @see UserTypeEnum
     */
    private Integer type;
    private Integer sno;
}
