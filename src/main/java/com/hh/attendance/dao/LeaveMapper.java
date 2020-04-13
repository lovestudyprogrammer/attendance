package com.hh.attendance.dao;

import com.hh.attendance.pojo.Leave;

public interface LeaveMapper {
    int deleteById(Integer id);

    int insert(Leave record);

    Leave selectById(Integer id);

    int updateById(Leave record);
}