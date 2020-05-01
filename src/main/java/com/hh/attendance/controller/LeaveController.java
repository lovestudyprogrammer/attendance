package com.hh.attendance.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.enums.ApprovalStatusEnum;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.Leave;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.ClassService;
import com.hh.attendance.service.LeaveService;
import com.hh.attendance.service.MdUserService;
import com.hh.attendance.service.UserService;
import com.hh.attendance.vo.IdVo;
import com.hh.attendance.vo.LeaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private MdUserService mdUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

    @GetMapping("/getLeave")
    public ResultBody getLeave(@RequestParam("id") Integer id) {
        Leave leave = leaveService.getLeaveById(id);
        return ResultBody.success(leave);
    }

    @PostMapping("/addLeave")
    public ResultBody addLeave(@RequestBody Leave leave) {
        //学生请假，学生用户
        Integer userId = SessionHolder.getUserId();
        ClassMdUser mdUser = mdUserService.getMdUserById(userId);
        leave.setClassId(mdUser.getClassId());
        leave.setApprovalState(ApprovalStatusEnum.WAIT.getId());
        leave.setStuId(userId);
        int c = leaveService.addLeave(leave);
        return ResultBody.success(c);
    }

    @PostMapping("/updateLeave")
    public ResultBody updateLeave(@RequestBody Leave leave) {
        int c = leaveService.updateById(leave);
        return ResultBody.success(c);
    }

    @PostMapping("/agreeLeave")
    public ResultBody agreeLeave(@RequestBody IdVo idVo) {
        int[] ids = idVo.getIds();
        if (ids == null) {
            return ResultBody.failed("请选择要同意的是数据");
        }
        leaveService.updateApprovalStatus(ApprovalStatusEnum.AGREE.getId(), "无", ids);
        return ResultBody.success("");
    }

    @PostMapping("/refuseLeave")
    public ResultBody refuseLeave(@RequestBody IdVo idVo) {
        int[] ids = idVo.getIds();
        String approvalOpinion = idVo.getApprovalOpinion();
        CommonUtil.ckeckAugrmentIsNull(ids, "请选择要拒绝的是数据");
        CommonUtil.ckeckAugrmentIsNull(approvalOpinion, "拒绝理由不能为空");
        leaveService.updateApprovalStatus(ApprovalStatusEnum.REFUSE.getId(), approvalOpinion, ids);
        return ResultBody.success("");
    }

    @RequestMapping("/delLeave")
    public ResultBody delLeave(@RequestParam("id") Integer id) {
        int i = leaveService.deleteById(id);
        return ResultBody.success(i);
    }

    @RequestMapping("/getLeavePage")
    public ResultBody getLeavePage(Integer approvalStatus, int page, int size) {
        User user = SessionHolder.getUser();
        Integer type = user.getType();
        Map<String, Object> searchMap = new HashMap<>();
        if (type == UserTypeEnum.TEACHER.getId()) {
            if (approvalStatus != null) {
                searchMap.put("approvalState", approvalStatus);
            }
            Collection<ClassMdUser> mdUserByTeaId = mdUserService.getMdUserByTeaId(user.getId());
            searchMap.put("classIds", mdUserByTeaId.stream().map(ClassMdUser::getClassId).collect(Collectors.toList()));
        } else if (type == UserTypeEnum.STUDENT.getId()) {
            searchMap.put("studentId", user.getId());
        } else {
            return ResultBody.success("");
        }
        List<LeaveVo> leaveVos = new ArrayList<>();
        PageInfo<LeaveVo> leavePage = new PageInfo<>(leaveVos);
        PageHelper.startPage(page, size, true);
        List<Leave> pageList = leaveService.getLeavePage(searchMap);
        PageInfo<Leave> leavePageInfo = new PageInfo<>(pageList);
        BeanUtils.copyProperties(leavePageInfo, leavePage);
        if (!pageList.isEmpty()) {
            int[] classIDs = pageList.stream().mapToInt(Leave::getClassId).toArray();
            Collection<Class> classeList = classService.listByIDs(classIDs);
            Map<Integer, Class> classMap = classeList.stream().collect(Collectors.toMap(Class::getId, Function.identity()));
            for (Leave leave : pageList) {
                LeaveVo leaveVo = new LeaveVo();
                BeanUtils.copyProperties(leave, leaveVo);
                ApprovalStatusEnum approvalStatusEnum = ApprovalStatusEnum.getValue(leaveVo.getApprovalState());
                if (approvalStatusEnum != null) {
                    leaveVo.setApprovalStateName(approvalStatusEnum.getName());
                }
                User student = userService.getUserById(leaveVo.getStuId());
                leaveVo.setStuName(student.getName());
                leaveVo.setSno(student.getSno());
                leaveVo.setClassName(classMap.get(leaveVo.getClassId()) == null ? "" : classMap.get(leaveVo.getClassId()).getClassName());
                leaveVos.add(leaveVo);
            }
        }
        leavePage.setList(leaveVos);
        return ResultBody.success(leavePage);
    }
}
