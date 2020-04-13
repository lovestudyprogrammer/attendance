package com.hh.attendance.service;

import com.hh.attendance.pojo.ClassMdUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MdUserService {

    ClassMdUser getMdUserById(@RequestParam("id") Integer id);

    int addMdUser(@RequestBody ClassMdUser record);

    int updateById(@RequestBody ClassMdUser record);

    int deleteById(@RequestParam("id") Integer id);



}
