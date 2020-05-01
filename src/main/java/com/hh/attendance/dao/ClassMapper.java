package com.hh.attendance.dao;

import com.hh.attendance.pojo.Class;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ClassMapper {
    int deleteById(Integer id);

    int insert(Class record);

    List getClassList();

    Collection<Class> listByIDs(@Param("ids") int... ids);

    Class getClassById(Integer id);

    int updateById(Class record);

}