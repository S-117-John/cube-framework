package com.cube.kiosk.modules.system.service;

import com.cube.kiosk.modules.system.entity.DeptDO;
import com.cube.kiosk.modules.system.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;

    public DeptDO save(DeptDO deptDO){
        return deptRepository.save(deptDO);
    }

    public List<DeptDO> list(){
        return deptRepository.findAll();
    }

    public void delete(String id){
        deptRepository.deleteById(id);
    }

    public DeptDO getOne(String id){
        return deptRepository.getOne(id);
    }
}
