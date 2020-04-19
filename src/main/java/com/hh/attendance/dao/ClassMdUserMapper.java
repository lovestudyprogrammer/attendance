package com.hh.attendance.dao;

import com.hh.attendance.pojo.ClassMdUser;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface ClassMdUserMapper {
    int deleteById(Integer id);

    int insert(ClassMdUser record);

    ClassMdUser getClassMdUserById(@Param("id") Integer id);

    Collection<ClassMdUser> getByClassId(@Param("classId") Integer classId);

    int updateTeacherByClassIds(@Param("teacherId") int teacherId, @Param("classIds") int... classIds);

    int updateStudentClassById(@Param("studentId") int studentId, @Param("classId") int classId);

}