package com.hh.attendance.dao;

import com.hh.attendance.pojo.PunchClockType;

import java.util.List;

public interface PunchClockTypeMapper {
    int deleteById(Integer id);

    int insert(PunchClockType record);

    PunchClockType selectById(Integer id);

    List<PunchClockType> getPunchClockTypeList();

    int updateById(PunchClockType record);
}