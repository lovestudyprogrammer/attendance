package com.hh.attendance.controller;

import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.Constant;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/getClass")
    public ResultBody getClass(@RequestParam("classId") Integer classId) {
        Class className= classService.getClassById(classId);
        return ResultBody.success(className);
    }

    @GetMapping("/getClassList")
    public ResultBody getClassList() {
        List<Class> classList= classService.getClassList();
        return ResultBody.success(classList);
    }

    @PostMapping("/addClass")
    public ResultBody addClass(@RequestBody Class clas) {
        int c= classService.addClass(clas);
        return ResultBody.success(c);
    }

    @PostMapping("/updateClass")
    public ResultBody updateClass(@RequestBody Class clas) {
        int c= classService.updateById(clas);
        return ResultBody.success(c);
    }

    @RequestMapping("/delClass")
    public ResultBody delClass(@RequestParam("classId") Integer classId) {
        int i = classService.deleteById(classId);
        return ResultBody.success(i);
    }


}
