package com.hh.attendance.vo;

import com.hh.attendance.pojo.Leave;
import lombok.Data;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description:
 * @FileName: attendance
 * @Author: anthony
 * @Date: 2020/05/01 11:31
 **/
@Data
public class LeaveVo extends Leave {

    private String approvalStateName;
    private String stuName;
    private String className;
    private Integer sno;
}
