package com.hh.attendance.dao;

import com.hh.attendance.pojo.Class;

public interface ClassMapper {
    int deleteById(Integer id);

    int insert(Class record);

    Class getClassById(Integer id);

    int updateById(Class record);

}