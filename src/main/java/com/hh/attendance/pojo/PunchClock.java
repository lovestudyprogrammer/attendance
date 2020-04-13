package com.hh.attendance.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class PunchClock {
    private Integer id;

    private Integer classId;

    private Integer stuId;

    private Integer punchClockTypeId;

    private Date createTime;

    private String remark;

}