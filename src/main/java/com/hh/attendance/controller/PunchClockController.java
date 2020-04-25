package com.hh.attendance.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.attendance.commons.PageResult;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.PunchClock;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.MdUserService;
import com.hh.attendance.service.PunchClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance/punchClock")
public class PunchClockController {

    @Autowired
    private PunchClockService punchClockService;
    @Autowired
    private MdUserService mdUserService;

    /**
     * 学生签到考勤列表
     *
     * @return
     */
    @GetMapping("/getPunchClockPage")
    public ResultBody getPunchClockPage(int page, int size) {
        Map<String, Object> searchMap = new HashMap<>();
        //获取当前用户类型
        User user = SessionHolder.getUser();
        if (user.getType() == 1) {
            ClassMdUser mdUser = mdUserService.getMdUserById(user.getId());
            searchMap.put("classId", mdUser.getClassId());
        } else if (user.getType() == 2) {
            return ResultBody.success("");
        }
        List<PunchClock> pageList = punchClockService.getPunchClockPage(searchMap);
        PageHelper.startPage(page,size,true);
        PageInfo<PunchClock> punchPage = new PageInfo<>(pageList);
        return ResultBody.success(punchPage);
    }

    @GetMapping("/getPunchClock")
    public ResultBody getClass(@RequestParam("id") Integer id) {
        PunchClock punchClock = punchClockService.getPunchClockById(id);
        return ResultBody.success(punchClock);
    }

    /**
     * 学生考勤打卡
     *
     * @param punchClock
     * @return
     */
    @PostMapping("/addPunchClock")
    public ResultBody addPunchClock(@RequestBody PunchClock punchClock) {
        User user = SessionHolder.getUser();
        punchClock.setStuId(user.getId());
        ClassMdUser mdUser = mdUserService.getMdUserById(user.getId());
        punchClock.setClassId(mdUser.getClassId());
        punchClock.setCreateTime(new Date());
        int c = punchClockService.addPunchClock(punchClock);
        return ResultBody.success(c);
    }

    @PostMapping("/updatePunchClock")
    public ResultBody updatePunchClock(@RequestBody PunchClock punchClock) {
        int c = punchClockService.updateById(punchClock);
        return ResultBody.success(c);
    }

    @RequestMapping("/delPunchClock")
    public ResultBody delPunchClock(@RequestParam("classId") Integer classId) {
        int i = punchClockService.deleteById(classId);
        return ResultBody.success(i);
    }


}
