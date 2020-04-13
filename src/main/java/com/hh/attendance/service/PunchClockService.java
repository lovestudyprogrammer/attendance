package com.hh.attendance.service;

import com.hh.attendance.pojo.PunchClock;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PunchClockService {

    PunchClock getPunchClockById(@RequestParam("id") Integer id);

    int addPunchClock(@RequestBody PunchClock record);

    int updateById(@RequestBody PunchClock record);

    int deleteById(@RequestParam("id") Integer id);


}
