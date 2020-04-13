package com.hh.attendance.controller;

import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/getLeave")
    public ResultBody getLeave(@RequestParam("id") Integer id) {
        Leave leave= leaveService.getLeaveById(id);
        return ResultBody.success(leave);
    }

    @PostMapping("/addLeave")
    public ResultBody addLeave(@RequestBody Leave leave) {
        int c= leaveService.addLeave(leave);
        return ResultBody.success(c);
    }

    @PostMapping("/updateLeave")
    public ResultBody updateLeave(@RequestBody Leave leave) {
        int c= leaveService.updateById(leave);
        return ResultBody.success(c);
    }

    @RequestMapping("/delLeave")
    public ResultBody delLeave(@RequestParam("id") Integer id) {
        int i = leaveService.deleteById(id);
        return ResultBody.success(i);
    }
}
