package com.hh.attendance.dao;

import com.hh.attendance.pojo.Leave;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaveMapper {
    int deleteById(Integer id);

    int deleteByStuId(Integer studentId);

    int insert(@Param("vo") Leave record);

    Leave selectById(Integer id);

    int updateById(Leave record);

    List<Leave> getLeavePage(Map<String, Object> searchMap);

    int updateApprovalStatus(@Param("status") int status, @Param("approvalOpinion") String approvalOpinion, @Param("ids") int... ids);
}