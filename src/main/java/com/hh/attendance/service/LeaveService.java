package com.hh.attendance.service;

import com.hh.attendance.pojo.Leave;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface LeaveService {

    Leave getLeaveById(@RequestParam("id") Integer id);

    int addLeave(@RequestBody Leave record);

    int updateById(@RequestBody Leave record);

    int deleteById(@RequestParam("id") Integer id);

    List<Leave> getLeavePage(Map<String, Object> searchMap);

    void updateApprovalStatus(@RequestParam("status") int status, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("ids") int... ids);


}
