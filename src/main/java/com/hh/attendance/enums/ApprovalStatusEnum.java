package com.hh.attendance.enums;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description:
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/05/01 11:22
 **/
public enum ApprovalStatusEnum {
    WAIT(0, "审批中"),
    AGREE(1, "已同意"),
    REFUSE(2, "已拒绝"),
    ;


    private int id;
    private String name;

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

    ApprovalStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ApprovalStatusEnum getValue(int id) {
        for (ApprovalStatusEnum value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        return null;
    }
}
