package com.cube.kiosk.modules.security.service;

import com.cube.kiosk.modules.security.model.HardWareConfigDO;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "HardWare")
public class HardWareRegisterService {

    @Autowired
    private HardWareRepository hardWareRepository;


    public List<HardWareConfigDO> getAllHardWare(){
        List<HardWareConfigDO> hardWareList = hardWareRepository.findAll();
        return hardWareList;
    }

    public HardWareConfigDO save(HardWareConfigDO hardWareConfigDO){
        return hardWareRepository.save(hardWareConfigDO);
    }

}
