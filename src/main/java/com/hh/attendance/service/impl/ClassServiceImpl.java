package com.hh.attendance.service.impl;

import com.hh.attendance.dao.ClassMapper;
import com.hh.attendance.pojo.Class;
import com.hh.attendance.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;


    @Override
    public Class getClassById(Integer classId) {
        return classMapper.getClassById(classId);
    }

    @Override
    public Collection<Class> listByIDs(int... classIds) {
        return classMapper.listByIDs(classIds);
    }

    @Override
    public List getClassList() {
        return classMapper.getClassList();
    }

    @Override
    public int addClass(Class record) {
        return classMapper.insert(record);
    }

    @Override
    public int updateById(Class record) {
        return classMapper.updateById(record);
    }

    @Override
    public int deleteById(Integer classId) {
        return classMapper.deleteById(classId);
    }
}
