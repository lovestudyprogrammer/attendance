package com.hh.attendance.dao;

import com.hh.attendance.pojo.ClassMdUser;

public interface ClassMdUserMapper {
    int deleteById(Integer id);

    int insert(ClassMdUser record);

    ClassMdUser getClassMdUserById(Integer id);

    int updateById(ClassMdUser record);

}