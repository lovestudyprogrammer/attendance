package com.hh.attendance.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Leave {
    private Integer id;

    private Integer stuId;

    private Integer classId;

    private Date leaveStart;

    private Date leaveEnd;

    private Date creatTime;

    private String cause;

    private Integer approvalState;

    private String approvalOpinion;

    private Date approvalTime;


}