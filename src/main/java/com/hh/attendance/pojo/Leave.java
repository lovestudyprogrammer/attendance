package com.hh.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Leave {
    private Integer id;

    private Integer stuId;

    private Integer classId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveStart;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveEnd;

    private Date creatTime;

    private String cause;

    private Integer approvalState;

    private String approvalOpinion;

    private Date approvalTime;


}