package com.hh.attendance.controller;

import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/mdUser")
public class ClassMdUserController {


    @Autowired
    private MdUserService mdUserService;

    @GetMapping("/getClassMdUserById")
    public ResultBody getMdUser(@RequestParam("id") Integer id) {
        ClassMdUser mdUser = mdUserService.getMdUserById(id);
        return ResultBody.success(mdUser);
    }

    @PostMapping("/addMdUser")
    public ResultBody addMdUser(@RequestBody ClassMdUser mdUser) {
        int c = mdUserService.addMdUser(mdUser);
        return ResultBody.success(c);
    }

    @RequestMapping("/delMdUser")
    public ResultBody delMdUser(@RequestParam("id") Integer id) {
        int i = mdUserService.deleteById(id);
        return ResultBody.success(i);
    }


}
