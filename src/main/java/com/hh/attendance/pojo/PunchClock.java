package com.hh.attendance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PunchClock {
    private Integer id;

    private Integer classId;

    private Integer stuId;

    private Integer punchClockTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String remark;

}