package com.hh.attendance.service.impl;

import com.hh.attendance.dao.PunchClockMapper;
import com.hh.attendance.pojo.PunchClock;
import com.hh.attendance.service.PunchClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PunchClockServiceImpl implements PunchClockService {

    @Autowired
    private PunchClockMapper punchClockMapper;

    @Override
    public PunchClock getPunchClockById(Integer id) {
        return punchClockMapper.selectById(id);
    }

    @Override
    public int addPunchClock(PunchClock record) {
        return punchClockMapper.insert(record);
    }

    @Override
    public int updateById(PunchClock record) {
        record.setCreateTime(new Date());
        return punchClockMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return punchClockMapper.deleteById(id);
    }
}
