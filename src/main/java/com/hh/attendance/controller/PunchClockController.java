package com.hh.attendance.controller;

import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.pojo.PunchClock;
import com.hh.attendance.service.PunchClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/punchClock")
public class PunchClockController {

    @Autowired
    private PunchClockService punchClockService;

    @GetMapping("/getPunchClock")
    public ResultBody getClass(@RequestParam("id") Integer id) {
        PunchClock punchClock= punchClockService.getPunchClockById(id);
        return ResultBody.success(punchClock);
    }

    @PostMapping("/addPunchClock")
    public ResultBody addPunchClock(@RequestBody PunchClock punchClock) {
        int c= punchClockService.addPunchClock(punchClock);
        return ResultBody.success(c);
    }

    @PostMapping("/updatePunchClock")
    public ResultBody updatePunchClock(@RequestBody PunchClock punchClock) {
        int c= punchClockService.updateById(punchClock);
        return ResultBody.success(c);
    }

    @RequestMapping("/delPunchClock")
    public ResultBody delPunchClock(@RequestParam("classId") Integer classId) {
        int i = punchClockService.deleteById(classId);
        return ResultBody.success(i);
    }


}
