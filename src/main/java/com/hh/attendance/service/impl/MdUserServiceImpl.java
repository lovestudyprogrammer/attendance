package com.hh.attendance.service.impl;

import com.hh.attendance.dao.ClassMdUserMapper;
import com.hh.attendance.dao.UserMapper;
import com.hh.attendance.enums.UserTypeEnum;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.pojo.User;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public ClassMdUser getMdUserByTeaId(Integer teaId) {
        return classMdUserMapper.getMdUserByTeaId(teaId);
    }

    @Override
    public int addMdUser(ClassMdUser record) {
        return classMdUserMapper.insert(record);
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
                    ClassMdUser classMdUserById = classMdUserMapper.getClassMdUserById(userID);
                    int classId = classIds[0];
                    if (classMdUserById != null) {
                        classMdUserMapper.updateStudentClassById(userID, classId);
                    } else {
                        Collection<ClassMdUser> byClassId = classMdUserMapper.getByClassId(classId);
                        ClassMdUser classMdUser = new ClassMdUser();
                        classMdUser.setStudentId(userID);
                        classMdUser.setClassId(classId);
                        if (byClassId.isEmpty()) {
                            // 目前班级还没有老师管，随便设一个
                            classMdUser.setTeacherId(0);
                        } else {
                            ClassMdUser mdUser = byClassId.iterator().next();
                            classMdUser.setTeacherId(mdUser.getTeacherId());
                        }
                        addMdUser(classMdUser);

                    }
                } else if (type == UserTypeEnum.TEACHER.getId()) {
                    classMdUserMapper.updateTeacherByClassIds(userID, classIds);
                }
            }
        }
    }
}
