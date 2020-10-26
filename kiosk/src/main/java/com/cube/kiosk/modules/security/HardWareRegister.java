package com.cube.kiosk.modules.security;

import com.cube.kiosk.modules.security.model.HardWareDO;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import com.cube.kiosk.modules.security.service.HardWareRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HardWareRegister implements ApplicationRunner {

    @Autowired
    private HardWareRegisterService hardWareRegisterService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        hardWareRegisterService.getAllHardWare();
    }


}
