package com.hh.attendance.service.impl;

import com.hh.attendance.dao.LeaveMapper;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        record.setApprovalTime(new Date());
        return leaveMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return leaveMapper.deleteById(id);
    }

    @Override
    public List<Leave> getLeavePage(Map<String, Object> searchMap) {
        return leaveMapper.getLeavePage(searchMap);
    }

    @Override
    public void updateApprovalStatus(int status, String approvalOpinion, int... ids) {
        leaveMapper.updateApprovalStatus(status, approvalOpinion, ids);
    }
}
