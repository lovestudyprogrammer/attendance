package com.hh.attendance.service.impl;

import com.hh.attendance.dao.PunchClockTypeMapper;
import com.hh.attendance.pojo.PunchClockType;
import com.hh.attendance.service.PunchClockTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PunchClockTypeServiceImpl implements PunchClockTypeService {

    @Autowired
    private PunchClockTypeMapper punchClockTypeMapper;

    @Override
    public List<PunchClockType> getPunchClockTypeList() {
        return punchClockTypeMapper.getPunchClockTypeList();
    }

    @Override
    public PunchClockType getPunchClockTypeById(Integer id) {
        return punchClockTypeMapper.selectById(id);
    }

    @Override
    public int addPunchClockType(PunchClockType record) {
        return punchClockTypeMapper.insert(record);
    }

    @Override
    public int updateById(PunchClockType record) {
        return punchClockTypeMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return punchClockTypeMapper.deleteById(id);
    }
}
