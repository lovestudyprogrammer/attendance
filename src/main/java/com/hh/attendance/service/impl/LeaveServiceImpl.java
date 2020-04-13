package com.hh.attendance.service.impl;

import com.hh.attendance.dao.LeaveMapper;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;


    @Override
    public Leave getLeaveById(Integer id) {
        return leaveMapper.selectById(id);
    }

    @Override
    public int addLeave(Leave record) {
        return leaveMapper.insert(record);
    }

    @Override
    public int updateById(Leave record) {
        return leaveMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return leaveMapper.deleteById(id);
    }
}
