package com.hh.attendance.service;

import com.github.pagehelper.Page;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.pojo.PunchClock;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface PunchClockService {

    PunchClock getPunchClockById(@RequestParam("id") Integer id);

    List<PunchClock> getPunchClockPage(Map<String,Object> searchMap);

    int addPunchClock(@RequestBody PunchClock record);

    int updateById(@RequestBody PunchClock record);

    int deleteById(@RequestParam("id") Integer id);


}
