package com.hh.attendance.service;

import com.hh.attendance.pojo.ClassMdUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

public interface MdUserService {

    ClassMdUser getMdUserById(@RequestParam("id") Integer id);

    Collection<ClassMdUser> listByIds(@RequestParam("ids") int... ids);

    Collection<ClassMdUser> getMdUserByTeaId(@RequestParam("teaId") Integer teaId);

    Collection<ClassMdUser> getMdUserByTeaIds(@RequestParam("teaIds") int... teaIds);

    int addMdUser(@RequestBody ClassMdUser record);

    int deleteById(@RequestParam("id") Integer id);

    void saveClassMdUser(@RequestParam("userId") int userID,
                         @RequestParam("classIds") int... classIds);


}
