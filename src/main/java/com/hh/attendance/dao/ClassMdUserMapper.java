package com.hh.attendance.dao;

import com.hh.attendance.pojo.ClassMdUser;
import org.apache.ibatis.annotations.Param;

public interface ClassMdUserMapper {
    int deleteById(Integer id);

    int insert(ClassMdUser record);

    ClassMdUser getClassMdUserById(Integer id);

    int updateById(ClassMdUser record);

    ClassMdUser getByClassId(@Param("classId") Integer classId);

    int updateTeacherByClassIds(@Param("teacherId") int teacherId, @Param("classIds") int... classIds);

}