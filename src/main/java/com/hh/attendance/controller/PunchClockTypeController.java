package com.hh.attendance.controller;

import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.pojo.PunchClockType;
import com.hh.attendance.service.PunchClockTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance/punchClockType")
public class PunchClockTypeController {

    @Autowired
    private PunchClockTypeService punchClockTypeService;

    @GetMapping("/getPunchClockType")
    public ResultBody getPunchClockType(@RequestParam("classId") Integer id) {
        PunchClockType punchClockType= punchClockTypeService.getPunchClockTypeById(id);
        return ResultBody.success(punchClockType);
    }

    @PostMapping("/addPunchClockType")
    public ResultBody addPunchClockType(@RequestBody PunchClockType punchClockType) {
        int c= punchClockTypeService.addPunchClockType(punchClockType);
        return ResultBody.success(c);
    }

    @PostMapping("/updatePunchClockType")
    public ResultBody updatePunchClockType(@RequestBody PunchClockType punchClockType) {
        int c= punchClockTypeService.updateById(punchClockType);
        return ResultBody.success(c);
    }

    @RequestMapping("/delPunchClockType")
    public ResultBody delPunchClockType(@RequestParam("classId") Integer id) {
        int i = punchClockTypeService.deleteById(id);
        return ResultBody.success(i);
    }

}
