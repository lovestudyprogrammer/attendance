package com.hh.attendance.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.commons.ResultBody;
import com.hh.attendance.commons.SessionHolder;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.pojo.*;
import com.hh.attendance.service.*;
import com.hh.attendance.vo.PunchClockVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance/punchClock")
public class PunchClockController {

    @Autowired
    private PunchClockService punchClockService;
    @Autowired
    private MdUserService mdUserService;
    @Autowired
    private PunchClockTypeService punchClockTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

    /**
     * 学生签到考勤列表
     *
     * @return
     */
    @GetMapping("/getPunchClockPage")
    public ResultBody getPunchClockPage(Integer classId, int page, int size) {
        Map<String, Object> searchMap = new HashMap<>();
        //获取当前用户类型
        User user = SessionHolder.getUser();
        if (user.getType() == UserTypeEnum.STUDENT.getId()) {
            ClassMdUser mdUser = mdUserService.getMdUserById(user.getId());
            searchMap.put("classId", mdUser.getClassId());
        } else if (user.getType() == UserTypeEnum.TEACHER.getId()) {
            if (classId == null) {
                Collection<ClassMdUser> mdUserByTeaId = mdUserService.getMdUserByTeaId(user.getId());
                if (!mdUserByTeaId.isEmpty()) {
                    searchMap.put("classIds", mdUserByTeaId.stream().map(ClassMdUser::getClassId).collect(Collectors.toList()));
                }
            } else {
                searchMap.put("classId", classId);
            }
        }
        List<PunchClockVo> punchClockVos = new ArrayList<>();
        PageInfo<PunchClockVo> punchClockVoPageInfo = new PageInfo<>(punchClockVos);
        List<PunchClock> pageList = new ArrayList<>();
        if (!searchMap.isEmpty()) {
            PageHelper.startPage(page, size, true);
            pageList = punchClockService.getPunchClockPage(searchMap);
            PageInfo<PunchClock> punchPage = new PageInfo<>(pageList);
            BeanUtils.copyProperties(punchPage, punchClockVoPageInfo);
        }
        List<PunchClockType> punchClockTypeList = punchClockTypeService.getPunchClockTypeList();
        Map<Integer, String> punchClockTypeMap = punchClockTypeList.stream().collect(Collectors.toMap(PunchClockType::getId, PunchClockType::getTypeName));
        if (!pageList.isEmpty()) {
            int[] classIDs = pageList.stream().mapToInt(PunchClock::getClassId).toArray();
            Collection<Class> classeList = classService.listByIDs(classIDs);
            Map<Integer, Class> classMap = classeList.stream().collect(Collectors.toMap(Class::getId, Function.identity()));
            for (PunchClock punchClock : pageList) {
                PunchClockVo punchClockVo = new PunchClockVo();
                BeanUtils.copyProperties(punchClock, punchClockVo);
                punchClockVo.setPunchClockTypeName(punchClockTypeMap.get(punchClockVo.getPunchClockTypeId()));
                User student = userService.getUserById(punchClockVo.getStuId());
                punchClockVo.setStuName(student.getName());
                punchClockVo.setSno(student.getSno());
                punchClockVo.setClassName(classMap.get(punchClockVo.getClassId()) == null ? "" : classMap.get(punchClockVo.getClassId()).getClassName());
                punchClockVos.add(punchClockVo);
            }
        }
        punchClockVoPageInfo.setList(punchClockVos);
        return ResultBody.success(punchClockVoPageInfo);
    }

    @GetMapping("/getPunchClock")
    public ResultBody getClass(@RequestParam("id") Integer id) {
        PunchClock punchClock = punchClockService.getPunchClockById(id);
        return ResultBody.success(punchClock);
    }

    /**
     * 学生考勤打卡
     *
     * @param punchClock
     * @return
     */
    @PostMapping("/addPunchClock")
    public ResultBody addPunchClock(@RequestBody PunchClock punchClock) {
        User user = SessionHolder.getUser();
        punchClock.setStuId(user.getId());
        ClassMdUser mdUser = mdUserService.getMdUserById(user.getId());
        punchClock.setClassId(mdUser.getClassId());
        punchClock.setCreateTime(new Date());
        int c = punchClockService.addPunchClock(punchClock);
        return ResultBody.success(c);
    }

    @PostMapping("/updatePunchClock")
    public ResultBody updatePunchClock(@RequestBody PunchClock punchClock) {
        int c = punchClockService.updateById(punchClock);
        return ResultBody.success(c);
    }

    @GetMapping("/delPunchClock")
    public ResultBody delPunchClock(@RequestParam("classId") Integer classId) {
        CommonUtil.ckeckAugrmentIsNull(classId, "请选择删除的班级");
        punchClockService.deleteByClassId(classId);
        return ResultBody.success("");
    }


}
