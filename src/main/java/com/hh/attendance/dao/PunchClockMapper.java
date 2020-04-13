package com.hh.attendance.dao;

import com.hh.attendance.pojo.PunchClock;

public interface PunchClockMapper {
    int deleteById(Integer id);

    int insert(PunchClock record);

    PunchClock selectById(Integer id);

    int updateById(PunchClock record);
}