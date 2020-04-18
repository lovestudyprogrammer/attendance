package com.hh.attendance.service.impl;

import com.hh.attendance.commons.CommonUtil;
import com.hh.attendance.dao.ClassMdUserMapper;
import com.hh.attendance.dao.UserMapper;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdUserServiceImpl implements MdUserService {

    @Autowired
    private ClassMdUserMapper classMdUserMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public ClassMdUser getMdUserById(Integer id) {
        return classMdUserMapper.getClassMdUserById(id);
    }

    @Override
    public int addMdUser(ClassMdUser record) {
        return classMdUserMapper.insert(record);
    }

    @Override
    public int updateById(ClassMdUser record) {
        return classMdUserMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer id) {
        return classMdUserMapper.deleteById(id);
    }

    @Override
    public void saveClassMdUser(int userID, int... classIds) {
        if (classIds != null && classIds.length > 0) {
            User user = userMapper.getUserById(userID);
            if (user != null) {
                Integer type = user.getType();
                if (type == UserTypeEnum.STUDENT.getId()) {
                    int classId = classIds[0];
                    ClassMdUser byClassId = classMdUserMapper.getByClassId(classId);
                    CommonUtil.ckeckAugrmentIsNull(byClassId, "没有找到班级对应的老师");
                    ClassMdUser classMdUser = new ClassMdUser();
                    classMdUser.setStudentId(userID);
                    classMdUser.setClassId(classId);
                    classMdUser.setTeacherId(byClassId.getTeacherId());
                    addMdUser(classMdUser);
                } else if (type == UserTypeEnum.TEACHER.getId()) {
                    classMdUserMapper.updateTeacherByClassIds(userID, classIds);
                }
            }
        }
    }
}
