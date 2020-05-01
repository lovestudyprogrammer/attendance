package com.hh.attendance.controller;

import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.service.ClassService;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/attendance/class")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private MdUserService mdUserService;

    @GetMapping("/getClass")
    public ResultBody getClass(@RequestParam("classId") Integer classId) {
        Class className = classService.getClassById(classId);
        return ResultBody.success(className);
    }

    @GetMapping("/getClassList")
    public ResultBody getClassList() {
        Collection<ClassMdUser> mdUserByTeaId = mdUserService.getMdUserByTeaId(SessionHolder.getUserId());
        Collection<Class> classArrayList = new ArrayList<>();
        if (!mdUserByTeaId.isEmpty()) {
            classArrayList = classService.listByIDs(mdUserByTeaId.stream().mapToInt(ClassMdUser::getClassId).toArray());
        }
        return ResultBody.success(classArrayList);
    }

    @GetMapping("/getAllClassList")
    public ResultBody getAllClassList() {
        return ResultBody.success(classService.getClassList());
    }

    @PostMapping("/addClass")
    public ResultBody addClass(@RequestBody Class clas) {
        int c = classService.addClass(clas);
        return ResultBody.success(c);
    }

    @PostMapping("/updateClass")
    public ResultBody updateClass(@RequestBody Class clas) {
        int c = classService.updateById(clas);
        return ResultBody.success(c);
    }

    @RequestMapping("/delClass")
    public ResultBody delClass(@RequestParam("classId") Integer classId) {
        int i = classService.deleteById(classId);
        return ResultBody.success(i);
    }


}
