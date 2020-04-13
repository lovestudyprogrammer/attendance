package com.hh.attendance.dao;

import com.hh.attendance.pojo.PunchClockType;

public interface PunchClockTypeMapper {
    int deleteById(Integer id);

    int insert(PunchClockType record);

    PunchClockType selectById(Integer id);

    int updateById(PunchClockType record);
}