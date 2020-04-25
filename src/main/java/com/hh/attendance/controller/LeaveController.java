package com.hh.attendance.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.attendance.commons.PageResult;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.pojo.PunchClock;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.LeaveService;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private MdUserService mdUserService;

    @GetMapping("/getLeave")
    public ResultBody getLeave(@RequestParam("id") Integer id) {
        Leave leave = leaveService.getLeaveById(id);
        return ResultBody.success(leave);
    }

    @PostMapping("/addLeave")
    public ResultBody addLeave(@RequestBody Leave leave) {
        //学生请假，学生用户
        User user = SessionHolder.getUser();
        ClassMdUser mdUser = mdUserService.getMdUserById(user.getId());
        leave.setClassId(mdUser.getClassId());
        leave.setApprovalState(0);
        leave.setStuId(user.getId());
        int c = leaveService.addLeave(leave);
        return ResultBody.success(c);
    }

    @PostMapping("/updateLeave")
    public ResultBody updateLeave(@RequestBody Leave leave) {
        int c = leaveService.updateById(leave);
        return ResultBody.success(c);
    }

    @RequestMapping("/delLeave")
    public ResultBody delLeave(@RequestParam("id") Integer id) {
        int i = leaveService.deleteById(id);
        return ResultBody.success(i);
    }

    @RequestMapping("/getLeavePage")
    public ResultBody getLeavePage(Map<String, Object> searchMap, int page, int size) {
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        if (type == 2) {
            ClassMdUser mdUser = mdUserService.getMdUserByTeaId(user.getId());
            searchMap.put("classId", mdUser.getClassId());
            List<Leave> pageList = leaveService.getLeavePage(searchMap);
            PageHelper.startPage(page,size,true);
            PageInfo<Leave> leavePage = new PageInfo<>(pageList);
            return ResultBody.success(leavePage);
        } else if (type == 0) {
            List<Leave> pageList = leaveService.getLeavePage(searchMap);
            PageHelper.startPage(page,size,true);
            PageInfo<Leave> leavePage = new PageInfo<>(pageList);
            return ResultBody.success(leavePage);
        } else {
            return ResultBody.success("");
        }
    }
}
