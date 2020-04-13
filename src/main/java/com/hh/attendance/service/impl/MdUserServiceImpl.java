package com.hh.attendance.service.impl;

import com.hh.attendance.dao.ClassMdUserMapper;
import com.hh.attendance.pojo.ClassMdUser;
import com.hh.attendance.service.MdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdUserServiceImpl implements MdUserService {

    @Autowired
    private ClassMdUserMapper classMdUserMapper;


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
}
