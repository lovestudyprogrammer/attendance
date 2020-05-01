package com.hh.attendance.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description:
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/05/01 16:13
 **/
@Data
public class IdVo implements Serializable {
    private int[] ids;
    private int[] userIds;
    private int[] classIds;
    private String approvalOpinion;
}
