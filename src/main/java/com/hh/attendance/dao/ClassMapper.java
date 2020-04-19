package com.hh.attendance.dao;

import com.hh.attendance.pojo.Class;

import java.util.List;

public interface ClassMapper {
    int deleteById(Integer id);

    int insert(Class record);

    List getClassList();

    Class getClassById(Integer id);

    int updateById(Class record);

}