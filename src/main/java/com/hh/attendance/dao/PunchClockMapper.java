package com.hh.attendance.dao;

import com.hh.attendance.pojo.PunchClock;

import java.util.List;
import java.util.Map;

public interface PunchClockMapper {
    int deleteById(Integer id);

    int insert(PunchClock record);

    List<PunchClock> getPunchClockPage(Map<String, Object> searchMap);

    PunchClock selectById(Integer id);

    int updateById(PunchClock record);

    int deleteByClassId(int classId);

    int deleteByStuId(Integer studentId);
}