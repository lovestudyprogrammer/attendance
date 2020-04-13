package com.hh.attendance.enums;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: UserTypeEnum
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/04/13 13:28
 **/
public enum UserTypeEnum {
    ADMIN(0, "管理员"),
    STUDENT(1, "学生"),
    TEACHER(2, "老师");
    private int id;
    private String name;

    UserTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserTypeEnum getValue(int id) {
        for (UserTypeEnum value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        return null;
    }
}
