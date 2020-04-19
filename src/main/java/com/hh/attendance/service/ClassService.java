package com.hh.attendance.service;

import com.hh.attendance.pojo.Class;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClassService {

    Class getClassById(@RequestParam("classId") Integer classId);

    List getClassList();

    int addClass(@RequestBody Class record);

    int updateById(@RequestBody Class record);

    int deleteById(@RequestParam("classId") Integer classId);

}
