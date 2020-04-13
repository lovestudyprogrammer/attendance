package com.hh.attendance.service;

import com.hh.attendance.pojo.PunchClockType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PunchClockTypeService {

    PunchClockType getPunchClockTypeById(@RequestParam("id") Integer id);

    int addPunchClockType(@RequestBody PunchClockType record);

    int updateById(@RequestBody PunchClockType record);

    int deleteById(@RequestParam("id") Integer id);

}
