package com.hh.attendance.dao;

import com.github.pagehelper.Page;
import com.hh.attendance.pojo.Leave;

import java.util.List;
import java.util.Map;

public interface LeaveMapper {
    int deleteById(Integer id);

    int insert(Leave record);

    Leave selectById(Integer id);

    int updateById(Leave record);

    List<Leave> getLeavePage(Map<String,Object> searchMap);
}